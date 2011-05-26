package algo42Full.modelo;

public abstract class Proyectil extends ObjetoVivo
{
	protected int danio;
	protected boolean enemigo;
	protected ZonaCombate zonaDeCombate;
	
	public Proyectil (ZonaCombate zona, boolean enemigo, int x, int y,int radio,int velX,int velY,int danio)
	{
		super(x,y,radio,velX,velY);
		this.enemigo = enemigo;
		this.zonaDeCombate = zona;
		this.danio = danio;
	}
	
	public int hacerDanio ()
	{
		return danio;
	}
	
	private void mover ()
	{
		  //se mueve en linea recta hacia adelante si es un proyectil enemigo o hacia atras si es un proyectil aliado.
			int tempY;

			if (this.enemigo) 
				tempY = this.y + this.velY;
			else 
				tempY = this.y - this.velY;
			if (zonaDeCombate.comprobarSalidaZona(this)) 
				muerto = true; 
			else
				 this.y = tempY ;
	  }
    
    public void vivir ()
    {
	  Atacable objetivo;

		if (!this.muerto)
		    this.mover();
		    if (this.enemigo) 
			   objetivo = zonaDeCombate.comprobarColisionAlgo42(this); 
		    else
		    	objetivo = zonaDeCombate.comprobarColisionFlotaEnemiga(this);
			if (objetivo == null)
				 objetivo = zonaDeCombate.comprobarColisionFlotaAliada(this);
			if (objetivo != null)
				 objetivo.recibirDanio(this.hacerDanio());
				 this.muerto = true;
  }
	
	

}
