package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class Torpedo extends ActualizacionAlgo42{
	

  
	public Torpedo (ZonaCombate zona, int x, int y){
		super(zona,x,y,2);
	}
	
//	public Torpedo (Element actualizacion, ZonaCombate zona){
//		super (actualizacion, zona);
//	}

	
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
	
	
	public static Torpedo fromElement(Element element, ZonaCombate zona) {
		Torpedo torpedo = new Torpedo(zona, 0, 0);
		
		writeActualizacion(element, torpedo);
		

		return torpedo;
	}
    
	public Element getElement(Document doc) {
		Element actualizacion = doc.createElement("Torpedo");
           this.writeElement (actualizacion,doc);
           return actualizacion;
	} 
			
}
