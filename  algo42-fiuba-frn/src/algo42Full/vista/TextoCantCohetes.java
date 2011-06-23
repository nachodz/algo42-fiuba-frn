package algo42Full.vista;

import algo42Full.modelo.Algo42;
import ar.uba.fi.algo3.titiritero.vista.ObjetoDeTexto;

public class TextoCantCohetes implements ObjetoDeTexto{
	private Algo42 algo42;
	
	public TextoCantCohetes(Algo42 algo42){
		this.algo42 = algo42;
	}

	@Override
	public String getTexto() {
		return Integer.toString(algo42.getCantCohetes());
	}
	
}