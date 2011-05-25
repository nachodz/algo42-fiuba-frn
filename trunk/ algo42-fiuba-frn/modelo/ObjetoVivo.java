package algo42Full.modelo;

public abstract class ObjetoVivo extends ObjetoPosicionable{
	protected boolean muerto;
	
	public ObjetoVivo(int x,int y,int radio){
		super(x,y,radio);
		this.muerto = false;
	}
	
	public boolean estaVivo(){
		if (muerto) return false;
		else return true;
	}
	
	public abstract void vivir();

}
