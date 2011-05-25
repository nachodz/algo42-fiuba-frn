package algo42Full.modelo;

public class Explorador {
	
	private int angulo;
	private int radioMov;
	
	public Explorador(ZonaCombate unaZonaDeCombate, int posX, int posY){
		
		this.posX = posX;
		this.posY = posY;
		this.radio = 8;
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 1;
		this.velX = 0;
		this.velY = 3;
		this.muerto = false;
		this.puntos = 50;
		this.radioMov = 100;
		this.angulo = 90;
		this.escapo = false;
	
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
