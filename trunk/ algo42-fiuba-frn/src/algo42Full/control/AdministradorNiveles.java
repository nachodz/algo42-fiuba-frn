package algo42Full.control;

import java.util.ArrayList;
import java.util.List;

import algo42Full.control.ControladorNivel.EstadoNivel;
import ar.uba.fi.algo3.titiritero.ControladorJuego;

public class AdministradorNiveles {
	private ControladorNivel controladorNivel;
	private ControladorJuego controlador;
	List<String> niveles;
	private int puntaje;
	private int puntajeTotal;
	private EstadoNivel estado;
	private boolean sigueJugando;
	
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
	
	//carga el administrador con un nivel de archivo
	public void cargarJuego(String archivoNivel){
		controladorNivel.cargarJuego(archivoNivel, this.controlador);
	}
	
	//guarda el juego en curso en un archivo
	public void guardarJuego(String archivoDestino){
		controladorNivel.guardarJuego(archivoDestino);
	}
	
	//carga el nivel en el ControladorJuego
	public void cargarNivel(){
		controladorNivel.cargar();
	}
	
	//quita el nivel del ControladorJuego
	public void descargarNivel(){
		controladorNivel.descargar();
	}
	
	public int getPuntajeNivel(){
		return controladorNivel.getPuntaje();
	}
	
	public void setPuntajeNivel(int puntaje){
		controladorNivel.setPuntaje(puntaje);
	}
	
	//devuelve el estado del nivel, puede ser JUGANDO, TERMINADO o ALGO42MUERTO
	public EstadoNivel getEstadoNivel(){
		return controladorNivel.getEstadoNivel();
	}
	
	public boolean sigueJugando(){
		return sigueJugando;
	}
	
	//carga el administrador con un nivel
	public void cargarNivel(String archivoNivel){
		controladorNivel.cargarNivel(archivoNivel);
	}
	
	//entra al loop principal para jugar el nivel cargado en el administrador
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
