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
        /**
         * @return escapo, es true si escapo y false sino.
         */
        public boolean seEscapo(){
                
                return (this.escapo);
        }
        /**
         * Si se escapo se lo tilda como muerto; sino se escapa. 
         */
        public void huir(){  
        	
    		if(this.escapo){
    			this.muerto = true;
    		}       
                
            else{
            	if ((this.velY) > 0 ){
                             
            		(this.velY) *= -1; 
                }
                        
                this.y += this.velY;
                this.escapo = (this.zonaDeCombate).comprobarSalidaZona(this);
            }

        }
        /**
         * @return esGuia, true si lo es, false sino.
         */
        public boolean esGuia(){
        	return this.esGuia;
        }
        /**
         * se lo tilda como guia.
         */
        public void hacerGuia(){

            this.esGuia = true;
            this.energia = 7;
                
        }
        /**
         * se lo destilda como guia.
         */
        public void desmarcarComoGuia(){
        	this.esGuia = false;
        	
        }
        
    	public void revivir(){
    		/**
    		 * Restaura las condiciones iniciales de las variables x, y,
    		 * 	energia, velY y escapo.
    		 */
    		this.x = this.posInicialX;
    		this.y= this.posInicialY;
    		this.energia = energiaOriginal;
    		if(this.velY < 0)this.velY *= -1;
    		if(this.escapo) this.escapo=false;
    	}
        
    	public void writeElement(Element unElement, Document unDoc) {
    		/**
    		 * Escribe en el Element pasado como parametro perteneciente al Document
    		 * tambien parametro, todas las variables pertenecientes
    		 * al tipo NaveVivaEnemiga.
    		 */		
			
    		super.writeElement(unElement, unDoc);
    		
    		Element escapo = unDoc.createElement("Escapo");
    		unElement.appendChild(escapo);
    		escapo.setTextContent(String.valueOf(this.escapo));
    		
    		Element esGuia = unDoc.createElement("EsGuia");
    		unElement.appendChild(esGuia);
    		esGuia.setTextContent(String.valueOf(this.esGuia));
    	}
    	
    	public static void writeNaveVivaEnemiga(Element element, NaveVivaEnemiga unaNaveVivaEnemiga) {
    		/**
    		 * Escribe en el Element pasado como parametro perteneciente al Document
    		 * tambien parametro, todas las variables pertenecientes
    		 * al tipo NaveViva
    		 */		
    		
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
