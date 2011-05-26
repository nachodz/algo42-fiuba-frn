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
		ObjetoPosicionable unObjetoPosicionable = new ObjetoPosicionable(250, 400, 5);
		
		assertEquals(true, ((zonaDeCombate.comprobarColisionAlgo42(unObjetoPosicionable)) == null) );
	}
	
	public void testComprobarColisionconAlgo42OK(){
		
		ObjetoPosicionable unObjetoPosicionable = new ObjetoPosicionable(250, 582, 5);
		
		assertTrue( (zonaDeCombate.comprobarColisionAlgo42(unObjetoPosicionable)) != null);
	}
	
	
	
	public void testComprobarSalidadeZonaFallar(){
		
		ObjetoPosicionable unObjetoPosicionable = new ObjetoPosicionable(250, 250, 5);
		
		assertFalse( zonaDeCombate.comprobarSalidaZona(unObjetoPosicionable));

	}
	
	
	public void testComprobarSalidadeZonaOK(){
		
		ObjetoPosicionable unObjetoPosicionable = new ObjetoPosicionable(800, 900, 5);
		
		assertTrue(zonaDeCombate.comprobarSalidaZona(unObjetoPosicionable));
	}
 
		
}
