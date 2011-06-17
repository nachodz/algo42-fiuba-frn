package algo42Full.modelo;


public class Torpedo extends ActualizacionAlgo42{
	

  
	public Torpedo (ZonaCombate zona, int x, int y){
		super(zona,x,y,2);
	}
	
	public void vivir (){
	    Algo42 algo42;
					
	    if (!muerto) { 
			this.mover();
			algo42 = this.zonaDeCombate.comprobarColisionAlgo42(this);
			if (algo42 != null){
				muerto = true;
				algo42.cargarTorpedo(); 
		    }
	    }
	}
			
}
