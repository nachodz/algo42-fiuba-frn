package algo42Full.modelo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ZonaCombate {
	private int ancho;
	private int alto;
	private Algo42 algo42;
	private FlotaEnemiga flotaEnemiga;
	private Flota flotaAliada;
	private List<Proyectil> listaProyectiles;
	private List<ObjetoVivo> listaActualizaciones; //revisar esto!!
	
	public ZonaCombate(int alto,int ancho){
		this.alto = alto; // se podría chekear
		this.ancho = ancho;
		this.algo42 = null;
		this.flotaAliada = null;
		this.flotaEnemiga = null;
		this.listaActualizaciones = new ArrayList<ObjetoVivo>();
		this.listaProyectiles = new ArrayList<Proyectiles>();
		
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
		if(this.flotaAliada) this.flotaAliada.vivir();
		if(this.flotaEnemiga) this.flotaEnemiga.vivir();
		Iterator<Proyectil> iProyectil = this.listaProyectiles.iterator();
		while(iProyectil.hasNext()){
			iProyectil.next().vivir();
		}
		Iterator<ObjetoVivo> iterador = this.listaActualizaciones.iterator();
		while(iterador.hasNext()){
			iterador.next().vivir();
		}	
	}
	
	public Atacable comprobarColisionAlgo42(ObjetoPosicionable objeto){
		if(this.algo42.huboColision(objeto)) return this.algo42;
	}
	
	public Atacable comprobarColisionFlotaAliada(ObjetoPosicionable objeto){
		if(this.flotaAliada != null){
			return this.flotaAliada.comprobarColision(objeto);
		}
		else return null;
	}
	
	public Atacable comprobarColisionFlotaEnemiga(ObjetoPosicionable objeto){
		if(this.flotaEnemiga != null ){
			return this.flotaEnemiga.comprobarColision(objeto);
		}
		else return null;
	}
	
	public boolean comprobarSalidaZona(ObjetoPosicionable objeto){
		int tempX,tempY,tempRadio, x,y,x2,y2;
		
		tempX = objeto.getPosx();
		tempY = objeto.getPosy();
		tempRadio = objeto.getRadio();
		x = tempX+tempRadio;
		y = tempY+tempRadio;
		x2 = tempX-tempRadio;
		y2 = tempY-tempRadio;
		
		if ((x>-1)&&(x2 <= this.ancho)&&(y>-1)&&(y2<=this.alto)) return false;
		else return true;
	}
	
	public int getAlgo42PosX(){
		return this.algo42.getPosx();
	}
	
	public int getAlgo42PosY(){
		return this.algo42.getPosy();
	}
	
	public void QuitarObjetosMuertos(){
		List<ObjetoVivo> tempLista;
		List<Proyectil> tempProyectiles;
		ObjetoVivo objeto;
		Proyectil proyectil;
		
		tempLista = new ArrayList<ObjetoVivo>();
		Iterator<ObjetoVivo> iterador = this.listaActualizaciones.iterator();
		while(iterador.hasNext()){
			objeto = iterador.next();
			if (objeto.estaVivo()) tempLista.add(objeto);	
		}
		this.listaActualizaciones = tempLista;
		tempProyectiles = new ArrayList<Proyectil>();
		Iterator<Proyectil> iProyectiles = this.listaProyectiles.iterator();
		while(iProyectiles.hasNext()){
			proyectil = iProyectiles.next();
			if (proyectil.estaVivo()) tempProyectiles.add(objeto);	
		}
		this.listaProyectiles = tempProyectiles;
		
		if(this.flotaAliada) this.flotaAliada.quitarBajas();
		if(this.flotaEnemiga) this.flotaEnemiga.quitarBajas();
	}
	
	public int reportarPuntosBajas(){
		int puntosEnemigos,puntosAliados;
		
		if(this.flotaAliada) puntosAliados = this.flotaAliada.reportarPuntosBajas();
		else puntosAliados = 0;
		
		if(this.flotaEnemiga) puntosEnemigos = this.flotaEnemiga.reportarPuntosBajas();
		else puntosEnemigos = 0;
		
		return puntosAliados+puntosEnemigos;	
	}
	
	
	
	

}
