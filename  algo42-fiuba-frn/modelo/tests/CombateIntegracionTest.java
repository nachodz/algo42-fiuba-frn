package algo42Full.modelo.tests;


import org.junit.Before;
import junit.framework.TestCase;
import algo42Full.modelo.*;


public class CombateIntegracionTest extends TestCase{
	private ZonaCombate zona;
	private Algo42 algo42;

	@Before
	public void setUp() throws Exception {
		this.zona = new ZonaCombate(600,500);
		this.algo42 = new Algo42(zona,250,580);
		this.zona.agregarAlgo42(this.algo42);
	}
	
	private boolean esMajor(int a,int b){
		if (a>b) return true;
		else return false;
	}
	
	public void testAlgo42recibeDanio(){
		int cantEnergia, i;
		NaveVivaEnemiga caza;
		FlotaEnemiga flota;
		
		caza = new Caza(this.zona,250,420);
		flota = new FlotaEnemiga();
		flota.agregarAvion(caza);
		this.zona.agregarFlotaEnemiga(flota);
		cantEnergia = this.algo42.getCantEnergia();
		for (i=0;i<38;i++)
			this.zona.combatir();
		this.zona.quitarObjetosMuertos();
		assertTrue(caza.estaVivo());
		assertTrue(esMajor(cantEnergia,this.algo42.getCantEnergia()));
		
	}
	
	public void testDestruirAvioneta(){
		NaveVivaEnemiga avioneta;
		FlotaEnemiga flota;
		int i;
		
		avioneta = new Avioneta(this.zona,250,530);
		flota = new FlotaEnemiga();
		flota.agregarAvion(avioneta);
		this.zona.agregarFlotaEnemiga(flota);
		this.algo42.dispararLaser();
		for(i=0;i<10;i++)
			this.zona.combatir();
		
		assertFalse(avioneta.estaVivo());
	
	}
	
	public void testDestruirBombarderoObtenerArma(){
		int cantTorpedos, cantCohetes, i;
		NaveVivaEnemiga bombardero;
		FlotaEnemiga flota;
		
		bombardero = new Bombardero(this.zona,250,520);
		bombardero.recibirDanio(3);
		flota = new FlotaEnemiga();
		flota.agregarAvion(bombardero);
		this.zona.agregarFlotaEnemiga(flota);
		cantTorpedos = this.algo42.getCantTorpedos();
		cantCohetes = this.algo42.getCantCohetes();
		this.algo42.dispararLaser();
		for (i=0;i<99;i++)
			this.zona.combatir();
		
		this.zona.quitarObjetosMuertos();
		cantTorpedos++;
		cantCohetes++;
		
		assertFalse(bombardero.estaVivo());
		assertTrue(this.algo42.estaVivo());
		assertEquals(cantTorpedos,this.algo42.getCantTorpedos());
		assertEquals(cantCohetes,this.algo42.getCantCohetes());
		
	}
	

}
