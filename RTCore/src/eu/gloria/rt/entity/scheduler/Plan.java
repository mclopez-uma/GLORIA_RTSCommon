
package eu.gloria.rt.entity.scheduler;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for plan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="plan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="templateId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://gloria.eu/rt/entity/scheduler}planState"/>
 *         &lt;element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="errorDevId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="errorDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="execWindowStart" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="execWindowEnd" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="execMaxTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="rtStart" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="rtSEnd" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "plan", propOrder = {
    "id",
    "user",
    "templateId",
    "state",
    "creationDate",
    "errorDevId",
    "errorCode",
    "errorDesc",
    "execWindowStart",
    "execWindowEnd",
    "execMaxTime",
    "rtStart",
    "rtSEnd"
})
public class Plan {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String user;
    @XmlElement(required = true)
    protected String templateId;
    @XmlElement(required = true)
    protected PlanState state;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar creationDate;
    @XmlElement(required = true)
    protected String errorDevId;
    @XmlElement(required = true)
    protected BigInteger errorCode;
    @XmlElement(required = true)
    protected String errorDesc;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar execWindowStart;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar execWindowEnd;
    protected long execMaxTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rtStart;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rtSEnd;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the user property.
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
     * Sets the value of the user property.
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
     * Gets the value of the templateId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateId(String value) {
        this.templateId = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link PlanState }
     *     
     */
    public PlanState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanState }
     *     
     */
    public void setState(PlanState value) {
        this.state = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the errorDevId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDevId() {
        return errorDevId;
    }

    /**
     * Sets the value of the errorDevId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDevId(String value) {
        this.errorDevId = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setErrorCode(BigInteger value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * Sets the value of the errorDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDesc(String value) {
        this.errorDesc = value;
    }

    /**
     * Gets the value of the execWindowStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExecWindowStart() {
        return execWindowStart;
    }

    /**
     * Sets the value of the execWindowStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExecWindowStart(XMLGregorianCalendar value) {
        this.execWindowStart = value;
    }

    /**
     * Gets the value of the execWindowEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExecWindowEnd() {
        return execWindowEnd;
    }

    /**
     * Sets the value of the execWindowEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExecWindowEnd(XMLGregorianCalendar value) {
        this.execWindowEnd = value;
    }

    /**
     * Gets the value of the execMaxTime property.
     * 
     */
    public long getExecMaxTime() {
        return execMaxTime;
    }

    /**
     * Sets the value of the execMaxTime property.
     * 
     */
    public void setExecMaxTime(long value) {
        this.execMaxTime = value;
    }

    /**
     * Gets the value of the rtStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRtStart() {
        return rtStart;
    }

    /**
     * Sets the value of the rtStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRtStart(XMLGregorianCalendar value) {
        this.rtStart = value;
    }

    /**
     * Gets the value of the rtSEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRtSEnd() {
        return rtSEnd;
    }

    /**
     * Sets the value of the rtSEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRtSEnd(XMLGregorianCalendar value) {
        this.rtSEnd = value;
    }

}
