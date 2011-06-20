package algo42Full.modelo;

import java.util.ArrayList;
import java.util.List;

import algo42Full.control.*;
import ar.uba.fi.algo3.titiritero.*;
import algo42Full.vista.*;

public class Menu{
	
	private ControladorJuego controlador;
	private List<Boton> botones;
	private enum Accion{JUEGONUEVO,CONTINUARJUEGO,SALIR};
	private Accion accion;
	
	public Menu(ControladorJuego controlador){
		
		this.accion = Accion.SALIR;
		this.controlador = controlador;
		
		Coordenada coord = new Coordenada(0,0,1);
		VistaMenuPrincipal vistaMenu = new VistaMenuPrincipal();
		vistaMenu.setPosicionable(coord);
		this.controlador.agregarDibujable(vistaMenu);
			
		botones = new ArrayList<Boton>();	
		
		VistaBoton vistaBotonNuevo = new VistaBoton("/media/botonJuegoNuevo.png");
		BotonJuegoNuevo botonNuevo = new BotonJuegoNuevo( this, 270, 350, vistaBotonNuevo.getAncho(), vistaBotonNuevo.getAlto());
		vistaBotonNuevo.setPosicionable(botonNuevo);
		botones.add(botonNuevo);
		controlador.agregarMouseClickObservador(botonNuevo);
		controlador.agregarDibujable(vistaBotonNuevo);
		
		
		VistaBoton vistaBotonContinuar = new VistaBoton("/media/botonContinuar.png");
		BotonContinuarJuego botonContinuar = new BotonContinuarJuego( this, 270, 460, vistaBotonContinuar.getAncho(), vistaBotonContinuar.getAlto());
		vistaBotonContinuar.setPosicionable(botonContinuar);
		botones.add(botonContinuar);
		controlador.agregarMouseClickObservador(botonContinuar);
		controlador.agregarDibujable(vistaBotonContinuar);

	}
	
	
	//llama al loop principal del juego
	public void ejecutar(){
		this.controlador.comenzarJuego();
		
		this.controlador.removerTodosDibujables();
		this.controlador.removerTodosObjetosVivos();
		for (Boton boton : this.botones){
			this.controlador.removerMouseClickObservador(boton);
		}
		switch (this.accion){
			case JUEGONUEVO:	@SuppressWarnings("unused")
								Nivel nivel = new Nivel(this.controlador);
								this.controlador.comenzarJuego();
								break;
			
			case CONTINUARJUEGO: //cuando se le hace click a continuar juego termina, aca,
								//como no hay nada que hacer, no pasa nada.
								break;
			
			default: break;
		}
		
	}
	
	
	/*
	 * la idea es que el BotonJuegoNuevo llame a este método cuando registra
	 * un click del mouse en BotonJuegoNuevo.
	 */
	public void empezarJuegoNuevo(){
		//empieza un juegoNuevo
		System.out.print("CLICK: empezar juego nuevo. \n");
		this.controlador.detenerJuego();
		this.accion = Accion.JUEGONUEVO;
	}
	
	// lo mismo que arriba pero para continuar juego
	public void continuarJuego(){
		//continua el juego guardado
		System.out.print("CLICK: continuar juego guardado. \n");
		this.controlador.detenerJuego();
		this.accion = Accion.CONTINUARJUEGO;
	}
	
}
