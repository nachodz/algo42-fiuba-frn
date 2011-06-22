package algo42Full.control;

import algo42Full.modelo.*;
import algo42Full.vista.*;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class ControladorNivel {
	
	private ControladorJuego controlador;
	private ZonaCombate zona;
	private FlotaEnemiga flotaEnemiga;
	private Algo42 algo42;
	private Flota flota;

	public ControladorNivel(ControladorJuego controlador){
		this.controlador = controlador;
		
		VistaFondoNivel vistaFondo = new VistaFondoNivel();
		Coordenada coord = new Coordenada(0,0,1);
		vistaFondo.setPosicionable(coord);
		vistaFondo.setNombreArchivoImagen("/media/fondo1.jpg");
		this.controlador.agregarDibujable(vistaFondo);
		
		zona = new ZonaCombate(600, 800);
		flotaEnemiga = new FlotaEnemiga();
		this.zona.agregarFlotaEnemiga(this.flotaEnemiga);
		
		
		NaveVivaEnemiga avion = new Avioneta(zona, 400, 250);
		this.flotaEnemiga.agregarAvion(avion);	
		VistaAvioneta vistaAvioneta = new VistaAvioneta();
		vistaAvioneta.setPosicionable(avion);
		this.controlador.agregarDibujable(vistaAvioneta);
		//this.controlador.agregarObjetoVivo(avion);
		
		
		NaveVivaEnemiga bombardero = new Bombardero(zona, 650, 0);
		this.flotaEnemiga.agregarAvion(bombardero);	
		VistaBombardero vistaBombardero = new VistaBombardero();
		vistaBombardero.setPosicionable(bombardero);
		this.controlador.agregarDibujable(vistaBombardero);
		//this.controlador.agregarObjetoVivo(bombardero);
		
		
		NaveVivaEnemiga caza = new Caza(zona, 100, 0);
		this.flotaEnemiga.agregarAvion(caza);	
		VistaCaza vistaCaza = new VistaCaza();
		vistaCaza.setPosicionable(caza);
		this.controlador.agregarDibujable(vistaCaza);
		//this.controlador.agregarObjetoVivo(caza);
		
		
		NaveVivaEnemiga explorador = new Explorador(zona, 100, 0);
		this.flotaEnemiga.agregarAvion(explorador);	
		VistaExplorador vistaExplorador = new VistaExplorador();
		vistaExplorador.setPosicionable(explorador);
		this.controlador.agregarDibujable(vistaExplorador);
		//this.controlador.agregarObjetoVivo(explorador);
		
		algo42 = new Algo42(zona, 250, 550);
		zona.agregarAlgo42(algo42);
		VistaAlgo42 vistaAlgo42 = new VistaAlgo42();
		vistaAlgo42.setPosicionable(algo42);
		this.controlador.agregarDibujable(vistaAlgo42);
		ControladorAlgo42 controladorAlgo42 = new ControladorAlgo42(algo42);
		this.controlador.agregarKeyPressObservador(controladorAlgo42);
		//this.controlador.agregarObjetoVivo(algo42);
		
		
		this.flota = new Flota();
		this.zona.agregarFlotaAliada(flota);
		
		NaveViva civil = new AvionCivil(zona, 100, 0);
		this.flota.agregarAvion(civil);	
		VistaAvionCivil vistaCivil = new VistaAvionCivil();
		vistaCivil.setPosicionable(civil);
		this.controlador.agregarDibujable(vistaCivil);
		//this.controlador.agregarObjetoVivo(civil);
		
		NaveViva heli = new Helicoptero(zona, 500, 0);
		this.flota.agregarAvion(heli);	
		VistaHelicoptero vistaHeli = new VistaHelicoptero();
		vistaHeli.setPosicionable(heli);
		this.controlador.agregarDibujable(vistaHeli);
		//this.controlador.agregarObjetoVivo(heli);
		
		this.controlador.agregarObjetoVivo(this.zona);
		
		
		
	}
}
