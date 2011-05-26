package algo42Full.modelo;

public abstract class NaveViva extends ObjetoVivo implements Atacable {
	
	protected int energia;
	protected int puntos;
	protected ZonaCombate zonaDeCombate;
	
	public NaveViva(ZonaCombate zona,int x,int y,int radio,int velX,int velY){
		super(x,y,radio,velX,velY);
		this.energia = 0;
		this.puntos = 0;
		this.zonaDeCombate = zona;
	}
	
	public int obtenerPuntos(){
		
		return puntos;
	}
	
	public void recibirDanio(int cantidadEnergia){
		
		int energiaTmp = (this.energia) - cantidadEnergia;
		if (energiaTmp < 1){
			
			this.energia = 0;
			this.muerto = true;
		}
		
		else {
			this.energia = energiaTmp;
		}
	}
	
	public void vivir(){}

}
