/**
 * 
 */
package com.varone.web.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VarOneConfigForm {
       @XmlElement(required=true)
       public String port;

       public String getPort() {
               return port;
       }

       public void setPort(String port) {
               this.port = port;
       }
       
       
}