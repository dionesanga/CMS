package vivo.odc.vo;

public class ChamadoVO {

	private String request;
	private RequestVO ultRequest;
	private String valor;	
	private String responsavel;
	private String id;
	private String status;
	private String resposta;
	private boolean existMis;
	
	public String getRequest() {
		return request;
	}	
	public void setRequest(String request) {
		this.request = request;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getResposta() {
		return resposta;
	}
	
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	
	public boolean isExistMis() {
		return existMis;
	}

	public void setExistMis(boolean existMis) {
		this.existMis = existMis;
	}

	public RequestVO getUltRequest() {
		return ultRequest;
	}

	public void setUltRequest(RequestVO ultRequest) {
		this.ultRequest = ultRequest;
	}
}
