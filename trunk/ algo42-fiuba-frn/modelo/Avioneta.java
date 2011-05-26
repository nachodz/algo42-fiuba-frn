package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class Avioneta extends NaveVivaEnemiga implements Atacable{
	
	
	private int frecuenciaDisparo;
	private int turnosDisparo;
	
	public Avioneta(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,7,0,4);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 1;
		this.puntos = 20;
		this.frecuenciaDisparo = 40;
		this.turnosDisparo = 0;		
	}	
	
	
	protected void disparar(){
		
		ProyectilLaser proyectilLaser;
		
		proyectilLaser = new ProyectilLaser(this.zonaDeCombate, true, this.x, (this.y) + 1);
		(this.zonaDeCombate).agregarProyectil(proyectilLaser);
	}
	
	
	protected void mover(){        //implementacion cambiada
		
		if (!((this.zonaDeCombate).comprobarSalidaZona(this))){
			this.y += this.velY;
		}
		
		else{
			
			this.velY *= -1;
		}		
	}
	
	public void vivir(){
		
		Atacable algo42;
		
		if (!(this.muerto)){
			this.mover();
			algo42 = (this.zonaDeCombate).comprobarColisionAlgo42(this);
			
			if (algo42 != null){
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
