/**
 * 
 */
package com.varone.web.form;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
public class VarOneConfigForm {
	@XmlElement public String port;
}