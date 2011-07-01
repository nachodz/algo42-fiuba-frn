package algo42Full.control;

/**
 * Pantalla a ejecutar cuando se gano el juego.
 */
import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaPantallaGanar;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class PantallaGanar {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	
	/**
	 * Constructor
	 * @param controlador El controladorJuego que se va a usar para dibujar la pantalla.
	 */
	public PantallaGanar(ControladorJuego controlador){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
	}
	
	/**
	 * Loop que ejecuta la pantalla de ganar.
	 */
	public void ejecutar(){
		Coordenada coord = new Coordenada(0,0,1);
		VistaPantallaGanar vistaPantalla = new VistaPantallaGanar();
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
