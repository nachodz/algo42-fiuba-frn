package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilTorpedo extends Proyectil{
	public ProyectilTorpedo(ZonaCombate zona, boolean enemigo, int x, int y) 
	{
		super(zona,enemigo,x,y,7,5,5,4);
	}
	
	public ProyectilTorpedo (Element proyectil, ZonaCombate zona){
		super (proyectil, zona);
	}
	
	public Element getElement(Document doc) {
		Element proyectil = doc.createElement("ProyectilTorpedo");
           this.grabar (proyectil,doc);
           return proyectil;
	} 
	
}
	
