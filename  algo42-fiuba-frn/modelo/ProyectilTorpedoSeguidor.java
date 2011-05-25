package nacho.dezan;

public class ProyectilTorpedoSeguidor extends ProyectilTorpedo {
    
	public ProyectilLaser(ZonaCombate zona, boolean enemy, int x, int y) 
	{
		super(zona,enemy,x,y);
		this.setRadio: 3;
		velX = 4;
		velY = 4;
		danio = 1;
	}
	private void mover ()
	{
	//persigue al algo42 sin descanso hasta hacerle un impacto
	int tempY, x, y;

	x = zonaDeCombate.getAlgo42PosX;
	y = zonaDeCombate.getAlgo42PosY;
	
	if (x < posX) 
		posX = posX - velX;
	else 
		posX = posX + velX;
	
	if (y < posY) 
		posY = posY - velY; 
	else 
		posY = posY + velY;
	}	
}
