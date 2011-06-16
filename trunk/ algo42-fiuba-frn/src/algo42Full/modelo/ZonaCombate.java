package algo42Full.modelo;


import java.util.ArrayList;
import java.util.List;
import ar.uba.fi.algo3.titiritero.ObjetoVivo;

public class ZonaCombate {
	private int ancho;
	private int alto;
	private Algo42 algo42;
	private FlotaEnemiga flotaEnemiga;
	private Flota flotaAliada;
	private List<Proyectil> listaProyectiles;
	private List<ObjetoVivo> listaActualizaciones; 
	
	public ZonaCombate(int alto,int ancho){
		this.alto = alto; // se podría chekear
		this.ancho = ancho;
		this.algo42 = null;
		this.flotaAliada = null;
		this.flotaEnemiga = null;
		this.listaActualizaciones = new ArrayList<ObjetoVivo>();
		this.listaProyectiles = new ArrayList<Proyectil>();
		
	}
	
	public void agregarActualizacionAlgo42(ObjetoVivo actualizacion){
		this.listaActualizaciones.add(actualizacion);
	}
	
	public void agregarAlgo42(Algo42 algo42){
		this.algo42 = algo42;	
	}
	
	public void agregarFlotaAliada(Flota flota){
		this.flotaAliada = flota;
	}
	
	public void agregarFlotaEnemiga(FlotaEnemiga flota){
		this.flotaEnemiga = flota;
	}
	
	public void agregarProyectil(Proyectil proyectil){
		this.listaProyectiles.add(proyectil);
	}
	

	public void combatir(){
		
		if(this.flotaAliada != null) this.flotaAliada.vivir();
		if(this.flotaEnemiga != null) this.flotaEnemiga.vivir();
		for (Proyectil proyect: this.listaProyectiles){
			proyect.vivir();
		}
		for (ObjetoVivo actualizacion: this.listaActualizaciones){
			actualizacion.vivir();
		}
	}
	
	public Algo42 comprobarColisionAlgo42(ObjetoColisionable objeto){
		if(this.algo42.huboColision(objeto)) return this.algo42;
		else return null;
	}
	
	public Atacable comprobarColisionFlotaAliada(ObjetoColisionable objeto){
		if(this.flotaAliada != null){
			return this.flotaAliada.comprobarColision(objeto);
		}
		else return null;
	}
	
	public Atacable comprobarColisionFlotaEnemiga(ObjetoColisionable objeto){
		if(this.flotaEnemiga != null ){
			return this.flotaEnemiga.comprobarColision(objeto);
		}
		else return null;
	}
	
	public boolean comprobarSalidaZona(ObjetoColisionable objeto){
		int tempX,tempY,tempRadio, x,y,x2,y2;
		
		tempX = objeto.getX();
		tempY = objeto.getY();
		tempRadio = objeto.getRadio();
		x = tempX+tempRadio;
		y = tempY+tempRadio;
		x2 = tempX-tempRadio;
		y2 = tempY-tempRadio;
		
		if ((x>-1)&&(x2 <= this.ancho)&&(y>-1)&&(y2<=this.alto)) return false;
		else return true;
	}
	
	public int getAlgo42PosX(){
		return this.algo42.getX();
	}
	
	public int getAlgo42PosY(){
		return this.algo42.getY();
	}
	
	
	public void quitarObjetosMuertos(){
		
		List<ObjetoVivo> tempLista  = new ArrayList<ObjetoVivo>();
		for (ObjetoVivo actualizacion : this.listaActualizaciones){
			if (actualizacion.estaVivo()) tempLista.add(actualizacion);
		}
		this.listaActualizaciones = tempLista;
		List<Proyectil> tempProyectiles = new ArrayList<Proyectil>();
		for (Proyectil proyect : this.listaProyectiles){
			if (proyect.estaVivo()) tempProyectiles.add(proyect);
		}
		this.listaProyectiles = tempProyectiles;
	
		if(this.flotaAliada != null) this.flotaAliada.quitarBajas();
		if(this.flotaEnemiga != null) this.flotaEnemiga.quitarBajas();
	}
	
	public int reportarPuntosBajas(){
		int puntosEnemigos,puntosAliados;
		
		if(this.flotaAliada != null) puntosAliados = this.flotaAliada.reportarPuntosBajas();
		else puntosAliados = 0;
		
		if(this.flotaEnemiga != null) puntosEnemigos = this.flotaEnemiga.reportarPuntosBajas();
		else puntosEnemigos = 0;
		
		return puntosAliados+puntosEnemigos;	
	}
	
	
	
	

}
