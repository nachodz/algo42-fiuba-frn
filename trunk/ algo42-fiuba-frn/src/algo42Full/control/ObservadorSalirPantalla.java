package algo42Full.control;

import java.awt.event.KeyEvent;

import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.KeyPressedObservador;
import ar.uba.fi.algo3.titiritero.MouseClickObservador;

/**
 * Observador de teclado que se fija si se apreto una tecla cualquiera,
 * en cuyo caso avisa que se quiere detener un ControladorJuego.
 */
public class ObservadorSalirPantalla implements KeyPressedObservador, MouseClickObservador {
	private ControladorJuego controlador;
	
	/**
	 * Constructor
	 * @param controlador el controladorJuego que se va a querer detener.
	 */
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