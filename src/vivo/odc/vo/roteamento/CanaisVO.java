package vivo.odc.vo.roteamento;

import java.util.List;

public class CanaisVO {

	private int	    idCanal;
	private String 	canal;
	private String	situacao;
	private List<RoteamentoVO> roteamentos;

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<RoteamentoVO> getRoteamentos() {
		return roteamentos;
	}
	
	public void setRoteamentos(List<RoteamentoVO> roteamentos) {
		this.roteamentos = roteamentos;
	}

	public int getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(int idCanal) {
		this.idCanal = idCanal;
	}


}
