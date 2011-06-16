package algo42Full.modelo.tests;

import static org.junit.Assert.*;
import algo42Full.modelo.*;

import org.junit.Test;

public class ObjetoColisionableTest {

	@Test
	public void testHuboColision() {
		
		ObjetoColisionable objeto1 = new ObjetoColisionable(30, 30 ,3);
		ObjetoColisionable objeto2 = new ObjetoColisionable(32, 32 ,4);
		ObjetoColisionable objeto3 = new ObjetoColisionable(36, 30 ,3);
		
		assertTrue(objeto1.huboColision(objeto2));
		assertTrue(objeto2.huboColision(objeto3));
		assertFalse(objeto1.huboColision(objeto3));
	}

	

}
