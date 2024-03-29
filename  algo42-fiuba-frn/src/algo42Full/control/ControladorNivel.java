package algo42Full.control;

import ar.uba.fi.algo3.titiritero.ControladorJuego;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import algo42Full.modelo.*;
import algo42Full.vista.*;
import ar.uba.fi.algo3.titiritero.Accion;
import ar.uba.fi.algo3.titiritero.vista.*;

/**
 * El controladorNivel se encarga de manejar todos los elementos que
 * hay en un nivel, tanto sus objetos vivos, como sus vistas.
 */
public class ControladorNivel implements Accion {
	
	private ControladorJuego controlador;
	private ZonaCombate zona;
	private FlotaEnemiga flotaEnemiga;
	private Algo42 algo42;
	private VistaAlgo42 vistaAlgo42;
	private ControladorAlgo42 controladorAlgo42;
	private ObservadorSalir observadorSalir;
	private Flota flota;
	private Map<Proyectil,VistaProyectil> mapaProyectiles;
	private Map<ActualizacionAlgo42,VistaActualizacion> mapaActualizaciones;
	private Map<NaveViva,VistaNave> mapaNaves;
	private Map<ObjetoDeTexto,Texto> mapaTexto;
	private Map<Coordenada,Imagen> mapaHUD;
	private VistaFondoNivel vistaFondo;	
	private int puntaje;
	private String nombreNivel;
	private EstadoNivel estado;
	
	public enum EstadoNivel{ JUGANDO, TERMINADO, ALGO42MUERTO};
	
	
	public void setControlador(ControladorJuego unControlador){
		this.controlador = unControlador;
	}
	
	public ControladorNivel(String pathArchivoNivel, ControladorJuego unControlador){
		
		this.controlador = unControlador;
		this.cargarNivel(pathArchivoNivel);
				
	}
	
	/**
	 * Carga un nivel al ControladorNivel.
	 * @param pathArchivoNivel El string con la ruta y nombre del nivel a cargar.
	 */
	public void cargarNivel(String pathArchivoNivel){
		try {
			estado = EstadoNivel.JUGANDO;
			this.nombreNivel = pathArchivoNivel;
			this.zona = new ZonaCombate(600, 800);
			this.puntaje = 0;
			this.observadorSalir = new ObservadorSalir(this.controlador);
			
			algo42 = new Algo42(zona, 250, 550);
			zona.agregarAlgo42(algo42);

				
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(pathArchivoNivel));
			Element flotas = doc.getDocumentElement();
			
			NodeList childs = flotas.getChildNodes();
			
			for (int i = 0; i < childs.getLength(); i++) {
				
				Node child = childs.item(i);			
				
				if (child.getNodeName().equals("Flota")) {
					Flota flota1 =  Flota.fromElement((Element)child, this.zona);
					this.zona.agregarFlotaAliada(flota1); 
					
				} else if (child.getNodeName().equals("FlotaEnemiga")) {
					
					FlotaEnemiga flota1 =  FlotaEnemiga.fromElement((Element)child, this.zona);
					this.zona.agregarFlotaEnemiga(flota1);										
				}						
			}
			
			for(NaveViva nave: this.zona.getFlotaAliada().getListaAviones()){
				nave.setZonaCombate(this.zona);
			}
			
			for(NaveViva nave: this.zona.getFlotaEnemiga().getListaAviones()){
				nave.setZonaCombate(this.zona);
			}
			
			completarNivelAPartirDeZonaCombate();	

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  agrega dibujables nuevos que corresponden a los proyectiles y actualizaciones
	 *  que se generan dentro de la zonaCombate.
	 */
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
	
	/**
	 *  quita los dibujables de proyectiles y actualizaciones muertas.
	 */
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
		
	}

	
	/**
	 * Accion que se va a ejecutar en el loop de comenzarJuego() del titiritero.
	 */
	@Override
	public void ejecutarAccion() {
		this.agregarDibujablesNuevos();
		this.quitarDibujablesObsoletos();	
		puntaje += this.zona.reportarPuntosBajas();
		// Cuando se tiene 1000 puntos termina el nivel
		if  (puntaje> 1000) {
			this.controlador.detenerJuego();
			estado = EstadoNivel.TERMINADO;
		}
		
		if (! this.algo42.estaVivo()){
			this.controlador.detenerJuego();
			estado = EstadoNivel.ALGO42MUERTO;
		}
	}	
	
	public EstadoNivel getEstadoNivel(){
		return estado;
	}
	
	/**
	 * Devuelve el nombre del nivel
	 * @return Un string con el nombre del nivel
	 */
	public String getNombre(){
		return this.nombreNivel;
	}
		
	
	/**
	 * carga el nivel actual en el ControladorJuego.
	 */
	public void cargar(){
		controlador.agregarAccion(this);
		controlador.agregarKeyPressObservador(controladorAlgo42);
		controlador.agregarKeyPressObservador(observadorSalir);
		
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
		
		Set<Coordenada> sHUD = mapaHUD.keySet();
		for (Coordenada coord : sHUD){
			Imagen imag = mapaHUD.get(coord);
			controlador.agregarDibujable(imag);
		}
		
	}
	
	/**
	 * quita el nivel actual del ControladorJuego.
	 */
	public void descargar(){
		this.controlador.removerTodosDibujables();
		this.controlador.removerTodosObjetosVivos();
		this.controlador.removerAccion(this);
		this.controlador.removerKeyPressObservador(controladorAlgo42);
		this.controlador.removerKeyPressObservador(observadorSalir);
	}
	
	
	public int getPuntaje(){
		return this.puntaje;
	}
	
	public void setPuntaje(int puntaje){
		this.puntaje = puntaje;
	}
	
	
	/**
	 * guarda el juego actual en un archivo.
	 * @param pathGuardado String con el nombre y la ruta del archivo donde se va a guardar.
	 */
	public void guardarJuego(String pathGuardado){
		try{
			System.out.print("guardando \n");
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
						
			Element nivel = this.getElement(doc); 			
			doc.appendChild(nivel);
		
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new PrintStream(pathGuardado)));
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * carga un juego guardado al ControladorNivel.
	 * @param pathJuegoGuardado String con el nombre y la ruta del archivo a cargar.
	 * @param unControlador El controlador al que se va a cargar el juego guardado.
	 */
	public void cargarJuego(String pathJuegoGuardado, ControladorJuego unControlador){
		
		try {
			
			this.controlador = unControlador;
				
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(pathJuegoGuardado));
			Element nivel = doc.getDocumentElement();
			
			NodeList childs = nivel.getChildNodes();
			
			for (int i = 0; i < childs.getLength(); i++){
				Node child = childs.item(i);
				if (child.getNodeName().equals("Puntaje")){
					this.puntaje = Integer.parseInt(child.getTextContent());
				} 
				else if (child.getNodeName().equals("ZonaCombate")){
						this.zona = ZonaCombate.fromElement((Element)child);				
				}
				else if (child.getNodeName().equals("NombreNivel")){
					this.nombreNivel = child.getTextContent();
				}
			}
			
			completarNivelAPartirDeZonaCombate();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Element getElement(Document doc){
		
		Element nivel = doc.createElement("Nivel");
		
		Element puntaje = doc.createElement("Puntaje");
		nivel.appendChild(puntaje);
		puntaje.setTextContent(String.valueOf(this.puntaje));
		
		Element nombre = doc.createElement("NombreNivel");
		nivel.appendChild(nombre);
		nombre.setTextContent(String.valueOf(this.nombreNivel));
		
		nivel.appendChild(this.zona.getElement(doc));
		
		return nivel;
	}
	
	/**
	 * metodo auxiliar para cargar los elementos al ControladorNivel.
	 */
	private void completarNivelAPartirDeZonaCombate(){
		
		mapaProyectiles = new HashMap<Proyectil, VistaProyectil>();
		mapaActualizaciones = new HashMap<ActualizacionAlgo42, VistaActualizacion>();
		mapaNaves = new HashMap<NaveViva,VistaNave>();
		mapaTexto = new HashMap<ObjetoDeTexto,Texto>();
		mapaHUD =  new HashMap<Coordenada,Imagen>();
					
		vistaFondo = new VistaFondoNivel();
		Coordenada coord = new Coordenada(0,0,1);
		vistaFondo.setPosicionable(coord);
		vistaFondo.setNombreArchivoImagen("/media/fondo1.jpg");
		
		algo42 = zona.getAlgo42();
		vistaAlgo42 = new VistaAlgo42();
		vistaAlgo42.setPosicionable(algo42);
		controladorAlgo42 = new ControladorAlgo42(algo42);
		
		this.flota = this.zona.getFlotaAliada();
		this.flotaEnemiga = this.zona.getFlotaEnemiga();
		
		agregarVistas();
		
		Coordenada hud = new Coordenada(670,40,1);
		Imagen vistaHUD = new Imagen();
		vistaHUD.setNombreArchivoImagen("/media/hud.png");
		vistaHUD.setPosicionable(hud);
		mapaHUD.put(hud, vistaHUD);
		
		TextoCantEnergia textoEnergia = new TextoCantEnergia(this.algo42);
		Coordenada cTextEnergia = new Coordenada(755,50,1);
		TextoDinamico vistaTextEnergia = new TextoDinamico(textoEnergia, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextEnergia.setPosicionable(cTextEnergia);
		mapaTexto.put(textoEnergia, vistaTextEnergia);
		
		TextoCantCohetes textoCohetes = new TextoCantCohetes(this.algo42);
		Coordenada cTextCohetes = new Coordenada(755,73,1);
		TextoDinamico vistaTextCohetes = new TextoDinamico(textoCohetes, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextCohetes.setPosicionable(cTextCohetes);
		mapaTexto.put(textoCohetes, vistaTextCohetes);
		
		TextoCantTorpedos textoTorpedos = new TextoCantTorpedos(this.algo42);
		Coordenada cTextTorpedos = new Coordenada(755,95,1);
		TextoDinamico vistaTextTorpedos = new TextoDinamico(textoTorpedos, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextTorpedos.setPosicionable(cTextTorpedos);
		mapaTexto.put(textoTorpedos, vistaTextTorpedos);
		
		Coordenada puntajeHud = new Coordenada(30,40,1);
		Imagen vistaPuntajeHUD = new Imagen();
		vistaPuntajeHUD.setNombreArchivoImagen("/media/hudPuntos.png");
		vistaPuntajeHUD.setPosicionable(puntajeHud);
		mapaHUD.put(puntajeHud, vistaPuntajeHUD);

		
		TextoPuntaje textPuntaje = new TextoPuntaje(this);
		Coordenada cTextPuntaje = new Coordenada(50,65,1);
		TextoDinamico vistaTextPuntaje = new TextoDinamico(textPuntaje, Color.WHITE, new Font("Serif",Font.BOLD,16));
		vistaTextPuntaje.setPosicionable(cTextPuntaje);
		mapaTexto.put(textPuntaje, vistaTextPuntaje);
		
	}
	
	/**
	 * Metodo auxiliar para agregar las vistas de los elementos del mapa
	 * luego de que se levanto un archivo guardado.
	 */
	private void agregarVistas(){
		VistaAvion vistaAvion;
		VistaActualizacion actVista;
		VistaProyectil proyeVista;
		
		List<Proyectil> lProyectil = zona.getProyectiles();
		List<ActualizacionAlgo42> lAct = zona.getActualizaciones();
		List<NaveViva> listaAviones1 = this.flota.getListaAviones();
		List<NaveVivaEnemiga> listaAviones2 = this.flotaEnemiga.getListaAviones();
		
		for (NaveViva avion : listaAviones1){
			vistaAvion = new VistaAvion(avion);
			mapaNaves.put(avion, vistaAvion);		
		}
		
		for (NaveVivaEnemiga avion : listaAviones2){
			vistaAvion = new VistaAvion(avion);
			mapaNaves.put(avion, vistaAvion);			
		}
		for (ActualizacionAlgo42 actualizacion : lAct){
			if (!mapaActualizaciones.containsKey(actualizacion)){
				actVista = new VistaActualizacion(actualizacion);
				mapaActualizaciones.put(actualizacion, actVista);
			}
		}
		if (!lProyectil.isEmpty()){
			synchronized(lProyectil){
				for (Proyectil proyectil : lProyectil){
					if (!mapaProyectiles.containsKey(proyectil)){
						proyeVista = new VistaProyectil(proyectil);
						mapaProyectiles.put(proyectil, proyeVista);
					}
				}	
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
