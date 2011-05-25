package algo42Full.modelo;

public class Avioneta extends NaveVivaEnemiga implements Atacable{
	
	
	private int cantMov;
	private int frecuenciaDisparo;
	private int turnosDisparo;
	
	public Avioneta(ZonaCombate unaZonaDeCombate, int posX, int posY){
		
		this.posX = posX;
		this.posY = posY;
		this.radio = 7;
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 1;
		this.velX = 0;
		this.velY = 4;
		this.muerto = false;
		this.puntos = 20;
		this.cantMov = 0;
		this.frecuenciaDisparo = 40;
		this.turnosDisparo = 0;		
		this.escapo = false;
	}	
	
	
	public void disparar(){
		
		ProyectilLaser proyectilLaser;
		
		proyectilLaser(this.zonaDeCombate, true, this.posX, (this.posY) + 1);
		(this.zonaDeCombate).agregarProyectil(proyectilLaser);
	}
	
	
	public void mover(){        //implementacion cambiada
		
		if (!((this.zonaDeCombate).comprobarSalidaZona(self))){
			this.posY += this.velY;
		}
		
		else{
			
			this.velY *= -1.
		}		
	}
	
	public void vivir(){
		
		Algo42 algo42;
		
		if (!(this.muerto)){
			this.mover();
			algo42 = (this.zonaDeCombate).comprobarColisionAlgo42(this);
			
			if (algo42 != void){
				algo42.recibirDanio(5);
				this.muerto = true;
			}
			
			else{
				
				this.turnosDisparo += 1;
				if ((this.turnosDisparo) == (this.frecuenciaDisparo)){
					this.disparar();
					this.turnosDisparo = 0;
				}
			}
		}
	}


}
