package eu.gloria.rt.db.scheduler;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the SchTimeFrame database table.
 */
@Entity
@Table(name = "SchTimeFrame")
public class SchTimeFrame implements Serializable {
	
	private static final long serialVersionUID = 1012114574270880723L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	
	private Timestamp dateIni;  // Date initial
	private Timestamp dateEnd;  // Date end
	
	private String uuidOp;  // Observing plan UUID

	public SchTimeFrame() {
		uuidOp = null;
	}
	
    public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getDateIni() {
		return dateIni;
	}

	public void setDateIni(Timestamp dateIni) {
		this.dateIni = dateIni;
	}

	public Timestamp getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getUuid() {
		return uuidOp;
	}

	public void setUuidOp(String uuidOp) {
		this.uuidOp = uuidOp;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("dateIni: " + dateIni + "\t");
		sb.append("dateEnd: " + dateEnd + "\t");
		sb.append("uuidOp: " + uuidOp);
		
		return sb.toString();
	}
}