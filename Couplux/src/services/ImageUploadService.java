package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import exceptions.ApplicationException;
import tools.MessageType;
/**
 * Currently not using this class because of Angular compatability problems.
 * 
 * IGNORE THIS CLASS
 * @author asafs
 *
 */
@Path("Upload")
public class ImageUploadService {

	
//########IGNORE THIS CLASS#####################

	/**
	 * A method that makes sure the string passed to it, describing a folder's path, exists.
	 * If it doesn't, created all the directories needed up to the correct folder.
	 * @param pathToFolder
	 */
	private void makeSureFolderExists(String pathToFolder) {
		File folder= new File(pathToFolder);
		if(!folder.exists()) {
			folder.mkdirs();
		}else {
			//If the folder exists do nothing.
		}
		
	}
	
	@SuppressWarnings("resource")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file")FormDataContentDisposition fileDetail) throws ApplicationException {
		// The Collection that will be returned
		Collection<String> strings=new ArrayList<String>();
		String title=null;
		String content=null;
		String messageType= null;
		String fullPath=null;
		//Find the folder to upload the file to:
		String pathToFolder="/Couplux/CouponImages/";
		//Make sure the folder exists, if it doesn't, creates one:
		makeSureFolderExists(pathToFolder);
		//Create a File object using the file name passed, with a .jpg ending:
		String randomName= "file"+ (Math.random()*1000000+1000);
		File file= new File(pathToFolder+randomName+".jpg");
		//Write using the inputStream received through an output stream:
		FileOutputStream os = null;
		try{
			os= new FileOutputStream(file);
			byte[] b= new byte[2048];
			int length;
			os = new FileOutputStream(file);
			while((length =uploadedInputStream.read(b)) != -1) {
				os.write(b, 0, length);
			}
			os.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			//In this case: folder could not be created, so:
			title="Could not upload File";
			content="The folder could not be reached or created in the server.";
			messageType= MessageType.ERROR.name();
		} catch (IOException e) {
			title="Could not upload File";
			content="Could not write file.";
			messageType= MessageType.ERROR.name();
			e.printStackTrace();
		}
	
		
		title="Image Uploaded";
		content="Image uploaded successfully";
		messageType= MessageType.SUCCESS.name();
		fullPath=file.getAbsolutePath();
		
		strings.add(title);
		strings.add(content);
		strings.add(messageType);
		strings.add(fullPath);
		return strings;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getImage( @QueryParam("url") String imageURL) {
		if(imageURL.contains("http")) {
			return Response.status(Status.NOT_FOUND).entity(imageURL).build();
		}
		File file = new File(imageURL);
	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition", "attachment; filename=newfile.jpg");
	    return response.build();
	}
}
