package algo42Full.modelo;

public class ProyectilTorpedo extends Proyectil{
	public ProyectilTorpedo(ZonaCombate zona, boolean enemigo, int x, int y) 
	{
		super(zona,enemigo,x,y);
		this.setRadio: 3;
		velX = 0;
		velY = 4;
		danio = 4;
	}
	
}
	
