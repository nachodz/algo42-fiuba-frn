package algo42Full.control;

import algo42Full.modelo.Menu;

public class BotonJuegoNuevo extends Boton{
	private Menu menu;

	public BotonJuegoNuevo(Menu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	
	//al redefinir apretar(), hago que haga lo que yo quiera este boton
	public void apretar(){
		this.menu.empezarJuegoNuevo();
	}

}
