package algo42Full.modelo;

import java.util.ArrayList;
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
		
		List<NaveVivaEnemiga> tempLista = new ArrayList<NaveVivaEnemiga>();
		for (NaveVivaEnemiga avion : this.listaAviones){
			if(avion.estaVivo()) tempLista.add(avion);
			else this.puntosBajas += avion.obtenerPuntos();
		}
		this.listaAviones = tempLista;
		if (this.avionGuia != null)
			if (!this.avionGuia.estaVivo())
				this.puntosBajas += avionGuia.obtenerPuntos(); 
		
	}
	
	public int reportarPuntosBajas(){
		return this.puntosBajas;
	}
	

	public void vivir(){
		
		if (this.avionGuia == null)
			for (NaveVivaEnemiga nave : this.listaAviones){
				nave.vivir();
			}
		else{
			for (NaveVivaEnemiga nave : this.listaAviones){
				if (this.avionGuia.estaVivo())
					nave.vivir();
				else
					nave.huir();
			}
		}
	}
	
	
	public Atacable comprobarColision(ObjetoColisionable objeto){
		
		if (this.avionGuia != null)
			if (this.avionGuia.huboColision(objeto)) return this.avionGuia;
		for (NaveVivaEnemiga nave : this.listaAviones){
			if (nave.huboColision(objeto)) return nave;
		}
		return null;
	}
	
	
	
	
}
