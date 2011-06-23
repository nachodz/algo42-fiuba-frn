package algo42Full.vista;


import algo42Full.modelo.*;

public class VistaAvion extends VistaNave{
	
	public VistaAvion(NaveViva avion){
		this.setPosicionable(avion);
		
		if (avion instanceof AvionCivil){
			this.setNombreArchivoImagen("/media/civil.png");
			
		}
		else if (avion instanceof Avioneta){
			this.setNombreArchivoImagen("/media/avioneta.png");
			
		}
		else if (avion instanceof Bombardero){
			this.setNombreArchivoImagen("/media/bombardero.png");
			
		}
		else if (avion instanceof Caza){
			this.setNombreArchivoImagen("/media/caza.png");
			
		}
		else if (avion instanceof CazaII){
			this.setNombreArchivoImagen("/media/cazaII.png");
			
		}
		
		else if (avion instanceof Explorador){
			this.setNombreArchivoImagen("/media/explorador.png");
			
		}
		
		else if (avion instanceof Helicoptero){
			this.setNombreArchivoImagen("/media/helicoptero.png");
			
		}
		
	}

}
