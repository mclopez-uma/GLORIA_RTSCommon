package eu.gloria.rtd;

import java.util.List;
import java.util.Date;

import eu.gloria.rt.entity.scheduler.*;
import eu.gloria.rt.exception.RTException;


/**
 *  This is the interface that controls the RTC scheduler
 *  
 *  
 * @author mclopez
 *
 */
public interface RTDSchedulerInterface {
	
	/**
	 * Creates a plan template in the RT.
	 * @param id Template identifier.
	 * @param xml XML Plan template.
	 * @param description Template description.
	 * @throws RTException In error case.
	 */
	public void schTmplPlanCreate(String id, String xml, String description) throws RTException;
	
	
	/**
	 * Updates a plan template in the RT.
	 * @param id Template identifier.
	 * @param xml XML Plan template.
	 * @param description  Template description.
	 * @throws RTException In error case.
	 */
	public void schTmplPlanUpdate(String id, String xml, String description) throws RTException;
	
	
	/**
	 * Deletes a plan template from RT.
	 * @param id Template identifier.
	 * @throws RTException In error case.
	 */
	public void schTmplPlanDelete(String id) throws RTException;
	
	/**
	 * Searches templates.
	 * @param id Template identifier.
	 * @param user User.
	 * @param creationFrom Starting of the creation date interval.
	 * @param creationTo  Ending of the creation date interval.
	 * @return List of matched TemplatePlan
	 * @throws RTException In error case.
	 */
	public List<TemplatePlan> schTmplPlanGet(String id, String user, Date creationFrom, Date creationTo) throws RTException;

	/**
	 * Creates a plan.
	 * @param idTmpl Template identifier.
	 * @param xmlMapping Inputs for the plan execution.
	 * @param execWindowStart  Starting of the execution window.
	 * @param execWindowEnd Ending of the execution window.
	 * @param execMaxTime Max. execution time.
	 * @return Plan identifier.
	 * @throws RTException In error case.
	 */
	public String schPlanCreate(String idTmpl, String xmlMapping, Date execWindowStart, Date execWindowEnd, long execMaxTime) throws RTException;
	
	/**
	 * Updates the plan information.
	 * @param id Plan identifier.
	 * @param idTmpl Template identifier.
	 * @param xmlMapping XML content indicating the values of the plan inputs.
	 * @param execWindowStart Stating of the execution window.
	 * @param execWindowEnd Ending of the execution window.
	 * @param execMaxTime Max. execution time.
	 * @throws RTException In error case.
	 */
	public void schPlanUpdate(String id, String idTmpl, String xmlMapping, Date execWindowStart, Date execWindowEnd, long execMaxTime) throws RTException;
	
	/**
	 * Removes a plan from RT.
	 * @param id Plan identifier.
	 * @throws RTException In error case
	 */
	public void schPlanDelete(String id) throws RTException;
	
	/**
	 * Searches plans.
	 * @param id Plan identifier.
	 * @param user User.
	 * @param state Plan state.
	 * @param creationFrom Starting of the creation date interval.
	 * @param creationTo Ending of the creation date interval.
	 * @param execWindowStartFrom Starting of the window execution beginning.
	 * @param execWindowStartTo Ending of the window execution beginning.
	 * @param execWindowEndFrom  Starting of the window execution ending.
	 * @param execWindowEndTo Ending of the window execution ending.
	 * @param execMaxFrom Starting of the maximum execution time interval.
	 * @param execMaxTo Ending of the maximum execution time interval.
	 * @return List of plans.
	 * @throws RTException In error case.
	 */
	public List<Plan> schPlanGet(String id, String user, String state, Date creationFrom, Date creationTo, Date execWindowStartFrom, Date execWindowStartTo, Date execWindowEndFrom, Date execWindowEndTo, long execMaxFrom, long execMaxTo) throws RTException;
	
	
	

}
