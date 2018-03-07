package vivo.odc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import vivo.odc.util.Constantes;
import vivo.odc.util.FileUtil;
import vivo.odc.util.Logs;
import vivo.odc.vo.ChamadoVO;
import vivo.odc.vo.upload.ArquivoResposta;
import vivo.odc.vo.upload.Registro;

import com.google.gson.Gson;

@Path("/gerarTrimestre")
public class GerarTrimestreController {
	private Map<String,Registro>   		hashZipFile 				= new HashMap<String, Registro>();
	private ArquivoResposta        		arquivoRespVivoBLarga		= null;
	private ArquivoResposta        		arquivoRespVivoFixa  		= null;
	private ArquivoResposta        		arquivoRespVivoMovel 		= null;
	private ArquivoResposta        		arquivoRespVivoTV   		= null;
	
	@POST
	@Path("/executar")
	public Response executar(String json){
		Status status = Response.Status.OK;		
		try{
			//System.out.println(json);
			createObjArquivoRest();
			ChamadoVO[] list = new Gson().fromJson(json, ChamadoVO[].class);
			for (ChamadoVO c : list) {
				if(c.getId() != null && !c.getId().isEmpty() && c.getStatus() != null && !c.getStatus().isEmpty() && c.getResposta() != null && !c.getResposta().isEmpty()){
					String indProcedencia = "S";
					
					
					if(c.getStatus().toUpperCase().equals("IMPROCEDENTE")){
						indProcedencia = "N";	
					}					
					Registro registro = new Registro("RS",c.getResposta(), c.getId(),indProcedencia,"S");
					
					if(c.getResponsavel().toUpperCase().equals("BANDA LARGA")){
						arquivoRespVivoBLarga.getRegistros().getRegistro().add(registro);	
					}
					else if(c.getResponsavel().toUpperCase().equals("FIXA")){
						arquivoRespVivoFixa.getRegistros().getRegistro().add(registro);	
					}
					else if(c.getResponsavel().toUpperCase().equals("TV")){
						arquivoRespVivoTV.getRegistros().getRegistro().add(registro);	
					}
					else if(c.getResponsavel().toUpperCase().equals("MOVEL")){
						arquivoRespVivoMovel.getRegistros().getRegistro().add(registro);	
					}
				}
			}
			createArquivoResposta(arquivoRespVivoBLarga);
			createArquivoResposta(arquivoRespVivoFixa);
			createArquivoResposta(arquivoRespVivoMovel);
			createArquivoResposta(arquivoRespVivoTV);
		}
		catch (Exception e) {
			e.printStackTrace();			
			status = Response.Status.FORBIDDEN;
		}
		return Response.status(status).build();
	}
	
	private void createArquivoResposta(ArquivoResposta arquivoResposta){
		try{
			if (arquivoResposta.getRegistros()!=null && arquivoResposta.getRegistros().getRegistro().size()!=0)
			{
				FileUtil.createFileResposta(arquivoResposta, hashZipFile);
			}
			else
			{
				Logs.warn("NENHUMA RESPOSTA GERADA! "+ arquivoResposta.getInfo_envio().getNome_prestadora());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.warn("Erro",e);
		}
	}
	
	private void createObjArquivoRest(){
		arquivoRespVivoBLarga	= new ArquivoResposta(Constantes.VIVO_BLARGA);
		arquivoRespVivoFixa		= new ArquivoResposta(Constantes.VIVO_FIXA);
		arquivoRespVivoMovel	= new ArquivoResposta(Constantes.VIVO_MOVEL);
		arquivoRespVivoTV		= new ArquivoResposta(Constantes.VIVO_TV);
	}

}
