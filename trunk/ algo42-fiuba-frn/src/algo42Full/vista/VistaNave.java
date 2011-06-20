package algo42Full.vista;

import java.awt.Graphics;

import ar.uba.fi.algo3.titiritero.Posicionable;
import ar.uba.fi.algo3.titiritero.SuperficieDeDibujo;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

public class VistaNave extends Imagen{
	
	public VistaNave(){
		
	}
	
	public void dibujar(SuperficieDeDibujo superficeDeDibujo) {
		Graphics grafico = (Graphics)superficeDeDibujo.getBuffer();
		Posicionable pos = this.getPosicionable();
		int posx = pos.getX()-pos.getRadio();
		int posy = pos.getY()-pos.getRadio();
		//if ((posx>=0) && (posy>=0))
			grafico.drawImage( this.imagen, posx, posy, null);
		//	
	}

}
