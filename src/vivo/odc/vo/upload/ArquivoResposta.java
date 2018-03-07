package vivo.odc.vo.upload;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("arquivo_resposta")
public class ArquivoResposta {
	
	private InfoEnvio info_envio = null;
	private Registros registros  = new Registros();
	
	public ArquivoResposta(String prestadora){
		this.info_envio = new InfoEnvio(prestadora);
	}

	public Registros getRegistros() {
		return registros;
	}

	public void setRegistros(Registros registros) {
		this.registros = registros;
	}

	public InfoEnvio getInfo_envio() {
		return info_envio;
	}

	public void setInfo_envio(InfoEnvio info_envio) {
		this.info_envio = info_envio;
	}

}
