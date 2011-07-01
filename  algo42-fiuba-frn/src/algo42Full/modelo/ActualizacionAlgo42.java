package algo42Full.modelo;

//import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import algo42Full.modelo.excepciones.ObjetoFueraDeZonaDeCombateException;
import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public abstract class ActualizacionAlgo42 extends ObjetoColisionable implements ObjetoVivo, ConvertibleAElement {
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected ZonaCombate zonaDeCombate;
	
	
	public ActualizacionAlgo42 (ZonaCombate zona, int x, int y,int radio){
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = 0;
		this.velY = 2;
		this.muerto = false;
		if (zona.comprobarSalidaZona(this))
			throw new ObjetoFueraDeZonaDeCombateException();
		else
			zonaDeCombate = zona;
	}
	
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	protected void mover(){
		if (!muerto) {
			this.y = this.y + velY;
			if (zonaDeCombate.comprobarSalidaZona(this))
					muerto = true;
		}
	}
	
	public void vivir(){}
	
	
	
	public void writeElement(Element actualizacion, Document doc){
		/*
		 * Escribe en el Element pasado como parametro perteneciente al Document
		 * tambien parametro, todas las variables pertenecientes
		 * al tipo ActualizacionAlgo42
		 */
		
		Element x = doc.createElement("X");
		actualizacion.appendChild(x);
		x.setTextContent(String.valueOf(this.x));
		
		Element y = doc.createElement("Y");
		actualizacion.appendChild(y);
		y.setTextContent(String.valueOf(this.y));
		
		Element radio = doc.createElement("Radio");
		actualizacion.appendChild(radio);
		radio.setTextContent(String.valueOf(this.radio));
		
		Element muerto = doc.createElement("Muerto");
		actualizacion.appendChild(muerto);
		muerto.setTextContent(Boolean.toString(this.muerto));

		Element velX = doc.createElement("VelocidadX");
		actualizacion.appendChild(velX);
		velX.setTextContent(Integer.toString(this.velX));
		
		Element velY = doc.createElement("VelocidadY");
		actualizacion.appendChild(velY);
		velY.setTextContent(Integer.toString(this.velY));
		
		Element posInicialX = doc.createElement("PosicionInicialX");
		actualizacion.appendChild(posInicialX);
		posInicialX.setTextContent(Integer.toString(this.posInicialX));
		
		Element posInicialY = doc.createElement("PosicionInicialY");
		actualizacion.appendChild(posInicialY);
		posInicialY.setTextContent(Integer.toString(this.posInicialY));
	}
	
	
	public static void writeActualizacion(Element element, ActualizacionAlgo42 unaActualizacion) {
		/*
		 * Escribe en el objeto de tipo ActualizacionAlgo42, los atributos que se
		 * encuentran dentro del Element pasado como parametro.
		 */
			
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("Muerto")) {
				unaActualizacion.muerto = Boolean.parseBoolean(child.getTextContent());				
			}else if (child.getNodeName().equals("X")) {
				unaActualizacion.x = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Y")) {				
				unaActualizacion.y = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Radio")) {
				unaActualizacion.radio = Integer.parseInt(child.getTextContent());
			}else if (child.getNodeName().equals("VelocidadX")) {
				unaActualizacion.velX = Integer.parseInt(child.getTextContent());
			}else if (child.getNodeName().equals("VelocidadY")) {
				unaActualizacion.velY = Integer.parseInt(child.getTextContent());
			}else if (child.getNodeName().equals("PosicionInicialX")) {
				unaActualizacion.posInicialX = Integer.parseInt(child.getTextContent());   
			}else if (child.getNodeName().equals("PosicionInicialY")) {
				unaActualizacion.posInicialY = Integer.parseInt(child.getTextContent());
			}
		}
	}
	
}
