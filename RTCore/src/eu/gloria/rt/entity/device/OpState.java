
package eu.gloria.rt.entity.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para opState.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="opState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DONE"/>
 *     &lt;enumeration value="ABORTED"/>
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="WAITING_TURN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "opState")
@XmlEnum
public enum OpState {

    DONE,
    ABORTED,
    RUNNING,
    WAITING_TURN;

    public String value() {
        return name();
    }

    public static OpState fromValue(String v) {
        return valueOf(v);
    }

}
