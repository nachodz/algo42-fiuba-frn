package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class AvionCivil extends NaveViva{
	
	public AvionCivil(ZonaCombate unaZonaDeCombate, int x, int y){
		super(unaZonaDeCombate,x,y,25,0,2,1);
		this.puntos = 300;
	
	}
	/**
	 * Se mueve y comprueba sino choco contra el Algo42, si choca
	 * el Algo42 se muere.
	 */
	public void vivir(){
		
		Atacable algo42tmp;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42tmp = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42tmp != null){
				algo42tmp.recibirDanio(20);   /**hacer q se muera*/
				this.muerto = true;
			}
		}
	}
	
	/**
	 * Se mueve en linea recta a poca velocidad.
	 */
	protected void mover(){
		
		this.y += (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.y) = (this.posInicialY);
		}	
	}
		
	public Element getElement(Document doc) {
		/**
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto AvionCivil.
		 */
		Element avionCivil = doc.createElement("AvionCivil");
		
		Element atributos = doc.createElement("Atributos");
		avionCivil.appendChild(atributos);
		
		super.writeElement(atributos, doc);

		return avionCivil;
	}

	public static AvionCivil fromElement(Element element, ZonaCombate zona) {
		/**
		 * Retorna un objeto del tipo AvionCivil, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		AvionCivil avionCivil = new AvionCivil(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
		
				
		writeNaveViva((Element)variables, avionCivil);

		return avionCivil;
	}
}
