package eu.gloria.rt.db.scheduler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import eu.gloria.rt.db.task.Task;
import eu.gloria.rt.db.task.TaskManager;
import eu.gloria.rt.db.task.TaskProperty;
import eu.gloria.rt.db.task.TaskPropertyType;
import eu.gloria.rt.db.util.DBUtil;
import eu.gloria.tools.log.LogUtil;
import eu.gloria.tools.time.DateTools;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		/*eu.gloria.rt.db.scheduler.ObservingPlan op = new eu.gloria.rt.db.scheduler.ObservingPlan();
		op.setScheduleDateIni(new Date());
		op.setScheduleDateEnd(new Date());
		op.setPriority("1");
		op.setFile("example01.xml");
		op.setState(ObservingPlanState.QUEUED);
		op.setUser("UNKNOWN");
		op.setUuid("example01");
		
		ObservingPlanManager manager1 = new ObservingPlanManager();
		manager1.create(null, op);*/


		/*EntityManager em = DBUtil.getEntityManager();
		
		Date ini = DateTools.getDate("20130312193234", "yyyyMMddHHmmss");
		Date end = DateTools.getDate("20130313074219", "yyyyMMddHHmmss");
		
		manager.getCount(em, ini, end);
		
		em.close();*/

		

		
		
		//createObservingPlan(em);
		
		for (int x = 0; x < 100; x++){
			Thread.sleep(5000);
			EntityManager em = DBUtil.getEntityManager();
			testOpList(em);
			DBUtil.close(em);
		}
		
		
		
		//testOp(em);
		
		
		
		
		
		//createTask(em);
		
		/*adv = manager.get(em, "3");
		
		GregorianCalendar calendar1 = new GregorianCalendar();
		calendar1.setTime(adv.getProcessed());
		System.out.println("processed-offset:" + calendar1.get(Calendar.ZONE_OFFSET));
		System.out.println("processed-dst:" + calendar1.get(Calendar.DST_OFFSET));
		calendar1.setTime(adv.getDeadline());
		System.out.println("deadline-offset:" + calendar1.get(Calendar.ZONE_OFFSET));
		System.out.println("deadline-dst:" + calendar1.get(Calendar.DST_OFFSET));*/
		
	
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		

	
		
		//createAdvertisement(em);
		//createAdvertisement(em);
		//em.close();
		
		/*createTask(em);
		
		TaskManager man = new TaskManager();
		List<Task> taskList = man.getTasks(em);
		

		try {

			DBUtil.beginTransaction(em);

			do {

				adv = manager.getNextToProcess(em);

				if (adv != null) {
					adv.setState(AdvertisementState.OFFERED);
				}

			} while (adv != null);

			DBUtil.commit(em);

		} catch (Exception ex) {
			DBUtil.rollback(em);
		} finally {
			DBUtil.close(em);
		}*/

	}
	
	public static void testOpList(EntityManager em) {
		
		List<ObservingPlan> list = null;
		
		try{
			
			ObservingPlanManager manager = new ObservingPlanManager();
			
			DBUtil.beginTransaction(em);
			
			list = manager.getAll(em);
			
			
			DBUtil.commit(em);
			
		} catch (Exception ex) {
			
			DBUtil.rollback(em);
			
		} finally {
			//em.clear();
			//DBUtil.close(em);
		}
		
		
	}
	
	public static void testOp(EntityManager em) {
		
		ObservingPlan dbOp = null;
		
		try{
			
			ObservingPlanManager manager = new ObservingPlanManager();
			
			DBUtil.beginTransaction(em);
			
			dbOp = manager.getNextToProcess(em);
			
			
			DBUtil.commit(em);
			
		} catch (Exception ex) {
			
			DBUtil.rollback(em);
			
		} finally {
			DBUtil.close(em);
		}
		
		
	}
	
	public static void createObservingPlan(EntityManager em) {
		
		em.getTransaction().begin();
	    
		ObservingPlan op = new ObservingPlan();
		
		op.setFile("kk");
		op.setPriority(100);
		op.setState(ObservingPlanState.ABORTED);
		op.setUser("yo");
		op.setUuid("dfsdfdddsf");
		op.setPredDuration(21354);
		op.setExecDuration(21354);
		//op.setAdvertDeadline(new Date());
		op.setType(ObservingPlanType.BIAS);
	    
	    em.persist(op);
	    em.flush();
	    
	    ObservingPlan st = em.find(ObservingPlan.class, op.getId());    
	    System.out.println(st.getId());
	    
	    em.getTransaction().rollback();

	    em.close();
	    
		
		/*try{
			
			

			if (em != null){
				em.getTransaction().begin();
			}
		
			ObservingPlan op = new ObservingPlan();
			
			
			op.setFile("kk");
			op.setPriority(100);
			op.setState(ObservingPlanState.ABORTED);
			op.setUser("yo");
			op.setUuid("dfsdfdddsf");
			op.setPredDuration(21354);
			op.setExecDuration(21354);
			//op.setAdvertDeadline(new Date());
			op.setType(ObservingPlanType.BIAS);
		
			em.persist(op);
			em.flush();
			//ObservingPlanManager manager = new ObservingPlanManager();
			//manager.create(em , op);
			
			
			if ("1".equals("1")) throw new Exception("toma ya!!!");
		
			if (em != null){
				em.getTransaction().commit();
			}


			System.out.println("");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			
			if (em != null){
				EntityTransaction tx = em.getTransaction();
				if(tx != null && tx.isActive()) {
					tx.rollback();
				}
			}
		}finally{
			
			try{
				if (em != null){
					em.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
				LogUtil.severe(null, "Error closing EntityManager: " + ex.getMessage());
			}
		}*/

	}
	
	
	

	public static void createTask(EntityManager em) {

		EntityTransaction entr = em.getTransaction();
		entr.begin();
		
		Task t = new Task();
		t.setEnable(1);
		t.setName("ACP_Publisher");
		t.setProvider("eu.gloria.rt.worker.offshore.WorkerOffshorePublisher");
		t.setSleepTime(1000);
		
		em.persist(t);
		
		/*ArrayList<TaskProperty> props = new ArrayList<TaskProperty>();
		
		TaskProperty prop = new TaskProperty();
		prop.setName("sleepTime");
		prop.setValue("5000");
		prop.setType(TaskPropertyType.INT);
		
		prop.setTask(t);
		
		props.add(prop);
		
		em.persist(prop);*/
		
		
		entr.commit();


		System.out.println("");

	}

}
