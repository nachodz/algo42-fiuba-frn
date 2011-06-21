package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilLaser extends Proyectil {

	public ProyectilLaser(ZonaCombate zona, boolean enemigo, int x, int y) {
		super(zona,enemigo,x,y,2,0,5,1);
	}
	
	public ProyectilLaser (Element proyectil, ZonaCombate zona){
		super (proyectil, zona);
	}
	
	public Element serializar(Document doc) {
		Element proyectil = doc.createElement("ProyectilLaser");
           this.grabar (proyectil,doc);
           return proyectil;
	} 
  
}
