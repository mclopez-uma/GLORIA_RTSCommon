package eu.gloria.rt.db.scheduler;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the ObservingPlan database table.
 * 
 */
@Entity
@Table(name = "ObservingPlan")
public class ObservingPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String uuid;
	
	private String user;
	
	private int priority;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date receivedDate;
	  
	@Enumerated( EnumType.ORDINAL)
	private ObservingPlanState state;
	
	@Enumerated( EnumType.ORDINAL)
	private ObservingPlanType type;
	
	private String file;	

	private String comment;
	
	private String description;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date advertDeadline;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date advertDateIni;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date advertDateEnd;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date offeredDate;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date scheduleDateEnd;

	@Temporal( TemporalType.TIMESTAMP)
	private Date scheduleDateIni;

    @Temporal( TemporalType.TIMESTAMP)
	private Date execDateEnd;

    @Temporal( TemporalType.TIMESTAMP)
	private Date execDateIni;
    
    @Temporal( TemporalType.TIMESTAMP)
	private Date execDeadline;
    
    private long execDuration;
    
    @Temporal( TemporalType.TIMESTAMP)
    private Date execDateObservationSession;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date predAstr;
	
    private long predDuration;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date eventAdvertReplyDeadline;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date eventAdvertReplyDate;
	
	private int eventAdvertReplyAccepted;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date eventOfferConfirmDate;
	
	private int eventOfferConfirmAccepted;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date eventOffshoreReqDate;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date eventFinishEventDate;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date offerDeadline;
	

    public ObservingPlan() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getExecDateEnd() {
		return this.execDateEnd;
	}

	public void setExecDateEnd(Date execDateEnd) {
		this.execDateEnd = execDateEnd;
	}

	public Date getExecDateIni() {
		return this.execDateIni;
	}

	public void setExecDateIni(Date execDateIni) {
		this.execDateIni = execDateIni;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getScheduleDateEnd() {
		return this.scheduleDateEnd;
	}

	public void setScheduleDateEnd(Date scheduleDateEnd) {
		this.scheduleDateEnd = scheduleDateEnd;
	}

	public Date getScheduleDateIni() {
		return this.scheduleDateIni;
	}

	public void setScheduleDateIni(Date scheduleDateIni) {
		this.scheduleDateIni = scheduleDateIni;
	}

	public ObservingPlanState getState() {
		return this.state;
	}

	public void setState(ObservingPlanState state) {
		this.state = state;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Date getPredAstr() {
		return predAstr;
	}

	public void setPredAstr(Date predAstr) {
		this.predAstr = predAstr;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getEventAdvertReplyDate() {
		return eventAdvertReplyDate;
	}

	public void setEventAdvertReplyDate(Date eventAdvertReplyDate) {
		this.eventAdvertReplyDate = eventAdvertReplyDate;
	}

	public int getEventAdvertReplyAccepted() {
		return eventAdvertReplyAccepted;
	}

	public void setEventAdvertReplyAccepted(int eventAdvertReplyAccepted) {
		this.eventAdvertReplyAccepted = eventAdvertReplyAccepted;
	}

	public Date getEventOfferConfirmDate() {
		return eventOfferConfirmDate;
	}

	public void setEventOfferConfirmDate(Date eventOfferConfirmDate) {
		this.eventOfferConfirmDate = eventOfferConfirmDate;
	}

	public int getEventOfferConfirmAccepted() {
		return eventOfferConfirmAccepted;
	}

	public void setEventOfferConfirmAccepted(int eventOfferConfirmAccepted) {
		this.eventOfferConfirmAccepted = eventOfferConfirmAccepted;
	}

	public Date getEventFinishEventDate() {
		return eventFinishEventDate;
	}

	public void setEventFinishEventDate(Date eventFinishEventDate) {
		this.eventFinishEventDate = eventFinishEventDate;
	}

	public Date getEventOffshoreReqDate() {
		return eventOffshoreReqDate;
	}

	public void setEventOffshoreReqDate(Date eventOffshoreReqDate) {
		this.eventOffshoreReqDate = eventOffshoreReqDate;
	}

	public Date getEventAdvertReplyDeadline() {
		return eventAdvertReplyDeadline;
	}

	public void setEventAdvertReplyDeadline(Date eventAdvertReplyDeadline) {
		this.eventAdvertReplyDeadline = eventAdvertReplyDeadline;
	}

	public long getExecDuration() {
		return execDuration;
	}

	public void setExecDuration(long execDuration) {
		this.execDuration = execDuration;
	}

	public long getPredDuration() {
		return predDuration;
	}

	public void setPredDuration(long predDuration) {
		this.predDuration = predDuration;
	}

	public Date getAdvertDeadline() {
		return advertDeadline;
	}

	public void setAdvertDeadline(Date advertDeadline) {
		this.advertDeadline = advertDeadline;
	}

	public Date getAdvertDateIni() {
		return advertDateIni;
	}

	public void setAdvertDateIni(Date advertDateIni) {
		this.advertDateIni = advertDateIni;
	}

	public Date getAdvertDateEnd() {
		return advertDateEnd;
	}

	public void setAdvertDateEnd(Date advertDateEnd) {
		this.advertDateEnd = advertDateEnd;
	}

	public Date getOfferedDate() {
		return offeredDate;
	}

	public void setOfferedDate(Date offeredDate) {
		this.offeredDate = offeredDate;
	}

	public ObservingPlanType getType() {
		return type;
	}

	public void setType(ObservingPlanType type) {
		this.type = type;
	}

	public Date getExecDateObservationSession() {
		return execDateObservationSession;
	}

	public void setExecDateObservationSession(Date execDateObsSession) {
		this.execDateObservationSession = execDateObsSession;
	}

	public Date getExecDeadline() {
		return execDeadline;
	}

	public void setExecDeadline(Date execDeadline) {
		this.execDeadline = execDeadline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOfferDeadline() {
		return offerDeadline;
	}

	public void setOfferDeadline(Date offerDeadline) {
		this.offerDeadline = offerDeadline;
	}

}