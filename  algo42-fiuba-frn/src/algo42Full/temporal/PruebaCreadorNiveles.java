package algo42Full.temporal;

import algo42Full.Utilidades.CreadorNiveles;

public class PruebaCreadorNiveles {
	
	public static void main(String args[]){
		
		CreadorNiveles creador = new CreadorNiveles(600, 800);
		creador.agregarAvionesCiviles(1);
		creador.agregarAvionetas(1);
		creador.agregarBombarderos(1);
		creador.agregarCazas(2);
		
		creador.generarNivel("nivel1.xml");
	}

}
