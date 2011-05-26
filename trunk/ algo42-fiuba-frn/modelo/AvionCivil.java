package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class AvionCivil extends NaveViva{

	public void vivir(){
		
		Atacable algo42tmp;
		
		if (!(this.muerto)){
			
			this.mover();
			algo42tmp = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42tmp != null){
				algo42tmp.recibirDanio(20);   //hacer q se muera
				this.muerto = true;
			}
			

		}
	}
	
	
	protected void mover(){
		
		this.y = (this.y) + (this.velY);
		if ((this.zonaDeCombate).comprobarSalidaZona(this)){
			(this.y) = (this.posInicialY);
		}
		
	
	}
	
	public AvionCivil(ZonaCombate unaZonaDeCombate, int x, int y){
		super(unaZonaDeCombate,x,y,7,0,2);
		if (unaZonaDeCombate.comprobarSalidaZona(this)){
			
			throw new ObjetoFueraDeZonaDeCombateException();			
		}
		
		this.energia = 1;
		this.puntos = -300;
	
	}
}
