package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;
import java.lang.Math;

public class Helicoptero extends NaveViva{
	
	private int radioMov;
	private int angulo;
	
	public Helicoptero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,7,0,3);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 1;
		this.puntos = -200;
		this.radioMov = 150;
		this.angulo = 90;
	}
	
	
	public void mover(){
		
		int tempX;
		int tempY;
		int centroX;
		int centroY;
		
		if ((this.angulo) < 361){
			(this.angulo) = (this.angulo) + (this.velY);
		}
		
		else{
			this.angulo = 0;
		}
		

		tempX  = (int) (((double)(this.radioMov)) *(Math.cos(this.angulo)));
		tempY = (int) (((double)(this.radioMov)) *(Math.sin(this.angulo)));
		centroX = this.x;
		centroY = (this.y) + (this.radioMov);
		this.x = (int)(centroX + tempX);
		this.y = (int) (centroY - tempY);

	}
	
	public void vivir(){
		
		Atacable algo42tmp;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42tmp = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42tmp != null){
				algo42tmp.recibirDanio(20);   //hacer q se muera
				this.muerto = true;
			}
			

		}
	}

}
