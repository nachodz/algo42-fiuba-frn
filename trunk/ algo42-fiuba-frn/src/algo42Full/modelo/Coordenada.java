package algo42Full.modelo;

import ar.uba.fi.algo3.titiritero.*;

public class Coordenada implements Posicionable {

	private int x;
	private int y;
	private int radio;
	
	public Coordenada(int x,int y, int radio){
		this.x = x;
		this.y = y;
		this.radio = radio;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getRadio(){
		return this.radio;
	}
	
}

