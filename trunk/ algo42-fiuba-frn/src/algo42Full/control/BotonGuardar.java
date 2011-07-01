package algo42Full.control;

/**
 * Boton para guardar un juego en curso.
 */
public class BotonGuardar extends Boton{
	private ControladorMenu menu;

	/**
	 * Constructor.
	 * @param menu El menu que va a contener el boton.
	 * @param x Posicion en x, un int.
	 * @param y Posicion en y, un int.
	 * @param ancho El ancho del boton, un int.
	 * @param alto El alto del boton, un int.
	 */
	public BotonGuardar(ControladorMenu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	
	/**
	 * avisa al menu que se quiere guardar un juego en curso
	 */
	public void apretar(){
		this.menu.guardar();
	}

}