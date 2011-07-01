package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Torpedo extends ActualizacionAlgo42{
	

  
	public Torpedo (ZonaCombate zona, int x, int y){
		super(zona,x,y,2);
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
	
	
	public static Torpedo fromElement(Element element, ZonaCombate zona) {
		/*
		 * Retorna un objeto del tipo Torpedo, con un estado interno cargado
		 * desde el Element pasado como parametro.
		 */
		Torpedo torpedo = new Torpedo(zona, 0, 0);
		
		writeActualizacion(element, torpedo);
		

		return torpedo;
	}
    
	public Element getElement(Document doc) {
		/*
		 * Retorna un Element perteneciente al Document pasado
		 * como parametro, en el que guardan todos los atributos
		 * del objeto Torpedo.
		 */
		Element actualizacion = doc.createElement("Torpedo");
           this.writeElement (actualizacion,doc);
           return actualizacion;
	} 
			
}
