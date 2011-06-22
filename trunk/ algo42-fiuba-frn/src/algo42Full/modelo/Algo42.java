package algo42Full.modelo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import algo42Full.modelo.excepciones.*;

public class Algo42 extends ObjetoColisionable implements Atacable{
	private int energia;
	private ZonaCombate zonaDeCombate;
	private int cantCohetes;
	private int cantTorpedos;
	private boolean muerto;
	private int velocidad;
	
	

	public Element getElement(Document doc) {
		Element algo42 = doc.createElement("Algo42");
		
		Element atributos = doc.createElement("Atributos");
		algo42.appendChild(atributos);
		
		/*
		 * atributos de ObjetoColisionable
		 */
		
		Element x = doc.createElement("X");
		atributos.appendChild(x);
		x.setTextContent(String.valueOf(this.x));
		
		Element y = doc.createElement("Y");
		atributos.appendChild(y);
		y.setTextContent(String.valueOf(this.y));
		
		Element radio = doc.createElement("Radio");
		atributos.appendChild(radio);
		radio.setTextContent(String.valueOf(this.radio));
		
		///////////////////////////////////////////////////

		Element energia = doc.createElement("Energia");
		atributos.appendChild(energia);
		energia.setTextContent(String.valueOf(this.energia));

//		Element zonaDeCombate = doc.createElement("ZonaDeCombate");
//	    algo42.appendChild(zonaDeCombate);
//		zonaDeCombate.setTextContent(this.zonaDeCombate);
		
		Element cantCohetes = doc.createElement("CantCohetes");
		atributos.appendChild(cantCohetes);
		cantCohetes.setTextContent(String.valueOf(this.cantCohetes));
		
		Element cantTorpedos = doc.createElement("CantTorpedos");
		atributos.appendChild(cantTorpedos);
		cantTorpedos.setTextContent(String.valueOf(this.cantTorpedos));
		
		Element muerto = doc.createElement("Muerto");
		atributos.appendChild(muerto);
		muerto.setTextContent(String.valueOf(this.muerto));
		
		Element velocidad = doc.createElement("Velocidad");
		atributos.appendChild(velocidad);
		velocidad.setTextContent(String.valueOf(this.velocidad));


		return algo42;
	}

	public static Algo42 fromElement(Element element, ZonaCombate zona) {
		Algo42 algo42 = new Algo42(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
			
		NodeList childs = variables.getChildNodes();
		
		for (int i = 0; i < childs.getLength(); i++) {
			
			Node child = childs.item(i);
			
			
			if (child.getNodeName().equals("X")) {
				algo42.x = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Y")) {
				
				algo42.y = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Radio")) {
				algo42.radio = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Energia")) {
				algo42.energia = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CantCohetes")) {
				algo42.cantCohetes = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CantTorpedos")) {
				algo42.cantTorpedos = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Muerto")) {
				algo42.muerto = Boolean.parseBoolean(child.getTextContent());
			} else if (child.getNodeName().equals("Velocidad")) {
				algo42.velocidad = Integer.parseInt(child.getTextContent());
			}
		}
		
		return algo42;
	}
	
	public Algo42(ZonaCombate zona,int x,int y){
		super(x,y,50);
		if (zona.comprobarSalidaZona(this)){
			throw  new ObjetoFueraDeZonaDeCombateException();
		}
		this.zonaDeCombate = zona;
		this.energia = 10;
		this.muerto = false;
		this.velocidad = 4;
		this.cantCohetes = 0;
		this.cantTorpedos = 0;
	}
	
	public void cargarCohete(){
		this.cantCohetes++;
	}
	
	public void cargarTorpedo(){
		this.cantTorpedos++;
	}
	
	public void cargarTanqueEnergia(){
		this.energia++;
	}
	
	public void dispararCohete(){
		Proyectil pCohete;
		
		if (this.cantCohetes > 0){
			pCohete = new ProyectilCohete(this.zonaDeCombate,false,this.getX(),this.getY());
			this.zonaDeCombate.agregarProyectil(pCohete);
			this.cantCohetes--;
		}
		else {
			throw new NoTieneCohetesException();
		}
	}
	
	public void dispararTorpedo(){
		Proyectil pTorpedo;
		
		if (this.cantTorpedos > 0){
			pTorpedo = new ProyectilTorpedo(this.zonaDeCombate,false,this.getX(),this.getY());
			this.zonaDeCombate.agregarProyectil(pTorpedo);
			this.cantTorpedos--;
		}
		else {
			throw new NoTieneTorpedosException();
		}
	}
	
	public void dispararLaser(){
		Proyectil pLaser;
		
		pLaser = new ProyectilLaser(this.zonaDeCombate,false,this.getX(),this.getY());
		this.zonaDeCombate.agregarProyectil(pLaser);
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int getCantCohetes(){
		return this.cantCohetes;
	}
	
	public int getCantTorpedos(){
		return this.cantTorpedos;
	}
	
	public int getCantEnergia(){
		return this.energia;
	}
	
	public void recibirDanio(int cantDanio){
		int tempEnergia;
		
		tempEnergia = this.energia - cantDanio;
		if (tempEnergia < 1){
			this.energia = 0;
			this.muerto = true;
		}
		else this.energia = tempEnergia;
	}
	
	public void moverAbajo(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.y++;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.y--;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverArriba(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.y--;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.y++;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverDerecha(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.x++;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.x--;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverIzquierda(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.x--;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.x++;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverAbajoDer(){
		this.moverAbajo();
		this.moverDerecha();
	}
	
	public void moverAbajoIzq(){
		this.moverAbajo();
		this.moverIzquierda();
	}
	
	public void moverArribaDer(){
		this.moverArriba();
		this.moverDerecha();
	}
	
	public void moverArribaIzq(){
		this.moverArriba();
		this.moverIzquierda();
	}
	
	
	
	

}
