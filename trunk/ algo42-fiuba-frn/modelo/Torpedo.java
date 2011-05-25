package algo42Full.modelo;

import algo42Full.modelo.excepciones.*;

public class Torpedo extends ObjetoVivo {
	
  private ZonaCombate zonaDeCombate;
  
	public Torpedo (ZonaCombate zona, int x, int y){
		posInicialX = x;
		posInicialY = y; //aca en vez de posInicialY decia posInicialX en tu TP, lo acomode.
		posX = x;
		posY = y;
		radio = 2;
		if (zonaDeCombate.comprobarSalidaZona(this))
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
				algo42.cargarTorpedo(); // problema, reveer!!!!
		    }
	}
			
}
