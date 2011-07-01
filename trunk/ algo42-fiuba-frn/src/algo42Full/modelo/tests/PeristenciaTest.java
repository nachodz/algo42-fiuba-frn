package algo42Full.modelo.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

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

import algo42Full.modelo.*;

import junit.framework.TestCase;

public class PeristenciaTest extends TestCase{
	
	public void testPersistenciaZonaCombate(){
		
			String archivo = "pruebaPersistencia1.xml";			
			
			ZonaCombate zona = new ZonaCombate(500, 500);
			
			Algo42 algo42 = new Algo42(zona, 150, 88);
			
			Bombardero bombardero = new Bombardero (zona, 100, 100);
						
			Caza caza = new Caza(zona, 260, 260);
			caza.hacerGuia();
			
			Avioneta avioneta = new Avioneta(zona, 63, 63);
						
			AvionCivil avionCivil = new AvionCivil(zona, 60, 74);
						
			FlotaEnemiga flota = new FlotaEnemiga();
			
			flota.agregarAvionGuia(caza);
			
			flota.agregarAvion(bombardero);
			flota.agregarAvion(caza);
			flota.agregarAvion(avioneta);
			
			Flota flota2 = new Flota();
			flota2.agregarAvion(avionCivil);		
			
			zona.agregarAlgo42(algo42);
			zona.agregarFlotaAliada(flota2);
			zona.agregarFlotaEnemiga(flota);
			
			try{
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();			
				
				Element element1 = zona.getElement(doc); 			
				doc.appendChild(element1);
			
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(new DOMSource(doc), new StreamResult(new PrintStream(archivo)));
				
				
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(archivo));
				Element element = doc.getDocumentElement();

				ZonaCombate zonaDesdeArchivo = ZonaCombate.fromElement(element);
				
				Flota flotaAlidadaDesdeArchivo = zonaDesdeArchivo.getFlotaAliada();
				FlotaEnemiga flotaEnemigaDesdeArchivo = zonaDesdeArchivo.getFlotaEnemiga();
				
				ArrayList<NaveViva> listaAliada = (ArrayList<NaveViva>) flotaAlidadaDesdeArchivo.getListaAviones();

				ArrayList<NaveVivaEnemiga> listaEnemiga = (ArrayList<NaveVivaEnemiga>) flotaEnemigaDesdeArchivo.getListaAviones();
	
				
				AvionCivil avionCivilDesdeArchivo = (AvionCivil)listaAliada.get(0);
				Bombardero bombarderoDesdeArchivo = (Bombardero) listaEnemiga.get(0);
				Caza cazaDesdeArchivo = (Caza) listaEnemiga.get(1);
				NaveVivaEnemiga avionetaDesdeArchivo =  listaEnemiga.get(2);
				
				assertTrue( avionCivilDesdeArchivo.getX() == 60);
				assertTrue( bombarderoDesdeArchivo.getX() == 100);
				assertTrue( cazaDesdeArchivo.getX()== 260);
				assertTrue( avionetaDesdeArchivo.getX() == 63);
				
				assertTrue( algo42.getX() == 150);
				assertTrue( algo42.getCantEnergia() == 20);
				
			
				
			} catch (ParserConfigurationException e) {
				fail();
			} catch (TransformerConfigurationException e) {
				fail();
			} catch (TransformerFactoryConfigurationError e) {
				fail();
			} catch (FileNotFoundException e) {
				fail();
			} catch (TransformerException e) {
				fail();
			} catch (SAXException e) {
				fail();
			} catch (IOException e) {
				fail();
			} 
		
		
		
		
	}
}
	
	


