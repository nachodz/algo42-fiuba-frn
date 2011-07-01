package algo42Full.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import algo42Full.modelo.Coordenada;
import algo42Full.vista.VistaBoton;
import algo42Full.vista.VistaMenuPrincipal;
import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

/**
 * El menu principal del programa.
 */
public class ControladorMenu {
	
	private ControladorJuego controlador;
	private Map<Boton,VistaBoton> mapBotones;
	private enum Accion{ JUGAR, JUEGONUEVO, GUARDARJUEGO, CARGARJUEGO, SALIR};
	private Accion accion;	
	private ObservadorSalir observadorSalir;
	private boolean botonGuardarActivado;
	private VistaBoton vistaBotonGuardar;
	
	/**
	 * Constructor.
	 * @param controlador El controladorJuego que va a utilizar el menu para dibujarse e
	 * 					  interactuar con el usuario.
	 */
	public ControladorMenu(ControladorJuego controlador){
		
		botonGuardarActivado = false;
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
		
		this.vistaBotonGuardar = new VistaBoton("/media/botonGuardar.png");
		BotonGuardar botonGuardar= new BotonGuardar( this, 270, 410, vistaBotonGuardar.getAncho(), vistaBotonGuardar.getAlto());
		vistaBotonGuardar.setPosicionable(botonGuardar);
		
		VistaBoton vistaBotonSalir = new VistaBoton("/media/botonSalir.png");
		BotonSalir botonSalir= new BotonSalir( this, 270, 490, vistaBotonSalir.getAncho(), vistaBotonSalir.getAlto());
		vistaBotonSalir.setPosicionable(botonSalir);
		mapBotones.put(botonSalir, vistaBotonSalir);
		
		
		this.cargarMenu();
	}
	
	/**
	 * Carga el menu en el ControladorJuego
	 */
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
	
	/**
	 * quita el menu del controladorJuego.
	 */
	public void descargarMenu(){
		
		this.controlador.removerTodosDibujables();
		this.controlador.removerTodosObjetosVivos();
		this.controlador.removerKeyPressObservador(observadorSalir);
		Set<Boton> setBotones = mapBotones.keySet();
		for (Boton boton : setBotones){
			this.controlador.removerMouseClickObservador(boton);
		}
		
	}
	
		
	/**
	 * activa en el menu el boton para guardar juego en curso.
	 */
	private void activarBotonGuardar(){
		botonGuardarActivado = true;
		mapBotones.put((Boton)vistaBotonGuardar.getPosicionable(), vistaBotonGuardar);
	}
	
	/**
	 * desactiva del menu el boton para guardar juego en curso.
	 */
	private void desactivarBotonGuardar(){
		botonGuardarActivado = false;
		mapBotones.remove((Boton)vistaBotonGuardar.getPosicionable());
	}
	

	/**
	 * El loop princpial del menu.
	 */
	public void ejecutar(){
		boolean salir = false;
		PantallaGuardar pantallaGuardar = new PantallaGuardar(controlador);

		//Cargando inicio (esto se puede sacar, dps vemos)
		Imagen vistaCargando = new Imagen();
		Coordenada coord = new Coordenada(0,0,1);
		vistaCargando.setNombreArchivoImagen("/media/cargando.jpg");
		vistaCargando.setPosicionable(coord);
		controlador.agregarDibujable(vistaCargando);
		controlador.comenzarJuego(1);
		controlador.removerDibujable(vistaCargando);
		// Cargando FIN
		
		ControladorNivel nivel = new ControladorNivel("niveles/nivel1.xml", controlador);
		AdministradorNiveles admNiveles = new AdministradorNiveles(nivel, controlador);
		while (!salir){
			this.controlador.comenzarJuego();
			//this.descargarMenu();
			switch (this.accion){
				case JUEGONUEVO:	descargarMenu();
									if (!botonGuardarActivado) activarBotonGuardar();
									admNiveles.cargarNivel("niveles/nivel1.xml");
									accion = Accion.JUGAR;
									admNiveles.jugar();
									if (!admNiveles.sigueJugando()) {
										accion = Accion.SALIR;
										desactivarBotonGuardar();
									}
									this.cargarMenu();
									break;
			
				case JUGAR:			descargarMenu();
									if (!botonGuardarActivado) activarBotonGuardar();
									admNiveles.jugar();
									if (!admNiveles.sigueJugando()){
										accion = Accion.SALIR;
										desactivarBotonGuardar();
									}
									this.cargarMenu();
									break;
				
				case CARGARJUEGO:	descargarMenu();
									if (!botonGuardarActivado) activarBotonGuardar();
									admNiveles.cargarJuego("save.xml");
									accion = Accion.JUGAR;
									admNiveles.jugar();
									if (!admNiveles.sigueJugando()) {
										accion = Accion.SALIR;
										desactivarBotonGuardar();
									}
									this.cargarMenu();
									break;
									
				case GUARDARJUEGO:	admNiveles.guardarJuego("save.xml");
									accion = Accion.JUGAR;
									descargarMenu();
									pantallaGuardar.ejecutar();
									cargarMenu();
									break;
				
				case SALIR:			salir = true;
									break;
			}
		}
		
	}
	
	
	
	
	
	/*
	 * Cambia el estado de la accion que se quiere hacer en el menu a JUEGONUEVO.
	 */
	public void empezarJuegoNuevo(){
		//empieza un juegoNuevo
		this.controlador.detenerJuego();
		this.accion = Accion.JUEGONUEVO;
	}
	
	
	/*
	 * Cambia el estado de la accion que se quiere hacer en el menu a CARGARJUEGO.
	 */
	public void continuarJuego(){
		//continua el juego guardado
		this.controlador.detenerJuego();
		this.accion = Accion.CARGARJUEGO;
	}
	
	/*
	 * Cambia el estado de la accion que se quiere hacer en el menu a SALIR.
	 */
	public void salir(){
		this.controlador.detenerJuego();
		accion = Accion.SALIR;
	}
	
	/*
	 * Cambia el estado de la accion que se quiere hacer en el menu a GUARDARJUEGO.
	 */
	public void guardar(){
		this.controlador.detenerJuego();
		accion = Accion.GUARDARJUEGO;
	}

}
