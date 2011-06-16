package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;
import ar.uba.fi.algo3.titiritero.ObjetoVivo;


public class TanqueEnergia extends ObjetoColisionable implements ObjetoVivo{
	
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	private ZonaCombate zonaDeCombate;
   
  public TanqueEnergia (ZonaCombate zona, int x, int y){
		super(x,y,2);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = 0;
		this.velY = 2;
		this.muerto = false;
		if (zona.comprobarSalidaZona(this))
				throw new ObjetoFueraDeZonaDeCombateException();
		else
			this.zonaDeCombate = zona;
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
  public void vivir (){
		Algo42 algo42;

		if (!muerto) { 
			this.mover();
			algo42 = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42 != null){
				muerto = true;
				algo42.cargarTanqueEnergia();
		     }
		}
   }
  
}
