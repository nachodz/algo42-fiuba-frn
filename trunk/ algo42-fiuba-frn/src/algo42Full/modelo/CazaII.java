package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CazaII extends Caza implements Atacable{

	
	public CazaII(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate, posX, posY);		

	}	
	
	public void disparar(){
		
		ProyectilTorpedoAdaptable proyectilTorpedoAdaptable = new ProyectilTorpedoAdaptable((this.zonaDeCombate), true, (this.x), (this.y + 1));
		(this.zonaDeCombate).agregarProyectil(proyectilTorpedoAdaptable);
		
	}
	
	public Element getElement(Document doc) {
		/**
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto CazaII.
		 */
		Element cazaII = doc.createElement("CazaII");
		
		Element atributos = doc.createElement("Atributos");
		cazaII.appendChild(atributos);
		
		super.writeElement(atributos, doc);		
			
		Element frecuenciaDisparo = doc.createElement("FrecuenciaDisparo");
		atributos.appendChild(frecuenciaDisparo);
		frecuenciaDisparo.setTextContent(String.valueOf(this.frecuenciaDisparo));
		
		Element turnosDisparo = doc.createElement("TurnosDisparo");
		atributos.appendChild(turnosDisparo);
		turnosDisparo.setTextContent(String.valueOf(this.turnosDisparo));
		
		return cazaII;
	}

	public static CazaII fromElement(Element element, ZonaCombate zona) {
		/**
		 * Retorna un objeto del tipo CazaII, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		CazaII cazaII = new CazaII(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
		
				
		writeNaveVivaEnemiga((Element)variables, cazaII);

		NodeList childs = variables.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("FrecuenciaDisparo")) {
				cazaII.frecuenciaDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("TurnosDisparo")) {
				cazaII.turnosDisparo = Integer.parseInt(child.getTextContent());

		}
	}
		
		return cazaII;
	}
	
	

}
