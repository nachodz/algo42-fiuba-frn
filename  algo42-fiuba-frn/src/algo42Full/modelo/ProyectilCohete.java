package algo42Full.modelo;

import org.w3c.dom.*;

public class ProyectilCohete extends Proyectil {

	public ProyectilCohete (ZonaCombate zona, boolean enemigo, int x, int y)
	{
		super(zona,enemigo,x,y,7,0,5,2);
		if (!enemigo) this.velY = 18;
	}
	
	public static ProyectilCohete fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo ProyectilCohete, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		ProyectilCohete proyectilCohete = new ProyectilCohete(zona,true, 0, 0);
		
		writeProyectil(element, proyectilCohete);

		return proyectilCohete;
	}
	
	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto ProyectilCohete.
		 */
		Element proyectil = doc.createElement("ProyectilCohete");
           this.writeElement(proyectil,doc);
           return proyectil;
	}  
}