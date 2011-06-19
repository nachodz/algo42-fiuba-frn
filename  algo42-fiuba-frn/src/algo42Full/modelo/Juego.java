package algo42Full.modelo;

import ar.uba.fi.algo3.titiritero.*;
import ar.uba.fi.algo3.titiritero.vista.*;
import algo42Full.vista.*;

public class Juego {
	
	public static void main(String[] args) {
		
		
		ControladorJuego controlador = new ControladorJuego(false);
		Ventana ventanaPrincipal = new VentanaPrincipal(controlador);
		controlador.setSuperficieDeDibujo(ventanaPrincipal);
		
		ventanaPrincipal.setVisible(true);
		
		controlador.setIntervaloSimulacion(50);

		Menu menu = new Menu(controlador);
		menu.ejecutar();
	}

}
