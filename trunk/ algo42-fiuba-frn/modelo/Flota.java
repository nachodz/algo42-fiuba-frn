package algo42Full.modelo;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	public Atacable comprobarColision(ObjetoPosicionable objeto){
		NaveViva avion;

		Iterator<NaveViva> iterador = this.listaAviones.iterator();
		boolean hubo = false;
		while((!hubo)&&(iterador.hasNext())){
			avion = iterador.next();
			if (avion.huboColision(objeto)) hubo = true;
		}
		if (hubo) return avion;
		else return null;
	}
	
	public void quitarBajas(){
		List<NaveViva> tempLista;
		NaveViva avion;
		
		tempLista = new ArrayList<NaveViva>();
		Iterator<NaveViva> iterador = this.listaAviones.iterator();
		while(iterador.hasNext()){
			avion = iterador.next();
			if(avion.estaVivo()) tempLista.add(avion);
			else this.puntosBajas += avion.obtenerPuntos();
		}
		this.listaAviones = tempLista;
	}
	
	public int reportarPuntosBajas(){
		return this.puntosBajas;
	}
	
	public void vivir(){
		List<NaveViva> tempLista;
		NaveViva avion;
		
		tempLista = new ArrayList<NaveViva>();
		Iterator<NaveViva> iterador = this.listaAviones.iterator();
		while(iterador.hasNext()){
			iterador.next().vivir();
		}
	}
	

}
