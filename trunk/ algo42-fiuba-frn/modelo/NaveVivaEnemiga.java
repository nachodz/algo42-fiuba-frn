package algo42Full.modelo; 

public abstract class NaveVivaEnemiga extends NaveViva{
	
	protected boolean escapo;
	protected boolean esGuia;
	
		
	public boolean seEscapo(){
		
		return (this.escapo);
	}
	
	public void huir(){  //antes estaba implementado en cada clase
		
		
		if (!(this.escapo)){
			if ((this.velY) > 0 ){
				
				(this.velY) -= 1; 
			}
			
			this.posY -= this.velY;
			this.escapo = (this.zonaDeCombate).comprobarSalidaZonaDe: this;
		}

	}

}
