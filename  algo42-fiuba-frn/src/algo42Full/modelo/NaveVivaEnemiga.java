package algo42Full.modelo; 

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class NaveVivaEnemiga extends NaveViva{
        
        protected boolean escapo;
        protected boolean esGuia;
        
        public NaveVivaEnemiga(ZonaCombate zona,int x,int y,int radio,int velX,int velY,int energia){
                super(zona,x,y,radio,velX,velY,energia);
                this.escapo = false;
                this.esGuia = false;
        }
        
                
        public boolean seEscapo(){
                
                return (this.escapo);
        }
        
        public void huir(){  //antes estaba implementado en cada clase
        	
    		if(this.escapo){
    			this.muerto = true;
    		}
                
                
                if (!(this.escapo)){
                        if ((this.velY) > 0 ){
                                
                                (this.velY) *= -1; 
                        }
                        
                        this.y += this.velY;
                        this.escapo = (this.zonaDeCombate).comprobarSalidaZona(this);
                }

        }
        
        public boolean esGuia(){
        	return this.esGuia;
        }
        
        public void hacerGuia(){
                this.esGuia = true;
                this.energia = 7;
                
        }
        
        public void desmarcarComoGuia(){
        	this.esGuia = false;
        	
        }
        
    	public void revivir(){
    		this.x = this.posInicialX;
    		this.y= this.posInicialY;
    		this.energia = energiaOriginal;
    		if(this.velY < 0)this.velY *= -1;
    		if(this.escapo) this.escapo=false;
    	}
        
    	public void writeElement(Element unElement, Document unDoc) {
			
    		super.writeElement(unElement, unDoc);
    		
    		Element escapo = unDoc.createElement("Escapo");
    		unElement.appendChild(escapo);
    		escapo.setTextContent(String.valueOf(this.escapo));
    		
    		Element esGuia = unDoc.createElement("EsGuia");
    		unElement.appendChild(esGuia);
    		esGuia.setTextContent(String.valueOf(this.esGuia));
    	}
    	
    	public static void writeNaveVivaEnemiga(Element element, NaveVivaEnemiga unaNaveVivaEnemiga) {
    		
    		writeNaveViva(element, unaNaveVivaEnemiga);
    		
    		NodeList childs = element.getChildNodes();
    		for (int i = 0; i < childs.getLength(); i++) {
    			Node child = childs.item(i);
    			if (child.getNodeName().equals("Escapo")) {
    				unaNaveVivaEnemiga.escapo = Boolean.parseBoolean(child.getTextContent());
    			}else if (child.getNodeName().equals("EsGuia")) {
        				unaNaveVivaEnemiga.esGuia = Boolean.parseBoolean(child.getTextContent());
    		
    			}
    		}
    		
    		
    	}

}
