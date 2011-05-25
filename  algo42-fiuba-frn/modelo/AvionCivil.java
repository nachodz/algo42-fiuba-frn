package algo42Full.modelo;

public class AvionCivil extends NaveViva{

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
	
	
	public void mover(){
		
		this.posY = (this.posY) + (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.posY) = (this.posInicialY);
		}
		
	
	}
	
	public AvionCivil(ZonaCombate unaZonaDeCombate, int posX, int posY){
		
		this.posX = posX;
		this.posY = posY;
		this.radio = 7;
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 1;
		this.velX = 0;
		this.velY = 2;
		this.muerto = false;
		this.puntos = -300;
	
	}
}
