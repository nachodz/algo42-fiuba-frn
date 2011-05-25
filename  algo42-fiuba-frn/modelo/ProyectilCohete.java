package algo42Full.modelo;

public class ProyectilCohete extends Proyectil {
	private ZonaCombate zonaDeCombate;

	public ProyectilCohete (ZonaCombate zona, boolean enemy, int x, int y)
	{
		super(zona,enemy,x,y);
		this.setRadio: 2;
		velX = 0;
		velY = 5;
		danio = 2;
	}
	
}
