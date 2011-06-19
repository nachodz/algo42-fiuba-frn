package algo42Full.modelo;

import algo42Full.vista.VistaFondoNivel;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class Nivel {
	
	private ControladorJuego controlador;

	public Nivel(ControladorJuego controlador){
		this.controlador = controlador;
		
		VistaFondoNivel vistaFondo = new VistaFondoNivel();
		Coordenada coord = new Coordenada(0,0);
		vistaFondo.setPosicionable(coord);
		vistaFondo.setNombreArchivoImagen("/media/fondo1.jpg");
		this.controlador.agregarDibujable(vistaFondo);
	}
	
}
