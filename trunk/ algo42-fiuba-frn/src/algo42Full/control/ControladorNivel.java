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
import ar.uba.fi.algo3.titiritero.ControladorJuego;
import ar.uba.fi.algo3.titiritero.vista.*;

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
	
	///////
	
//	private void agregarVistaAviones(){
//		
//		VistaAvion vistaAvion;
//		
//		
//		List<NaveViva> listaAviones1 = this.flota.getListaAviones();
//		List<NaveVivaEnemiga> listaAviones2 = this.flotaEnemiga.getListaAviones();
//		
//		
//		for (NaveViva avion : listaAviones1){
//			vistaAvion = new VistaAvion(avion);
//			mapaNaves.put(avion, vistaAvion);
//			this.controlador.agregarDibujable(vistaAvion);			
//		}
//		
//		for (NaveVivaEnemiga avion : listaAviones2){
//			vistaAvion = new VistaAvion(avion);
//			mapaNaves.put(avion, vistaAvion);
//			this.controlador.agregarDibujable(vistaAvion);			
//		}
//
//	}
	
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
		
//		Set<NaveViva> setN = mapaNaves.keySet();
//		HashMap<NaveViva,VistaNave> nMap = new HashMap<NaveViva,VistaNave>();
//		for( NaveViva nave : setN){
//			VistaNave naveVista = mapaNaves.get(nave);
//			if (!nave.estaVivo())
//				this.controlador.removerDibujable(naveVista);
//			else
//				nMap.put(nave, naveVista);
//		}
//		mapaNaves.clear();
//		mapaNaves = nMap;
		
	}

	@Override
	public void ejecutarAccion() {
		this.agregarDibujablesNuevos();
		this.quitarDibujablesObsoletos();
//		if((this.flotaEnemiga.estaDestruida()) || (this.flota.estaDestruida())){
//			this.agregarVistaAviones();
//		}		
		puntaje += this.zona.reportarPuntosBajas();
		// Cuando se tiene 1000 puntos termina el nivel
		if  (puntaje> 10) {
			this.controlador.detenerJuego();
			estado = EstadoNivel.TERMINADO;
			System.out.print("GANE!!!!!");
		}
		
		if (! this.algo42.estaVivo()){
			this.controlador.detenerJuego();
			estado = EstadoNivel.ALGO42MUERTO;
			System.out.print("PERDISTE, SOS MALISIMO");
		}
	}	
	
	public EstadoNivel getEstadoNivel(){
		return estado;
	}
	
	public String getNombre(){
		return this.nombreNivel;
	}
		
	
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
		
	}
	
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
	
	private void completarNivelAPartirDeZonaCombate(){
		
		mapaProyectiles = new HashMap<Proyectil, VistaProyectil>();
		mapaActualizaciones = new HashMap<ActualizacionAlgo42, VistaActualizacion>();
		mapaNaves = new HashMap<NaveViva,VistaNave>();
		mapaTexto = new HashMap<ObjetoDeTexto,Texto>();
					
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
		
		//agregarVistaAviones();
		agregarVistas();
		//agregarDibujablesNuevos();
		
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
