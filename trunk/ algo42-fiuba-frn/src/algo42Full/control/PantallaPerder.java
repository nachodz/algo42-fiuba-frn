package algo42Full.control;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaPantallaPerder;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

/**
 * Pantalla que se muestra cuando se perdio el juego.
 */
public class PantallaPerder {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	
	/**
	 * Constructor.
	 * @param controlador El controladorJuego donde se va dibujar y ejecutar la pantalla.
	 */
	public PantallaPerder(ControladorJuego controlador){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
	}
	
	/**
	 * Ejecuta el loop de la pantalla de perder el juego
	 */
	public void ejecutar(){
		Coordenada coord = new Coordenada(0,0,1);
		VistaPantallaPerder vistaPantalla = new VistaPantallaPerder();
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
