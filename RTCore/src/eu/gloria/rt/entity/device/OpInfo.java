
package eu.gloria.rt.entity.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para opInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="opInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uuidOp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://gloria.eu/rt/entity/device}opState"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "opInfo", propOrder = {
    "uuidOp",
    "user",
    "duration",
    "description",
    "state"
})
public class OpInfo {

    @XmlElement(required = true)
    protected String uuidOp;
    @XmlElement(required = true)
    protected String user;
    protected long duration;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected OpState state;

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
     * Obtiene el valor de la propiedad user.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad duration.
     * 
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Define el valor de la propiedad duration.
     * 
     */
    public void setDuration(long value) {
        this.duration = value;
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
     *     {@link OpState }
     *     
     */
    public OpState getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     * 
     * @param value
     *     allowed object is
     *     {@link OpState }
     *     
     */
    public void setState(OpState value) {
        this.state = value;
    }

}
