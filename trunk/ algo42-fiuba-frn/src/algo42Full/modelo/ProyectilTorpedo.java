package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilTorpedo extends Proyectil{
	public ProyectilTorpedo(ZonaCombate zona, boolean enemigo, int x, int y) 
	{
		super(zona,enemigo,x,y,7,5,5,4);
		if (!enemigo) this.velY = 12;
	}
	
	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto ProyectilTorpedo.
		 */
		Element proyectil = doc.createElement("ProyectilTorpedo");
           this.writeElement (proyectil,doc);
           return proyectil;
	} 
	
	public static ProyectilTorpedo fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo ProyectilTorpedo, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		ProyectilTorpedo proyectilTorpedo = new ProyectilTorpedo(zona,true, 0, 0);
		
		writeProyectil(element, proyectilTorpedo);

		return proyectilTorpedo;
	}
	
}
	
