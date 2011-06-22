package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import algo42Full.modelo.excepciones.*;

public class Bombardero extends NaveVivaEnemiga implements Atacable{
	

	private int frecuenciaDisparo;
	private int turnosDisparo;
	private int cantMov;
	
	
	public Element getElement(Document doc) {
		Element bombardero = doc.createElement("Bombardero");
		
		Element atributos = doc.createElement("Atributos");
		bombardero.appendChild(atributos);
		
		super.writeElement(atributos, doc);
		
			
		Element frecuenciaDisparo = doc.createElement("FrecuenciaDisparo");
		atributos.appendChild(frecuenciaDisparo);
		frecuenciaDisparo.setTextContent(String.valueOf(this.frecuenciaDisparo));
		
		Element turnosDisparo = doc.createElement("TurnosDisparo");
		atributos.appendChild(turnosDisparo);
		turnosDisparo.setTextContent(String.valueOf(this.turnosDisparo));
		

		
		Element cantMov = doc.createElement("CantMov");
		atributos.appendChild(cantMov);
		cantMov.setTextContent(String.valueOf(this.cantMov));


		return bombardero;
	}

	public static Bombardero fromElement(Element element, ZonaCombate zona) {
		Bombardero bombardero = new Bombardero(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
		
		writeNaveVivaEnemiga((Element)variables, bombardero);
		
		NodeList childs = variables.getChildNodes();
		
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("FrecuenciaDisparo")) {
				bombardero.frecuenciaDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("TurnosDisparo")) {
				bombardero.turnosDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CantMov")) {
				bombardero.cantMov = Integer.parseInt(child.getTextContent());
		}
	}
		
		return bombardero;
	}	
	
	
	public Bombardero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,50,3,3);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 4;
		this.puntos = 30;
		this.frecuenciaDisparo = 30;
		this.turnosDisparo = 0;
		this.cantMov = 0;
	}	
	
	
	protected void disparar(){
		
		Proyectil proyectil;
		int numero = 1 + (int)(Math.random()* 3); //genera un numero aleatorio del 1 al 3
		
		switch(numero){
		case 1:
			proyectil = new ProyectilLaser((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		
		case 2:
			proyectil = new ProyectilTorpedoSeguidor((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		
		case 3:
			proyectil = new ProyectilCohete((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		default:
			proyectil = new ProyectilLaser((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		}
		
		(this.zonaDeCombate).agregarProyectil(proyectil);
		
		
	}
	
	
	protected void morir(){
		
		this.muerto = true;
		Cohete cohete = new Cohete(this.zonaDeCombate, this.x, this.y);
		Torpedo torpedo = new Torpedo(this.zonaDeCombate, this.x,this.y);	
		this.zonaDeCombate.agregarActualizacionAlgo42(torpedo);
		this.zonaDeCombate.agregarActualizacionAlgo42(cohete);
		
	}
		
	protected void mover(){
		this.cantMov++;
		
		if ((this.cantMov<30)&&(!this.zonaDeCombate.comprobarSalidaZona(this))){
			this.y += this.velY;
			this.x += this.velX;
		}
		else{
			if(this.zonaDeCombate.comprobarSalidaZona(this)){
				this.x = this.posInicialX;
				this.y = this.posInicialY;
			}
			this.cantMov = 0;
			this.velX = this.velX * -1;
		}
	}
	
	
	public void recibirDanio(int cantidadDanio){
		int energiaTmp = (this.energia) - cantidadDanio;
		if(energiaTmp < 1){
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
				algo42.recibirDanio(20);
				this.morir();
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
}
