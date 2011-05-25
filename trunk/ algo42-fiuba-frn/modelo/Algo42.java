package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class Algo42 extends ObjetoPosicionable implements Atacable{
	private int energia;
	private ZonaCombate zonaDeCombate;
	private int cantCohetes;
	private int cantTorpedos;
	private boolean muerto;
	private int velocidad;
	
	public Algo42(ZonaCombate zona,int x,int y){
		super(x,y,8);
		if (zona.comprobarSalidaZona(this)){
			throw  new ObjetoFueraDeZonaDeCombateException();
		}
		this.zonaDeCombate = zona;
		this.energia = 10;
		this.muerto = false;
		this.velocidad = 4;
		this.cantCohetes = this.cantTorpedos = 0;
	}
	
	public void cargarCohete(){
		this.cantCohetes++;
	}
	
	public void cargarTorpedo(){
		this.cantTorpedos++;
	}
	
	public void cargarTanqueEnergia(){
		this.energia++;
	}
	
	public void dispararCohete(){
		Proyectil pCohete;
		
		if (this.cantCohetes > 0){
			pCohete = new ProyectilCohete(this.zonaDeCombate,this.getPosx(),this.getPosy(),false);
			this.zonaDeCombate.agregarProyectil(pCohete);
			this.cantCohetes--;
		}
		else {
			throw new NoTieneCohetesException();
		}
	}
	
	public void dispararTorpedo(){
		Proyectil pTorpedo;
		
		if (this.cantCohetes > 0){
			pTorpedo = new ProyectilTorpedo(this.zonaDeCombate,this.getPosx(),this.getPosy(),false);
			this.zonaDeCombate.agregarProyectil(pTorpedo);
			this.cantTorpedos--;
		}
		else {
			throw new NoTieneTorpedosException();
		}
	}
	
	public void dispararLaser(){
		Proyectil pLaser;
		
		pLaser = new ProyectilLaser(this.zonaDeCombate,this.getPosx(),this.getPosy(),false);
		this.zonaDeCombate.agregarProyectil(pLaser);
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int getCantCohetes(){
		return this.cantCohetes;
	}
	
	public int getCantTorpedos(){
		return this.cantTorpedos;
	}
	
	public int getCantEnergia(){
		return this.energia;
	}
	
	public void recibirDanio(int cantDanio){
		int tempEnergia;
		
		tempEnergia = this.energia - cantDanio;
		if (tempEnergia < 1){
			this.energia = 0;
			this.muerto = true;
		}
		else this.energia = tempEnergia;
	}
	
	public void moverAbajo(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.y++;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.y--;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverArriba(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.y--;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.y++;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverDerecha(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.x++;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.x--;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverIzquierda(){
		int contador;
		boolean noSalir;
		
		contador = 1;
		noSalir = true;
		
		if((contador<(this.velocidad+1))&&(noSalir)){
			this.x--;
			if (this.zonaDeCombate.comprobarSalidaZona(this)){
				this.x++;
				noSalir = false;
			}
			contador++;
		}
	}
	
	public void moverAbajoDer(){
		this.moverAbajo();
		this.moverDerecha();
	}
	
	public void moverAbajoIzq(){
		this.moverAbajo();
		this.moverIzquierda();
	}
	
	public void moverArribaDer(){
		this.moverArriba();
		this.moverDerecha();
	}
	
	public void moverArribaIzq(){
		this.moverArriba();
		this.moverIzquierda();
	}
	
	
	
	

}
