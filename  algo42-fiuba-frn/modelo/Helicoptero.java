package algo42Full.modelo;

public class Helicoptero extends NaveViva{
	
	private int radioMov;
	private int angulo;
	
	public Helicoptero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		
		this.posX = posX;
		this.posY = posY;
		this.radio = 7;
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 1;
		this.velX = 0;
		this.velY = 3;
		this.muerto = false;
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
			(this.angulo = 0);
		}
		

		tempX  = ((double)(this.radioMov)) *(java.lang.Math.cos(this.angulo));
		tempY = ((double)(this.radioMov)) *(java.lang.Math.sin(this.angulo));
		centroX = this.posX;
		centroY = (this.posY) + (this.radioMov);
		this.posX = (int)(centroX + tempX);
		this.posY = (int) (centroY - tempY);

	}
	
	public void vivir(){
		
		Algo42 algo42tmp;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42 = zonaDeCombate.comprobarColisionAlgo42Con(this);
			if (algo42 != void){
				algo42.recibirDanio(20);   //hacer q se muera
				this.muerto = true;
			}
			

		}
	}

}
