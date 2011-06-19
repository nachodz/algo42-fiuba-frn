package algo42Full.control;

import ar.uba.fi.algo3.titiritero.MouseClickObservador;
import ar.uba.fi.algo3.titiritero.Posicionable;

/*
 * Clase abstracta que representa el boton, implementa MouseClickObservador.
 */
public abstract class Boton implements MouseClickObservador, Posicionable{
	private int x;
	private int y;
	private int verticeX;
	private int verticeY;
	
	
	public Boton(int x, int y, int ancho, int alto){
		this.x = x;
		this.y = y;
		this.verticeX = this.x + ancho;
		this.verticeY = this.y + alto;
	}
	
	/*
	 * La idea es redefinir apretar() en las clases que heradan. Lo que se hace en apretar()
	 * es lo que sucede cuando el usuario hace click sobre el boton.
	 * 
	 */
	public void apretar(){}
	
	public void MouseClick(int x, int y) {	
		if ((x>=this.x)&&(x<=verticeX))
			if((y>=this.y)&&(y<=verticeY))
				this.apretar();
	}

	public int getX() {
		return this.x;
	}


	public int getY() {
		return this.y;
	}

}
