package algo42Full.temporal;

import algo42Full.Utilidades.CreadorNiveles;

public class PruebaCreadorNiveles {
	
	public static void main(String args[]){
		
		CreadorNiveles creador = new CreadorNiveles(600, 800);
		creador.agregarAvionesCiviles(3);
//		creador.agregarAvionetas(2);
		creador.agregarBombarderos(4);
//		creador.agregarCazas(5);
		creador.agregarCazasII(7);
//		creador.agregarExploradores(1);
	//	creador.agregarHelicoptero();
		
		
		
		creador.generarNivel("nivel7.xml");
	}

}
