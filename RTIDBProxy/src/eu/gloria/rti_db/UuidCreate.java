
package eu.gloria.rti_db;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.gloria.rt.entity.db.UuidType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uuidType" type="{http://gloria.eu/rt/entity/db}uuidType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "uuidType"
})
@XmlRootElement(name = "uuidCreate")
public class UuidCreate {

    @XmlElement(required = true)
    protected UuidType uuidType;

    /**
     * Obtiene el valor de la propiedad uuidType.
     * 
     * @return
     *     possible object is
     *     {@link UuidType }
     *     
     */
    public UuidType getUuidType() {
        return uuidType;
    }

    /**
     * Define el valor de la propiedad uuidType.
     * 
     * @param value
     *     allowed object is
     *     {@link UuidType }
     *     
     */
    public void setUuidType(UuidType value) {
        this.uuidType = value;
    }

}
