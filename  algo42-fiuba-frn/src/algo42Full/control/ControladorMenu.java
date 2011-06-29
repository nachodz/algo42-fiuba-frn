package algo42Full.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaBoton;
import algo42Full.vista.VistaMenuPrincipal;
import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

public class ControladorMenu {
	
	private ControladorJuego controlador;
	private Map<Boton,VistaBoton> mapBotones;
	private enum Accion{ JUGAR, GUARDARJUEGO, CARGARJUEGO, SALIR};
	private Accion accion;	
	private ObservadorSalir observadorSalir;
	
	public ControladorMenu(ControladorJuego controlador){
		
		this.accion = Accion.SALIR;
		this.controlador = controlador;
		mapBotones = new HashMap<Boton,VistaBoton>();
		observadorSalir = new ObservadorSalir(this.controlador);
		
		VistaBoton vistaBotonNuevo = new VistaBoton("/media/botonJuegoNuevo.png");
		BotonJuegoNuevo botonNuevo = new BotonJuegoNuevo( this, 270, 250, vistaBotonNuevo.getAncho(), vistaBotonNuevo.getAlto());
		vistaBotonNuevo.setPosicionable(botonNuevo);
		mapBotones.put(botonNuevo, vistaBotonNuevo);
		
		VistaBoton vistaBotonContinuar = new VistaBoton("/media/botonContinuar.png");
		BotonContinuarJuego botonContinuar = new BotonContinuarJuego( this, 270, 330, vistaBotonContinuar.getAncho(), vistaBotonContinuar.getAlto());
		vistaBotonContinuar.setPosicionable(botonContinuar);
		mapBotones.put(botonContinuar, vistaBotonContinuar);
		
		VistaBoton vistaBotonGuardar = new VistaBoton("/media/botonGuardar.png");
		BotonGuardar botonGuardar= new BotonGuardar( this, 270, 410, vistaBotonGuardar.getAncho(), vistaBotonGuardar.getAlto());
		vistaBotonGuardar.setPosicionable(botonGuardar);
		mapBotones.put(botonGuardar, vistaBotonGuardar);
		
		VistaBoton vistaBotonSalir = new VistaBoton("/media/botonSalir.png");
		BotonSalir botonSalir= new BotonSalir( this, 270, 490, vistaBotonSalir.getAncho(), vistaBotonSalir.getAlto());
		vistaBotonSalir.setPosicionable(botonSalir);
		mapBotones.put(botonSalir, vistaBotonSalir);
		
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
	

	public void ejecutar(){
		boolean salir = false;
		int puntaje = 0;

		//Cargando inicio (esto se puede sacar, dps vemos)
		Imagen vistaCargando = new Imagen();
		Coordenada coord = new Coordenada(0,0,1);
		vistaCargando.setNombreArchivoImagen("/media/cargando.jpg");
		vistaCargando.setPosicionable(coord);
		controlador.agregarDibujable(vistaCargando);
		controlador.comenzarJuego(1);
		controlador.removerDibujable(vistaCargando);
		// Cargando FIN
		
		ControladorNivel nivel = new ControladorNivel("nivel1.xml", controlador);
		while (!salir){
			this.controlador.comenzarJuego();
			//this.descargarMenu();
			switch (this.accion){
				case JUGAR:			descargarMenu();
									nivel.cargar();
									nivel.setPuntaje(puntaje);
									controlador.comenzarJuego();
									puntaje = nivel.getPuntaje();
									nivel.descargar();
									this.cargarMenu();
									break;
				
				case CARGARJUEGO:	descargarMenu();
									nivel.cargarJuego("save.xml", controlador);
									accion = Accion.JUGAR;
									nivel.cargar();
									nivel.setPuntaje(puntaje);
									controlador.comenzarJuego();
									puntaje = nivel.getPuntaje();
									nivel.descargar();
									this.cargarMenu();
									break;
									
				case GUARDARJUEGO:	nivel.guardarJuego("save.xml");
									accion = Accion.JUGAR;
									break;
				
				case SALIR:			salir = true;
									break;
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
		this.accion = Accion.JUGAR;
	}
	
	// lo mismo que arriba pero para cargar un juego guardado juego
	public void continuarJuego(){
		//continua el juego guardado
		System.out.print("CLICK: continuar juego guardado. \n");
		this.controlador.detenerJuego();
		this.accion = Accion.CARGARJUEGO;
	}
	
	public void salir(){
		this.controlador.detenerJuego();
		accion = Accion.SALIR;
	}
	
	public void guardar(){
		//haga algo para guardar?
		System.out.print("CLICK: guardar juego. \n");
		this.controlador.detenerJuego();
		accion = Accion.GUARDARJUEGO;
	}

}
