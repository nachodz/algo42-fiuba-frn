package algo42Full.control;


public class BotonContinuarJuego extends Boton{
	private ControladorMenu menu;

	public BotonContinuarJuego(ControladorMenu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	public void apretar(){
		this.menu.continuarJuego();
	}

}
