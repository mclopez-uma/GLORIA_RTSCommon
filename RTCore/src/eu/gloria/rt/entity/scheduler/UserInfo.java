
package eu.gloria.rt.entity.scheduler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information about the user:
 * * id_user:  user identifier. It can be the user login, a numeric identifier...
 * * karma: karma information of the user 
 * 
 * <p>Java class for userInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_user" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="karma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userInfo", propOrder = {
    "idUser",
    "karma"
})
public class UserInfo {

    @XmlElement(name = "id_user", required = true)
    protected String idUser;
    @XmlElement(required = true)
    protected String karma;

    /**
     * Gets the value of the idUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Sets the value of the idUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUser(String value) {
        this.idUser = value;
    }

    /**
     * Gets the value of the karma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKarma() {
        return karma;
    }

    /**
     * Sets the value of the karma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKarma(String value) {
        this.karma = value;
    }

}
