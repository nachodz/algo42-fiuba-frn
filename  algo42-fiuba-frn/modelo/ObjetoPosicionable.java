package algo42Full.modelo;

import java.lang.Math;

public class ObjetoPosicionable {
	protected int x;
	protected int y;
	protected int radio;
	
	public ObjetoPosicionable(int x,int y,int radio){
		this.x = x;
		this.y = y;
		this.radio = radio;
	}
	
	public int getPosx(){
		return this.x;
	}
	
	public int getPosy(){
		return this.y;
	}
	
	public int getRadio(){
		return this.radio;
	}
	
	public boolean huboColision(ObjetoPosicionable objeto){
		int tempX,tempY,tempRadio;
		
		tempY = objeto.getPosy();
		tempX = objeto.getPosx();
		tempRadio = objeto.getRadio();
		
		return ( (Math.hypot((tempX-this.x), (tempY-this.y))) <(this.radio+tempRadio));
				
	}

}
