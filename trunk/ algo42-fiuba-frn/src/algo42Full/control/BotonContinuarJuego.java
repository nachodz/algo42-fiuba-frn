package algo42Full.control;

/*
 * Boton para continuar con un juego guardado.
 */
public class BotonContinuarJuego extends Boton{
	private ControladorMenu menu;

	/**
	 * Constructor
	 * @param menu El menu donde va a estar el boton.
	 * @param x Posicion en x, un int.
	 * @param y Posicion en y, un int.
	 * @param ancho int con el ancho del boton.
	 * @param alto int con el alto del boton.
	 */
	public BotonContinuarJuego(ControladorMenu menu, int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
		this.menu = menu;
	}
	
	/**
	 * avisa al menu de que se quiere cargar un juego guardado.
	 */
	public void apretar(){
		this.menu.continuarJuego();
	}

}
