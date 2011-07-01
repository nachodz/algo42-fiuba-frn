package algo42Full.control;

/**
 * Boton para salir del juego.
 */
public class BotonSalir extends Boton{
	private ControladorMenu menu;

	/**
	 * Constructor
	 * @param menu El menu que va a tener al boton.
	 * @param x Posicion en x, un int.
	 * @param y Posicion en y, un int.
	 * @param ancho El ancho del boton, un int.
	 * @param alto El alto del boton, un int.
	 */
	public BotonSalir(ControladorMenu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	
	/**
	 * Le avisa el menu que se quiere salir del juego
	 */
	public void apretar(){
		this.menu.salir();
	}

}