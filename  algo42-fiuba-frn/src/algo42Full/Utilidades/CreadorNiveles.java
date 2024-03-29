package algo42Full.Utilidades;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import algo42Full.modelo.AvionCivil;
import algo42Full.modelo.Avioneta;
import algo42Full.modelo.Bombardero;
import algo42Full.modelo.Caza;
import algo42Full.modelo.CazaII;
import algo42Full.modelo.Explorador;
import algo42Full.modelo.Flota;
import algo42Full.modelo.FlotaEnemiga;
import algo42Full.modelo.Helicoptero;
import algo42Full.modelo.NaveViva;
import algo42Full.modelo.NaveVivaEnemiga;
import algo42Full.modelo.ZonaCombate;


public class CreadorNiveles {
	
	private FlotaEnemiga flotaEnemiga;
	private Flota flota;
	private int anchoZona;
	@SuppressWarnings("unused")
	private int altoZona;
	private ZonaCombate zona;
	
	public CreadorNiveles(int alto, int ancho){
		this.flota = new Flota();
		this.flotaEnemiga = new FlotaEnemiga();
		this.anchoZona = ancho;
		this.altoZona = alto;
		this.zona = new ZonaCombate(alto, ancho);
		
	}
	
	public int generarPosXSalida(int radio){
		
		int posXSalida = (int) (Math.random()* (this.anchoZona - radio))+ radio;
		
		return posXSalida;
	}
	
	public void agregarAvionesCiviles(int cantidad){
		
		for(int i = 0; i < cantidad; i++ ){
			
			NaveViva civil = new AvionCivil(this.zona, generarPosXSalida(25), 0);
			this.flota.agregarAvion(civil);	

		}
	}
	
	public void agregarHelicoptero(){
							
		NaveViva helicoptero = new Helicoptero(this.zona, generarPosXSalida(25), 0);
		this.flota.agregarAvion(helicoptero);	
	
	}
	
	public void agregarAvionetas(int cantidad){
		
		for(int i = 0; i < cantidad; i++ ){
			
			NaveVivaEnemiga avioneta = new Avioneta(this.zona, generarPosXSalida(25), 0);
			this.flotaEnemiga.agregarAvion(avioneta);	

		}
	}
	
	public void agregarBombarderos(int cantidad){
		
		for(int i = 0; i < cantidad; i++ ){
			
			NaveVivaEnemiga bombardero = new Bombardero(this.zona, generarPosXSalida(25), 0);
			this.flotaEnemiga.agregarAvion(bombardero);	

		}
	}
	
	public void agregarCazas(int cantidad){
		
		int posXSalidaCabeza = 200 + ((int) (Math.random()* (this.anchoZona - 300)));
		int posYSalidaCabeza = 0;
		int contadorCaza = 1;
		
		for(int i = 0; i < cantidad; i++ ){
			
			if(contadorCaza == 1)
			{
				NaveVivaEnemiga caza = new Caza(this.zona, posXSalidaCabeza, posYSalidaCabeza  );
				this.flotaEnemiga.agregarAvion(caza);	
				contadorCaza ++;
			}
			
			else if( (contadorCaza % 2) == 0){
				NaveVivaEnemiga caza = new Caza(this.zona, posXSalidaCabeza + (50 * (contadorCaza / 2)), (posYSalidaCabeza - (50 * (contadorCaza / 2))) );
				this.flotaEnemiga.agregarAvion(caza);	
				contadorCaza ++;
			}
			
			else if( (contadorCaza % 2) == 1){
				NaveVivaEnemiga caza = new Caza(this.zona, posXSalidaCabeza - (50 * (contadorCaza / 2)), (posYSalidaCabeza - (50 * (contadorCaza / 2))) );
				this.flotaEnemiga.agregarAvion(caza);
				contadorCaza ++;
			}


		}
	}
	
	public void agregarCazasII(int cantidad){
		
		
		int posXSalidaCabeza = 200 + ((int) (Math.random()* (this.anchoZona - 300)));
		int posYSalidaCabeza = 0;
		int contadorCaza = 1;
		
		for(int i = 0; i < cantidad; i++ ){
			
			if(contadorCaza == 1)
			{
				NaveVivaEnemiga cazaII = new CazaII(this.zona, posXSalidaCabeza, posYSalidaCabeza );
				this.flotaEnemiga.agregarAvion(cazaII);	
				contadorCaza ++;
			}
			
			else if( (contadorCaza % 2) == 0){
				NaveVivaEnemiga cazaII = new CazaII(this.zona, posXSalidaCabeza + (50 * (contadorCaza / 2)), (posYSalidaCabeza - (50 * (contadorCaza / 2))) );
				this.flotaEnemiga.agregarAvion(cazaII);	
				contadorCaza ++;
			}
			
			else if( (contadorCaza % 2) == 1){
				NaveVivaEnemiga cazaII = new CazaII(this.zona, posXSalidaCabeza - (50 * (contadorCaza / 2)), (posYSalidaCabeza - (50 * (contadorCaza / 2))));
				this.flotaEnemiga.agregarAvion(cazaII);	
				contadorCaza ++;
			}


		}
	}
	
	public void agregarExploradores(int cantidad){
		
		for(int i = 0; i < cantidad; i++ ){
			
			NaveVivaEnemiga explorador = new Explorador(this.zona, generarPosXSalida(25), 0);
			this.flotaEnemiga.agregarAvion(explorador);	

		}
	}
	
	public void generarNivel(String pathGuardado){
		/*
		 * Genera un archivo xml en la direccion en la ruta pasada como String en el parametro,
		 * con 2 flotas, una aliada y una enemiga que contienen los aviones agregados anteriormente
		 * al llamado de este metodo por medio de los metodos que permiten agregar aeronaves.
		 * Se selecciona aleatoriamente un lider a la flota de navesEnemigas.
		 */
		
		
		int indice = 1+ ( (int) (Math.random()* (this.flotaEnemiga.getListaAviones().size() - 1))) ;
		
		NaveVivaEnemiga nave = this.flotaEnemiga.getListaAviones().get(indice);
		nave.hacerGuia();
		this.flotaEnemiga.agregarAvionGuia(nave);
		
		
		try{
		
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
						
			Element nivel = doc.createElement("Nivel");	
			doc.appendChild(nivel);
			
			nivel.appendChild(this.flota.getElement(doc));
			nivel.appendChild(this.flotaEnemiga.getElement(doc));
		
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new PrintStream(pathGuardado)));
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
}
	

