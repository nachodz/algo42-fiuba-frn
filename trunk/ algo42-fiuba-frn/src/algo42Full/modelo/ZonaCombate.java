package algo42Full.modelo;


import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public class ZonaCombate implements ObjetoVivo{
	private int ancho;
	private int alto;
	private Algo42 algo42;
	private FlotaEnemiga flotaEnemiga;
	private Flota flotaAliada;
	private List<Proyectil> listaProyectiles;
	private List<ActualizacionAlgo42> listaActualizaciones; //////////////se cambio el tipo de la lista: Objeto Vivo por ActualizacionAlgo42
	
	public ZonaCombate(int alto,int ancho){
		this.alto = alto; // se podría chekear
		this.ancho = ancho;
		this.algo42 = null;
		this.flotaAliada = null;
		this.flotaEnemiga = null;
		this.listaActualizaciones = new ArrayList<ActualizacionAlgo42>();  /////////////////se cambio el tipo
		this.listaProyectiles = new ArrayList<Proyectil>();
		
	}
	
	public void agregarActualizacionAlgo42(ActualizacionAlgo42 actualizacion){          ///se cambio el tipo
		this.listaActualizaciones.add(actualizacion);
	}
	
	public void agregarAlgo42(Algo42 algo42){
		this.algo42 = algo42;	
	}
	
	public void agregarFlotaAliada(Flota flota){
		this.flotaAliada = flota;
	}
	
	public void agregarFlotaEnemiga(FlotaEnemiga flota){
		this.flotaEnemiga = flota;
	}
	
	public void agregarProyectil(Proyectil proyectil){
		synchronized (this.listaProyectiles){
			this.listaProyectiles.add(proyectil);
		}
	}
	

	public synchronized void combatir(){
		
		/////////////////////////////////////////
		//////////////////////////////////////
		/*agregar el delay para el revivir de las flotas
		 * 
		 */
		if(this.flotaAliada.estaDestruida()){
			this.flotaAliada.revivirFlota();
		}
		
		if(this.flotaEnemiga.estaDestruida()){
			this.flotaEnemiga.revivirFlota();
		}
		
		
		
		if(this.flotaAliada != null) this.flotaAliada.vivir();
		if(this.flotaEnemiga != null) this.flotaEnemiga.vivir();
		synchronized (this.listaProyectiles){
			for (Proyectil proyect: this.listaProyectiles){
				proyect.vivir();
			}
		}
		synchronized (this.listaActualizaciones){
		for (ObjetoVivo actualizacion: this.listaActualizaciones){
			actualizacion.vivir();
		}
		}
	}
	
	public synchronized Algo42 comprobarColisionAlgo42(ObjetoColisionable objeto){
		if(this.algo42.huboColision(objeto)) return this.algo42;
		else return null;
	}
	
	public Atacable comprobarColisionFlotaAliada(ObjetoColisionable objeto){
		if(this.flotaAliada != null){
			return this.flotaAliada.comprobarColision(objeto);
		}
		else return null;
	}
	
	public Atacable comprobarColisionFlotaEnemiga(ObjetoColisionable objeto){
		if(this.flotaEnemiga != null ){
			return this.flotaEnemiga.comprobarColision(objeto);
		}
		else return null;
	}
	
	public boolean comprobarSalidaZonaEx(ObjetoColisionable objeto){
		int x, y, radio;
		
		x = objeto.getX();
		y = objeto.getY();
		radio = objeto.getRadio();
		
		if (((x+radio)<=this.ancho) && (x-radio>=0) && (y+radio<=this.alto) && (y-radio>=0))
			return false;
		return true;
		
		}
		
		public boolean comprobarSalidaZona(ObjetoColisionable objeto){
			int derO, izqO, arribaO, abajoO;
			int derZ, izqZ, arribaZ, abajoZ;
			int x, y, radio;
			
			x = objeto.getX();
			y = objeto.getY();
			radio = objeto.getRadio();
			
			derO = x+radio;
			izqO = x-radio;
			arribaO = y-radio;
			abajoO = y+radio;
			derZ = this.ancho;
			izqZ = 0;
			arribaZ = 0;
			abajoZ = this.alto;
			
			if (abajoO < arribaZ)
				return true;
			if (arribaO > abajoZ)
				return true;
			if (derO < izqZ)
				return true;
			if (izqO > derZ)
				return true;
			return false;
		}
	
	public int getAlgo42PosX(){
		return this.algo42.getX();
	}
	
	public int getAlgo42PosY(){
		return this.algo42.getY();
	}
	
	public int getAncho(){
		return this.ancho;
	}
	
	public int getAlto(){
		return this.alto;
	}
	
	
	public void quitarObjetosMuertos(){
		
		List<ActualizacionAlgo42> tempLista  = new ArrayList<ActualizacionAlgo42>();         //se cambio el tipo de la lista
		for (ActualizacionAlgo42 actualizacion : this.listaActualizaciones){         //se cambio el tipo de la lista
			if (actualizacion.estaVivo()) tempLista.add(actualizacion);
		} 
		this.listaActualizaciones = tempLista;
		List<Proyectil> tempProyectiles = new ArrayList<Proyectil>();
		synchronized(this.listaProyectiles){
			for (Proyectil proyect : this.listaProyectiles){
				if (proyect.estaVivo()) tempProyectiles.add(proyect);
			}
		}
		this.listaProyectiles = tempProyectiles;
	
		if(this.flotaAliada != null) this.flotaAliada.quitarBajas();
		if(this.flotaEnemiga != null) this.flotaEnemiga.quitarBajas();
	}
	
	public int reportarPuntosBajas(){
		int puntosEnemigos,puntosAliados;
		
		if(this.flotaAliada != null) puntosAliados = this.flotaAliada.reportarPuntosBajas();
		else puntosAliados = 0;
		
		if(this.flotaEnemiga != null) puntosEnemigos = this.flotaEnemiga.reportarPuntosBajas();
		else puntosEnemigos = 0;
		
		
		int puntaje = (puntosEnemigos-puntosAliados);		
		return puntaje;	
	}
	


	
	public Element getElement(Document doc) {
		Element zonaCombate = doc.createElement("ZonaCombate");
		
		Element atributos = doc.createElement("Atributos");
		zonaCombate.appendChild(atributos);
		
		Element ancho = doc.createElement("Ancho");
		atributos.appendChild(ancho);
		ancho.setTextContent(String.valueOf(this.ancho));
		
		Element alto = doc.createElement("Alto");
		atributos.appendChild(alto);
		alto.setTextContent(String.valueOf(this.alto));
		
		@SuppressWarnings("unused")
		Element algo42 = doc.createElement("Algo42");
		zonaCombate.appendChild(this.algo42.getElement(doc));
		
		@SuppressWarnings("unused")
		Element flotaEnemiga = doc.createElement("FlotEnemiga");
		zonaCombate.appendChild(this.flotaEnemiga.getElement(doc));
		
		@SuppressWarnings("unused")
		Element flotaAliada = doc.createElement("FlotaAliada");
		zonaCombate.appendChild(this.flotaAliada.getElement(doc));
		
		Element listaProyectiles = doc.createElement("ListaProyectiles");
		zonaCombate.appendChild(listaProyectiles);
		
		if(this.listaProyectiles != null){
			for (Proyectil proyectil : this.listaProyectiles){
				listaProyectiles.appendChild(proyectil.getElement(doc));
			}
		}

				
		Element listaActualizaciones = doc.createElement("ListaActualizaciones");
		zonaCombate.appendChild(listaActualizaciones);
		
		if(this.listaActualizaciones != null){
			for (ActualizacionAlgo42 actualizacion : this.listaActualizaciones){   //se utilizo el tipo ActualizacionAlgo42 cambiada al comienzo
				listaActualizaciones.appendChild(actualizacion.getElement(doc)); ////////////
			}
				
		}
	
				

		return zonaCombate;
	}

	public static ZonaCombate fromElement(Element element) {
		ZonaCombate zonaCombate = new ZonaCombate(1, 1);
		
		NodeList childs = element.getChildNodes(); //contiene atributos y lista de NavesVivas
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("Atributos")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de atributos
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los atributos
					Node childLevel3 = childsLevel2.item(h);
					if (childLevel3.getNodeName().equals("Ancho")) {
						zonaCombate.ancho = Integer.parseInt(childLevel3.getTextContent());
					}
					
					else if (childLevel3.getNodeName().equals("Alto")) {
						zonaCombate.alto = Integer.parseInt(childLevel3.getTextContent());
					}
				}//fin for
			}
			
			else if (child.getNodeName().equals("Algo42")) {
				zonaCombate.algo42 = Algo42.fromElement((Element)child, zonaCombate);
			}
			
			else if (child.getNodeName().equals("FlotaEnemiga")) {
				zonaCombate.flotaEnemiga = FlotaEnemiga.fromElement((Element)child, zonaCombate);
			}
			
			else if (child.getNodeName().equals("Flota")) {
				zonaCombate.flotaAliada = Flota.fromElement((Element)child, zonaCombate);
			}
			
			else if (child.getNodeName().equals("ListaProyectiles")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de proyectiles
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los proyectiles
					Node childLevel3 = childsLevel2.item(h); //nodo que representa un proyectil de la lista
					if (childLevel3.getNodeName().equals("ProyectilCohete")) {
						(zonaCombate.listaProyectiles).add(new ProyectilCohete(((Element)childLevel3) , zonaCombate) );
					}
					
					else if (childLevel3.getNodeName().equals("ProyectilLaser")){
						(zonaCombate.listaProyectiles).add(new ProyectilLaser(((Element)childLevel3) , zonaCombate) );
						
					}
					
					else if (childLevel3.getNodeName().equals("ProyectilTorpedo")){
						(zonaCombate.listaProyectiles).add(new ProyectilTorpedo(((Element)childLevel3) , zonaCombate) );
						
					}
					
					else if (childLevel3.getNodeName().equals("ProyectilTorpedoAdaptable")){
						(zonaCombate.listaProyectiles).add(new ProyectilTorpedoAdaptable(((Element)childLevel3) , zonaCombate) );
						
					}
					
					else if (childLevel3.getNodeName().equals("ProyectilTorpedoSeguidor")){
						(zonaCombate.listaProyectiles).add(new ProyectilTorpedoSeguidor(((Element)childLevel3) , zonaCombate) );
						
					}					
										
				}
			} 
			
			else if (child.getNodeName().equals("ListaActualizaciones")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de actualizaciones
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre las actualizaciones
					Node childLevel3 = childsLevel2.item(h); //nodo que representa una actualizacion de la lista
					if (childLevel3.getNodeName().equals("Cohete")) {
						(zonaCombate.listaActualizaciones).add(new Cohete(((Element)childLevel3) , zonaCombate) );
					}
					
					else if (childLevel3.getNodeName().equals("TanqueEnergia")){
						(zonaCombate.listaActualizaciones).add(new TanqueEnergia(((Element)childLevel3) , zonaCombate) );
						
					}
					
					else if (childLevel3.getNodeName().equals("Torpedo")){
						(zonaCombate.listaActualizaciones).add(new Torpedo(((Element)childLevel3) , zonaCombate) );
						
					}
					
					
										
				}
			} 
		} 
				


		return zonaCombate;
	}
	
	
	@Override
	public void vivir() {
		this.combatir();
		this.quitarObjetosMuertos();
		
	}

	@Override
	public boolean estaVivo() {
		return true;
	}
	
	public List<Proyectil> getProyectiles(){
		return this.listaProyectiles;
	}
	
	public List<ActualizacionAlgo42> getActualizaciones(){
		return this.listaActualizaciones;
	}
	
	public Algo42 getAlgo42(){
		return this.algo42;
	}
	
	
	public FlotaEnemiga getFlotaEnemiga(){
		return this.flotaEnemiga;
	}
	
	public Flota getFlotaAliada(){
		return this.flotaAliada;
	}
	

}
