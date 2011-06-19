package algo42Full.vista;

import java.awt.Graphics;

import ar.uba.fi.algo3.titiritero.SuperficieDeDibujo;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

public class VistaFondoNivel extends Imagen{
	
	private int scrY1;
	private int scrY2;

	public VistaFondoNivel(){
		this.scrY1 = 800;
		this.scrY2 = 1400;
		
	}
	
	public void dibujar(SuperficieDeDibujo superficeDeDibujo){
		Graphics grafico = (Graphics)superficeDeDibujo.getBuffer();
		scrY1 -= 1;
		scrY2 -= 1;
		grafico.drawImage(this.imagen, 0, 0, 800, 600,
						  0, scrY1, 800, scrY2, null);	
	}
}
