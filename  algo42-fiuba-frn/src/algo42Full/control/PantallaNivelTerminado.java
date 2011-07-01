package algo42Full.control;


import algo42Full.modelo.Coordenada;

import algo42Full.vista.VistaPantallaNivelTerminado;
import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.vista.TextoEstatico;

/**
 * Pantalla que se muestra al usuario cuando termino un nivel.
 */
public class PantallaNivelTerminado {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	
	/**
	 * Constructor
	 * @param controlador El controladorJuego donde se va dibujar y ejecutar la pantalla.
	 */
	public PantallaNivelTerminado(ControladorJuego controlador){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
	}
	
	/**
	 * Ejecuta la pantalla de nivel terminado.
	 * @param puntajeTotal El puntaje total de todo el juego para mostrarlo en la pantalla, un int.
	 * @param puntaje El puntaje del nivel para mostrarlo en la pantalla, un int.
	 */
	public void ejecutar(int puntajeTotal, int puntaje){
		
		TextoEstatico textPuntaje = new TextoEstatico(Integer.toString(puntaje));
		Coordenada cTextPuntaje = new Coordenada(220,420,1);
		textPuntaje.setPosicionable(cTextPuntaje);
		textPuntaje.setFuente("serif", 26);
		
		TextoEstatico textPuntajeTotal = new TextoEstatico(Integer.toString(puntajeTotal));
		Coordenada cTextPuntajeTotal = new Coordenada(550,420,1);
		textPuntajeTotal.setPosicionable(cTextPuntajeTotal);
		textPuntajeTotal.setFuente("serif", 26);
		
		Coordenada coord = new Coordenada(0,0,1);
		VistaPantallaNivelTerminado vistaPantalla = new VistaPantallaNivelTerminado();
		vistaPantalla.setPosicionable(coord);
		this.controlador.agregarDibujable(vistaPantalla);
		controlador.agregarDibujable(textPuntajeTotal);
		controlador.agregarDibujable(textPuntaje);
		controlador.agregarKeyPressObservador(observadorSalir);
		controlador.agregarMouseClickObservador(observadorSalir);
		controlador.comenzarJuego();
		controlador.removerTodosDibujables();
		controlador.removerKeyPressObservador(observadorSalir);
		controlador.removerMouseClickObservador(observadorSalir);
	}

}