package databaseConnections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**A Class for containing Database related Variables and Constants
	 * 
	 * @author asafs94
	 *
	 */
	public class DatabaseVC{
		/**
		 * Returns a String array containing the database Data.
		 * <ul>
		 * <li>databaseData[0] - being the database URL.</li>
		 * <li>databaseData[1] - being the database Driver.</li>
		 * </ul>
		 * Reads that data from the database.txt file that must be located in the classpath!
		 * @return String[2]
		 * @throws IOException
		 */
		public String[] getDatabaseData() throws IOException{
			String[] databaseData= new String[2];
			String databaseURL;
			String databaseDriver;
			InputStream is =this.getClass().getClassLoader().getResourceAsStream("database.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			databaseURL = br.readLine();
			databaseDriver= br.readLine();
			databaseData[0]=databaseURL;
			databaseData[1]= databaseDriver;
			
			return databaseData;
		}
	}
	
