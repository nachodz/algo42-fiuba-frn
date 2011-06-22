package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;
import java.lang.Math;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Helicoptero extends NaveViva{
	
	private int radioGiro;
	private int centroGiroX;
	private int centroGiroY;
	private boolean regresando;
	private boolean entroAlCirculo;

	
	public Helicoptero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,25,0,3);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 1;
		this.puntos = -200;
		this.centroGiroX = 300;
		this.centroGiroY = 300;
		this.radioGiro = 120;
		this.regresando = false;
		this.entroAlCirculo = false;
	}
	
	
	protected void mover(){
	//La aeronave se mueve de la posicion en la que se encuentra hacia el borde superior de la circunferencia sobre 
	//la cual realizara su moviento. Una vez alli, comienza a moverse en circulos en sentdo contrario a las agujas del reloj. 
	//Si al moverse la aeronave sobrepasa los limites del espacioAereo sale del mismo"
		
		int centroX;
		int centroY;
		int radioGiro1;
		int coordX;
		int incrementoY;
		int diferenciaEnX;
		
		if(! this.entroAlCirculo){
			dirigirHaciaPunto(this.centroGiroX, (this.centroGiroY - this.radioGiro));
		}
		
		else{				
			
			centroX = this.centroGiroX;
			centroY = this.centroGiroY;
			incrementoY = 1;
			radioGiro1 = this.radioGiro;
			
			if(this.x > centroX){					
				this.regresando = true;
			}
			
			if(this.regresando){        //chequea si el avion se sobrepasa de los limites verticales del circulo, si es el caso cambia la direccion
				
				if((this.y - incrementoY) < (centroY - radioGiro1)){
					this.regresando = false;
				}
				else{}
				
			}
			else{
					if((this.y + incrementoY) > (centroY + radioGiro1)){
						this.regresando = true;
					}
					
					else{}
			}
			
			
			
			if ((this.y <= (centroGiroY + (radioGiro - incrementoY))) || (this.y >= (centroGiroY - (radioGiro - incrementoY)))){
				
				if(this.regresando){
					
					//calcula la posicion de x de acuerdo al movimiento en y, 
					//con la formula de una circunferencia plana
					coordX = (int) (Math.sqrt( (Math.pow(radioGiro1, 2)) - (Math.pow(((this.y - 1) - centroY), 2))) ) + centroX ;
					
					
					diferenciaEnX = coordX - centroX;
					
					this.x = coordX;
					this.y -= incrementoY;
				}
				
				else{
					coordX = (int) (Math.sqrt( (Math.pow(radioGiro1, 2)) - (Math.pow(((this.y + 1) - centroY), 2))) ) + centroX ;
					
					
					diferenciaEnX = coordX - centroX;
					
					this.x = centroX - diferenciaEnX;
					this.y += incrementoY;
				}
			}
			
		}
			

				

	}



	public void vivir(){
	
	Atacable algo42tmp;
	
	if (!(this.muerto)){
		for(int i = 0; i <= this.velY; i++){
			this.mover();
		}
		
		algo42tmp = zonaDeCombate.comprobarColisionAlgo42(this);
		if (algo42tmp != null){
			algo42tmp.recibirDanio(20);   //hacer q se muera
			this.muerto = true;
		}
		

		}
	}


	public void dirigirHaciaPunto(int x, int y){
	
	//"El metodo se encarga de dirigir la aeronave hacia un punto pasado como parametro que 
	//"pertene al borde de la circunferencia en la cual debe moverse.
	//Cuando el avion llega al punto indicado, marca el atributo entroAlCirculo como true."

	int coordenadaXObjetivo;
	int coordenadaYObjetivo;
	int distanciaEnX;
	int distanciaEnY;
	int incrementoDesplazamiento = 1;
	
			
						
	coordenadaXObjetivo = x;	
	distanciaEnX = coordenadaXObjetivo - this.x;
					
	coordenadaYObjetivo = y;	
	distanciaEnY = coordenadaYObjetivo - this.y;
	
	if(distanciaEnX <= 0){
		if (distanciaEnX == 0){
			if(distanciaEnY < 0){
				this.y -= incrementoDesplazamiento; 
			}
			else{
				
				this.y += incrementoDesplazamiento;
			}
		}
		
		else{
			if(distanciaEnY <= 0){
				if(distanciaEnY == 0){
					this.x -= incrementoDesplazamiento;
											
				}
				else{
					this.x -= incrementoDesplazamiento;
					this.y -= incrementoDesplazamiento;
				}
			}
			else{
				this.x -= incrementoDesplazamiento;
				this.y += incrementoDesplazamiento;
			}
		}
	}
	
	else{
		if(distanciaEnY <= 0){
			if(distanciaEnY == 0){
				this.x +=incrementoDesplazamiento;
			}
			
			else{
				this.x +=incrementoDesplazamiento;
				this.y -= incrementoDesplazamiento;
			}
		}
		
		else{
			this.x +=incrementoDesplazamiento;
			this.y += incrementoDesplazamiento;
		}
	}
	
	if((this.x >= x - incrementoDesplazamiento) && (this.x <= x + incrementoDesplazamiento) && 
			(this.y >= y - incrementoDesplazamiento) && (this.y <= y + incrementoDesplazamiento)){
		this.x = x;
		this.y = y;
		this.entroAlCirculo = true;
		
		}	
	
									
	}
	
	public Element getElement(Document doc) {
		Element helicoptero = doc.createElement("Helicoptero");
		
		Element atributos = doc.createElement("Atributos");
		helicoptero.appendChild(atributos);
		
		super.writeElement(atributos, doc);
		
			
		Element radioGiro = doc.createElement("RadioGiro");
		atributos.appendChild(radioGiro);
		radioGiro.setTextContent(String.valueOf(this.radioGiro));
		
		Element centroGiroX = doc.createElement("CentroGiroX");
		atributos.appendChild(centroGiroX);
		centroGiroX.setTextContent(String.valueOf(this.centroGiroX));
		
		Element centroGiroY = doc.createElement("CentroGiroY");
		atributos.appendChild(centroGiroY);
		centroGiroY.setTextContent(String.valueOf(this.centroGiroY));
		
		Element regresando = doc.createElement("Regresando");
		atributos.appendChild(regresando);
		regresando.setTextContent(String.valueOf(this.regresando));
		
		Element entroAlCirculo = doc.createElement("EntroAlCirculo");
		atributos.appendChild(entroAlCirculo);
		entroAlCirculo.setTextContent(String.valueOf(this.entroAlCirculo));


		return helicoptero;
	}

	public static Helicoptero fromElement(Element element, ZonaCombate zona) {
		Helicoptero helicoptero = new Helicoptero(zona, 0, 0);
		
		Node variables = element.getFirstChild().getNextSibling();  //selecciona el nodo que tiene las variables
				
		writeNaveViva((Element)variables, helicoptero);

		NodeList childs = variables.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeName().equals("RadioGiro")) {
				helicoptero.radioGiro = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CentroGiroX")) {
				helicoptero.centroGiroX = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("CentroGiroY")) {
				helicoptero.centroGiroY = Integer.parseInt(child.getTextContent());
			} else if (child.getNodeName().equals("Regresando")) {
				helicoptero.regresando = Boolean.parseBoolean(child.getTextContent());
			} else if (child.getNodeName().equals("EntroAlCirculo")) {
				helicoptero.entroAlCirculo = Boolean.parseBoolean(child.getTextContent());
		}
	}
		
		return helicoptero;
	}

}
