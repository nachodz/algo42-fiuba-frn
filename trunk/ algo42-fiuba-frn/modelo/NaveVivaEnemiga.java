package algo42Full.modelo; 

public abstract class NaveVivaEnemiga extends NaveViva{
	
	protected boolean escapo;
	protected boolean esGuia;
	
	public NaveVivaEnemiga(ZonaCombate zona,int x,int y,int radio,int velX,int velY){
		super(zona,x,y,radio,velX,velY);
		this.escapo = false;
		this.esGuia = false;
	}
	
		
	public boolean seEscapo(){
		
		return (this.escapo);
	}
	
	public void huir(){  //antes estaba implementado en cada clase
		
		
		if (!(this.escapo)){
			if ((this.velY) > 0 ){
				
				(this.velY) *= -1; 
			}
			
			this.y -= this.velY;
			this.escapo = (this.zonaDeCombate).comprobarSalidaZona(this);
		}

	}
	
	public void hacerGuia(){
		this.esGuia = true;
	}

}
