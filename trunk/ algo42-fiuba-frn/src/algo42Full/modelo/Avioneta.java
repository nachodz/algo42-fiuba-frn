package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Avioneta extends NaveVivaEnemiga implements Atacable{
	
	
	private int frecuenciaDisparo;
	private int turnosDisparo;
	
	public Avioneta(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,25,0,4);
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 1;
		this.puntos = 20;
		this.frecuenciaDisparo = 40;
		this.turnosDisparo = 0;		
	}	
	
	
	protected void disparar(){
		
		ProyectilLaser proyectilLaser;
		
		proyectilLaser = new ProyectilLaser(this.zonaDeCombate, true, this.x, (this.y) + 1);
		(this.zonaDeCombate).agregarProyectil(proyectilLaser);
	}
	
	
	protected void mover(){        //implementacion cambiada
		
		if (((this.zonaDeCombate).comprobarSalidaZona(this)))
			this.velY *= -1;
		this.y += this.velY;
	}
	
	public void vivir(){
		
		Atacable algo42;
		
		if (!(this.muerto)){
			this.mover();
			algo42 = (this.zonaDeCombate).comprobarColisionAlgo42(this);
			
			if (algo42 != null){
				algo42.recibirDanio(5);
				this.muerto = true;
			}
			
			else{
				
				this.turnosDisparo += 1;
				if ((this.turnosDisparo) == (this.frecuenciaDisparo)){
					this.disparar();
					this.turnosDisparo = 0;
				}
			}
		}
	}
	
	public Element getElement(Document doc) {
		Element avioneta = doc.createElement("Avioneta");
		
		Element atributos = doc.createElement("Atributos");
		avioneta.appendChild(atributos);
		
		super.writeElement(atributos, doc);
		
			
		Element frecuenciaDisparo = doc.createElement("FrecuenciaDisparo");
		atributos.appendChild(frecuenciaDisparo);
		frecuenciaDisparo.setTextContent(String.valueOf(this.frecuenciaDisparo));
		
		Element turnosDisparo = doc.createElement("TurnosDisparo");
		atributos.appendChild(turnosDisparo);
		turnosDisparo.setTextContent(String.valueOf(this.turnosDisparo));
		
		return avioneta;
	}

	public static Avioneta fromElement(Element element, ZonaCombate zona) {
		Avioneta avioneta = new Avioneta(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
		
		writeNaveVivaEnemiga((Element)variables, avioneta);
		
		NodeList childs = variables.getChildNodes();
				
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("FrecuenciaDisparo")) {
				avioneta.frecuenciaDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("TurnosDisparo")) {
				avioneta.turnosDisparo = Integer.parseInt(child.getTextContent());

		}
	}
		
		return avioneta;
	}


}
