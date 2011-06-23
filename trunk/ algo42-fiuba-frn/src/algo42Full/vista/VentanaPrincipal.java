package algo42Full.vista;

import ar.uba.fi.algo3.titiritero.vista.*;
import ar.uba.fi.algo3.titiritero.*;

public class VentanaPrincipal extends Ventana {

	public VentanaPrincipal(ControladorJuego unControladorJuego) {
		super(800,600, unControladorJuego);
		this.setTitle("Algo2042");
		//this.addKeyListener(new ControladorTeclado(unControladorJuego));
		
//		Toolkit kit = Toolkit.getDefaultToolkit();
//		Image img = kit.createImage("media\\icono.png");
//		this.setIconImage(img);
//		kit = null;
	}

	private static final long serialVersionUID = 1L;

}