package algo42Full.modelo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Flota {
	protected List<NaveViva> listaAviones;
	protected int puntosBajas;
	protected int cantidadNaves;	
	protected boolean flotaDestruida;
	
	public Flota(){
		this.listaAviones = new ArrayList<NaveViva>();
		this.puntosBajas = 0;
		this.cantidadNaves = 0;
		this.flotaDestruida = false;
	}
	
	public void agregarAvion(NaveViva avion){
		this.listaAviones.add(avion);
		this.cantidadNaves ++;
	}
	
	public Atacable comprobarColision(ObjetoColisionable objeto){
		for( NaveViva avion : this.listaAviones){
			if(avion.estaVivo()){
				if (avion.huboColision(objeto)) return avion;
			}
			
		}
		return null;
	}
	
	public void quitarBajas(){
		
		//List<NaveViva> tempLista = new ArrayList<NaveViva>();
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
	
	public boolean estaDestruida(){
		return this.flotaDestruida;
	}
	
	public void revivirFlota(){
		for (NaveViva avion : this.listaAviones){
			avion.setRelevado(false);
			avion.setMuerto(false);
			avion.revivir();
		}
		this.flotaDestruida = false;
	}
	
	public int reportarPuntosBajas(){
		int puntos = this.puntosBajas;
		this.puntosBajas = 0;
		return puntos;
	}
	
	public void vivir(){
		for (NaveViva nave : this.listaAviones ){
			nave.vivir();
		}
	}
	
	public Element getElement(Document doc) {
		Element flota = doc.createElement("Flota");
		
		Element atributos = doc.createElement("Atributos");
		flota.appendChild(atributos);
		
		Element puntosBajas = doc.createElement("PuntosBajas");
		atributos.appendChild(puntosBajas);
		puntosBajas.setTextContent(String.valueOf(this.puntosBajas));
		
		Element listaNavesVivas = doc.createElement("ListaNavesVivas");
		flota.appendChild(listaNavesVivas);
		
		if(this.listaAviones != null){
			
			for (NaveViva avion : this.listaAviones){
				listaNavesVivas.appendChild(avion.getElement(doc));
			}
			
		}


		return flota;
	}

	public static Flota fromElement(Element element, ZonaCombate zona) {
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
	
	
	
	public List<NaveViva> getListaAviones(){
		return this.listaAviones;
	}
	

}
