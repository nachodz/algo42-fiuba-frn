package algo42Full.modelo;

public class CazaII extends Caza implements Atacable{

	
	public CazaII(ZonaCombate unaZonaDeCombate, int posX, int posY){
		super(unaZonaDeCombate, posX, posY);		

	}	
	
	public void disparar(){
		
		ProyectilTorpedoAdaptable proyectilTorpedoAdaptable = new ProyectilTorpedoAdaptable((this.zonaDeCombate), true, (this.x), (this.y + 1));
		(this.zonaDeCombate).agregarProyectil(proyectilTorpedoAdaptable);
		
	}
	
	

}
