package algo42Full.modelo;

import org.w3c.dom.*;

public class ProyectilCohete extends Proyectil {

	public ProyectilCohete (ZonaCombate zona, boolean enemigo, int x, int y)
	{
		super(zona,enemigo,x,y,7,0,5,2);
		if (!enemigo) this.velY = 18;
	}
	
	public ProyectilCohete (Element proyectil, ZonaCombate zona){
		super (proyectil, zona);
	}
	
	public Element getElement(Document doc) {
		Element proyectil = doc.createElement("ProyectilCohete");
           this.grabar (proyectil,doc);
           return proyectil;
	}  
}