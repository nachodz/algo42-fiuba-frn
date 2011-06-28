package algo42Full.control;

public class BotonGuardar extends Boton{
	private ControladorMenu menu;

	public BotonGuardar(ControladorMenu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	
	//al redefinir apretar(), hago que haga lo que yo quiera este boton
	public void apretar(){
		this.menu.guardar();
	}

}