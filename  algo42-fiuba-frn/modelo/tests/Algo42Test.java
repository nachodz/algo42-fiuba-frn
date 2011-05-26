package algo42Full.modelo.tests;


import org.junit.Before;
import junit.framework.TestCase;
import algo42Full.modelo.*;
import algo42Full.modelo.excepciones.*;

public class Algo42Test extends TestCase {
	private ZonaCombate zona;
	private Algo42 algo42;

	@Before
	public void setUp() throws Exception {
		this.zona = new ZonaCombate(600,500);
		this.algo42 = new Algo42(zona,250,580);
	}
	
	public void testRecibirDanio(){
		this.algo42.recibirDanio(4);
		assertTrue(this.algo42.estaVivo());
		
		this.algo42.recibirDanio(50);
		assertFalse(this.algo42.estaVivo());
	}
	
	private boolean esMajor(int a,int b){
		if (a>b) return true;
		else return false;
	}
	
	public void testMoverAbajo(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverAbajo();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertEquals(actX,iniX);
		assertTrue(this.esMajor(actY, iniY));
	}
	
	public void testMoverAbajoDer(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverAbajoDer();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertTrue(this.esMajor(actX, iniX));
		assertTrue(this.esMajor(actY, iniY));
	}
	
	public void testMoverAbajoIzq(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverAbajoIzq();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertTrue(this.esMajor(iniX, actX));
		assertTrue(this.esMajor(actY, iniY));
	}
	
	public void testMoverArriba(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverArriba();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertEquals(actX,iniX);
		assertTrue(this.esMajor(iniY, actY));
	}
	
	public void testMoverArribaDer(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverArribaDer();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertTrue(this.esMajor(actX, iniX));
		assertTrue(this.esMajor(iniY, actY));
	}
	
	public void testMoverArribaIzq(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverArribaIzq();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertTrue(this.esMajor(iniX, actX));
		assertTrue(this.esMajor(iniY, actY));
	}
	
	public void testMoverDerecha(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverDerecha();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertEquals(actY,iniY);
		assertTrue(this.esMajor(actX, iniX));
	}
	
	public void testMoverIzqierda(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getPosx();
		iniY = this.algo42.getPosy();
		this.algo42.moverIzquierda();
		actX = this.algo42.getPosx();
		actY = this.algo42.getPosy();
		assertEquals(actY,iniY);
		assertTrue(this.esMajor(iniX, actX));
	}
	
	public void testDispararCoheteFallar(){
		try{
			this.algo42.dispararCohete();
			fail("El método debería haber lanzado NoTieneCohetesException");
		}catch(NoTieneCohetesException e){
			assertTrue(true);
		}
		
	}
	
	public void testDispararCoheteOk(){
		
		this.algo42.cargarCohete();
		this.algo42.dispararCohete();
		assertEquals(this.algo42.getCantCohetes(),0);
	}
	
	public void testDispararTorpedoFallar(){
		try{
			this.algo42.dispararTorpedo();
			fail("El método debería haber lanzado NoTieneTorpedosException");
		}catch(NoTieneTorpedosException e){
			assertTrue(true);
		}
		
	}
	
	public void testDispararTorpedoOk(){
		
		this.algo42.cargarTorpedo();
		this.algo42.dispararTorpedo();
		assertEquals(this.algo42.getCantTorpedos(),0);
	}
	


}
