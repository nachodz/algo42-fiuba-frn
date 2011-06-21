package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Torpedo extends ActualizacionAlgo42{
	

  
	public Torpedo (ZonaCombate zona, int x, int y){
		super(zona,x,y,2);
	}
	
	public Torpedo (Element actualizacion, ZonaCombate zona){
		super (actualizacion, zona);
	}
    
	public Element serializar(Document doc) {
		Element actualizacion = doc.createElement("Torpedo");
           this.grabar (actualizacion,doc);
           return actualizacion;
	} 
	
	public void vivir (){
	    Algo42 algo42;
					
	    if (!muerto) { 
			this.mover();
			algo42 = this.zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42 != null){
				muerto = true;
				algo42.cargarTorpedo(); 
		    }
	    }
	}
			
}
