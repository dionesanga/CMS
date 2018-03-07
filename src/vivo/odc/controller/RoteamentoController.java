package vivo.odc.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import vivo.odc.dao.roteamento.CanaisDAO;
import vivo.odc.vo.roteamento.CanaisVO;
import vivo.odc.vo.roteamento.RoteamentoVO;

import com.google.gson.Gson;


@Path("/roteamento")
public class RoteamentoController {

	private CanaisDAO			canaisDAO =			new CanaisDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/canais")
	public Response getCanais(){
		return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(canaisDAO.getCanais()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/roteamentos")
	public Response getRoteamentos(@QueryParam("idCanal") final Integer idCanal){
		return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(canaisDAO.getRoteamentos(idCanal)).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateRoteamento")
	public Response updateRoteamento(String json){
		Status status 	= Response.Status.OK;
		String msg = "Gravado com sucesso!";
		try{
			RoteamentoVO r = new Gson().fromJson(json, RoteamentoVO.class);
			canaisDAO.updateRoteamento(r);	
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage(); 
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/removerCanal")
	public Response removerCanal(String json){
		Status status 	= Response.Status.OK;
		String msg = "Removido com sucesso!";
		try{
			CanaisVO c = new Gson().fromJson(json, CanaisVO.class);
			canaisDAO.removerCanal(c);	
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage(); 
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/removerRoteamento")
	public Response removerRoteamento(String json){
		Status status 	= Response.Status.OK;
		String msg = "Removido com sucesso!";
		try{
			RoteamentoVO r = new Gson().fromJson(json, RoteamentoVO.class);
			canaisDAO.removerRoteamento(r);	
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage(); 
		}
		return Response.status(status).entity(msg).build();
	}
	
	@POST
	@Path("/newCanal")
	public Response gravarNewCanal(CanaisVO canal){
		Status status 	= Response.Status.OK;
		String msg = "Gravado com sucesso!";
		try{
			canaisDAO.gravarNewCanal(canal);	
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "Erro ao gravar novo canal! " + e.getMessage(); 
		}
		return Response.status(status).entity(msg).build();
	}
}
