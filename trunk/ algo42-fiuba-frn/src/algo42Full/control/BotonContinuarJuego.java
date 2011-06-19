package algo42Full.control;

import algo42Full.modelo.Menu;

public class BotonContinuarJuego extends Boton{
	private Menu menu;

	public BotonContinuarJuego(Menu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	public void apretar(){
		this.menu.continuarJuego();
	}

}
