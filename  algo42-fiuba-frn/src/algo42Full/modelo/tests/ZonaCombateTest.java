package algo42Full.modelo.tests;

import junit.framework.TestCase;
import algo42Full.modelo.*;

public class ZonaCombateTest extends TestCase{
	
	ZonaCombate zonaDeCombate;
	Algo42 algo42;
		
	
	public void setUp(){
		this.zonaDeCombate = new ZonaCombate(600, 500);
		this.algo42 = new Algo42(zonaDeCombate, 250, 580);
		(this.zonaDeCombate).agregarAlgo42(algo42);
	}
	
	public void testComprobarColisionconAlgo42Fail() {
		ObjetoColisionable unObjetoColisionable = new ObjetoColisionable(250, 400, 5);
		
		assertEquals(true, ((zonaDeCombate.comprobarColisionAlgo42(unObjetoColisionable)) == null) );
	}
	
	public void testComprobarColisionconAlgo42OK(){
		
		ObjetoColisionable unObjetoColisionable = new ObjetoColisionable(250, 582, 5);
		
		assertTrue( (zonaDeCombate.comprobarColisionAlgo42(unObjetoColisionable)) != null);
	}
	
	
	
	public void testComprobarSalidadeZonaFallar(){
		
		ObjetoColisionable unObjetoColisionable = new ObjetoColisionable(250, 250, 5);
		
		assertFalse( zonaDeCombate.comprobarSalidaZona(unObjetoColisionable));

	}
	
	
	public void testComprobarSalidadeZonaOK(){
		
		ObjetoColisionable unObjetoColisionable = new ObjetoColisionable(800, 900, 5);
		
		assertTrue(zonaDeCombate.comprobarSalidaZona(unObjetoColisionable));
	}
 
		
}
