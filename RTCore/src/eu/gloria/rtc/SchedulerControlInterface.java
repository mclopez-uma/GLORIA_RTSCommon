package eu.gloria.rtc;

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
public interface SchedulerControlInterface {
	
	/**
	 * Add an operation to the scheduler. In the operation field the following information is included:
	 * - operation owner information (identifier & karma)
	 * - list of sequential actions to be executed
	 * - execution date
	 * - date to stop the execution if it has not finish yet.
	 * - timeOut of the execution	  
	 * 
	 * @param operation List of sequential actions to be executed, including user information and date of execution
	 * @throws RTException In error case.
	 */	
	public void schAddOperation (OperationCommand operation) throws RTException;
	
	/**
	 * Get all the operations in the scheduler of a specified user
	 * 
	 * @param user User Identifier
	 * @return List<OperationCommand> 
	 * @throws RTException In error case.
	 */
	
	public List<OperationCommand> schGetOperations (String user) throws RTException;
	
	/**
	 * Get the operation information of the specified identifier. 
	 * 
	 * @param idOperation Identifier of the operation
	 * @return OperationCommand
	 * @throws RTException In error case.
	 */
	public OperationCommand schGetOperation (long idOperation) throws RTException;
	
	
	/**
	 * Get all the operations in the scheduler of a specified user within the interval specified by iniDate and finDate
	 * 
	 * @param iniDate Initial date
	 * @param finDate Final date
	 * @param user User Identifier
	 * @return List<OperationCommand>
	 * @throws RTException In error case.
	 */
	public List<OperationCommand> schGetOperations (Date iniDate, Date finDate, String user) throws RTException;
	
	
	/**
	 * Remove from the scheduler the specified operation 
	 * 
	 * @param idOperation Identifier of the operation
	 * @throws RTException In error case.
	 */
	public void schRemoveOperation (long idOperation) throws RTException;
	
	
	/**
	 * Remove from the scheduler all the operation of a specified user
	 * 
	 * @param user User Identifier
	 * @throws RTException In error case.
	 */
	public void schRemoveOperations (String user) throws RTException;
	
	/**
	 * Remove from the scheduler all the operation of a specified user within the interval specified by iniDate and finDate
	 * 
	 * @param iniDate Initial date
	 * @param finDate Final date
	 * @param user User Identifier
	 * @throws RTException In error case.
	 */
	public void schRemoveOperations (Date iniDate, Date finDate, String user) throws RTException;
	
	

}
