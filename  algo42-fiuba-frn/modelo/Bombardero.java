package algo42Full.modelo;

public class Bombardero extends NaveVivaEnemiga implements Atacable{
	
	private int cantMov;
	private int frecuenciaDisparo;
	private int turnosDisparo;
	
	public Bombardero(ZonaCombate unaZonaDeCombate, int posX, int posY){
		
		this.posX = posX;
		this.posY = posY;
		this.radio = 15;
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 4;
		this.velX = 1;
		this.velY = 1;
		this.muerto = false;
		this.puntos = 30;
		this.cantMov = 0;
		this.frecuenciaDisparo = 30;
		this.turnosDisparo = 0;		
		this.escapo = false;
	}	
	
	
	public void disparar(){
		
		Proyectil proyectil;
		int numero = 1 + (int)(Math.random()* 3); //genera un numero aleatorio del 1 al 3
		
		switch(numero){
		case (numero = 1):
			proyectil = ProyectilLaser((this.zonaDeCombate), true, (this.posX), (this.posY + 1));
			break;
		
		case (numero = 2):
			proyectil = ProyectilTorpedoSeguidor((this.zonaDeCombate), true, (this.posX), (this.posY + 1));
			break;
		
		case (numero = 3):
			proyectil = ProyectilCohete((this.zonaDeCombate), true, (this.posX), (this.posY + 1));
			break;
		
		}
		
		(this.zonaDeCombate).agregarProyectil(proyectil);
		
		
	}
	
	
	public void morir(){
		
		ProyectilCohete cohete = ProyectilCohete((this.zonaDeCombate), true, (this.posX), (this.posY));
		
		ProyectilTorpedo torpedo = ProyectilTorpedoSeguidor((this.zonaDeCombate), true, (this.posX), (this.posY));
		
		this.muerto = true;
	  
		(this.zonaDeCombate).agregarActualizacionAlgo42(torpedo);
		(this.zonaDeCombate).agregarActualizacionAlgo42(cohete);
	}
	
	public void mover(){        //implementacion cambiada
		
		if (!((this.zonaDeCombate).comprobarSalidaZona(self))){
			this.posY += this.velY;
		}
		
		else{
			
			this.velY *= -1.
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
		
		Algo42 algo42;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42 = (this.zonaDeCombate).comprobarColisionAlgo42(this);
			
			if (algo42 != void){
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
