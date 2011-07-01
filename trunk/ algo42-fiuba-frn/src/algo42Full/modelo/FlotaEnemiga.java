package algo42Full.modelo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FlotaEnemiga{
	protected List<NaveVivaEnemiga> listaAviones;
	protected int puntosBajas;
	private NaveVivaEnemiga avionGuia;
	protected int cantidadNaves;	
	protected boolean flotaDestruida;
	
	public FlotaEnemiga(){
		this.listaAviones = new ArrayList<NaveVivaEnemiga>();
		this.puntosBajas = 0;
		this.avionGuia = null;
		this.cantidadNaves = 0;
		this.flotaDestruida = false;
	}
	
	public void agregarAvion(NaveVivaEnemiga avionEnemigo){
		this.listaAviones.add(avionEnemigo);
		this.cantidadNaves ++;
	}
	
	
	public void agregarAvionGuia(NaveVivaEnemiga avionGuia){
		this.avionGuia = avionGuia;
	}
	
	public void quitarBajas(){
		/*
		 * Comprueba los aviones de la flota destruidos, agregando sus puntos por destruccion
		 * a la flota si es que anteriormente no fuero relevados. 
		 * Si todos los aviones de la flota fueron destruidos, la flota se marca como destruida.
		 */
	
		int contador = 0;
		for (NaveVivaEnemiga avion : this.listaAviones){
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
	
	public boolean estaDestruida(){
		return this.flotaDestruida;
	}
	
	public List<NaveVivaEnemiga> getListaAviones(){
		return this.listaAviones;
	}
	
	public void revivirFlota(){
		/*
		 * Revive los aviones de la flota, asignandoles una nueva posicion
		 * de salida aleatoria y cambiando el avion guia de la flota.
		 */
		for (NaveVivaEnemiga avion : this.listaAviones){
			int posXSalida = (int) (Math.random()* (avion.zonaDeCombate.getAncho() - avion.getRadio()))+ avion.getRadio();
			avion.setPosInicialX(posXSalida);
			avion.setRelevado(false);
			avion.setMuerto(false);
			avion.revivir();
		}
		this.flotaDestruida = false;
		
		this.avionGuia.desmarcarComoGuia();
		int indice = (int) (Math.random()* (this.getListaAviones().size() - 1)+ 1);		
		NaveVivaEnemiga nave = this.getListaAviones().get(indice);
		nave.hacerGuia();
		this.agregarAvionGuia(nave);
	}
	
	public int reportarPuntosBajas(){
		int puntos = this.puntosBajas;
		this.puntosBajas = 0;
		return puntos;
	}
	

	public void vivir(){
		
		if (this.avionGuia == null){
			for (NaveVivaEnemiga nave : this.listaAviones){
				nave.vivir();
			}
		}
		else{
			if (this.avionGuia.estaVivo())
				for (NaveVivaEnemiga nave : this.listaAviones){
					nave.vivir();
				}
			else{
				for (NaveVivaEnemiga nave : this.listaAviones){
					nave.huir();
				
				}
			}
		}
	}	
	
	public Atacable comprobarColision(ObjetoColisionable objeto){
		/*
		 * Chequea si el objetoColisionable pasado como parametro, choca contra alguno
		 * de los aviones de la flota. En caso de ser verdadero, devuelve el avion
		 * con el que colisiona. En caso contrario, retorna null.
		 */
		
		for (NaveVivaEnemiga nave : this.listaAviones){
			if(nave.estaVivo()){
				if (nave.huboColision(objeto)) return nave;
			}			
		}
		return null;
	}
	
	
	public Element getElement(Document doc) {
		
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto FlotaEnemiga, incluyendo la lista de todos
		 * sus aviones.
		 */
		Element flotaEnemiga = doc.createElement("FlotaEnemiga");
		
		Element atributos = doc.createElement("Atributos");
		flotaEnemiga.appendChild(atributos);
		
		Element puntosBajas = doc.createElement("PuntosBajas");
		atributos.appendChild(puntosBajas);
		puntosBajas.setTextContent(String.valueOf(this.puntosBajas));
		
		Element cantNaves = doc.createElement("CantidadNaves");
		atributos.appendChild(cantNaves);
		cantNaves.setTextContent(String.valueOf(this.cantidadNaves));
		
		Element flotaEstaDestruida = doc.createElement("FlotaDestruida");
		atributos.appendChild(puntosBajas);
		flotaEstaDestruida.setTextContent(String.valueOf(this.flotaDestruida));
		
		Element listaNavesVivasEnemigas = doc.createElement("ListaNavesVivasEnemigas");
		flotaEnemiga.appendChild(listaNavesVivasEnemigas);
		
		if(this.listaAviones != null){
			for (NaveVivaEnemiga avion : this.listaAviones){
				listaNavesVivasEnemigas.appendChild(avion.getElement(doc));
			}
		}

		return flotaEnemiga;
	}

	public static FlotaEnemiga fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo FlotaEnemiga, con un estado interno cargado
		 * desde el Element pasado como parametro, incluyendo su lista de naves.
		 */
		
		FlotaEnemiga flotaEnemiga = new FlotaEnemiga();
		
		NodeList childs = element.getChildNodes(); //contiene atributos y lista de NavesVivas
		for (int i = 0; i < childs.getLength(); i++) {
			
			Node child = childs.item(i);
			if (child.getNodeName().equals("Atributos")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de atributos
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los atributos
					Node childLevel3 = childsLevel2.item(h);
					if (childLevel3.getNodeName().equals("PuntosBajas")) {
						flotaEnemiga.puntosBajas = Integer.parseInt(childLevel3.getTextContent());
					}
					else if (childLevel3.getNodeName().equals("CantidadNaves")) {
						flotaEnemiga.cantidadNaves = Integer.parseInt(childLevel3.getTextContent());
					}
					
					else if (childLevel3.getNodeName().equals("FlotaDestruida")) {
						flotaEnemiga.flotaDestruida = Boolean.parseBoolean(childLevel3.getTextContent());
					}
				}//fin for
			}//fin if
			
			else if (child.getNodeName().equals("ListaNavesVivasEnemigas")) {				
				
				NodeList childsLevel2 = child.getChildNodes();  //lista de aviones
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los aviones
					Node childLevel3 = childsLevel2.item(h); //nodo que representa un avion de la lista
					
					if (childLevel3.getNodeName().equals("Avioneta")){
						NaveVivaEnemiga unaNave = Avioneta.fromElement((Element)childLevel3, zona);
						flotaEnemiga.listaAviones.add(unaNave);
						if(unaNave.esGuia()){
							flotaEnemiga.agregarAvionGuia(unaNave);
						}						
					}					
					else if (childLevel3.getNodeName().equals("Bombardero")){
						NaveVivaEnemiga unaNave = Bombardero.fromElement((Element)childLevel3, zona);
						flotaEnemiga.listaAviones.add(unaNave);
						if(unaNave.esGuia()){
							flotaEnemiga.agregarAvionGuia(unaNave);
						}						
					}					
					else if (childLevel3.getNodeName().equals("Caza")){
						NaveVivaEnemiga unaNave = Caza.fromElement((Element)childLevel3, zona);
						flotaEnemiga.listaAviones.add(unaNave);
						if(unaNave.esGuia()){
							flotaEnemiga.agregarAvionGuia(unaNave);
						}						
					}					
					else if (childLevel3.getNodeName().equals("CazaII")){
						NaveVivaEnemiga unaNave = CazaII.fromElement((Element)childLevel3, zona);
						flotaEnemiga.listaAviones.add(unaNave);
						if(unaNave.esGuia()){
							flotaEnemiga.agregarAvionGuia(unaNave);
						}						
					}					
					else if (childLevel3.getNodeName().equals("Explorador")){
						NaveVivaEnemiga unaNave = Explorador.fromElement((Element)childLevel3, zona);
						flotaEnemiga.listaAviones.add(unaNave);
						if(unaNave.esGuia()){
							flotaEnemiga.agregarAvionGuia(unaNave);
						}						
					}					
				}
			} 			
		} 	

		return flotaEnemiga;
	}	
	
}
