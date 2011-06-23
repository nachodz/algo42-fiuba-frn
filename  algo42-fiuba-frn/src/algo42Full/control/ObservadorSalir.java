package algo42Full.control;

import java.awt.event.KeyEvent;

import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.KeyPressedObservador;

public class ObservadorSalir implements KeyPressedObservador {
	private ControladorJuego controlador;
	
	
	public ObservadorSalir(ControladorJuego controlador){
		this.controlador = controlador;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// nada
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int tecla = event.getKeyCode();
		if (tecla == KeyEvent.VK_ESCAPE){
			controlador.detenerJuego();
			return;
		}
		
	}

}
