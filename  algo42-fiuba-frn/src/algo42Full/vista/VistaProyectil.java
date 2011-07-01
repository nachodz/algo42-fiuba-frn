package algo42Full.vista;

import algo42Full.modelo.*;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

public class VistaProyectil extends Imagen{
	
	public VistaProyectil(Proyectil proyectil){
		this.setPosicionable(proyectil);
		if (proyectil instanceof ProyectilLaser){
			this.setNombreArchivoImagen("/media/pLaser.png");
			return;
		}
		if (proyectil instanceof ProyectilCohete){
			this.setNombreArchivoImagen("/media/pCohete.png");
			return;
		}

		if (proyectil instanceof ProyectilTorpedoAdaptable){
			this.setNombreArchivoImagen("/media/pAdaptable.png");
			return;
		}
		if (proyectil instanceof ProyectilTorpedoSeguidor){
			this.setNombreArchivoImagen("/media/pSeguidor.png");
			return;
		}
		
		if (proyectil instanceof ProyectilTorpedo){
			this.setNombreArchivoImagen("/media/pTorpedo.png");
			return;
		}
		
	}

}
