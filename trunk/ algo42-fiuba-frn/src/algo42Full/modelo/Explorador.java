package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;
import java.lang.Math;

public class Explorador extends NaveVivaEnemiga implements Atacable{
	
	private int angulo;
	private int radioMov;
	
	public Explorador(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,8,0,3);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 1;
		this.puntos = 50;
		this.radioMov = 100;
		this.angulo = 90;	
	}
	
	
	protected void mover(){
		
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
		centroX = this.y;
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
