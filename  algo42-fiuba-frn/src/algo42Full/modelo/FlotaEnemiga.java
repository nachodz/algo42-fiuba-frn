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
	
	public FlotaEnemiga(){
		this.listaAviones = new ArrayList<NaveVivaEnemiga>();
		this.puntosBajas = 0;
		this.avionGuia = null;
	}
	
	public void agregarAvion(NaveVivaEnemiga avionEnemigo){
		this.listaAviones.add(avionEnemigo);
	}
	
	
	public void agregarAvionGuia(NaveVivaEnemiga avionGuia){
		this.avionGuia = avionGuia;
	}
	
	public void quitarBajas(){
		
		List<NaveVivaEnemiga> tempLista = new ArrayList<NaveVivaEnemiga>();
		for (NaveVivaEnemiga avion : this.listaAviones){
			if(avion.estaVivo()) tempLista.add(avion);
			else this.puntosBajas += avion.obtenerPuntos();
		}
		this.listaAviones = tempLista;
		if (this.avionGuia != null)
			if (!this.avionGuia.estaVivo())
				this.puntosBajas += avionGuia.obtenerPuntos(); 
		
	}
	
	public int reportarPuntosBajas(){
		int puntos = this.puntosBajas;
		this.puntosBajas = 0;
		return puntos;
	}
	

	public void vivir(){
		
		if (this.avionGuia == null)
			for (NaveVivaEnemiga nave : this.listaAviones){
				nave.vivir();
			}
		else{
			for (NaveVivaEnemiga nave : this.listaAviones){
				if (this.avionGuia.estaVivo())
					nave.vivir();
				else
					nave.huir();
			}
		}
	}
	
	
	public Atacable comprobarColision(ObjetoColisionable objeto){
		
		if (this.avionGuia != null)
			if (this.avionGuia.huboColision(objeto)) return this.avionGuia;
		for (NaveVivaEnemiga nave : this.listaAviones){
			if (nave.huboColision(objeto)) return nave;
		}
		return null;
	}
	
	
	public Element getElement(Document doc) {
		Element flotaEnemiga = doc.createElement("FlotaEnemiga");
		
		Element atributos = doc.createElement("Atributos");
		flotaEnemiga.appendChild(atributos);
		
		Element puntosBajas = doc.createElement("PuntosBajas");
		atributos.appendChild(puntosBajas);
		puntosBajas.setTextContent(String.valueOf(this.puntosBajas));
		
		Element listaNavesVivasEnemigas = doc.createElement("ListaNavesVivasEnemigas");
		flotaEnemiga.appendChild(listaNavesVivasEnemigas);
		
		if(this.listaAviones != null){
			for (NaveVivaEnemiga avion : this.listaAviones){
				listaNavesVivasEnemigas.appendChild(avion.getElement(doc));
			}
		}

		
		Element avionGuia = doc.createElement("AvionGuia");
		flotaEnemiga.appendChild(avionGuia);
		avionGuia.appendChild(this.avionGuia.getElement(doc));

		return flotaEnemiga;
	}

	public static FlotaEnemiga fromElement(Element element, ZonaCombate zona) {
		
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
				}//fin for
			}//fin if
			
			else if (child.getNodeName().equals("ListaNavesVivasEnemigas")) {
				
				
				NodeList childsLevel2 = child.getChildNodes();  //lista de aviones
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los aviones
					Node childLevel3 = childsLevel2.item(h); //nodo que representa un avion de la lista
					
					if (childLevel3.getNodeName().equals("Avioneta")){
						flotaEnemiga.listaAviones.add(Avioneta.fromElement((Element)childLevel3, zona));
						
					}
					
					else if (childLevel3.getNodeName().equals("Bombardero")){
						flotaEnemiga.listaAviones.add(Bombardero.fromElement((Element)childLevel3, zona));
						
					}
					
					else if (childLevel3.getNodeName().equals("Caza")){
						flotaEnemiga.listaAviones.add(Caza.fromElement((Element)childLevel3, zona));
						
					}
					
					else if (childLevel3.getNodeName().equals("CazaII")){
						flotaEnemiga.listaAviones.add(CazaII.fromElement((Element)childLevel3, zona));
						
					}
					
					else if (childLevel3.getNodeName().equals("Explorador")){
						flotaEnemiga.listaAviones.add(Explorador.fromElement((Element)childLevel3, zona));
						
					}					
				}
			} //fin else if primero
			
			else if (child.getNodeName().equals("ListaNavesVivasEnemigas")) {
				NodeList childsLevel2 = child.getChildNodes();  //lista de aviones
				for (int h = 0; h < childsLevel2.getLength(); h++) { //itera entre los aviones
					Node childLevel3 = childsLevel2.item(h); //nodo que representa un avion de la lista
					
					if (childLevel3.getNodeName().equals("Avioneta")){
						flotaEnemiga.avionGuia = Avioneta.fromElement((Element)childLevel3, zona);
						
					}
					
					else if (childLevel3.getNodeName().equals("Bombardero")){
						flotaEnemiga.avionGuia = Bombardero.fromElement((Element)childLevel3, zona);
						
					}
					
					else if (childLevel3.getNodeName().equals("Caza")){
						flotaEnemiga.avionGuia = Caza.fromElement((Element)childLevel3, zona);
						
					}
					
					else if (childLevel3.getNodeName().equals("CazaII")){
						flotaEnemiga.avionGuia = CazaII.fromElement((Element)childLevel3, zona);
						
					}
					
					else if (childLevel3.getNodeName().equals("Explorador")){
						flotaEnemiga.avionGuia = Explorador.fromElement((Element)childLevel3, zona);
					}
				}
			}
			
			
		} //fin primer for
				


		return flotaEnemiga;
	}
	
	
	
}
