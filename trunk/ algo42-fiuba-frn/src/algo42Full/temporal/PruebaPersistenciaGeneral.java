package algo42Full.temporal;

import ar.uba.fi.algo3.titiritero.*;
import algo42Full.control.*;


public class PruebaPersistenciaGeneral {
	
	public static void main(String[] args) {
		
		
		ControladorJuego controlador = new ControladorJuego(false);
			
		ControladorNivel cNivel = new ControladorNivel(controlador);
		
		cNivel.guardarJuego("juego.xml");
		
		
		cNivel.cargarJuego("juego.xml", controlador);
		
		System.out.println(cNivel.getPuntaje());
		
		
		
	}

}
