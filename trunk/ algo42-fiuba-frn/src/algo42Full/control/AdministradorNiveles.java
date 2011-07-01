package algo42Full.control;

import java.util.ArrayList;
import java.util.List;

import algo42Full.control.ControladorNivel.EstadoNivel;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

/**
 * El administrador de niveles. Se encarga de administrar el controladorNivel,
 * para cargar el nivel siguiente si es que existe y resolver que sucede cuando
 * se termina un nivel, el algo42 muere o se gana el juego. Funciona como un mediador
 * entre el ControladorNivel y el mundo exterior.
 */
public class AdministradorNiveles {
	private ControladorNivel controladorNivel;
	private ControladorJuego controlador;
	List<String> niveles;
	private int puntaje;
	private int puntajeTotal;
	private EstadoNivel estado;
	private boolean sigueJugando;
	
	/**
	 * Constructor del administrador.
	 * @param controladorNivel El controladorNivel a administrar.
	 * @param controlador El controladorJuego a usar.
	 */
	public AdministradorNiveles(ControladorNivel controladorNivel, ControladorJuego controlador){
		this.controlador = controlador;
		this.controladorNivel = controladorNivel;
		niveles = new ArrayList<String>();
		niveles.add("niveles/nivel1.xml");
		niveles.add("niveles/nivel2.xml");
		niveles.add("niveles/nivel3.xml");
		puntaje = 0;
		puntajeTotal = 0;
		estado = EstadoNivel.JUGANDO;
		sigueJugando = true;
	}
	
	/**
	 * carga el administrador con un nivel de archivo
	 * @param archivoNivel El String con la ruta y nombre del nivel a cargar. 
	 */
	public void cargarJuego(String archivoNivel){
		controladorNivel.cargarJuego(archivoNivel, this.controlador);
	}
	
	/**
	 * guarda el juego en curso en un archivo
	 * @param archivoDestino El String con la ruta y nombre del juego a guardar.
	 */
	public void guardarJuego(String archivoDestino){
		controladorNivel.guardarJuego(archivoDestino);
	}
	
	/**
	 * carga el nivel en el ControladorJuego.
	 */
	public void cargarNivel(){
		controladorNivel.cargar();
	}
	
	/**
	 * quita el nivel del ControladorJuego
	 */
	public void descargarNivel(){
		controladorNivel.descargar();
	}
	
	/**
	 * devuelve el puntaje del nivel.
	 * @return Devuelve un int con el valor de puntaje del nivel.
	 */
	public int getPuntajeNivel(){
		return controladorNivel.getPuntaje();
	}
	
	/**
	 * Setea el puntaje actual del nivel
	 * @param puntaje el puntaje deseado a tener en el nivel.
	 */
	public void setPuntajeNivel(int puntaje){
		controladorNivel.setPuntaje(puntaje);
	}
	
	/**
	 * devuelve el estado del nivel.
	 * @return El EstadoNivel, puede ser JUGANDO, TERMINADO o ALGO42MUERTO
	 */
	public EstadoNivel getEstadoNivel(){
		return controladorNivel.getEstadoNivel();
	}
	
	/**
	 * Devuelve si se sigue jugando el juego en curso.
	 * @return True si todavía no termino el juego en curso, false si termino.
	 */
	public boolean sigueJugando(){
		return sigueJugando;
	}
	
	/**
	 * carga el administrador con un nivel
	 * @param archivoNivel El string con ruta y nombre de archivo a cargar al administrador.
	 */
	public void cargarNivel(String archivoNivel){
		controladorNivel.cargarNivel(archivoNivel);
	}
	
	/**
	 * entra al loop principal para jugar el nivel cargado en el administrador.
	 */
	public void jugar(){
		this.sigueJugando = true;
		boolean irMenu = false;
		
		while ((sigueJugando) && (!irMenu)){
			controladorNivel.cargar();
			controladorNivel.setPuntaje(puntaje);
			controlador.comenzarJuego();
			puntaje = controladorNivel.getPuntaje();
			puntajeTotal += puntaje;
			controladorNivel.descargar();
			estado = controladorNivel.getEstadoNivel();
			
			if (estado != EstadoNivel.JUGANDO){
				if (estado == EstadoNivel.ALGO42MUERTO){
					PantallaPerder pantallaPerder = new PantallaPerder(controlador);
					pantallaPerder.ejecutar();
					pantallaPerder = null;
					puntaje = 0;
					this.sigueJugando = false;
				}
				else{
					int index = niveles.indexOf(controladorNivel.getNombre());
					if (index != -1){
						index++;
						try{
							String proximoNivel = niveles.get(index);
							controladorNivel.cargarNivel(proximoNivel);
							PantallaNivelTerminado pantallaTerminado = new PantallaNivelTerminado(controlador);
							pantallaTerminado.ejecutar(puntajeTotal,puntaje);
							pantallaTerminado = null;
						}
						catch (IndexOutOfBoundsException e){
							PantallaGanar pantallaGanar = new PantallaGanar(controlador);
							pantallaGanar.ejecutar();
							pantallaGanar = null;
							this.sigueJugando = false;
						}
						puntaje = 0;
					}
					else System.out.print("BOOOM\n");
				}
			}
			else irMenu = true;
		}
	}

}
