package ar.uba.fi.algo3.titiritero;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.uba.fi.algo3.titiritero.audio.Reproductor;


/**
 * @author Nicolas
 * Esta clase es la encargada de manejar todo el gameloop. Básicamente tiene una lista
 * de ObjetosVivos y una Dibujables que son recorridas en cada gameloop.
 */
public class ControladorJuego implements Runnable {
	
	public ControladorJuego(boolean activarReproductor){
		this.objetosVivos = new ArrayList<ObjetoVivo>();
		this.dibujables = new ArrayList<Dibujable>();
		this.mouseClickObservadores = new ArrayList<MouseClickObservador>();
		this.keyPressedObservadores = new ArrayList<KeyPressedObservador>();
		this.acciones = new ArrayList<Accion>();
		this.estaReproductorActivo = activarReproductor;
		if(this.estaReproductorActivo){
			this.reproductor = new Reproductor();	
		}
	}
	
	public boolean estaEnEjecucion(){
		return this.estaEnEjecucion;
	}
	
	public void comenzarJuego(){
		estaEnEjecucion = true;
		try{
			while(estaEnEjecucion){
				simular();
				ejecutarAcciones();
				dibujar();
				Thread.sleep(intervaloSimulacion);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void comenzarJuegoAsyn(){
		Thread thread = new Thread(this);
		thread.start();
		if(this.estaReproductorActivo){
			this.reproductor.encender();
			this.hiloAudio =  new Thread(this.reproductor);
			this.hiloAudio.start();
		}
	}

	/**
	 * Le da comienzo al juego poniendo en marcha el gameloop.
	 * @param cantidadDeCiclos cantidad de ciclos que debe correr el gameloop..  
	 */
	public void comenzarJuego(int cantidadDeCiclos){
		int contador = 0;
		estaEnEjecucion = true;
		try{
			while(contador < cantidadDeCiclos && estaEnEjecucion){
				simular();
				dibujar();
				Thread.sleep(intervaloSimulacion);
				contador++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Detiene el juego provocando la salida del gameloop.
	 */
	public void detenerJuego(){
		this.estaEnEjecucion = false;
		if(reproductor!=null){
			this.reproductor.apagar();
		}
	}
	

	public void agregarObjetoVivo(ObjetoVivo objetoVivo){
		objetosVivos.add(objetoVivo);
	}
	
	public void removerObjetoVivo(ObjetoVivo objetoVivo){
		objetosVivos.remove(objetoVivo);
	}
	
	public void removerTodosObjetosVivos(){
		this.objetosVivos.clear();
	}

	public void agregarDibujable(Dibujable unDibujable){
		dibujables.add(unDibujable);
	}
	
	public void removerDibujable(Dibujable unDibujable){
		dibujables.remove(unDibujable);
	}
	
	public void removerTodosDibujables(){
		dibujables.clear();
	}
	
	public long getIntervaloSimulacion() {
		return intervaloSimulacion;
	}

	public void setIntervaloSimulacion(long intervaloSimulacion) {
		this.intervaloSimulacion = intervaloSimulacion;
	}
 
	protected void dibujar() {
		Iterator<Dibujable> iterador = dibujables.iterator();
		while(iterador.hasNext()){
			Dibujable dibujable = iterador.next();
			dibujable.dibujar(this.superficieDeDibujo);
		}		
		this.superficieDeDibujo.actualizar();
	}
	
	/**
	 * Ejecuta la simulacion recorriendo la coleccion de objetivos vivos.
	 */
	protected void simular() {
		this.superficieDeDibujo.limpiar();
		Iterator<ObjetoVivo> iterador = objetosVivos.iterator();
		while(iterador.hasNext()){
			iterador.next().vivir();
		}
	}

	public SuperficieDeDibujo getSuperficieDeDibujo() {
		return superficieDeDibujo;
	}

	public void setSuperficieDeDibujo(SuperficieDeDibujo superficieDeDibujo) {
		this.superficieDeDibujo = superficieDeDibujo;
	}
	
	/**
	 * Se encarga de derivar el manejo del evento click al objeto vista correspondiente
	 * @param x posición horizontal del mouse
	 * @param y posición vertial del mouse
	 */
	public void despacharMouseClick(int x, int y){
		MouseClickObservador mouseClickObservador;
		Iterator<MouseClickObservador> iterador = this.mouseClickObservadores.iterator();
		while(iterador.hasNext()){
			mouseClickObservador = iterador.next();
			mouseClickObservador.MouseClick(x, y);
		}
	}
	
	public void agregarMouseClickObservador(MouseClickObservador unMouseClickObservador){
		this.mouseClickObservadores.add(unMouseClickObservador);
	}
	
	public void removerMouseClickObservador(MouseClickObservador unMouseClickObservador){
		this.mouseClickObservadores.remove(unMouseClickObservador);
	}
	
	public void removerTodosMouseClickObservador(){
		this.mouseClickObservadores.clear();
	}
	
	/**
	 * Se encarga de derivar el manejo del evento keyPress al objeto vista correspondiente
	 * @param KeyEvent evento
	 */
	public void despacharKeyPress(KeyEvent event){
		for (KeyPressedObservador observador : this.keyPressedObservadores){
			observador.keyPressed(event);
		}
	}
	
	public void despacharKeyRelease(KeyEvent event) {
		for (KeyPressedObservador observador : this.keyPressedObservadores){
			observador.keyReleased(event);
		}
	}	
	
	public void agregarKeyPressObservador(KeyPressedObservador unMouseClickObservador){
		this.keyPressedObservadores.add(unMouseClickObservador);
	}
	
	public void removerKeyPressObservador(KeyPressedObservador unMouseClickObservador){
		this.keyPressedObservadores.remove(unMouseClickObservador);
	}
	
	public void removerTodosKeyPressObservador(){
		this.keyPressedObservadores.clear();
	}
	
	public void agregarAccion(Accion accionNueva){
		this.acciones.add(accionNueva);
	}
	
	public void removerAccion(Accion accion){
		this.acciones.remove(accion);
	}
	
	private void ejecutarAcciones(){
		for (Accion accion: this.acciones){
			accion.ejecutarAccion();
		}
	}
	
	protected long intervaloSimulacion;
	protected boolean estaEnEjecucion;
	private List<ObjetoVivo> objetosVivos;
	private List<Dibujable> dibujables;
	private List<MouseClickObservador> mouseClickObservadores;
	private List<KeyPressedObservador> keyPressedObservadores;
	private List<Accion> acciones;
	private SuperficieDeDibujo superficieDeDibujo;
	private Reproductor reproductor;
	private Thread hiloAudio;
	private boolean estaReproductorActivo;
	
	public void run() {
		this.comenzarJuego();
	}

	public Reproductor getReproductorDeAudio() {
		if(!this.estaReproductorActivo)
			throw new OperacionNoValida();
		return this.reproductor;
	}	
}
