package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProyectilTorpedoAdaptable extends Proyectil{
	
	public ProyectilTorpedoAdaptable(ZonaCombate zona, boolean enemigo, int x, int y) 
	{
		super(zona,enemigo,x,y,3,0,5,4);
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

	public ProyectilTorpedoAdaptable (Element proyectil, ZonaCombate zona){
		super (proyectil, zona);
	}
	
	public Element getElement(Document doc) {
		Element proyectil = doc.createElement("ProyectilTorpedoAdaptable");
           this.grabar (proyectil,doc);
           return proyectil;
	} 
}