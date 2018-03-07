package vivo.odc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import vivo.odc.dao.Procon0800DAO;

@Path("/procon0800Controller")
public class Procon0800Controller {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ufs")
	public Response getUFsByProcon(){
		List<String>	ufs			= new ArrayList<String>();
		Status 			status 		= Response.Status.OK;
		try{
			ufs = new Procon0800DAO().getUfsProcon();
		}
		catch (Exception e) {
			status = Response.Status.FORBIDDEN;
			e.printStackTrace();
		}
		return Response.status(status).header("Access-Control-Allow-Origin", "*").entity(ufs).build();
	}
}
