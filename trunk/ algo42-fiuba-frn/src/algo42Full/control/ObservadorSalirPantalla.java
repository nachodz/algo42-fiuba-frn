package algo42Full.control;

import java.awt.event.KeyEvent;

import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.KeyPressedObservador;
import ar.uba.fi.algo3.titiritero.MouseClickObservador;

public class ObservadorSalirPantalla implements KeyPressedObservador, MouseClickObservador {
	private ControladorJuego controlador;
	
	
	public ObservadorSalirPantalla(ControladorJuego controlador){
		this.controlador = controlador;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		controlador.detenerJuego();
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		controlador.detenerJuego();
		
	}

	@Override
	public void MouseClick(int x, int y) {
		controlador.detenerJuego();
	}

}