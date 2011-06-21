package algo42Full.modelo;

import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ar.uba.fi.algo3.titiritero.ObjetoVivo;


public abstract class Proyectil extends ObjetoColisionable implements ObjetoVivo
{
	
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected int danio;
	protected boolean enemigo;
	protected ZonaCombate zonaDeCombate;
	 
	
	public void grabar (Element proyectil, Document doc){
		Element muerto = doc.createElement("Muerto");
		proyectil.appendChild(muerto);
		muerto.setTextContent(Boolean.toString(this.muerto));

		Element velX = doc.createElement("VelocidadX");
		proyectil.appendChild(velX);
		velX.setTextContent(Integer.toString(this.velX));
		
		Element velY = doc.createElement("VelocidadY");
		proyectil.appendChild(velY);
		velX.setTextContent(Integer.toString(this.velY));
		
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
	
	public Proyectil (Element proyectil, ZonaCombate zona){
		NodeList childs = proyectil.getChildNodes();
		
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = (Node) childs.item(i);
			if (child.getNodeName().equals("Muerto")) {
				this.muerto = Boolean.parseBoolean(child.getTextContent());
			 }else if (child.getNodeName().equals("VelocidadX")) {
				 this.velX = Integer.parseInt(child.getTextContent());
			    	}else if (child.getNodeName().equals("VelocidadY")) {
			    		this.velY = Integer.parseInt(child.getTextContent());
				         }else if (child.getNodeName().equals("PosicionInicialX")) {
				        	 this.posInicialX = Integer.parseInt(child.getTextContent());   
			                     }else if (child.getNodeName().equals("PosicionInicialY")) {
			                    	 this.posInicialY = Integer.parseInt(child.getTextContent());
			                       } else if (child.getNodeName().equals("Danio")) {
			                    	   this.danio = Integer.parseInt(child.getTextContent());
			                          } else if (child.getNodeName().equals("Enemigo")) {
			                        	  this.enemigo = Boolean.parseBoolean(child.getTextContent());
			             			     }
			if (zona != null) this.zonaDeCombate = zona;
		}

	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int hacerDanio ()
	{
		return danio;
	}
	
	protected void mover ()
	{
		  //se mueve en linea recta hacia adelante si es un proyectil enemigo o hacia atras si es un proyectil aliado.
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
	

}
