package algo42Full.control;

import java.awt.event.KeyEvent;

import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.KeyPressedObservador;

/**
 * Observador de teclado que se fija si se apreto una tecla especifica
 * para detener el controladorJuego.
 */
public class ObservadorSalir implements KeyPressedObservador {
	private ControladorJuego controlador;
	
	/**
	 * Constructor
	 * @param controlador El controladorJuego que se va a querer detener.
	 */
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
