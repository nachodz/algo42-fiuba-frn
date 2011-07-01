package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Caza extends NaveVivaEnemiga implements Atacable{
	
	
	protected int frecuenciaDisparo;
	protected int turnosDisparo;
	
	public Caza(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,25,0,3,2);

		this.puntos = 30;	
		this.frecuenciaDisparo = 20;
		this.turnosDisparo = 0;		

	}	
	
	/**
	 * Dispara un torpedo y lo agrega a la zona de combate.
	 */
	protected void disparar(){
		
		ProyectilTorpedo proyectilTorpedo = new ProyectilTorpedo((this.zonaDeCombate), true, (this.x), (this.y + 1));
		(this.zonaDeCombate).agregarProyectil(proyectilTorpedo);
		
	}
	
	protected synchronized void morir(){
		/**
		 * Deja la actualizacionAlgo42 TanqueEnergia en la zona de combate y setea
		 * al Caza como muerto.
		 */
		
		TanqueEnergia tanqueEnergia= new TanqueEnergia((this.zonaDeCombate), (this.x), (this.y));
		(this.zonaDeCombate).agregarActualizacionAlgo42(tanqueEnergia);
		this.muerto = true;
	}
	/**
	 * Se mueve en linea recta.
	 */
	protected void mover(){
		
		this.y = (this.y) + (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.y) = (this.posInicialY);
		}	
	}
	/**
	 * Se le quita cantidadDanio de la energia que tiene, si llega a 0 se muere.
	 */
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
	
	/**
	 * Se mueve, sino esta muerto, comprueba si choco contra el Algo42 y dispara.
	 */
	public void vivir(){
		
		if(this.escapo){
			this.muerto = true;
		}
		
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
		/**
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto Caza.
		 */
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
		/**
		 * Retorna un objeto del tipo Caza, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
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
