package algo42Full.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaBoton;
import algo42Full.vista.VistaMenuPrincipal;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class ControladorMenu {
	
	private ControladorJuego controlador;
	private Map<Boton,VistaBoton> mapBotones;
	private enum Accion{JUEGONUEVO,CONTINUARJUEGO,SALIR};
	private Accion accion;	
	private ObservadorSalir observadorSalir;
	
	public ControladorMenu(ControladorJuego controlador){
		
		this.accion = Accion.SALIR;
		this.controlador = controlador;
		mapBotones = new HashMap<Boton,VistaBoton>();
		observadorSalir = new ObservadorSalir(this.controlador);
		
		VistaBoton vistaBotonNuevo = new VistaBoton("/media/botonJuegoNuevo.png");
		BotonJuegoNuevo botonNuevo = new BotonJuegoNuevo( this, 270, 350, vistaBotonNuevo.getAncho(), vistaBotonNuevo.getAlto());
		vistaBotonNuevo.setPosicionable(botonNuevo);
		mapBotones.put(botonNuevo, vistaBotonNuevo);
		
		VistaBoton vistaBotonContinuar = new VistaBoton("/media/botonContinuar.png");
		BotonContinuarJuego botonContinuar = new BotonContinuarJuego( this, 270, 460, vistaBotonContinuar.getAncho(), vistaBotonContinuar.getAlto());
		vistaBotonContinuar.setPosicionable(botonContinuar);
		mapBotones.put(botonContinuar, vistaBotonContinuar);
		
		this.cargarMenu();
	}
	
	public void cargarMenu(){
		
		Coordenada coord = new Coordenada(0,0,1);
		VistaMenuPrincipal vistaMenu = new VistaMenuPrincipal();
		vistaMenu.setPosicionable(coord);
		this.controlador.agregarDibujable(vistaMenu);
		controlador.agregarKeyPressObservador(observadorSalir);
		
		Set<Boton> setBotones = mapBotones.keySet();
		for (Boton boton : setBotones){
			VistaBoton vista = mapBotones.get(boton);
			controlador.agregarMouseClickObservador(boton);
			controlador.agregarDibujable(vista);
		}

	}
	
	public void descargarMenu(){
		
		this.controlador.removerTodosDibujables();
		this.controlador.removerTodosObjetosVivos();
		Set<Boton> setBotones = mapBotones.keySet();
		for (Boton boton : setBotones){
			this.controlador.removerMouseClickObservador(boton);
		}
		
	}
	
	
	//llama al loop principal del juego
//	public void ejecutar(){
//		this.controlador.comenzarJuego();
//		this.descargarMenu();
//		switch (this.accion){
//			case JUEGONUEVO:	@SuppressWarnings("unused")
//								ControladorNivel nivel = new ControladorNivel(controlador);
//								controlador.comenzarJuego();
//								nivel.descargar();
//								this.cargarMenu();
//								break;
//			
//			case CONTINUARJUEGO: //cuando se le hace click a continuar juego termina, aca,
//								//como no hay nada que hacer, no pasa nada.
//								break;
//			
//			default: break;
//		}
//		
//	}
	
	public void ejecutar(){
		boolean salir = false;
		int puntaje = 0;
		ControladorNivel nivel = new ControladorNivel("nivel1.xml", controlador);
		while (!salir){
			this.controlador.comenzarJuego();
			this.descargarMenu();
			switch (this.accion){
				case JUEGONUEVO:	nivel.cargar();
									nivel.setPuntaje(puntaje);
									controlador.comenzarJuego();
									puntaje = nivel.getPuntaje();
									nivel.descargar();
									this.cargarMenu();
									break;
				
				case CONTINUARJUEGO: //cuando se le hace click a continuar juego termina, aca,
									//como no hay nada que hacer, no pasa nada.
									break;
				
				default: break;
			}
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
