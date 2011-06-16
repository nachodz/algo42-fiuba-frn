package algo42Full.modelo;

import java.util.ArrayList;
import java.util.List;


public class Flota {
	protected List<NaveViva> listaAviones;
	protected int puntosBajas;
	
	public Flota(){
		this.listaAviones = new ArrayList<NaveViva>();
		this.puntosBajas = 0;
	}
	
	public void agregarAvion(NaveViva avion){
		this.listaAviones.add(avion);
	}
	
	public Atacable comprobarColision(ObjetoColisionable objeto){
		for( NaveViva avion : this.listaAviones){
			if (avion.huboColision(objeto)) return avion;
		}
		return null;
	}
	
	public void quitarBajas(){
		
		List<NaveViva> tempLista = new ArrayList<NaveViva>();
		for (NaveViva avion : this.listaAviones){
			if(avion.estaVivo()) tempLista.add(avion);
			else this.puntosBajas += avion.obtenerPuntos();
		}
		this.listaAviones = tempLista;
	}
	
	public int reportarPuntosBajas(){
		return this.puntosBajas;
	}
	
	public void vivir(){
		for (NaveViva nave : this.listaAviones ){
			nave.vivir();
		}
	}
	

}
