package algo42Full.vista;

import algo42Full.modelo.*;

import java.awt.Graphics;

import ar.uba.fi.algo3.titiritero.Posicionable;
import ar.uba.fi.algo3.titiritero.SuperficieDeDibujo;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

public class VistaNave extends Imagen{
	
	NaveViva posicionable2;
	
	public VistaNave(){
		
	}
	
	public void setPosicionable(NaveViva posicionable) {
		this.posicionable = posicionable;
		this.posicionable2 = posicionable;
		
		
	}
	
	public void dibujar(SuperficieDeDibujo superficeDeDibujo) {
		Graphics grafico = (Graphics)superficeDeDibujo.getBuffer();
		Posicionable pos = this.getPosicionable();
		int posx = pos.getX()-pos.getRadio();
		int posy = pos.getY()-pos.getRadio();
		//if ((posx>=0) && (posy>=0))
		
	
		
		if(posicionable2.estaVivo()){
			grafico.drawImage( this.imagen, posx, posy, null);
		}
			
	
	}

}
