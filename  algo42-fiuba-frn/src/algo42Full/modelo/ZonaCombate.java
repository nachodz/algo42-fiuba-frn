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
	private List<ActualizacionAlgo42> listaActualizaciones; 
	private int contadorRevivirFlotaAliada;
	private int contadorRevivirFlotaEnemiga;
	
	/**
	 * Constructor ZonaCombate
	 * @param alto es la dimension vertical de la zona de combate
	 * @param ancho es la dimension horizontal de la zona de combate
	 */
	public ZonaCombate(int alto,int ancho){
		this.alto = alto; 
		this.ancho = ancho;
		this.algo42 = null;
		this.flotaAliada = null;
		this.flotaEnemiga = null;
		this.listaActualizaciones = new ArrayList<ActualizacionAlgo42>();  
		this.listaProyectiles = new ArrayList<Proyectil>();
		this.contadorRevivirFlotaAliada = 0;
		this.contadorRevivirFlotaEnemiga = 0;
		
	}
	
	/**
	 * Agrega la actualizacion a la zona de combate
	 * @param actualizacion es el objeto de tipo ActualizacionAlgo42
	 * que se desea agregar a la lista de actualizaciones de la zona
	 * de combate
	 */
	
	public void agregarActualizacionAlgo42(ActualizacionAlgo42 actualizacion){         
		this.listaActualizaciones.add(actualizacion);
	}
	
	/**
	 * Agrega el avion de tipo Algo42 a la zona de combate
	 * @param algo42 es el avion de tipo Algo42 para agregar a la zona de combate
	 */
	
	public void agregarAlgo42(Algo42 algo42){
		this.algo42 = algo42;	
	}
	
	/**
	 * Agrega la flota a la zona de combate
	 * @param flota de tipo Flota es la flota a agregar
	 */
	
	public void agregarFlotaAliada(Flota flota){
		this.flotaAliada = flota;
	}
	
	/**
	 * Agrega la flotaEnemiga a la zona de combate
	 * @param flota de tipo FlotaEnemiga es la flota a agregar
	 */
	
	public void agregarFlotaEnemiga(FlotaEnemiga flota){
		this.flotaEnemiga = flota;
	}
	
	/**
	 * Agrega el proyectil a la zona de combate
	 * @param proyectil de tipo Proyectil que se va a agregar 
	 */
	
	public void agregarProyectil(Proyectil proyectil){
		synchronized (this.listaProyectiles){
			this.listaProyectiles.add(proyectil);
		}
	}
	
	/**
	 * Se encarga de que los objetos de la zona de combate vivan con toda su
	 * implicancia, los proyectiles, flotas, y actualizaciones. Que se lleven
	 * a cabo las colisiones que puedan producirse y de que los objetos muertos
	 * sean retirados de la zona de combate.
	 */
	
	@Override
	public void vivir() {
		this.combatir();
		this.quitarObjetosMuertos();		
	}

	@Override
	public boolean estaVivo() {
		return true;
	}
	
	/**
	 *  Se encarga de que las naves, actualizaciones y proyectiles de la zona
	 * de combate vivan. Si alguna flota se encuentra destruida, la revive.
	 */

	public synchronized void combatir(){
		/*
		 * Se encarga de que las naves, actualizaciones y proyectiles de la zona
		 * de combate vivan. Si alguna flota se encuentra destruida, la revive
		 * cuando el contador para revivir flotas alcanze el valor indicado.
		 */
		
		if (this.flotaAliada != null){
			if(this.flotaAliada.estaDestruida()){
				if(this.contadorRevivirFlotaAliada == 20){
					this.flotaAliada.revivirFlota();
					this.contadorRevivirFlotaAliada = 0;
				}
				else{
					this.contadorRevivirFlotaAliada ++;
				}
	
			}
		}
		
		if (this.flotaEnemiga != null){
			if(this.flotaEnemiga.estaDestruida()){
				if(this.contadorRevivirFlotaEnemiga == 30){
					this.flotaEnemiga.revivirFlota();
					this.contadorRevivirFlotaEnemiga = 0;
				}
				
				else{
					this.contadorRevivirFlotaEnemiga ++;
				}
			}
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
	
	/**
	 * Comprueba si el objeto pasado colisiona con el Algo42 
	 * @param objeto de tipo ObjetoColisionable objeto para comprobar la
	 * 		colision con el algo42
	 * @return retorna el Algo42 si hubo colision con el objeto pasado, en
	 * 			caso contrario retorna null.
	 */
	
	public synchronized Algo42 comprobarColisionAlgo42(ObjetoColisionable objeto){
		if(this.algo42.huboColision(objeto)) return this.algo42;
		else return null;
	}
	
	/**
	 * Comprueba si hubo colision entre el objeto pasado como parametro y 
	 * alguna aeronave de la flota aliada de la zona.
	 * @param objeto de tipo ObjetoColisionable con el cual comprobar
	 * la colision.
	 * @return retorna una aeronave de la flota con la que hubo colision,
	 * 			retorna null si no hubo colision con las naves de la flota.
	 */
	
	public Atacable comprobarColisionFlotaAliada(ObjetoColisionable objeto){
		if(this.flotaAliada != null){
			return this.flotaAliada.comprobarColision(objeto);
		}
		else return null;
	}
	
	/**
	 * Comprueba si hubo colision entre el objeto pasado como parametro y 
	 * alguna aeronave de la flota enemiga de la zona.
	 * @param objeto de tipo ObjetoColisionable con el cual comprobar
	 * la colision.
	 * @return retorna una aeronave de la flota enemigacon la que hubo colision,
	 * 			retorna null si no hubo colision con las naves de la flota.
	 */
	
	public Atacable comprobarColisionFlotaEnemiga(ObjetoColisionable objeto){
		if(this.flotaEnemiga != null ){
			return this.flotaEnemiga.comprobarColision(objeto);
		}
		else return null;
	}
	
	/**
	 * 
	 * @param objeto de tipo ObjetoColisionable para el cual comprobar si
	 * 		ha salido o no de la zona de combate.
	 * @return retorna false si algun punto de el objeto colisionable esta
	 * 				fuera de la zona de combate
	 */
	
	public boolean comprobarSalidaZonaEx(ObjetoColisionable objeto){
		/*
		 * Devuelve false si algun punto de el objeto colisionable esta
		 * fuera de la zona de combate
		 */
		int x, y, radio;
		
		x = objeto.getX();
		y = objeto.getY();
		radio = objeto.getRadio();
		
		if (((x+radio)<=this.ancho) && (x-radio>=0) && (y+radio<=this.alto) && (y-radio>=0))
			return false;
		return true;
		
		}
	
	/**
	 * 
	 * @param objeto de tipo ObjetoColisionable para el cual comprobar si
	 * 		ha salido o no de la zona de combate.
	 * @return retorna false si el objeto colisionable esta
	 * 				fuera de la zona de combate
	 */
		
		public boolean comprobarSalidaZona(ObjetoColisionable objeto){
			/*
			 * 
			 */
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
	
	/**
	 * 	Elimina las actualizacione y los proyectiles muertos de la zona de combate,
	 * 		y llama a quitarBajas() de las flotas.
	 */
	
	
	public void quitarObjetosMuertos(){
		/*
		 * Elimina las actualizacione y los proyectiles muertos de la zona de combate,
		 * y llama a quitarBajas() de las flotas.
		 */
		
		List<ActualizacionAlgo42> tempLista  = new ArrayList<ActualizacionAlgo42>();         
		for (ActualizacionAlgo42 actualizacion : this.listaActualizaciones){         
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
	
	/**
	 * retorna el puntaje de los aviones que fueron destruidos y resetea
	 * los contadores de puntaje de las flotas a cero.
	 * @return retorna un entero que es el saldo entre el puntaje de los 
	 * aviones de la flota aliada y enemiga que fueron destruidos
	 */
	
	public int reportarPuntosBajas(){
		/*
		 * retorna el puntaje de los aviones que fueron destruidos
		 */
		int puntosEnemigos,puntosAliados;
		
		if(this.flotaAliada != null) puntosAliados = this.flotaAliada.reportarPuntosBajas();
		else puntosAliados = 0;
		
		if(this.flotaEnemiga != null) puntosEnemigos = this.flotaEnemiga.reportarPuntosBajas();
		else puntosEnemigos = 0;		
		
		int puntaje = (puntosEnemigos-puntosAliados);		
		return puntaje;	
	}
	
	/**
	 * Persiste a la zona de combate en un objeto de tipo Element
	 * 	que retorna
	 * @param doc es el objeto de tipo Document en el cual estara
	 * 			el objeto de tipo Element que retorna el metodo.
	 * @return 	 Retorna un Element perteneciente al Document pasado
	 * 		como parametro, en el que guardan todos los atributos
	 * 		del objeto ZonaCombate, que incluye sus flotas, listas
	 * 		de proyectiles y actualizaciones.
	 */
	
	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto ZonaCombate, que incluye sus flotas, listas
		 * de proyectiles y actualizaciones.
		 */
		Element zonaCombate = doc.createElement("ZonaCombate");
		
		Element atributos = doc.createElement("Atributos");
		zonaCombate.appendChild(atributos);
		
		Element ancho = doc.createElement("Ancho");
		atributos.appendChild(ancho);
		ancho.setTextContent(String.valueOf(this.ancho));
		
		Element alto = doc.createElement("Alto");
		atributos.appendChild(alto);
		alto.setTextContent(String.valueOf(this.alto));
		
		Element contadorRevivirFlotaAliada = doc.createElement("ContadorRevivirFlotaAliada");
		atributos.appendChild(contadorRevivirFlotaAliada);
		contadorRevivirFlotaAliada.setTextContent(String.valueOf(this.contadorRevivirFlotaAliada));
		
		Element contadorRevivirFlotaEnemiga = doc.createElement("ContadorRevivirFlotaEnemiga");
		atributos.appendChild(contadorRevivirFlotaEnemiga);
		contadorRevivirFlotaEnemiga.setTextContent(String.valueOf(this.contadorRevivirFlotaEnemiga));
		
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
				if(! this.comprobarSalidaZona(proyectil)){
					listaProyectiles.appendChild(proyectil.getElement(doc));
				}				
			}
		}
				
		Element listaActualizaciones = doc.createElement("ListaActualizaciones");
		zonaCombate.appendChild(listaActualizaciones);
		
		if(this.listaActualizaciones != null){
			for (ActualizacionAlgo42 actualizacion : this.listaActualizaciones){   //se utilizo el tipo ActualizacionAlgo42 cambiada al comienzo
				if(! this.comprobarSalidaZona(actualizacion)){
					listaActualizaciones.appendChild(actualizacion.getElement(doc)); ////////////
				}				
			}				
		}				

		return zonaCombate;
	}
	
	/**
	 * 	Construye una zona de combate a partir del Element 
	 * 	 pasado como parametro.
	 * @param element  de tipo Element reprensenta a la zona de combate
	 *  desde la cual se creara el objeto de tipo ZonaCombate que se devulve.
	 * @return Retorna un objeto del tipo ZonaCombate, con un estado interno cargado
	*		 desde el Element pasado como parametro.
	 */

	public static ZonaCombate fromElement(Element element) {
		/*
		 * Retorna un objeto del tipo ZonaCombate, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
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
					else if (childLevel3.getNodeName().equals("ContadorRevivirFlotaAliada")) {
						zonaCombate.contadorRevivirFlotaAliada = Integer.parseInt(childLevel3.getTextContent());
					}
					else if (childLevel3.getNodeName().equals("ContadorRevivirFlotaEnemiga")) {
						zonaCombate.contadorRevivirFlotaEnemiga = Integer.parseInt(childLevel3.getTextContent());
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
						(zonaCombate.listaProyectiles).add(ProyectilCohete.fromElement(((Element)childLevel3) , zonaCombate) );					}					
					else if (childLevel3.getNodeName().equals("ProyectilLaser")){
						(zonaCombate.listaProyectiles).add(ProyectilLaser.fromElement(((Element)childLevel3) , zonaCombate) );						
					}					
					else if (childLevel3.getNodeName().equals("ProyectilTorpedo")){
						(zonaCombate.listaProyectiles).add(ProyectilTorpedo.fromElement(((Element)childLevel3) , zonaCombate) );						
					}					
					else if (childLevel3.getNodeName().equals("ProyectilTorpedoAdaptable")){
						(zonaCombate.listaProyectiles).add(ProyectilTorpedoAdaptable.fromElement(((Element)childLevel3) , zonaCombate) );						
					}					
					else if (childLevel3.getNodeName().equals("ProyectilTorpedoSeguidor")){
						(zonaCombate.listaProyectiles).add(ProyectilTorpedoSeguidor.fromElement(((Element)childLevel3) , zonaCombate) );						
					}										
				}
			} 			
			else if (child.getNodeName().equals("ListaActualizaciones")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de actualizaciones
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre las actualizaciones
					Node childLevel3 = childsLevel2.item(h); //nodo que representa una actualizacion de la lista
					if (childLevel3.getNodeName().equals("Cohete")) {
						(zonaCombate.listaActualizaciones).add(Cohete.fromElement(((Element)childLevel3) , zonaCombate) );
					}					
					else if (childLevel3.getNodeName().equals("TanqueEnergia")){
						(zonaCombate.listaActualizaciones).add(TanqueEnergia.fromElement(((Element)childLevel3) , zonaCombate) );						
					}					
					else if (childLevel3.getNodeName().equals("Torpedo")){
						(zonaCombate.listaActualizaciones).add(Torpedo.fromElement(((Element)childLevel3) , zonaCombate) );						
					}										
				}
			} 
		} 

		return zonaCombate;
	}
	
	

	

}
