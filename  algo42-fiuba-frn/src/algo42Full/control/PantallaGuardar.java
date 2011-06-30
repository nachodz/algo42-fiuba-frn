package algo42Full.control;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaPantallaGuardar;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class PantallaGuardar {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	
	public PantallaGuardar(ControladorJuego controlador){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
	}
	
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