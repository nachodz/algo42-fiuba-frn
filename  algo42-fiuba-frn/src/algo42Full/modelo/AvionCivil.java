package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;



import algo42Full.modelo.excepciones.*;

public class AvionCivil extends NaveViva{

	public void vivir(){
		
		Atacable algo42tmp;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42tmp = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42tmp != null){
				algo42tmp.recibirDanio(20);   //hacer q se muera
				this.muerto = true;
			}
			

		}
	}
	
	
	protected void mover(){
		
		this.y = (this.y) + (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.y) = (this.posInicialY);
		}
		
	
	}
	
	public AvionCivil(ZonaCombate unaZonaDeCombate, int x, int y){
		super(unaZonaDeCombate,x,y,7,0,2);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 1;
		this.puntos = -300;
	
	}
	
	public Element getElement(Document doc) {
		Element avionCivil = doc.createElement("AvionCivil");
		
		Element atributos = doc.createElement("Atributos");
		avionCivil.appendChild(atributos);
		
		super.writeElement(atributos, doc);

		return avionCivil;
	}

	public static AvionCivil fromElement(Element element, ZonaCombate zona) {
		AvionCivil avionCivil = new AvionCivil(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
		
				
		writeNaveViva((Element)variables, avionCivil);

		return avionCivil;
	}
}
