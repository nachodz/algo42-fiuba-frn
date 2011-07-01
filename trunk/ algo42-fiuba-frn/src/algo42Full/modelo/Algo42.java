package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import algo42Full.modelo.excepciones.*;
import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public class Algo42 extends ObjetoColisionable implements Atacable,ObjetoVivo{
	private int energia;
	private ZonaCombate zonaDeCombate;
	private int cantCohetes;
	private int cantTorpedos;
	private boolean muerto;
	private int velocidad;
	private int velX; 
	private int velY; 

	
	
	public Algo42(ZonaCombate zona,int x,int y){
		super(x,y,25);
		this.zonaDeCombate = zona;
		this.energia = 20;
		this.muerto = false;
		this.velocidad = 10;
		this.cantCohetes = 0;
		this.cantTorpedos = 0;
		this.velX = 0;
		this.velY = 0;
	}

	
	/**
	 * Se le agregan cohetes si impacta contra una Actualizacion del tipo Cohete.
	 */
	public void cargarCohete(){
		this.cantCohetes++;
	}
	/**
	 * Se le agregan torpedos si impacta contra una Actualizacion del tipo Torpedo.
	 */
	public void cargarTorpedo(){
		this.cantTorpedos++;
	}
	/**
	 * Se le agrega energia si impacta contra una Actualizacion del tipo Energia.
	 */
	public void cargarTanqueEnergia(){
		this.energia++;
	}
	/**
	 * Dispara un ProyectilCohete y lo agrega la zona de combate, sino tiene mas lanza una excepcion.
	 */
	public void dispararCohete(){
		Proyectil pCohete;
		
		if (this.cantCohetes > 0){
			pCohete = new ProyectilCohete(this.zonaDeCombate,false,this.getX(),this.getY());
			this.zonaDeCombate.agregarProyectil(pCohete);
			this.cantCohetes--;
		}
		else {
			throw new NoTieneCohetesException();
		}
	}
	/**
	 * Dispara un ProyectilTorpedo y lo agrega la zona de combate, sino tiene mas lanza una excepcion.
	 */
	
	public void dispararTorpedo(){
		Proyectil pTorpedo;
		
		if (this.cantTorpedos > 0){
			pTorpedo = new ProyectilTorpedo(this.zonaDeCombate,false,this.getX(),this.getY());
			this.zonaDeCombate.agregarProyectil(pTorpedo);
			this.cantTorpedos--;
		}
		else {
			throw new NoTieneTorpedosException();
		}
	}
	/**
	 * Dispara un ProyectilCohete y lo agrega la zona de combate.
	 */
	public void dispararLaser(){
		Proyectil pLaser;
		
			pLaser = new ProyectilLaser(this.zonaDeCombate,false,this.getX(),this.getY());
			this.zonaDeCombate.agregarProyectil(pLaser);
	}
	
	/**
	 * @return true si esta vivo, sino false.
	 */
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int getCantCohetes(){
		return this.cantCohetes;
	}
	
	public int getCantTorpedos(){
		return this.cantTorpedos;
	}
	
	public int getCantEnergia(){
		return this.energia;
	}
	/**
	 * Le quita energia al Algo42.
	 * @param cantDanio
	 */
	public void recibirDanio(int cantDanio){
		int tempEnergia;
		
		tempEnergia = this.energia - cantDanio;
		if (tempEnergia < 1){
			this.energia = 0;
			this.muerto = true;
		}
		else this.energia = tempEnergia;
	}
	
	public void moverAbajo(){
		this.velY = velocidad;
	}
	
	public void moverArriba(){
		this.velY = velocidad*-1;
	}
	
	public void moverDerecha(){
		this.velX = velocidad;
	}
	
	public void moverIzquierda(){
		this.velX = velocidad*-1;
	}
	
	public void detenerEjeX(){
		this.velX = 0;
	}
	
	public void detenerEjeY(){
		this.velY = 0;
	}
	
	
	private void mover() {
		if (this.velX!=0){
			this.x += this.velX;
			while(this.zonaDeCombate.comprobarSalidaZonaEx(this)){
				if (this.velX >0)
					this.x--;
				else
					this.x++;
			}	
		}
		
		if (this.velY !=0){
			this.y += this.velY;
			while(this.zonaDeCombate.comprobarSalidaZonaEx(this)){
				if (this.velY >0)
					this.y--;
				else
					this.y++;
			}
		}
	}

	@Override
	public void vivir() {
		this.mover();
	}
	
	
	public Element getElement(Document doc) {
		/**
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto Algo42.
		 */
		Element algo42 = doc.createElement("Algo42");
		
		Element atributos = doc.createElement("Atributos");
		algo42.appendChild(atributos);
		
		/**
		 * atributos de ObjetoColisionable
		 */
		
		Element x = doc.createElement("X");
		atributos.appendChild(x);
		x.setTextContent(String.valueOf(this.x));
		
		Element y = doc.createElement("Y");
		atributos.appendChild(y);
		y.setTextContent(String.valueOf(this.y));
		
		Element radio = doc.createElement("Radio");
		atributos.appendChild(radio);
		radio.setTextContent(String.valueOf(this.radio));
		

		Element energia = doc.createElement("Energia");
		atributos.appendChild(energia);
		energia.setTextContent(String.valueOf(this.energia));

		Element cantCohetes = doc.createElement("CantCohetes");
		atributos.appendChild(cantCohetes);
		cantCohetes.setTextContent(String.valueOf(this.cantCohetes));
		
		Element cantTorpedos = doc.createElement("CantTorpedos");
		atributos.appendChild(cantTorpedos);
		cantTorpedos.setTextContent(String.valueOf(this.cantTorpedos));
		
		Element muerto = doc.createElement("Muerto");
		atributos.appendChild(muerto);
		muerto.setTextContent(String.valueOf(this.muerto));
		
		Element velocidad = doc.createElement("Velocidad");
		atributos.appendChild(velocidad);
		velocidad.setTextContent(String.valueOf(this.velocidad));
		
		Element velX = doc.createElement("VelX");     //nuevo agregado
		atributos.appendChild(velX);
		velX.setTextContent(String.valueOf(this.velX));
		
		Element velY = doc.createElement("VelY");      //nuevo agregado
		atributos.appendChild(velY);
		velY.setTextContent(String.valueOf(this.velY));


		return algo42;
	}

	public static Algo42 fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo Algo42, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		Algo42 algo42 = new Algo42(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
			
		NodeList childs = variables.getChildNodes();
		
		for (int i = 0; i < childs.getLength(); i++) {
			
			Node child = childs.item(i);			
			
			if (child.getNodeName().equals("X")) {
				algo42.x = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Y")) {				
				algo42.y = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Radio")) {
				algo42.radio = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Energia")) {
				algo42.energia = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CantCohetes")) {
				algo42.cantCohetes = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CantTorpedos")) {
				algo42.cantTorpedos = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Muerto")) {
				algo42.muerto = Boolean.parseBoolean(child.getTextContent());
			} else if (child.getNodeName().equals("Velocidad")) {
				algo42.velocidad = Integer.parseInt(child.getTextContent());
			}
			else if (child.getNodeName().equals("VelX")) {        
				algo42.velX = Integer.parseInt(child.getTextContent());
			}
			else if (child.getNodeName().equals("VelY")) {     
				algo42.velY = Integer.parseInt(child.getTextContent());
			}		
		}		
		return algo42;
	}	

}
