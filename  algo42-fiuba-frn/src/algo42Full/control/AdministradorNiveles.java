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
	private EstadoNivel estado;
	
	public AdministradorNiveles(ControladorNivel controladorNivel, ControladorJuego controlador){
		this.controlador = controlador;
		this.controladorNivel = controladorNivel;
		niveles = new ArrayList<String>();
		niveles.add("nivel1.xml");
		niveles.add("nivel2.xml");
		niveles.add("nivel3.xml");
		puntaje = 0;
		estado = EstadoNivel.JUGANDO;
	}
	
	public void cargarNuevoNivel(String archivoNivel){
		controladorNivel.cargarJuego(archivoNivel, this.controlador);
	}
	
	public void guardarNivel(String archivoDestino){
		controladorNivel.guardarJuego(archivoDestino);
	}
	
	public void cargarNivel(){
		controladorNivel.cargar();
	}
	
	public void descargarNivel(){
		controladorNivel.descargar();
	}
	
	public int getPuntajeNivel(){
		return controladorNivel.getPuntaje();
	}
	
	public void setPuntajeNivel(int puntaje){
		controladorNivel.setPuntaje(puntaje);
	}
	
	public EstadoNivel getEstadoNivel(){
		return controladorNivel.getEstadoNivel();
	}
	
	/*se fija porque termino el nivel y hace la accion necesaria para cada situacion:
	 * -Si el algo42 esta muerto llama a la pantalla de perder
	 * -Si termino el nivel llama a la pantalla de nivel terminado o a la de Pantallaganar
	 * si es que no hay mas niveles por jugar
	 */
	public void resolverResultadoNivel(){
		EstadoNivel estado = controladorNivel.getEstadoNivel();
		if (estado != EstadoNivel.JUGANDO){
			PantallaResultado pantalla = new PantallaResultado(controlador,estado,controladorNivel.getPuntaje());
			pantalla.ejecutar();
		}
		
	}
	
	//el loop principal del nivel
	public void jugar(){
		boolean seguirJugando = true;
		boolean irMenu = false;
		
		while ((seguirJugando) && (!irMenu)){
			controladorNivel.cargar();
			controladorNivel.setPuntaje(puntaje);
			controlador.comenzarJuego();
			puntaje = controladorNivel.getPuntaje();
			controladorNivel.descargar();
			estado = controladorNivel.getEstadoNivel();
			
			//PROBLEMA: no sale mas del juego asi, no vuelve al menu
			if (estado != EstadoNivel.JUGANDO){
				if (estado == EstadoNivel.ALGO42MUERTO){
					PantallaPerder pantallaPerder = new PantallaPerder(controlador);
					pantallaPerder.ejecutar();
					pantallaPerder = null;
					puntaje = 0;
					seguirJugando = false;
				}
				else{
					int index = niveles.indexOf(controladorNivel.getNombre());
					if (index != -1){
						index++;
						try{
							String proximoNivel = niveles.get(index);
							this.cargarNuevoNivel(proximoNivel);
						}
						catch (IndexOutOfBoundsException e){
							PantallaGanar pantallaGanar = new PantallaGanar(controlador);
							pantallaGanar.ejecutar();
							pantallaGanar = null;
							seguirJugando = false;
						}
					}
					else System.out.print("BOOOM\n");
				}
			}
			else irMenu = true;
		}
	}

}
