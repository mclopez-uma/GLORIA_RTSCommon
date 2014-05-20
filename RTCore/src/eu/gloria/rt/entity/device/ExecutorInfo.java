
package eu.gloria.rt.entity.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para executorInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="executorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uuidOp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://gloria.eu/rt/entity/device}executorState"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executorInfo", propOrder = {
    "uuidOp",
    "description",
    "state"
})
public class ExecutorInfo {

    @XmlElement(required = true)
    protected String uuidOp;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected ExecutorState state;

    /**
     * Obtiene el valor de la propiedad uuidOp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidOp() {
        return uuidOp;
    }

    /**
     * Define el valor de la propiedad uuidOp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidOp(String value) {
        this.uuidOp = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad state.
     * 
     * @return
     *     possible object is
     *     {@link ExecutorState }
     *     
     */
    public ExecutorState getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecutorState }
     *     
     */
    public void setState(ExecutorState value) {
        this.state = value;
    }

}
