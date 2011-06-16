package algo42Full.modelo;

import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public abstract class Proyectil extends ObjetoColisionable implements ObjetoVivo
{
	
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	protected int danio;
	protected boolean enemigo;
	protected ZonaCombate zonaDeCombate;
	
	public Proyectil (ZonaCombate zona, boolean enemigo, int x, int y,int radio,int velX,int velY,int danio)
	{
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.velX = velX;
		this.velY = velY;
		this.muerto = false;
		this.enemigo = enemigo;
		this.zonaDeCombate = zona;
		this.danio = danio;
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public int hacerDanio ()
	{
		return danio;
	}
	
	protected void mover ()
	{
		  //se mueve en linea recta hacia adelante si es un proyectil enemigo o hacia atras si es un proyectil aliado.
			int tempY;
			if (this.enemigo) 
				tempY = this.y + this.velY;
			else 
				tempY = this.y - this.velY;
			if (this.zonaDeCombate.comprobarSalidaZona(this)) 
				this.muerto = true; 
			else
				 this.y = tempY ;
	  }
    
    public void vivir (){
	  Atacable objetivo;

		if (!this.muerto){
		    this.mover();
		    if (this.enemigo)
			   objetivo = zonaDeCombate.comprobarColisionAlgo42(this);
		    else{
		    	objetivo = zonaDeCombate.comprobarColisionFlotaEnemiga(this);
		    	if (objetivo == null)
		    		objetivo = zonaDeCombate.comprobarColisionFlotaAliada(this);
		    }
			if (objetivo != null){
				 objetivo.recibirDanio(this.hacerDanio());
				 this.muerto = true;
			}
		}
  }
	
	

}
