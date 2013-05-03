
package eu.gloria.rt.entity.scheduler;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scheduler complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scheduler">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationCommandList" type="{http://gloria.eu/rt/entity/scheduler}operationCommand" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scheduler", propOrder = {
    "operationCommandList"
})
public class Scheduler {

    protected List<OperationCommand> operationCommandList;

    /**
     * Gets the value of the operationCommandList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operationCommandList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperationCommandList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperationCommand }
     * 
     * 
     */
    public List<OperationCommand> getOperationCommandList() {
        if (operationCommandList == null) {
            operationCommandList = new ArrayList<OperationCommand>();
        }
        return this.operationCommandList;
    }

}
