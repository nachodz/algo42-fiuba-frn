package algo42Full.modelo;

import ar.uba.fi.algo3.titiritero.Posicionable;

public class ObjetoColisionable  implements Posicionable{
	protected int x;
	protected int y;
	protected int radio;
	
	public ObjetoColisionable(){};
	public ObjetoColisionable(int x,int y,int radio){
		this.x = x;
		this.y = y;
		this.radio = radio;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getRadio(){
		return this.radio;
	}
	
	public boolean huboColision(ObjetoColisionable objeto){
		int tempX,tempY,tempRadio;
		
		tempY = objeto.getY();
		tempX = objeto.getX();
		tempRadio = objeto.getRadio();
		
		return ( (Math.hypot((tempX-this.x), (tempY-this.y))) <(this.radio+tempRadio));
				
	}
}
