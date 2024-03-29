package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Bombardero extends NaveVivaEnemiga implements Atacable{
	

	private int frecuenciaDisparo;
	private boolean dispararSeguidor;
	private int turnosDisparo;
	private int cantMov;
	
	public Bombardero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,25,3,3,4);
		this.puntos = 30;
		this.frecuenciaDisparo = 30;
		this.dispararSeguidor = true;
		this.turnosDisparo = 0;
		this.cantMov = 0;
	}	
	
	/**
	 * Dispara un torpedo rastreador.
	 */
	protected void disparar(boolean seguidor){
		
		Proyectil proyectil;
		int numero;
		
		int rand = 1 + (int)(Math.random()* 80); /**disminuye la frecuencia de torpedos seguidores*/
		if (rand<10){
			proyectil = new ProyectilTorpedoSeguidor((this.zonaDeCombate), true, (this.x), (this.y + 1));
		}
		else{
			numero = 1 + (int)(Math.random()* 2); /**genera un numero aleatorio del 1 al 2*/
			switch(numero){
				case 1:
					proyectil = new ProyectilLaser((this.zonaDeCombate), true, (this.x), (this.y + 1));
					this.dispararSeguidor = true;
					break;
						
				case 2:
					proyectil = new ProyectilCohete((this.zonaDeCombate), true, (this.x), (this.y + 1));
					this.dispararSeguidor = true;
					break;
					
				default:
					proyectil = new ProyectilLaser((this.zonaDeCombate), true, (this.x), (this.y + 1));
					break;
			}
		}
		(this.zonaDeCombate).agregarProyectil(proyectil);
			
	}
	
	
	protected void morir(){
		/**
		 * Se encarga de dejar el cohete y torpedo al morir, agregando a la 
		 * zona de combate estas ActualizacionesAlgo42. Luego marca al
		 * Bombardero como muerto.
		 */
		
		Cohete cohete = new Cohete(this.zonaDeCombate, this.x, this.y);
		Torpedo torpedo = new Torpedo(this.zonaDeCombate, (this.x-5),this.y);	
		this.zonaDeCombate.agregarActualizacionAlgo42(torpedo);
		this.zonaDeCombate.agregarActualizacionAlgo42(cohete);
		this.muerto = true;
		
	}
	/**
	 * Se encarga de mover el Bombardero de en Zig-Zag.
	 */
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
	
	/**
	 * Se le quita energia cuando impacta con un proyectil, si no tiene mas energia se lo marca como muerto.
	 * @param cantidadDanio.
	 */
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
	/**
	 * Si no esta muerto, y no escapo porque mataron a la nave guia, se mueve, comprueba si no choco contra el Algo42
	 * y dispara.
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
				algo42.recibirDanio(20);
				this.morir();
			}
			
			else{
				this.turnosDisparo += 1;
				if ((this.turnosDisparo) == (this.frecuenciaDisparo)){
					if (this.dispararSeguidor)
						this.disparar(true);
					else
						this.disparar(false);
					this.turnosDisparo = 0;
				}
			}
		}
	}
	
	public Element getElement(Document doc) {
		/**
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto Bombardero.
		 */
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
		
		Element dispararSeguidor = doc.createElement("DispararSeguidor"); 
		atributos.appendChild(dispararSeguidor);
		dispararSeguidor.setTextContent(String.valueOf(this.dispararSeguidor));


		return bombardero;
	}

	public static Bombardero fromElement(Element element, ZonaCombate zona) {
		/**
		 * Retorna un objeto del tipo Bombardero, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		Bombardero bombardero = new Bombardero(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
		
		writeNaveVivaEnemiga((Element)variables, bombardero);
		
		NodeList childs = variables.getChildNodes();  //lista con las variables
		
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("FrecuenciaDisparo")) {
				bombardero.frecuenciaDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("TurnosDisparo")) {
				bombardero.turnosDisparo = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CantMov")) {
				bombardero.cantMov = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("DispararSeguidor")) {     
				bombardero.dispararSeguidor = Boolean.parseBoolean(child.getTextContent());
		}
	}
		
		return bombardero;
	}	
	
}
