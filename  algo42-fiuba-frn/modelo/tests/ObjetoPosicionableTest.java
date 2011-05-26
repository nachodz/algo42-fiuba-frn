package algo42Full.modelo.tests;

import static org.junit.Assert.*;
import algo42Full.modelo.*;

import org.junit.Test;

public class ObjetoPosicionableTest {

	@Test
	public void testHuboColision() {
		
		ObjetoPosicionable objeto1 = new ObjetoPosicionable(30, 30 ,3);
		ObjetoPosicionable objeto2 = new ObjetoPosicionable(32, 32 ,4);
		ObjetoPosicionable objeto3 = new ObjetoPosicionable(36, 30 ,3);
		
		assertTrue(objeto1.huboColision(objeto2));
		assertTrue(objeto2.huboColision(objeto3));
		assertFalse(objeto1.huboColision(objeto3));
	}

	

}
