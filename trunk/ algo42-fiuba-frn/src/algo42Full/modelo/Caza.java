package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import algo42Full.modelo.excepciones.*;

public class Caza extends NaveVivaEnemiga implements Atacable{
	
	
	protected int frecuenciaDisparo;
	protected int turnosDisparo;
	
	public Caza(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,50,0,3);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}

		this.energia = 2;
		this.puntos = 30;
	
		this.frecuenciaDisparo = 20;
		this.turnosDisparo = 0;		

	}	
	
	protected void disparar(){
		
		ProyectilTorpedo proyectilTorpedo = new ProyectilTorpedo((this.zonaDeCombate), true, (this.x), (this.y + 1));
		(this.zonaDeCombate).agregarProyectil(proyectilTorpedo);
		
	}
	
	protected void morir(){
		
		this.muerto = true;
		TanqueEnergia tanqueEnergia= new TanqueEnergia((this.zonaDeCombate), (this.x), (this.y));
		(this.zonaDeCombate).agregarActualizacionAlgo42(tanqueEnergia);
	}
	
	protected void mover(){
		
		this.y = (this.y) + (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.y) = (this.posInicialY);
		}
		
	
	}
	
	public void recibirDanio(int cantidadDanio){
		
		int energiaTmp = (this.energia) - cantidadDanio;
		
		if (energiaTmp < 1){
			this.energia = 0;
			this.morir();
		}
		
		else{
			
			this.energia = energiaTmp;
		}
	}
	
	
	public void vivir(){
		
		Atacable algo42;
		
		if (!(this.muerto)){
			this.mover();
			algo42 = (this.zonaDeCombate).comprobarColisionAlgo42(this);
			if (algo42 != null){
				algo42.recibirDanio(5);
				this.morir();
			
			}
			
			else{
				
				this.turnosDisparo += 1;
				if((this.turnosDisparo) == (this.frecuenciaDisparo)){
					this.disparar();
					this.turnosDisparo = 0;
				}
			}
		}

	}
	
	public Element getElement(Document doc) {
		Element caza = doc.createElement("Caza");
		
		Element atributos = doc.createElement("Atributos");
		caza.appendChild(atributos);
		
		super.writeElement(atributos, doc);
		
			
		Element frecuenciaDisparo = doc.createElement("FrecuenciaDisparo");
		atributos.appendChild(frecuenciaDisparo);
		frecuenciaDisparo.setTextContent(String.valueOf(this.frecuenciaDisparo));
		
		Element turnosDisparo = doc.createElement("TurnosDisparo");
		atributos.appendChild(turnosDisparo);
		turnosDisparo.setTextContent(String.valueOf(this.turnosDisparo));
		
		return caza;
	}

	public static Caza fromElement(Element element, ZonaCombate zona) {
		Caza caza = new Caza(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
					
		writeNaveVivaEnemiga((Element)variables, caza);

		NodeList childs = variables.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("FrecuenciaDisparo")) {
				caza.frecuenciaDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("TurnosDisparo")) {
				caza.turnosDisparo = Integer.parseInt(child.getTextContent());

		}
	}
		
		return caza;
	}

}
