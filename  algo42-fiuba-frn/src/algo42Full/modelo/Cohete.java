package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Cohete extends ActualizacionAlgo42{

	
	public Cohete (ZonaCombate zona, int x, int y){
		super(zona,x,y,7);
	}
	
//	public Cohete (Element actualizacion, ZonaCombate zona){
//		super (actualizacion, zona);
//	}
	

	
	public void vivir (){
	 Algo42 algo42;
	 
	    if (!muerto){ 
			this.mover();
	     	algo42 = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42 != null){
				muerto = true;
				algo42.cargarCohete(); 
		    } 
	    }
   }
	
	public static Cohete fromElement(Element element, ZonaCombate zona) {
		Cohete cohete = new Cohete(zona, 0, 0);
		
		writeActualizacion(element, cohete);

		return cohete;
	}
    
	public Element getElement(Document doc) {
		Element actualizacion = doc.createElement("Cohete");
           this.writeElement(actualizacion,doc);
           return actualizacion;
	} 
}
