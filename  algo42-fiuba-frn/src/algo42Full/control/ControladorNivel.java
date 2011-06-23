package algo42Full.control;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algo42Full.modelo.*;
import algo42Full.vista.*;
import ar.uba.fi.algo3.titiritero.Accion;
import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.vista.*;
public class ControladorNivel implements Accion {
	
	private ControladorJuego controlador;
	private ZonaCombate zona;
	private FlotaEnemiga flotaEnemiga;
	private Algo42 algo42;
	private VistaAlgo42 vistaAlgo42;
	private ControladorAlgo42 controladorAlgo42;
	private Flota flota;
	private Map<Proyectil,VistaProyectil> mapaProyectiles;
	private Map<ActualizacionAlgo42,VistaActualizacion> mapaActualizaciones;
	private Map<NaveViva,VistaNave> mapaNaves;
	private Map<ObjetoDeTexto,Texto> mapaTexto;
	private VistaFondoNivel vistaFondo;
	
	private int puntaje;
	
	
	private void agregarDibujablesNuevos(){
		VistaActualizacion actVista;
		VistaProyectil proyeVista;
		
		List<Proyectil> lProyectil = zona.getProyectiles();
		List<ActualizacionAlgo42> lAct = zona.getActualizaciones();
		for (ActualizacionAlgo42 actualizacion : lAct){
			if (!mapaActualizaciones.containsKey(actualizacion)){
				actVista = new VistaActualizacion(actualizacion);
				mapaActualizaciones.put(actualizacion, actVista);
				this.controlador.agregarDibujable(actVista);
			}
		}
		synchronized(lProyectil){
			for (Proyectil proyectil : lProyectil){
				if (!mapaProyectiles.containsKey(proyectil)){
					proyeVista = new VistaProyectil(proyectil);
					mapaProyectiles.put(proyectil, proyeVista);
					this.controlador.agregarDibujable(proyeVista);
				}
			}	
		}
	}
	
	private void quitarDibujablesObsoletos(){
		VistaActualizacion actVista;
		VistaProyectil proyeVista;
		
		Set<Proyectil> setp = mapaProyectiles.keySet();
		HashMap<Proyectil,VistaProyectil> pMap = new HashMap<Proyectil,VistaProyectil>();
		for( Proyectil proyectil : setp){
			proyeVista = mapaProyectiles.get(proyectil);
			if (!proyectil.estaVivo())
				this.controlador.removerDibujable(proyeVista);
			else
				pMap.put(proyectil, proyeVista);
		}
		mapaProyectiles.clear();
		mapaProyectiles = pMap;
		
		
		Set<ActualizacionAlgo42> setA = mapaActualizaciones.keySet();
		HashMap<ActualizacionAlgo42,VistaActualizacion> aMap = new HashMap<ActualizacionAlgo42,VistaActualizacion>();
		for( ActualizacionAlgo42 act : setA){
			actVista = mapaActualizaciones.get(act);
			if (!act.estaVivo())
				this.controlador.removerDibujable(actVista);
			else
				aMap.put(act, actVista);
		}
		mapaActualizaciones.clear();
		mapaActualizaciones = aMap;
		
		Set<NaveViva> setN = mapaNaves.keySet();
		HashMap<NaveViva,VistaNave> nMap = new HashMap<NaveViva,VistaNave>();
		for( NaveViva nave : setN){
			VistaNave naveVista = mapaNaves.get(nave);
			if (!nave.estaVivo())
				this.controlador.removerDibujable(naveVista);
			else
				nMap.put(nave, naveVista);
		}
		mapaNaves.clear();
		mapaNaves = nMap;
		
	}

	@Override
	public void ejecutarAccion() {
		this.agregarDibujablesNuevos();
		this.quitarDibujablesObsoletos();
		puntaje += this.zona.reportarPuntosBajas();
		// Cuando se tiene 1000 puntos termina el nivel
		if (puntaje> 1000){
			this.controlador.detenerJuego();
			System.out.print("GANE!!!!!");
		}
	}
	
	
	
	
	
	
	
	public ControladorNivel(ControladorJuego controlador){
		this.controlador = controlador;
		mapaProyectiles = new HashMap<Proyectil, VistaProyectil>();
		mapaActualizaciones = new HashMap<ActualizacionAlgo42, VistaActualizacion>();
		mapaNaves = new HashMap<NaveViva,VistaNave>();
		mapaTexto = new HashMap<ObjetoDeTexto,Texto>();
		puntaje = 0;
		
		
		vistaFondo = new VistaFondoNivel();
		Coordenada coord = new Coordenada(0,0,1);
		vistaFondo.setPosicionable(coord);
		vistaFondo.setNombreArchivoImagen("/media/fondo1.jpg");
		
		zona = new ZonaCombate(600, 800);
		flotaEnemiga = new FlotaEnemiga();
		this.zona.agregarFlotaEnemiga(this.flotaEnemiga);
		
		
		NaveVivaEnemiga avion = new Avioneta(zona, 400, 250);
		this.flotaEnemiga.agregarAvion(avion);	
		VistaAvioneta vistaAvioneta = new VistaAvioneta();
		vistaAvioneta.setPosicionable(avion);
		mapaNaves.put(avion, vistaAvioneta);
		
		
		NaveVivaEnemiga bombardero = new Bombardero(zona, 650, 0);
		this.flotaEnemiga.agregarAvion(bombardero);	
		VistaBombardero vistaBombardero = new VistaBombardero();
		vistaBombardero.setPosicionable(bombardero);
		mapaNaves.put(bombardero, vistaBombardero);
		
		
		NaveVivaEnemiga caza = new Caza(zona, 100, 0);
		this.flotaEnemiga.agregarAvion(caza);	
		VistaCaza vistaCaza = new VistaCaza();
		vistaCaza.setPosicionable(caza);
		mapaNaves.put(caza, vistaCaza);
		
		
		NaveVivaEnemiga explorador = new Explorador(zona, 100, 0);
		this.flotaEnemiga.agregarAvion(explorador);	
		VistaExplorador vistaExplorador = new VistaExplorador();
		vistaExplorador.setPosicionable(explorador);
		mapaNaves.put(explorador, vistaExplorador);
		
		algo42 = new Algo42(zona, 250, 550);
		zona.agregarAlgo42(algo42);
		vistaAlgo42 = new VistaAlgo42();
		vistaAlgo42.setPosicionable(algo42);
		controladorAlgo42 = new ControladorAlgo42(algo42);
		
		
		this.flota = new Flota();
		this.zona.agregarFlotaAliada(flota);
		
		NaveViva civil = new AvionCivil(zona, 100, 0);
		this.flota.agregarAvion(civil);	
		VistaAvionCivil vistaCivil = new VistaAvionCivil();
		vistaCivil.setPosicionable(civil);
		mapaNaves.put(civil,vistaCivil);
		
		NaveViva heli = new Helicoptero(zona, 500, 0);
		this.flota.agregarAvion(heli);	
		VistaHelicoptero vistaHeli = new VistaHelicoptero();
		vistaHeli.setPosicionable(heli);
		mapaNaves.put(heli, vistaHeli);
		
		
		TextoCantEnergia textoEnergia = new TextoCantEnergia(this.algo42);
		Coordenada cTextEnergia = new Coordenada(700,50,1);
		TextoDinamico vistaTextEnergia = new TextoDinamico(textoEnergia, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextEnergia.setPosicionable(cTextEnergia);
		mapaTexto.put(textoEnergia, vistaTextEnergia);
		
		
		TextoCantTorpedos textoTorpedos = new TextoCantTorpedos(this.algo42);
		Coordenada cTextTorpedos = new Coordenada(700,70,1);
		TextoDinamico vistaTextTorpedos = new TextoDinamico(textoTorpedos, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextTorpedos.setPosicionable(cTextTorpedos);
		mapaTexto.put(textoTorpedos, vistaTextTorpedos);
		
		
		TextoCantCohetes textoCohetes = new TextoCantCohetes(this.algo42);
		Coordenada cTextCohetes = new Coordenada(700,90,1);
		TextoDinamico vistaTextCohetes = new TextoDinamico(textoCohetes, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextCohetes.setPosicionable(cTextCohetes);
		mapaTexto.put(textoCohetes, vistaTextCohetes);
		
		TextoPuntaje textPuntaje = new TextoPuntaje(this);
		Coordenada cTextPuntaje = new Coordenada(30,50,1);
		TextoDinamico vistaTextPuntaje = new TextoDinamico(textPuntaje, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextPuntaje.setPosicionable(cTextPuntaje);
		mapaTexto.put(textPuntaje, vistaTextPuntaje);
		
		
	}
	
	
	public void cargar(){
		controlador.agregarAccion(this);
		controlador.agregarKeyPressObservador(controladorAlgo42);
		
		this.controlador.agregarDibujable(vistaFondo);
		this.controlador.agregarDibujable(vistaAlgo42);
		this.controlador.agregarObjetoVivo(algo42);
		this.controlador.agregarObjetoVivo(this.zona);
		
		
		Set<NaveViva> sNaves = this.mapaNaves.keySet();
		for (NaveViva nave: sNaves){
			VistaNave vistaNave = mapaNaves.get(nave);
			controlador.agregarDibujable(vistaNave);
		}
		
		Set<Proyectil> sProyectil = mapaProyectiles.keySet();
		for (Proyectil proyectil: sProyectil){
			VistaProyectil vistaPro = mapaProyectiles.get(proyectil);
			controlador.agregarDibujable(vistaPro);
		}
		
		Set<ActualizacionAlgo42> sActualizaciones = mapaActualizaciones.keySet();
		for (ActualizacionAlgo42 act : sActualizaciones){
			VistaActualizacion vistaAct = mapaActualizaciones.get(act);
			controlador.agregarDibujable(vistaAct);
		}
		
		Set<ObjetoDeTexto> sTexto = mapaTexto.keySet();
		for (ObjetoDeTexto objeto : sTexto){
			Texto vistaTexto= mapaTexto.get(objeto);
			controlador.agregarDibujable(vistaTexto);
		}
		
	}
	
	public void descargar(){
		this.controlador.removerTodosDibujables();
		this.controlador.removerTodosObjetosVivos();
		this.controlador.removerAccion(this);
		this.controlador.removerKeyPressObservador(controladorAlgo42);
	}
	
	
	public int getPuntaje(){
		return this.puntaje;
	}
	
	public void setPuntaje(int puntaje){
		this.puntaje = puntaje;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
