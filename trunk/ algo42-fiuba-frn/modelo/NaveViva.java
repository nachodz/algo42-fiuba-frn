package algo42Full.modelo;

public abstract class NaveViva extends ObjetoVivo implements Atacable {
	
	protected int energia = 0;
	protected int puntos = 0;
	protected ZonaCombate zonaDeCombate;
	
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
