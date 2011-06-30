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
		this.algo42 = new Algo42(zona,250,480);
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
		
		iniX = this.algo42.getX();
		iniY = this.algo42.getY();
		this.algo42.moverAbajo();
		this.algo42.vivir();
		actX = this.algo42.getX();
		actY = this.algo42.getY();
		assertEquals(actX,iniX);
		assertTrue(this.esMajor(actY, iniY));
	}
	

	
	public void testMoverArriba(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getX();
		iniY = this.algo42.getY();
		this.algo42.moverArriba();
		this.algo42.vivir();
		actX = this.algo42.getX();
		actY = this.algo42.getY();
		assertEquals(actX,iniX);
		assertTrue(this.esMajor(iniY, actY));
	}
	

	
	public void testMoverDerecha(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getX();
		iniY = this.algo42.getY();
		this.algo42.moverDerecha();
		this.algo42.vivir();
		actX = this.algo42.getX();
		actY = this.algo42.getY();
		assertEquals(actY,iniY);
		assertTrue(this.esMajor(actX, iniX));
	}
	
	public void testMoverIzquierda(){
		int iniX, iniY, actX, actY;
		
		iniX = this.algo42.getX();
		iniY = this.algo42.getY();
		this.algo42.moverIzquierda();
		this.algo42.vivir();
		actX = this.algo42.getX();
		actY = this.algo42.getY();
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
