package services;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import elements.Company;
import elements.Customer;
import exceptions.ApplicationException;
import exceptions.ErrorType;
import facades.AdminFacade;
import tools.Message;
import tools.MessageType;

@Path("Private/Admin")
public class AdminServices {

	AdminFacade adminFacade;

	public AdminServices() throws ApplicationException {
		adminFacade = new AdminFacade();

	}

	// ============== COMPANIES METHODS===============
	// ===============================================

	/**
	 * A Method to create a new Company in the database.
	 * 
	 * @param company
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("/Companies")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message createCompany(Company company) throws ApplicationException {

		adminFacade.createCompany(company);

		return new Message("Company Created", "Company " + company.getCompName() + " was registered",
				MessageType.SUCCESS);
	}

	/**
	 * A Method to get a single company from the database.
	 * 
	 * @param company
	 * @return {@link elements.Company Company}
	 * @throws ApplicationException
	 */
	/*
	 * Found this method not useful at the client side. so did not use it. still
	 * wrote it to supply the project's requirements.
	 */
	@Path("Companies/{companyID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Company readCompany(@PathParam("companyID") String ID) throws ApplicationException {

		// <= TODO need to perform a check later that string entered is a number.
		long id = Long.parseLong(ID);

		Company company = adminFacade.readCompany(id);

		return company;
	}

	/**
	 * A Method to delete an existing company from the database.
	 * 
	 * @param ID
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Companies/{companyID}")
	@DELETE
	public Message deleteCompany(@PathParam("companyID") String ID) throws ApplicationException {

		long id = 0;

		// If the parseLong doesn't work, then the client Side did not pass the correct
		// element, and its a system error.
		try {
			id = Long.parseLong(ID);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		}

		adminFacade.deleteCompany(id);
		return new Message("Company Deleted", "Company " + id + " Deleted succesfully", MessageType.SUCCESS);
	}

	/**
	 * A Method to update an existing company in the database.
	 * 
	 * @param ID
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Companies")
	@PUT
	public Message updateCompany(Company company) throws ApplicationException {

		adminFacade.updateCompany(company);

		return new Message("Success", "Company " + company.getCompName() + " updated in the system.",
				MessageType.SUCCESS);
	}

	/**
	 * A Method to read all companies from the database.
	 * 
	 * @param ID
	 * @return {@link java.util.Collection Collection} of {@link elements.Company
	 *         Company}
	 * @throws ApplicationException
	 */
	@Path("Companies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readCompanies() throws ApplicationException {

		Collection<Company> companies = adminFacade.readAllCompanies();

		return Response.status(200).entity(companies).type(MediaType.APPLICATION_JSON).build();
	}

	// ================================END=====================================

	// =================CUSTOME METHODS==========================
	// ===========================================================

	/**
	 * A Method to create a new Customer in the database.
	 * @param customer
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("/Customers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message createCustomer(Customer customer) throws ApplicationException {

		adminFacade.createCustomer(customer);

		return new Message("Customer Created",
				"Customer " + customer.getCustName() + " successfully registered in the system.", MessageType.SUCCESS);
	}
	
	/**
	 * A Method to read a single customer from the database.
	 * @param customer
	 * @return {@link elements.Customer Customer}
	 * @throws ApplicationException
	 */
	/*
	 * Found this method not useful at the client side. so did not use it. still
	 * wrote it to supply the project's requirements.
	 */
	@Path("Customers/{customerID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer readCustomer(@PathParam("customerID") String ID) throws ApplicationException {
		long id = Long.parseLong(ID);

		Customer customer = adminFacade.readCustomer(id);

		return customer;
	}
	
	/**
	 * A Method to delete a single Customer from the database.
	 * @param customer
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Customers/{customerID}")
	@DELETE
	public Message deleteCustomer(@PathParam("customerID") String ID) throws ApplicationException {

		long id = Long.parseLong(ID);

		adminFacade.deleteCustomer(id);
		return new Message("Customer Deleted", "Customer " + id + " deleted from the system.", MessageType.SUCCESS);
	}

	/**
	 * A Method to update a single Customer in the database.
	 * @param customer
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Customers")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Message updateCustomer(Customer customer) throws ApplicationException {

		adminFacade.updateCustomer(customer);

		return new Message("Customer Updated", "Customer " + customer.getCustName() + " updated in the system.",
				MessageType.SUCCESS);
	}

	/**
	 * A Method to read all Customers in the database.
	 * @param customer
	 * @return {@link java.util.Collection Collection} of {@link elements.Customer
	 *         Customer}
	 * @throws ApplicationException
	 */
	@Path("Customers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> readCustomers() throws ApplicationException {
		Collection<Customer> customers = adminFacade.readAllCustomers();

		return customers;
	}

}
