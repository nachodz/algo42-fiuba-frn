package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class TanqueEnergia extends ObjetoVivo{
	
   private ZonaCombate zonaDeCombate;
   
  public TanqueEnergia (ZonaCombate zona, int x, int y){
	  	super(x,y,2,0,2);
		if (zona.comprobarSalidaZona(this))
				throw new ObjetoFueraDeZonaDeCombateException();
		else
			this.zonaDeCombate = zona;
  }
  private void mover(){
		if (!muerto) {
			this.y = this.y + velY;
			if (zonaDeCombate.comprobarSalidaZona(this))
					muerto = true;
		}
  }
  public void vivir (){
		Algo42 algo42;

		if (!muerto) { 
			this.mover();
			algo42 = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42 != null)
				muerto = true;
				algo42.cargarTanqueEnergia();
		     }
	  }
  
}
