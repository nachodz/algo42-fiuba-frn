package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public abstract class NaveViva extends ObjetoColisionable implements Atacable, ObjetoVivo, ConvertibleAElement {
	
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected int energia;
	protected int puntos;
	protected ZonaCombate zonaDeCombate;
	
	public NaveViva(ZonaCombate zona,int x,int y,int radio,int velX,int velY){
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = velX;
		this.velY = velY;
		this.muerto = false;
		this.energia = 0;
		this.puntos = 0;
		this.zonaDeCombate = zona;
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int obtenerPuntos(){
		
		return puntos;
	}
	
	public void recibirDanio(int cantidadEnergia){
		
		int energiaTmp = (this.energia) - cantidadEnergia;
		if (energiaTmp < 1){
			
			this.energia = 0;
			this.muerto = true;
		}
		
		else {
			this.energia = energiaTmp;
		}
	}
	
	public void vivir(){}
	
	public Element getElement(Document doc){
		Element naveViva= doc.createElement("AvionCivil");
		
		return naveViva;
	}
	
	public void writeElement(Element unElement, Document unDoc) {
		
		super.writeElement(unElement, unDoc);
		
		Element muerto = unDoc.createElement("Muerto");
		unElement.appendChild(muerto);
		muerto.setTextContent(String.valueOf(this.muerto));
		
		Element velX = unDoc.createElement("VelX");
		unElement.appendChild(velX);
		velX.setTextContent(String.valueOf(this.velX));
		
		Element velY = unDoc.createElement("VelY");
		unElement.appendChild(velX);
		velY.setTextContent(String.valueOf(this.velY));
		
		Element posInicialX = unDoc.createElement("PosInicialX");
		unElement.appendChild(posInicialX);
		posInicialX.setTextContent(String.valueOf(this.posInicialX));
		
		Element posInicialY = unDoc.createElement("PosInicialY");
		unElement.appendChild(posInicialY);
		posInicialY.setTextContent(String.valueOf(this.posInicialY));

		Element energia = unDoc.createElement("Energia");
		unElement.appendChild(energia);
		energia.setTextContent(String.valueOf(this.energia));
		
		Element puntos = unDoc.createElement("Puntos");
		unElement.appendChild(puntos);
		puntos.setTextContent(String.valueOf(this.puntos));

//		Element zonaDeCombate = unDoc.createElement("ZonaDeCombate");
//		unElement.appendChild(zonaDeCombate);
//		zonaDeCombate.setTextContent(this.zonaDeCombate);
	}
	
	public static void writeNaveViva(Element element, NaveViva unaNaveViva) {
		
		writeObjetoColisionable(element, unaNaveViva);
		
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("Muerto")) {
				unaNaveViva.muerto = Boolean.parseBoolean(child.getTextContent());
			} else if (child.getNodeName().equals("VelX")) {
				unaNaveViva.velX = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("VelY")) {
				unaNaveViva.velY = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("PosInicialX")) {
				unaNaveViva.posInicialX = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("PosInicialY")) {
				unaNaveViva.posInicialY = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Energia")) {
				unaNaveViva.energia = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Puntos")) {
				unaNaveViva.puntos = Integer.parseInt(child.getTextContent());
			}
		}
		
		
	}

}
