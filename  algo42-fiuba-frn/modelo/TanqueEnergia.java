package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class TanqueEnergia extends ObjetoVivo{
	
   private ZonaCombate zonaDeCombate;
   
  public TanqueEnergia (ZonaCombate zona, int x, int y){
		posInicialX = x;
		posInicialX = y;
		posX = x;
		posY = y;
		radio = 2;
		if (zona.comprobarSalidaZona(this))
				throw new ObjetoFueraDeZonaDeCombateException();
		else
			zonaDeCombate = zona;
		velX = 0;
		velY = 2;
		muerto = false;  
  }
  private void mover(){
		if (!muerto) {
			posY = posY + velY;
			if (zonaDeCombate.comprobarSalidaZona(this))
					muerto = true;
		}
  }
  public void vivir (){
		Atacable algo42; //el algo42 seria Atacable lo cual nos trae un problema.

		if (!muerto) { 
			this.mover();
			algo42 = zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42 != null)
				muerto = true;
				algo42.cargarTanqueEnergia(); // esto no puede suceder, Reveer
		     }
	  }
  
}
