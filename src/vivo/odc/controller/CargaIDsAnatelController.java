package vivo.odc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import vivo.odc.dao.RequestDAO;
import vivo.odc.dao.SolicDAO;
import vivo.odc.dao.UtilDAO;
import vivo.odc.util.Constantes;
import vivo.odc.util.Logs;
import vivo.odc.vo.ChamadoVO;
import vivo.odc.vo.RequestVO;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Path("/cargaAnatelAutomidia")
public class CargaIDsAnatelController {
	
	private final 	String 		TOKEN 		= "START_CARGA_ANATEL";
	private UtilDAO				utilDAO		= new UtilDAO();
	
	@POST
	@Path("/insertIdsCargaAnatel")
	public Response startCarga(String json){
		Status status = Response.Status.OK;
		try {			
			if(!utilDAO.isExecutantoToken(this.TOKEN)){
				utilDAO.updateTokenProperty(this.TOKEN, Constantes.EXECUTANDO);
				
				this.insertIdsCargaAnatel(json);				
				this.callCarga();
				
			    utilDAO.updateTokenProperty(this.TOKEN, Constantes.LIBERADO);	
			}else{
				status = Response.Status.CONFLICT;
			}
		} catch (Exception e) {
			status = Response.Status.FORBIDDEN;
			e.printStackTrace();
		}
		return Response.status(status).build();
	}

	@GET // utilizando apenas GET, ou seja, vou apenas ler o recurso
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/callValidarIDs")
	public Response validarIDs(@QueryParam("idCanal") final String json){
		SolicDAO			solicDAO		= new SolicDAO();
		RequestDAO			requestDAO		= new RequestDAO();
		
		List<ChamadoVO> 	newList 		= new ArrayList<ChamadoVO>();
		Status 				status 			= Response.Status.OK;
		try{
			ChamadoVO[] list = new Gson().fromJson(json, ChamadoVO[].class);

			for (ChamadoVO c : list) {
				if(this.validarTextIDs(c.getId()) && solicDAO.existIdInMis(c.getId())){
					c.setExistMis(true);
					c.setUltRequest(new RequestVO());
					c.getUltRequest().setRequest(requestDAO.getUltimoChamado(c.getId()));
					c.getUltRequest().setDtFechamento(requestDAO.getCloseDate(c.getUltRequest().getRequest()));
					if(!requestDAO.isOpen(c.getUltRequest().getRequest())){
						c.getUltRequest().setRequest("");
						c.getUltRequest().setDtFechamento("");	
					}
					newList.add(c);	
				}
				else{
					status = Response.Status.FORBIDDEN;
					break;
				}
			}

			System.out.println("OK");
		}catch (Exception e) {
			status = Response.Status.FORBIDDEN;
			e.printStackTrace();
			Logs.error("validarIDs",e);
		}
		return Response.status(status).header("Access-Control-Allow-Origin", "*").entity(newList).build();	
	}

	private boolean validarTextIDs(String id){
		boolean				retorno			= true;
		try{
			if(id != null){
				String aux = id;					
				if(!aux.contains(".")){
					retorno = false;
				}
				String[] arrayID = aux.split("\\.");
				if(arrayID.length != 2){
					retorno = false;
				}
				int auxInt = Integer.parseInt(arrayID[0]);
				auxInt = Integer.parseInt(arrayID[1]);
				if(arrayID[1].length() != 4){
					retorno = false;
				}
			}	
			else{
				retorno = false;
			}
		
		}catch (Exception e) {
			retorno = false;
			e.printStackTrace();
			Logs.error("validarIDs",e);
		}
		finally{
			System.out.println(retorno);
		}
		return retorno;	
	}

	private void insertIdsCargaAnatel(String json){
		UtilDAO 			utilDAO 		= new UtilDAO();
		
		try{
			ChamadoVO[] list = new Gson().fromJson(json, ChamadoVO[].class);
			utilDAO.deleteOuv_Gr_Segmento();
			utilDAO.insertIdsCargaAnatel(list);
		}catch (Exception e) {
			e.printStackTrace();
			Logs.error("insertIdsCargaAnatel",e);
		}
	}
	
	private void callCarga(){
		Client c = Client.create();
	    WebResource wr = c.resource("http://sv2kprel3:7001/docs/services/iniciarCargaAnatel");
	    wr.get(String.class);
	}
}
