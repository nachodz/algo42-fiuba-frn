package algo42Full.modelo;

import algo42Full.modelo.excepciones.ObjetoFueraDeZonaDeCombateException;
import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public abstract class ActualizacionAlgo42 extends ObjetoColisionable implements ObjetoVivo {
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected ZonaCombate zonaDeCombate;
	
	
	public ActualizacionAlgo42 (ZonaCombate zona, int x, int y,int radio){
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = 0;
		this.velY = 2;
		this.muerto = false;
		if (zona.comprobarSalidaZona(this))
			throw new ObjetoFueraDeZonaDeCombateException();
		else
			zonaDeCombate = zona;
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	protected void mover(){
		if (!muerto) {
			this.y = this.y + velY;
			if (zonaDeCombate.comprobarSalidaZona(this))
					muerto = true;
		}
	}
	
	public void vivir(){}
	
	
	
}
