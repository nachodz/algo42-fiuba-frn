package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class Bombardero extends NaveVivaEnemiga implements Atacable{
	

	private int frecuenciaDisparo;
	private int turnosDisparo;
	
	public Bombardero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate,posX,posY,15,1,1);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 4;
		this.puntos = 30;
		this.frecuenciaDisparo = 30;
		this.turnosDisparo = 0;		
	}	
	
	
	public void disparar(){
		
		Proyectil proyectil;
		int numero = 1 + (int)(Math.random()* 3); //genera un numero aleatorio del 1 al 3
		
		switch(numero){
		case 1:
			proyectil = new ProyectilLaser((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		
		case 2:
			proyectil = new ProyectilTorpedoSeguidor((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		
		case 3:
			proyectil = new ProyectilCohete((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		default:
			proyectil = new ProyectilLaser((this.zonaDeCombate), true, (this.x), (this.y + 1));
			break;
		}
		
		(this.zonaDeCombate).agregarProyectil(proyectil);
		
		
	}
	
	
	public void morir(){
		
		ProyectilCohete cohete = new ProyectilCohete((this.zonaDeCombate), true, (this.x), (this.y));
		
		ProyectilTorpedo torpedo = new ProyectilTorpedoSeguidor((this.zonaDeCombate), true, (this.x), (this.y));
		
		this.muerto = true;
	  
		(this.zonaDeCombate).agregarActualizacionAlgo42(torpedo);
		(this.zonaDeCombate).agregarActualizacionAlgo42(cohete);
	}
	
	public void mover(){        //implementacion cambiada
		
		if (!((this.zonaDeCombate).comprobarSalidaZona(this))){
			this.y += this.velY;
		}
		
		else{
			
			this.velY *= -1;
		}		
	}
	
	
	public void recibirDanio(int cantidadDanio){
		int energiaTmp = (this.energia) - cantidadDanio;
		if(energiaTmp < 1){
			this.energia = 0;
			this.morir();
		}
		
		else{
			
			this.energia = energiaTmp;
		}		
	}
	
	public void vivir(){
		
		Atacable algo42;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42 = (this.zonaDeCombate).comprobarColisionAlgo42(this);
			
			if (algo42 != null){
				algo42.recibirDanio(20);
				this.morir();
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
