package algo42Full.modelo;

public abstract class ObjetoVivo extends ObjetoPosicionable{
	protected boolean muerto;
	protected int velX;
	protected int velY;
	protected int posInicialX;
	protected int posInicialY;
	
	public ObjetoVivo(int x,int y,int radio, int velX, int velY){
		super(x,y,radio);
		this.posInicialX = x;
		this.posInicialY = y;
		this.muerto = false;
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public abstract void vivir();

}
