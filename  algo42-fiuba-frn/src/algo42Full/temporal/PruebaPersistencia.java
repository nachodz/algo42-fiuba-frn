package algo42Full.temporal;
import algo42Full.modelo.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.xml.sax.SAXException;

import algo42Full.modelo.Algo42;
import algo42Full.modelo.AvionCivil;
import algo42Full.modelo.Avioneta;
import algo42Full.modelo.Bombardero;
import algo42Full.modelo.Caza;
import algo42Full.modelo.Flota;
import algo42Full.modelo.FlotaEnemiga;
import algo42Full.modelo.ZonaCombate;



public class PruebaPersistencia {
	

	
	public static void main(String args[]){
		persistirAlgo42();
		//recuperarDeArchivo("algo42.xml");
	}
	
	public static void persistirAlgo42(){
		
		String archivo = "algo42.xml";
		
		
		ZonaCombate zona = new ZonaCombate(500, 500);
		
		Algo42 algo42 = new Algo42(zona, 150, 88);
		Bombardero bombardero = new Bombardero (zona, 100, 100);
		Caza caza = new Caza(zona, 260, 260);
		Avioneta avioneta = new Avioneta(zona, 63, 63);
		AvionCivil avionCivil = new AvionCivil(zona, 60, 74);
		
		FlotaEnemiga flota = new FlotaEnemiga();
		flota.agregarAvionGuia(caza);
	//	flota.puntosBajas = 20;
		flota.agregarAvion(bombardero);
		flota.agregarAvion(caza);
		flota.agregarAvion(avioneta);
		
		Flota flota2 = new Flota();
		flota2.agregarAvion(avionCivil);
	//	flota.puntosBajas = 210;
		
		zona.agregarAlgo42(algo42);
		zona.agregarFlotaAliada(flota2);
		zona.agregarFlotaEnemiga(flota);
		
		try{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
		
			
			Element element1 = zona.getElement(doc); 			
			doc.appendChild(element1);
			
			
			
//			Element element3 = algo42.getElement(doc); 
//			doc.appendChild(element3);
		
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new PrintStream(archivo)));
			
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
	
	static void recuperarDeArchivo(String archivo){
		
		
		try {
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(archivo));
			Element element = doc.getDocumentElement();

			ZonaCombate zona = ZonaCombate.fromElement(element);
			System.out.println(zona.getAlgo42PosX());
			System.out.println(zona.getAlgo42PosY());
			

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

}
