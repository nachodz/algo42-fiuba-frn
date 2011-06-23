package algo42Full.control;


public class BotonJuegoNuevo extends Boton{
	private ControladorMenu menu;

	public BotonJuegoNuevo(ControladorMenu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	
	//al redefinir apretar(), hago que haga lo que yo quiera este boton
	public void apretar(){
		this.menu.empezarJuegoNuevo();
	}

}