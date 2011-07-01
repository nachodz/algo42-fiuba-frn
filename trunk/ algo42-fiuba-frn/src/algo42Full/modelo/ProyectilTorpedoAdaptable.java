package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilTorpedoAdaptable extends Proyectil{
	
	public ProyectilTorpedoAdaptable(ZonaCombate zona, boolean enemigo, int x, int y) 
	{
		super(zona,enemigo,x,y,7,0,5,4);
	}
	
	
    public void vivir (){
  	  Algo42 objetivo = null;

  		if (!this.muerto){
  		    this.mover();
  		    if (this.enemigo)
  			   objetivo = this.zonaDeCombate.comprobarColisionAlgo42(this);

  			if (objetivo != null){
  				 int energiaAQuitar = (objetivo.getCantEnergia()) / 2;
  				 objetivo.recibirDanio(energiaAQuitar);
  				 this.muerto = true;
  			}
  		}
    }

	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto ProyectilTorpedoAdaptable.
		 */
		Element proyectil = doc.createElement("ProyectilTorpedoAdaptable");
           this.writeElement(proyectil,doc);
           return proyectil;
	} 
	
	public static ProyectilTorpedoAdaptable fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo ProyectilTorpedoAdaptable, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		ProyectilTorpedoAdaptable proyectilTorpedoAdaptable = new ProyectilTorpedoAdaptable(zona,true, 0, 0);
		
		writeProyectil(element, proyectilTorpedoAdaptable);

		return proyectilTorpedoAdaptable;
	}
}