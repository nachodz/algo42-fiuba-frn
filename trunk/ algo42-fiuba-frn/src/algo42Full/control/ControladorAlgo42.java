package algo42Full.control;

import java.awt.event.KeyEvent;

import algo42Full.modelo.Algo42;
import algo42Full.modelo.excepciones.NoTieneCohetesException;
import algo42Full.modelo.excepciones.NoTieneTorpedosException;
import ar.uba.fi.algo3.titiritero.KeyPressedObservador;

/**
 * Se encarga de controlar la entrada por teclado del jugador
 * y avisarle lo que tiene que hacer el algo42 como respuesta.
 */
public class ControladorAlgo42 implements KeyPressedObservador {
	private Algo42 algo42;
	
	/**
	 * Constructor.
	 * @param algo42 El algo42 a controlar.
	 */
	public ControladorAlgo42(Algo42 algo42){
		this.algo42 = algo42;
	}

	@Override
	/**
	 * Resuelve lo que hace cuando se apreta un boton.
	 */
	public void keyPressed(KeyEvent event) {
		int tecla = event.getKeyCode();
		
		if (tecla == KeyEvent.VK_UP){
			algo42.moverArriba();
			return;
		}
		if (tecla==KeyEvent.VK_DOWN){
			algo42.moverAbajo();
			return;
		}
		if (tecla==KeyEvent.VK_LEFT){
			algo42.moverIzquierda();
			return;
		}
		if (tecla==KeyEvent.VK_RIGHT){
			algo42.moverDerecha();
			return;
		}
		
	}

	@Override
	/**
	 * resuelve lo que hace cuando se deja de apretar un boton.
	 */
	public void keyReleased(KeyEvent event) {
		int mov = event.getKeyCode();
		if ((mov == KeyEvent.VK_LEFT) || (mov == KeyEvent.VK_RIGHT)){
			algo42.detenerEjeX();
			return;
		}
		if ((mov == KeyEvent.VK_UP) || (mov == KeyEvent.VK_DOWN)){
			algo42.detenerEjeY();
			return;
		}
		if (mov==KeyEvent.VK_C){
			algo42.dispararLaser();
			return;
		}
		if (mov==KeyEvent.VK_X){
			try{
				algo42.dispararCohete();
			}
			catch (NoTieneCohetesException e){
				//No dispara nada
			}
			return;
		}
		if (mov==KeyEvent.VK_Z){
			try{
				algo42.dispararTorpedo();
			}
			catch (NoTieneTorpedosException e){
				//No dispara nada
			}
			return;
		}
		
	}

}
