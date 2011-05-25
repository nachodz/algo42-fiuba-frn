package algo42Full.modelo;

public class Caza {
	
	
	private int frecuenciaDisparo;
	private int turnosDisparo;
	
	public Caza(ZonaCombate unaZonaDeCombate, int posX, int posY){
		
		this.posX = posX;
		this.posY = posY;
		this.radio = 10;
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.zonaDeCombate = unaZonaDeCombate;
		this.energia = 2;
		this.velX = 0;
		this.velY = 3;
		this.muerto = false;
		this.puntos = 30;
	
		this.frecuenciaDisparo = 20;
		this.turnosDisparo = 0;		
		this.escapo = false;
	}	
	
	public void disparar(){
		
		ProyectilTorpedo proyectilTorpedo = ProyectilTorpedo((this.zonaDeCombate), true, (this.posX), (this.posY + 1));
		(this.zonaDeCombate).agregarProyectil(proyectilTorpedo);
		
	}
	
	public void morir(){
		
		this.muerto = true;
		TanqueEnergia tanqueEnergia= TanqueEnergia((this.zonaDeCombate), (this.posX), (this.posY));
		(this.zonaDeCombate).agregarActualilzacionAlgo42(tanqueEnergia);
	}
	
	public void mover(){
		
		this.posY = (this.posY) + (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.posY) = (this.posInicialY);
		}
		
	
	}
	
	public void recibirDanio(int cantidadDanio){
		
		int energiaTmp = (this.energia) - cantidadDanio;
		
		if (energiaTmp < 1){
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
			algo42 = (this.zonaDeCombate)comprobarColisionAlgo42(this);
			if (algo42 != void){
				algo42.recibirDanio(5);
				this.morir();
			
			}
			
			else{
				
				this.turnosDisparo += 1;
				if((this.turnosDisparo) == (this.frecuenciaDisparo)){
					this.disparar();
					this.turnosDisparo = 0;
				}
			}
		}

	}

}
