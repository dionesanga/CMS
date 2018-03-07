package vivo.odc.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import vivo.odc.dao.UtilDAO;
import vivo.odc.util.Constantes;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


@Path("/gerarResposta")
public class GerarRespostaController {
	
	private final 	String 		TOKEN 		= "GERAR_RESP_ANATEL";
	private UtilDAO				utilDAO		= new UtilDAO();

	@GET // utilizando apenas GET, ou seja, vou apenas ler o recurso
	@Produces("text/plain") // define qual tipo MIME é retornado para o cliente
	@Path("/exibir")
	public Response exibir(){
		Status status = Response.Status.OK;
		try {
			if(!utilDAO.isExecutantoToken(this.TOKEN)){
				utilDAO.updateTokenProperty(this.TOKEN, Constantes.EXECUTANDO);
				Client c = Client.create();
			    WebResource wr = c.resource("http://sv2kprel3:7001/docs/services/gerarRespostaAnatel");
			    wr.get(String.class);
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
}
