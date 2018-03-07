package vivo.odc.vo.upload;

import java.util.Date;

import vivo.odc.util.Constantes;
import vivo.odc.util.MasterDate;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("info_envio")
public class InfoEnvio {
	
	private String cod_prestadora;
	private String data;
	private String horario;
	private String login_sis;
	private String nome_prestadora;
	
	public InfoEnvio(String prestadora){
		data = MasterDate.formatter(new Date(), "dd/MM/yyyy");
		horario = MasterDate.formatter(new Date(), "HH:mm:ss");

		login_sis 		= "05169017960";
		nome_prestadora = prestadora;

		if(Constantes.VIVO_BLARGA.equals(prestadora)){
			cod_prestadora="889";
		}else if(Constantes.VIVO_FIXA.equals(prestadora)){
			cod_prestadora="245";
		}else if(Constantes.VIVO_MOVEL.equals(prestadora)){
			cod_prestadora="278";
		}else if(Constantes.VIVO_TV.equals(prestadora)){
			cod_prestadora="976";
		}
	}
	
	public String getCod_prestadora() {
		return cod_prestadora;
	}
	public void setCod_prestadora(String cod_prestadora) {
		this.cod_prestadora = cod_prestadora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getLogin_sis() {
		return login_sis;
	}
	public void setLogin_sis(String login_sis) {
		this.login_sis = login_sis;
	}
	public String getNome_prestadora() {
		return nome_prestadora;
	}
	public void setNome_prestadora(String nome_prestadora) {
		this.nome_prestadora = nome_prestadora;
	}
}
