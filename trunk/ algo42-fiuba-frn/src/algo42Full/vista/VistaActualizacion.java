package algo42Full.vista;

import algo42Full.modelo.*;
import ar.uba.fi.algo3.titiritero.vista.Imagen;

public class VistaActualizacion extends Imagen{
	
	public VistaActualizacion(ActualizacionAlgo42 actualizacion){
		this.setPosicionable(actualizacion);
		if (actualizacion instanceof TanqueEnergia)
			this.setNombreArchivoImagen("/media/tanqueEnergia.png");
		else
			if (actualizacion instanceof Torpedo)
				this.setNombreArchivoImagen("/media/torpedo.png");
			else
				this.setNombreArchivoImagen("/media/cohete.png");
	}
	
}
