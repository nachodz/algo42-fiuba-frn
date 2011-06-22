package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilTorpedoSeguidor extends ProyectilTorpedo {
    
	public ProyectilTorpedoSeguidor(ZonaCombate zona, boolean enemigo, int x, int y){
		super(zona,enemigo,x,y); 
	}
	protected void mover(){
		//persigue al algo42 sin descanso hasta hacerle un impacto
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
 
	public ProyectilTorpedoSeguidor (Element proyectil, ZonaCombate zona){
		super (proyectil, zona);
	}
	
	public Element getElement(Document doc) {
		Element proyectil = doc.createElement("ProyectilTorpedoSeguidor");
           this.grabar (proyectil,doc);
           return proyectil;
	}
}
