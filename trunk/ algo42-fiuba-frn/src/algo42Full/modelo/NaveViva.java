package algo42Full.modelo;

import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public abstract class NaveViva extends ObjetoColisionable implements Atacable, ObjetoVivo {
	
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected int energia;
	protected int puntos;
	protected ZonaCombate zonaDeCombate;
	
	public NaveViva(ZonaCombate zona,int x,int y,int radio,int velX,int velY){
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = velX;
		this.velY = velY;
		this.muerto = false;
		this.energia = 0;
		this.puntos = 0;
		this.zonaDeCombate = zona;
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int obtenerPuntos(){
		
		return puntos;
	}
	
	public void recibirDanio(int cantidadEnergia){
		
		int energiaTmp = (this.energia) - cantidadEnergia;
		if (energiaTmp < 1){
			
			this.energia = 0;
			this.muerto = true;
		}
		
		else {
			this.energia = energiaTmp;
		}
	}
	
	public void vivir(){}

}
