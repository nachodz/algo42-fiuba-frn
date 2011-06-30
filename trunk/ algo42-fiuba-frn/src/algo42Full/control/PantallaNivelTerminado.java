package algo42Full.control;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaPantallaNivelTerminado;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class PantallaNivelTerminado {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	
	public PantallaNivelTerminado(ControladorJuego controlador){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
	}
	
	public void ejecutar(){
		// TODO hacer que se vea el puntaje!!!
		Coordenada coord = new Coordenada(0,0,1);
		VistaPantallaNivelTerminado vistaPantalla = new VistaPantallaNivelTerminado();
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