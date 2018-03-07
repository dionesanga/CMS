package vivo.odc.vo.upload;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("registro")
public class Registro {

	private String cod_envio=""; 
	private String cpf_responsavel="";
	private String data_previsao="";
	private String descricao="";
	private String id_solicitacao=""; 
	private String idtSolicitacao=""; 
	private String indProcedencia="";  
	private String num_atividade="";
	private String tipoRegistro="";
	
	public Registro(String cod_envio,String descricao,String id_solicitacao,String indProcedencia,String tipoRegistro){
		this.cod_envio       = cod_envio; 
		this.descricao       = descricao;
		this.id_solicitacao  = id_solicitacao; 
		this.indProcedencia  = indProcedencia;  
		this.tipoRegistro    = tipoRegistro;
	}

	public String getCod_envio() {
		return cod_envio;
	}

	public void setCod_envio(String cod_envio) {
		this.cod_envio = cod_envio;
	}

	public String getCpf_responsavel() {
		return cpf_responsavel;
	}

	public void setCpf_responsavel(String cpf_responsavel) {
		this.cpf_responsavel = cpf_responsavel;
	}

	public String getData_previsao() {
		return data_previsao;
	}

	public void setData_previsao(String data_previsao) {
		this.data_previsao = data_previsao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getId_solicitacao() {
		return id_solicitacao;
	}

	public void setId_solicitacao(String id_solicitacao) {
		this.id_solicitacao = id_solicitacao;
	}

	public String getIdtSolicitacao() {
		return idtSolicitacao;
	}

	public void setIdtSolicitacao(String idtSolicitacao) {
		this.idtSolicitacao = idtSolicitacao;
	}

	public String getIndProcedencia() {
		return indProcedencia;
	}

	public void setIndProcedencia(String indProcedencia) {
		this.indProcedencia = indProcedencia;
	}

	public String getNum_atividade() {
		return num_atividade;
	}

	public void setNum_atividade(String num_atividade) {
		this.num_atividade = num_atividade;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
}
