
package eu.gloria.rt.entity.scheduler;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for planState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="planState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="QUEUED"/>
 *     &lt;enumeration value="ABORTED"/>
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="DONE"/>
 *     &lt;enumeration value="OUT_OF_TIME"/>
 *     &lt;enumeration value="EXCEEDED_TIME"/>
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "planState")
@XmlEnum
public enum PlanState {

    NONE,
    QUEUED,
    ABORTED,
    RUNNING,
    DONE,
    OUT_OF_TIME,
    EXCEEDED_TIME,
    ERROR;

    public String value() {
        return name();
    }

    public static PlanState fromValue(String v) {
        return valueOf(v);
    }

}
