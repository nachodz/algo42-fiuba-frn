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
	
		public void testDestruirCazaObtenerEnergia(){
		int cantEnergia, i;
		NaveVivaEnemiga caza;
		FlotaEnemiga flota;
		
		caza = new Caza(this.zona,250,420);
		flota = new FlotaEnemiga();
		flota.agregarAvion(caza);
		this.zona.agregarFlotaEnemiga(flota);
		cantEnergia = this.algo42.getCantEnergia();
		this.algo42.cargarCohete();
		this.algo42.dispararCohete();
		for (i=0;i<99;i++)
			this.zona.combatir();
		this.zona.quitarObjetosMuertos();
		assertFalse(caza.estaVivo());
		assertEquals(cantEnergia+1,this.algo42.getCantEnergia());
	}
	
	public void testGanarEnergia(){
		TanqueEnergia tanque;
		int cantEnergia, i;
		
		tanque = new TanqueEnergia(this.zona,250,570);
		this.zona.agregarActualizacionAlgo42(tanque);
		cantEnergia = this.algo42.getCantEnergia();
		for (i=0;i<10;i++){
			this.zona.combatir();
			this.algo42.moverArriba();
		}
		this.zona.quitarObjetosMuertos();
		
		assertEquals(cantEnergia+1,this.algo42.getCantEnergia());
	}
	
	public void testGanarUnCohete(){
		
			Cohete cohete;
			int cantCohetes, i;
			
			cohete = new Cohete(this.zona,250,570);
			this.zona.agregarActualizacionAlgo42(cohete);
			cantCohetes = this.algo42.getCantCohetes();
			for (i=0;i<10;i++){
				this.zona.combatir();
				this.algo42.moverArriba();
			}
			this.zona.quitarObjetosMuertos();
			
			assertEquals(cantCohetes+1,this.algo42.getCantCohetes());

	}
	
	public void testGanarUnTorpedo(){
		
		Torpedo torpedo;
		int cantTorpedos, i;
		
		torpedo = new Torpedo(this.zona,250,570);
		this.zona.agregarActualizacionAlgo42(torpedo);
		cantTorpedos = this.algo42.getCantTorpedos();
		for (i=0;i<10;i++){
			this.zona.combatir();
			this.algo42.moverArriba();
		}
		this.zona.quitarObjetosMuertos();
		
		assertEquals(cantTorpedos+1,this.algo42.getCantTorpedos());

	}
	
	public void testObtenerPuntosPositivos(){
		NaveVivaEnemiga avioneta;
		FlotaEnemiga flota;
		int i;
		
		avioneta = new Avioneta(this.zona,250,530);
		flota = new FlotaEnemiga();
		flota.agregarAvion(avioneta);
		this.zona.agregarFlotaEnemiga(flota);
		this.algo42.dispararLaser();
		for (i=0;i<10;i++)
			this.zona.combatir();
		this.zona.quitarObjetosMuertos();
		
		assertEquals(20,this.zona.reportarPuntosBajas());
	}
	
	public void testPerderPuntosDestruirAliado(){
		NaveViva avion;
		Flota flota;
		int i;
		
		avion = new AvionCivil(this.zona,250,530);
		flota = new Flota();
		flota.agregarAvion(avion);
		this.zona.agregarFlotaAliada(flota);
		this.algo42.dispararLaser();
		for (i=0;i<10;i++)
			this.zona.combatir();
		this.zona.quitarObjetosMuertos();
		
		assertTrue(esMajor(0,this.zona.reportarPuntosBajas()));
	}
	
	public void testAvionGuiaDestruidoFlotaEnemigaEscapa(){
		NaveVivaEnemiga avioneta, avionGuia, bombardero, caza, explorador;
		FlotaEnemiga flota;
		int i;
		
		avionGuia = new Avioneta(this.zona,250,500);
		avioneta = new Avioneta(this.zona,100,300);
		bombardero = new Bombardero(this.zona,400,240);
		caza = new Caza(this.zona,50,100);
		explorador = new Explorador(this.zona,250,200);
		flota = new FlotaEnemiga();
		flota.agregarAvionGuia(avionGuia);
		flota.agregarAvion(avioneta);
		flota.agregarAvion(bombardero);
		flota.agregarAvion(caza);
		flota.agregarAvion(explorador);
		this.zona.agregarFlotaEnemiga(flota);
		
		this.algo42.dispararLaser();
		for(i=0;i<500;i++) // es 500 para darle tiempo a las navesEnemigas a escapar
			this.zona.combatir();
		
		assertFalse(avionGuia.estaVivo());
		assertTrue(avioneta.seEscapo());
		assertTrue(bombardero.seEscapo());
		assertTrue(caza.seEscapo());
		assertTrue(explorador.seEscapo());
	}
	
	
	public void testAlgo42recibeDanioDeCazaII(){
		int cantEnergia, i;
		NaveVivaEnemiga cazaII;
		FlotaEnemiga flota;
		
		cazaII = new CazaII(this.zona,250,420);
		flota = new FlotaEnemiga();
		flota.agregarAvion(cazaII);
		this.zona.agregarFlotaEnemiga(flota);
		cantEnergia = this.algo42.getCantEnergia();
		for (i=0;i<38;i++)
			this.zona.combatir();
		this.zona.quitarObjetosMuertos();
		assertTrue(cazaII.estaVivo());
		assertTrue(esMajor(cantEnergia,this.algo42.getCantEnergia()));
		
	}
	
	
	public void testAlgo42recibeDanioDeTorpedoAdaptable(){
		int cantEnergia, i;
		Proyectil proyectilTorpedoAdaptable;
				
		proyectilTorpedoAdaptable = new ProyectilTorpedoAdaptable(this.zona, true, 250, 580);

		this.zona.agregarProyectil(proyectilTorpedoAdaptable);
		cantEnergia = this.algo42.getCantEnergia();
		
		for (i=0;i<5;i++)
			this.zona.combatir();
		this.zona.quitarObjetosMuertos();
		
		assertEquals((cantEnergia / 2), (this.algo42).getCantEnergia(), 0.2);
		
	}
	

}
