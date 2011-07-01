package algo42Full.control;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaPantallaGuardar;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

/**
 * Pantalla que se ejecuta cuando se guardo un juego en curso.
 * Se usa para darle una notificación visual al jugador.
 */
public class PantallaGuardar {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	
	/**
	 * Constructor.
	 * @param controlador El controladorJuego donde se va a dibujar y ejecutar la pantalla.
	 */
	public PantallaGuardar(ControladorJuego controlador){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
	}
	
	/**
	 * Loop que ejecuta la pantalla de confirmación de guardado.
	 */
	public void ejecutar(){
		Coordenada coord = new Coordenada(0,0,1);
		VistaPantallaGuardar vistaPantalla = new VistaPantallaGuardar();
		vistaPantalla.setPosicionable(coord);
		this.controlador.agregarDibujable(vistaPantalla);
		controlador.agregarKeyPressObservador(observadorSalir);
		controlador.agregarMouseClickObservador(observadorSalir);
		controlador.comenzarJuego();
		controlador.removerDibujable(vistaPantalla);
		controlador.removerKeyPressObservador(observadorSalir);
		controlador.removerMouseClickObservador(observadorSalir);
	}

}