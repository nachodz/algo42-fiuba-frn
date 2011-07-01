package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.uba.fi.algo3.titiritero.Posicionable;

public class ObjetoColisionable  implements Posicionable{
	protected int x;
	protected int y;
	protected int radio;
	
	public ObjetoColisionable(){};
	
	public ObjetoColisionable(int x,int y,int radio){
		this.x = x;
		this.y = y;
		this.radio = radio;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getRadio(){
		return this.radio;
	}
	
	public boolean huboColision(ObjetoColisionable objeto){
		int tempX,tempY,tempRadio;
		
		tempY = objeto.getY();
		tempX = objeto.getX();
		tempRadio = objeto.getRadio();
		
		return ( (Math.hypot((tempX-this.x), (tempY-this.y))) <(this.radio+tempRadio));
				
	}
	
	public void writeElement(Element unElement, Document unDoc) {
		/*
		 * Escribe en el Element pasado como parametro perteneciente al Document
		 * tambien parametro, todas las variables pertenecientes
		 * al tipo ObjetoColisionable.
		 */		
				
		/*
		 * atributos de ObjetoColisionable
		 */
		
		Element x = unDoc.createElement("X");
		unElement.appendChild(x);
		x.setTextContent(String.valueOf(this.x));
		
		Element y = unDoc.createElement("Y");
		unElement.appendChild(y);
		y.setTextContent(String.valueOf(this.y));
		
		Element radio = unDoc.createElement("Radio");
		unElement.appendChild(radio);
		radio.setTextContent(String.valueOf(this.radio));
	}
	
	public static void writeObjetoColisionable(Element element, ObjetoColisionable unColisionable) {
		/*
		 * Escribe en el Element pasado como parametro perteneciente al Document
		 * tambien parametro, todas las variables pertenecientes
		 * al tipo ObjetoColisionable
		 */		
		
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("X")) {
				unColisionable.x = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Y")) {
				unColisionable.y = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Radio")) {
				unColisionable.radio = Integer.parseInt(child.getTextContent());
			}
		}		
	}	
}
