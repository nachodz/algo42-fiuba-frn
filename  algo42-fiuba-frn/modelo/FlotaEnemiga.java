package algo42Full.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlotaEnemiga{
	protected List<NaveVivaEnemiga> listaAviones;
	protected int puntosBajas;
	private NaveVivaEnemiga avionGuia;
	
	public FlotaEnemiga(){
		this.listaAviones = new ArrayList<NaveVivaEnemiga>();
		this.puntosBajas = 0;
		this.avionGuia = null;
	}
	
	public void agregarAvion(NaveVivaEnemiga avionEnemigo){
		this.listaAviones.add(avionEnemigo);
	}
	
	
	public void agregarAvionGuia(NaveVivaEnemiga avionGuia){
		this.avionGuia = avionGuia;
	}
	
	public void quitarBajas(){
		List<NaveVivaEnemiga> tempLista;
		NaveVivaEnemiga avion;
		
		tempLista = new ArrayList<NaveVivaEnemiga>();
		Iterator<NaveVivaEnemiga> iterador = this.listaAviones.iterator();
		while(iterador.hasNext()){
			avion = iterador.next();
			if(avion.estaVivo()) tempLista.add(avion);
			else this.puntosBajas += avion.obtenerPuntos();
		}
		this.listaAviones = tempLista;
		if (!this.avionGuia.estaVivo()) this.puntosBajas += avionGuia.obtenerPuntos(); 
	}
	
	public int reportarPuntosBajas(){
		return this.puntosBajas;
	}
	
	public void vivir(){
		List<NaveVivaEnemiga> tempLista;
		NaveVivaEnemiga avion;
		
		tempLista = new ArrayList<NaveVivaEnemiga>();
		Iterator<NaveVivaEnemiga> iterador = this.listaAviones.iterator();
		while(iterador.hasNext()){
			if (this.avionGuia.estaVivo()) 
				iterador.next().vivir();
			else
				iterador.next().huir();
		}		
	}
	
	public Atacable comprobarColision(ObjetoPosicionable objeto){
		NaveVivaEnemiga avion;

		Iterator<NaveVivaEnemiga> iterador = this.listaAviones.iterator();
		boolean hubo = false;
		if (this.avionGuia.huboColision(objeto)) return this.avionGuia;
		while((!hubo)&&(iterador.hasNext())){
			avion = iterador.next();
			if (avion.huboColision(objeto)) hubo = true;
		}
		if (hubo) return avion;
		else return null;
	}
	
	
	
	
	
	
}
