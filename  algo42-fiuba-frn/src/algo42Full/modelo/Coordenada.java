package algo42Full.modelo;

import ar.uba.fi.algo3.titiritero.*;

public class Coordenada implements Posicionable {

	int x;
	int y;
	
	public Coordenada(int x,int y){
		this.x = x;
		this.y = y;
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
}

