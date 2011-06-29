package algo42Full.control;

import algo42Full.control.ControladorNivel.EstadoNivel;
import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaPantallaNivelTerminado;
import algo42Full.vista.VistaPantallaPerder;
import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.Dibujable;

public class PantallaResultado {
	private ControladorJuego controlador;
	private ObservadorSalirPantalla observadorSalir;
	private int puntaje;
	private Dibujable vistaPantalla;
	
	public PantallaResultado(ControladorJuego controlador, EstadoNivel estado,int puntaje){
		this.controlador = controlador; 
		observadorSalir = new ObservadorSalirPantalla(this.controlador);
		this.puntaje = puntaje;
		if (estado == EstadoNivel.ALGO42MUERTO){
			vistaPantalla = new VistaPantallaPerder();
		}
		else{
			vistaPantalla = new VistaPantallaNivelTerminado();
		}
	}
	
	public void ejecutar(){
		Coordenada coord = new Coordenada(0,0,1);
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
