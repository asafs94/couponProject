package databaseConnections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

import exceptions.ApplicationException;
import exceptions.ErrorType;

/**
 * A Connection Pool which will grant connections to the database server, in a
 * controlled way.
 * 
 * @author asafs94
 *
 */
public class ConnectionPool {

	private static ConnectionPool ConPool;
	private HashSet<Connection> cons = new HashSet<Connection>();
	private static final int Max_Cons = 10;
	private DatabaseVC dbVC = new DatabaseVC();

	/**
	 * Singleton CTR. Creates 10 Connections into ConnectionPool.cons.
	 * 
	 * @throws SQLException
	 * @throws ApplicationException
	 * @throws IOException
	 * 
	 */
	private ConnectionPool() throws SQLException, ApplicationException {
		String[] databaseData;
		String databaseURL;
		String databaseDriver;
		try {
			databaseData = dbVC.getDatabaseData();
			databaseURL = databaseData[0];
			databaseDriver = databaseData[1];
		} catch (IOException e1) {
			throw new ApplicationException(e1, ErrorType.GENERIC_SYSTEM_ERROR);
		}

		try {
			Class.forName(databaseDriver);
		} catch (ClassNotFoundException e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		}
		for (int i = 0; i < Max_Cons; i++) {
			Connection con = DriverManager.getConnection(databaseURL);
			cons.add(con);
		}

	}

	/**
	 * Singleton getInstance method.
	 * 
	 * @return ConPool;
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public static ConnectionPool getInstance() throws SQLException, ApplicationException {
		if (ConPool == null) {
			ConPool = new ConnectionPool();
		}

		return ConPool;
	}

	/**
	 * Returns a connection from the Connection Pool, and removes it from the pool.
	 * while the pool is empty, waits.
	 * 
	 * @return Connection con
	 * @throws InterruptedException
	 */
	public synchronized Connection getConnection() throws InterruptedException {
		while (cons.isEmpty()) {
			wait();
		}
		Connection con;
		Iterator<Connection> itr = cons.iterator();
		con = itr.next();
		cons.remove(con);
		return con;
	}

	/**
	 * Returns the Connection con, to the Connection Pool, and notifies, so
	 * getConnection Method can be used.
	 * 
	 * @param con
	 */
	public synchronized void returnConnection(Connection con) {
		// check if con is not null and return:

		cons.add(con);
		notify();

	}

	/**
	 * Removes all Connections
	 * 
	 */
	public synchronized void closeAllConnections() {
		int count = 0;
		// Checks if all connections are in place, before execution.
		while (count < Max_Cons) {
			count = 0;
			loop1: for (Connection connection : cons) {
				if (connection != null) {
					count++;
				} else {
					count = 0;
					break loop1;
				}
			}
		}
		if (count == Max_Cons) {
			cons.clear();
		}
	}
}
