package algo42Full.vista;

import algo42Full.control.ControladorNivel;
import ar.uba.fi.algo3.titiritero.vista.ObjetoDeTexto;

public class TextoPuntaje implements ObjetoDeTexto{
	private ControladorNivel nivel;
	
	public TextoPuntaje(ControladorNivel nivel){
		this.nivel = nivel;
	}

	@Override
	public String getTexto() {
		return Integer.toString(nivel.getPuntaje());
	}
	
}