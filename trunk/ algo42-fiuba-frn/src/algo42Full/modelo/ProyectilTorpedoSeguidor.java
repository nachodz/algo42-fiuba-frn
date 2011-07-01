package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilTorpedoSeguidor extends ProyectilTorpedo {
    
	public ProyectilTorpedoSeguidor(ZonaCombate zona, boolean enemigo, int x, int y){
		super(zona,enemigo,x,y); 
		this.danio = 2;
	}
	
	/**
	 * Se mueve siguiendo al algo42.
	 */
	protected void mover(){
		
		int algoX, algoY;
		
		algoX = zonaDeCombate.getAlgo42PosX();
		algoY = zonaDeCombate.getAlgo42PosY();
		
		if (algoX < this.x) 
			this.x = this.x - this.velX;
		else 
			this.x = this.x + this.velX;
		
		if (algoY < this.y) 
			this.y = this.y - this.velY; 
		else 
			this.y = this.y + this.velY;
	}	
 
	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto ProyectilTorpedoSeguidor.
		 */
		Element proyectil = doc.createElement("ProyectilTorpedoSeguidor");
           this.writeElement(proyectil,doc);
           return proyectil;
	}
	
	public static ProyectilTorpedoSeguidor fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo ProyectilTorpedoSeguidor, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		ProyectilTorpedoSeguidor proyectilTorpedoSeguidor = new ProyectilTorpedoSeguidor(zona,true, 0, 0);
		
		writeProyectil(element, proyectilTorpedoSeguidor);

		return proyectilTorpedoSeguidor;
	}
}
