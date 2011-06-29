package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilLaser extends Proyectil {

	public ProyectilLaser(ZonaCombate zona, boolean enemigo, int x, int y) {
		super(zona,enemigo,x,y,7,0,7,1);
		if (!enemigo) this.velY = 12;
	}
	
//	public ProyectilLaser (Element proyectil, ZonaCombate zona){
//		super (proyectil, zona);
//	}
	
	public Element getElement(Document doc) {
		Element proyectil = doc.createElement("ProyectilLaser");
           this.writeElement(proyectil,doc);
           return proyectil;
	}
	
	public static ProyectilLaser fromElement(Element element, ZonaCombate zona) {
		ProyectilLaser proyectilLaser = new ProyectilLaser(zona,true, 0, 0);
		
		writeProyectil(element, proyectilLaser);

		return proyectilLaser;
	}
  
}
