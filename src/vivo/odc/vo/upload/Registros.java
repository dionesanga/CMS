package vivo.odc.vo.upload;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("resgistros")
public class Registros {
	
	@XStreamImplicit
	private List<Registro> registro = new ArrayList<Registro>();

	public List<Registro> getRegistro() {
		return registro;
	}

	public void setRegistro(List<Registro> registro) {
		this.registro = registro;
	}
}
