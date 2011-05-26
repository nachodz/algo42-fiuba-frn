package algo42Full.modelo;

public class ProyectilTorpedoSeguidor extends ProyectilTorpedo {
    
	public ProyectilTorpedoSeguidor(ZonaCombate zona, boolean enemigo, int x, int y){
		super(zona,enemigo,x,y); 
	}
	protected void mover(){
	//persigue al algo42 sin descanso hasta hacerle un impacto
	int x, y;

	x = zonaDeCombate.getAlgo42PosX();
	y = zonaDeCombate.getAlgo42PosY();
	
	if (x < this.x) 
		this.x = this.x - this.velX;
	else 
		this.x = this.x + this.velX;
	
	if (y < this.y) 
		this.y = this.y - this.velY; 
	else 
		this.y = this.y + this.velY;
	}	
}
