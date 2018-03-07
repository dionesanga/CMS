package vivo.odc.vo.roteamento;

public class RoteamentoVO {

	private int	   idRoteamento;
	private String nome;
	private String consulta;
	private String fidelizacao;
	private String tpDistribuicao;
	private String filterDateByField;
	private String situacao;
	private int    idRotTipificacao;
	private int	   idCanal;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getFidelizacao() {
		return fidelizacao;
	}

	public void setFidelizacao(String fidelizacao) {
		this.fidelizacao = fidelizacao;
	}

	public String getFilterDateByField() {
		return filterDateByField;
	}

	public void setFilterDateByField(String filterDateByField) {
		this.filterDateByField = filterDateByField;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getIdRotTipificacao() {
		return idRotTipificacao;
	}

	public void setIdRotTipificacao(int idRotTipificacao) {
		this.idRotTipificacao = idRotTipificacao;
	}

	public int getIdRoteamento() {
		return idRoteamento;
	}

	public void setIdRoteamento(int idRoteamento) {
		this.idRoteamento = idRoteamento;
	}

	public String getTpDistribuicao() {
		return tpDistribuicao;
	}

	public void setTpDistribuicao(String tpDistribuicao) {
		this.tpDistribuicao = tpDistribuicao;
	}

	public int getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(int idCanal) {
		this.idCanal = idCanal;
	}
}
