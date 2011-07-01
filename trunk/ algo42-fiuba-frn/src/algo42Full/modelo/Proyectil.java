package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ar.uba.fi.algo3.titiritero.ObjetoVivo;


public abstract class Proyectil extends ObjetoColisionable implements ObjetoVivo, ConvertibleAElement
{
	
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected int danio;
	protected boolean enemigo;
	protected ZonaCombate zonaDeCombate;
	 
	
	public Proyectil (ZonaCombate zona, boolean enemigo, int x, int y,int radio,int velX,int velY,int danio)
	{
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = velX;
		this.velY = velY;
		this.muerto = false;
		this.enemigo = enemigo;
		this.zonaDeCombate = zona;
		this.danio = danio;
	}
	
	/**
	 * @return true si muerto es false, y false si esta muerto.
	 * 
	 */
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	/**
	 * @return danio.
	 * Es el danio que hace el proyectil disparado. 
	 */
	public int hacerDanio ()
	{
		return danio;
	}
	/**
	 * se mueve en linea recta hacia adelante si es un proyectil enemigo o hacia atras si es un proyectil aliado.
	 */
	protected void mover ()
	{		  
			int tempY;
			if (this.enemigo) 
				tempY = this.y + this.velY;
			else 
				tempY = this.y - this.velY;
			if (this.zonaDeCombate.comprobarSalidaZona(this)) 
				this.muerto = true; 
			else
				 this.y = tempY ;
	  }
    /**
     * Si no esta muerto el proyectil, y es enemigo se fija si choca contra el Algo42, en cambio si es de este ultimo
     * se fija si choco contra algun avion de la flota enemiga, en los 2 casos devuelve contra que nave choco el proyectil
     * y le hace el danio que el proyectil tiene asignado, y destruye el proyectil que hizo impacto.
     */
    public void vivir (){
	  Atacable objetivo;

		if (!this.muerto){
		    this.mover();
		    if (this.enemigo)
			   objetivo = zonaDeCombate.comprobarColisionAlgo42(this);
		    else{
		    	objetivo = zonaDeCombate.comprobarColisionFlotaEnemiga(this);
		    	if (objetivo == null)
		    		objetivo = zonaDeCombate.comprobarColisionFlotaAliada(this);
		    }
			if (objetivo != null){
				 objetivo.recibirDanio(this.hacerDanio());
				 this.muerto = true;
			}
		}
  }
    
	public void writeElement(Element proyectil, Document doc){
		/*
		 * Escribe en el Element pasado como parametro perteneciente al Document
		 * tambien parametro, todas las variables pertenecientes
		 * al tipo Proyectil.
		 */	
		
		Element x = doc.createElement("X");
		proyectil.appendChild(x);
		x.setTextContent(String.valueOf(this.x));
		
		Element y = doc.createElement("Y");
		proyectil.appendChild(y);
		y.setTextContent(String.valueOf(this.y));
		
		Element radio = doc.createElement("Radio");
		proyectil.appendChild(radio);
		radio.setTextContent(String.valueOf(this.radio));
		
		Element muerto = doc.createElement("Muerto");
		proyectil.appendChild(muerto);
		muerto.setTextContent(Boolean.toString(this.muerto));

		Element velX = doc.createElement("VelocidadX");
		proyectil.appendChild(velX);
		velX.setTextContent(Integer.toString(this.velX));
		
		Element velY = doc.createElement("VelocidadY");
		proyectil.appendChild(velY);
		velY.setTextContent(Integer.toString(this.velY));
		
		Element posInicialX = doc.createElement("PosicionInicialX");
		proyectil.appendChild(posInicialX);
		posInicialX.setTextContent(Integer.toString(this.posInicialX));
		
		Element posInicialY = doc.createElement("PosicionInicialY");
		proyectil.appendChild(posInicialY);
		posInicialY.setTextContent(Integer.toString(this.posInicialY));
        
		Element danio = doc.createElement("Danio");
		proyectil.appendChild(danio);
		danio.setTextContent(Integer.toString(this.danio));
        
		Element enemigo = doc.createElement("Enemigo");
		proyectil.appendChild(enemigo);
		enemigo.setTextContent(Boolean.toString(this.enemigo));

	}
	
	public static void writeProyectil(Element element, Proyectil unProyectil) {
		/*
		 * Escribe en el Element pasado como parametro perteneciente al Document
		 * tambien parametro, todas las variables pertenecientes
		 * al tipo Proyectil
		 */		
		
		NodeList childs = element.getChildNodes();
		
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = (Node) childs.item(i);
			if (child.getNodeName().equals("Muerto")) {
				unProyectil.muerto = Boolean.parseBoolean(child.getTextContent());				
			}else if (child.getNodeName().equals("X")) {
				unProyectil.x = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Y")) {				
				unProyectil.y = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Radio")) {
				unProyectil.radio = Integer.parseInt(child.getTextContent());
			 }else if (child.getNodeName().equals("VelocidadX")) {
				 unProyectil.velX = Integer.parseInt(child.getTextContent());
			 }else if (child.getNodeName().equals("VelocidadY")) {
				 unProyectil.velY = Integer.parseInt(child.getTextContent());
			 }else if (child.getNodeName().equals("PosicionInicialX")) {
				 unProyectil.posInicialX = Integer.parseInt(child.getTextContent());   
			 }else if (child.getNodeName().equals("PosicionInicialY")) {
				 unProyectil.posInicialY = Integer.parseInt(child.getTextContent());
			 } else if (child.getNodeName().equals("Danio")) {
				 unProyectil.danio = Integer.parseInt(child.getTextContent());
			 } else if (child.getNodeName().equals("Enemigo")) {
				 unProyectil.enemigo = Boolean.parseBoolean(child.getTextContent());
			 }
		}		
	}
}
