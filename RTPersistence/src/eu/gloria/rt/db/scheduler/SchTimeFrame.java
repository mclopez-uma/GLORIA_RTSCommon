package eu.gloria.rt.db.scheduler;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the SchTimeFrame database table.
 * 
 */
@Entity
@Table(name = "SchTimeFrame")
public class SchTimeFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Timestamp dateEnd;

	private Timestamp dateIni;

	private String uuidOp;

    public SchTimeFrame() {
    }
    
    public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Timestamp getDateIni() {
		return this.dateIni;
	}

	public void setDateIni(Timestamp dateIni) {
		this.dateIni = dateIni;
	}

	public String getUuidOp() {
		return this.uuidOp;
	}

	public void setUuidOp(String uuidOp) {
		this.uuidOp = uuidOp;
	}

}