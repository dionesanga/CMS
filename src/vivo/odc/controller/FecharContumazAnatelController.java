package vivo.odc.controller;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import vivo.odc.dao.AutomidiaDAO;
import vivo.odc.util.Constantes;
import vivo.odc.vo.ChamadoVO;

@Path("/FecharContumazAnatel")
public class FecharContumazAnatelController {
	
	private AutomidiaDAO automidiaDAO = new AutomidiaDAO();

	@POST
	@Path("/fecharChamados")
	public Response fecharChamados(List<ChamadoVO> chamados){
		Status status 	= Response.Status.OK;
		String erro		= "";
		try {
			for (ChamadoVO chamado : chamados) {
				if(automidiaDAO.isOpen(chamado.getRequest())){
					System.out.println(chamado.getRequest());
					automidiaDAO.changeStatus(chamado.getRequest(), Constantes.SIT_FECHADO);
					automidiaDAO.fecharChamado(chamado.getRequest(), chamado.getValor());
				}	
			}
			
			/*
			for (ChamadoVO c : chamados) {
				if(!requestDAO.chamadoIsOpen(c.getRequest())){
					status = Response.Status.CONFLICT;
					erro = "Chamado fechado! " + c.getRequest();
					break;
				}	
			}
			*/
		} catch (Exception e) {
			status = Response.Status.FORBIDDEN;
			e.printStackTrace();
		}
		return Response.status(status).entity(erro).build();
	}
}
