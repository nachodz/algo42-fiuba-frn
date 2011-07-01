package algo42Full.modelo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * La flota se encarga de organizar y administrar un conjunto de aeronaves de
 * tipo NaveViva. De comprobar las colisiones entre sus naves, de marcar las 
 * naves destruidas, de sumar el puntaje de las naves destruidas,
 * de revivir las naves muertas cuando toda la flota esta destruida,
 * de indicarle a cada nave que actue y de llevar a cabo su persistencia.
 */

public class Flota {
	protected List<NaveViva> listaAviones;
	protected int puntosBajas;
	protected int cantidadNaves;	
	protected boolean flotaDestruida;
	
	
	/**
	 * Contructor de Flota
	 * 
	 */
	public Flota(){
		this.listaAviones = new ArrayList<NaveViva>();
		this.puntosBajas = 0;
		this.cantidadNaves = 0;
		this.flotaDestruida = false;
	}
	/**
	 * Agrega a la flota la aeronave pasada como parametro. 
	 * Agregando al contador de aviones de la flota una aeronave mas.
	 * @param avion
	 */
	public void agregarAvion(NaveViva avion){
		this.listaAviones.add(avion);
		this.cantidadNaves ++;
	}
	
	/**
	 * Se encarga de comprobar si se produjo una colision entre el objetoColisionable
	 * pasado como parametro y alguna aeronave de la flota.
	 * @param objeto
	 * @return devuelve un ObjetoAtacable de la flota si se prudujo una colision 
	 * 			con el objeto pasado como parametro, 
	 * 			si no retorna null.
	 */
	public Atacable comprobarColision(ObjetoColisionable objeto){
		/*
		 * Chequea si el objetoColisionable pasado como parametro, choca contra alguno
		 * de los aviones de la flota. En caso de ser verdadero, devuelve el avion
		 * con el que colisiona. En caso contrario, retorna null.
		 */
		for( NaveViva avion : this.listaAviones){
			if(avion.estaVivo()){
				if (avion.huboColision(objeto)) return avion;
			}			
		}
		return null;
	}
	
	/**
	 * El metodo se encarga de agregar los puntos de las aeronaves destruidas
	 * al contador de puntaje de la flota y las marca como relevadas.
	 * Si todas las naves de la flota se encuentran destruidas, la flota se marca como
	 * destruida.
	 */
	public void quitarBajas(){
		/*
		 * Comprueba los aviones de la flota destruidos, agregando sus puntos por destruccion
		 * a la flota si es que anteriormente no fuero relevados. 
		 * Si todos los aviones de la flota fueron destruidos, la flota se marca como destruida.
		 */
		
		int contador = 0;
		for (NaveViva avion : this.listaAviones){
			if(! avion.estaVivo()){
				contador++;
				if(! avion.fueRelevado()){
					this.puntosBajas += avion.obtenerPuntos();
					avion.setRelevado(true);
				}				
			}
			
		}
		if(contador == this.cantidadNaves){
			this.flotaDestruida = true;
		}
	}
	
	/**
	 * 
	 * @return true si la flota esta destruida, false si no lo esta.
	 */
	
	public boolean estaDestruida(){
		return this.flotaDestruida;
	}
	
	/**
	 * Se encarga de revivir todas las aeronaves de la flota, y les
	 * asigna una nueva posicion de salida de manera aleatoria.
	 */
	
	public void revivirFlota(){
	
		for (NaveViva avion : this.listaAviones){
			int posXSalida = (int) (Math.random()* (avion.zonaDeCombate.getAncho() - avion.getRadio()))+ avion.getRadio();
			avion.setPosInicialX(posXSalida);
			avion.setRelevado(false);
			avion.setMuerto(false);
			avion.revivir();
		}
		this.flotaDestruida = false;
	}
	
	/**
	 * Devuelve el puntaje y resetea su contador a cero.
	 * @return devuelve un entero que indica el puntaje acumulado
	 * de las aeronaves de la flota que fueron destruidas.
	 * 
	 */
	
	public int reportarPuntosBajas(){
		/*
		 * Retorna los puntos de los aviones destruidos, cuyos puntos no fueron reportados.
		 */
		int puntos = this.puntosBajas;
		this.puntosBajas = 0;
		return puntos;
	}
	
	/**
	 * Se encarga de que cada nave de la flota actue. 
	 *
	 */
	
	public void vivir(){
		for (NaveViva nave : this.listaAviones ){
			nave.vivir();
		}
	}
	
	public List<NaveViva> getListaAviones(){
		return this.listaAviones;
	}
	/**
	 * Persiste la flota en un Element que retorna.
	 * @param doc es el objeto de tipo Document en el cual estara
	 * 			el objeto de tipo Element que retorna el metodo.
	 * @return Retorna un Element perteneciente al Document pasado
	 * 		como parametro, en el que guardan todos los atributos
	 * 		del objeto Flota, incluyendo la lista de todos
	 * 		sus aviones.
	 */
	
	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto Flota, incluyendo la lista de todos
		 * sus aviones.
		 */
		Element flota = doc.createElement("Flota");
		
		Element atributos = doc.createElement("Atributos");
		flota.appendChild(atributos);
		
		Element puntosBajas = doc.createElement("PuntosBajas");
		atributos.appendChild(puntosBajas);
		puntosBajas.setTextContent(String.valueOf(this.puntosBajas));
		
		Element cantNaves = doc.createElement("CantidadNaves");
		atributos.appendChild(cantNaves);
		cantNaves.setTextContent(String.valueOf(this.cantidadNaves));
		
		Element flotaEstaDestruida = doc.createElement("FlotaDestruida");
		atributos.appendChild(puntosBajas);
		flotaEstaDestruida.setTextContent(String.valueOf(this.flotaDestruida));
		
		Element listaNavesVivas = doc.createElement("ListaNavesVivas");
		flota.appendChild(listaNavesVivas);
		
		if(this.listaAviones != null){
			
			for (NaveViva avion : this.listaAviones){
				listaNavesVivas.appendChild(avion.getElement(doc));
			}			
		}

		return flota;
	}

	/**
	 * Construye una flota a partir del Element y la zona de combate pasada 
	 * 			como parametro.
	 * @param element de tipo Element reprensenta a la flota desde la cual se
	 * 			creara el objeto de tipo Flota que se devulve.
	 * @param zona es la zona de combate en la cual estara la flota que se retorna.
	 * @return 	 Retorna un objeto del tipo Flota, con un estado interno cargado
	 * 				desde el Element pasado como parametro, incluyendo su lista de naves.
	 */
	public static Flota fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo Flota, con un estado interno cargado
		 * desde el Element pasado como parametro, incluyendo su lista de naves.
		 */
		Flota flota = new Flota();
		
		NodeList childs = element.getChildNodes(); //contiene atributos y lista de NavesVivas
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("Atributos")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de atributos
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los atributos
					Node childLevel3 = childsLevel2.item(h);
					if (childLevel3.getNodeName().equals("PuntosBajas")) {
						flota.puntosBajas = Integer.parseInt(childLevel3.getTextContent());
					}
					else if (childLevel3.getNodeName().equals("CantidadNaves")) {
						flota.cantidadNaves = Integer.parseInt(childLevel3.getTextContent());
					}
					
					else if (childLevel3.getNodeName().equals("FlotaDestruida")) {
						flota.flotaDestruida = Boolean.parseBoolean(childLevel3.getTextContent());
					}
				}//fin for
			}
			
			else if (child.getNodeName().equals("ListaNavesVivas")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de aviones
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los aviones
					Node childLevel3 = childsLevel2.item(h); //nodo que representa un avion de la lista
					if (childLevel3.getNodeName().equals("AvionCivil")) {
						flota.listaAviones.add(AvionCivil.fromElement((Element)childLevel3, zona));
					}					
					else if (childLevel3.getNodeName().equals("Helicoptero")){
						flota.listaAviones.add(Helicoptero.fromElement((Element)childLevel3, zona));
					}					
					else if (childLevel3.getNodeName().equals("Avioneta")){
						flota.listaAviones.add(Avioneta.fromElement((Element)childLevel3, zona));
					}					
					else if (childLevel3.getNodeName().equals("Bombardero")){
						flota.listaAviones.add(Bombardero.fromElement((Element)childLevel3, zona));						
					}					
					else if (childLevel3.getNodeName().equals("Caza")){
						flota.listaAviones.add(Caza.fromElement((Element)childLevel3, zona));						
					}					
					else if (childLevel3.getNodeName().equals("CazaII")){
						flota.listaAviones.add(CazaII.fromElement((Element)childLevel3, zona));						
					}					
					else if (childLevel3.getNodeName().equals("Explorador")){
						flota.listaAviones.add(Explorador.fromElement((Element)childLevel3, zona));						
					}					
				}
			} //fin else if primero
		} //fin primer for

		return flota;
	}		

}
