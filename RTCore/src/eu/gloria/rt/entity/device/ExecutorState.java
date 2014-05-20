
package eu.gloria.rt.entity.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para executorState.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="executorState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="BUSY"/>
 *     &lt;enumeration value="IDLE"/>
 *     &lt;enumeration value="WAIT_TURN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "executorState")
@XmlEnum
public enum ExecutorState {

    RUNNING,
    BUSY,
    IDLE,
    WAIT_TURN;

    public String value() {
        return name();
    }

    public static ExecutorState fromValue(String v) {
        return valueOf(v);
    }

}
