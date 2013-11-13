package eu.gloria.rt.db.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eu.gloria.rt.db.util.DBPaginationSearch;
import eu.gloria.rt.db.util.DBUtil;
import eu.gloria.tools.log.LogUtil;

public class ObservingPlanManager {
	
	public ObservingPlan getNextToProcess(EntityManager em){
		
	ObservingPlan result = null;
		
		try{
		
			Query query = em.createQuery("SELECT op FROM ObservingPlan op where op.state=?1 order by op.id asc");
			query.setParameter(1, ObservingPlanState.QUEUED );
			
			result = (ObservingPlan) query.setMaxResults(1).getSingleResult();
			
		}catch(NoResultException ex){
			//All right...
		}
			
		return result;
			
	}
	
	public ObservingPlan getNextToProcessFromOffshore(EntityManager em){
		
		ObservingPlan result = null;
			
			try{
				
				Date now = new Date();
			
				Query query = em.createQuery("SELECT op FROM ObservingPlan op where op.state=?1 and op.scheduleDateIni<?2 and op.scheduleDateEnd<?3 and op.execDeadline<?4 order by op.id asc");
				query.setParameter(1, ObservingPlanState.OFFSHORE );
				query.setParameter(2, now );
				query.setParameter(3, now );
				query.setParameter(4, now );
				
				result = (ObservingPlan) query.setMaxResults(1).getSingleResult();
				
			}catch(NoResultException ex){
				//All right...
			}
				
			return result;
				
	}
	
	public ObservingPlan getNextAvertToProcess(EntityManager em){
		
		ObservingPlan result = null;
		
		try{
		
			Query query = em.createQuery("SELECT op FROM ObservingPlan op where op.state=?1 order by op.id asc");
			query.setParameter(1, ObservingPlanState.ADVERT_QUEUED);
			
			result = (ObservingPlan) query.setMaxResults(1).getSingleResult();
		}catch(NoResultException ex){
			//All right...
		}
			
		return result;
			
	}
	
	public List<ObservingPlan> getAll(EntityManager em){
		
		List<ObservingPlan> result = new ArrayList<ObservingPlan>();
			
		try{
				
			
			Query query = em.createQuery("SELECT op FROM ObservingPlan op order by op.id desc");
				
			List items = query.getResultList();
			if (items != null){
				for (int x = 0; x < items.size(); x++){
					result.add((ObservingPlan)items.get(x));
				}
			}
				
		}catch(NoResultException ex){
			//All right...
		}
				
		return result;
				
	}
	
	
	public ObservingPlan get(EntityManager em, long id){
		
		ObservingPlan result = (ObservingPlan)em.find(ObservingPlan.class , id);
			
		return result;
		
	}
	
	public ObservingPlan getByUuid(EntityManager em, String uuid){
		
		ObservingPlan result = null;
			
			try{
				
				Query query = em.createQuery("SELECT op FROM ObservingPlan op where op.uuid=?1");
				query.setParameter(1, uuid );
				
				result = (ObservingPlan) query.setMaxResults(1).getSingleResult();
				
			}catch(NoResultException ex){
				//All right...
			}
				
			return result;
				
	}
	
	public ObservingPlan get(EntityManager em, Date observationSession, ObservingPlanType opType){
		
		ObservingPlan result = null;
			
			try{
				
				Query query = em.createQuery("SELECT op FROM ObservingPlan op where op.execDateObservationSession=?1 AND op.type=?2");
				query.setParameter(1, observationSession);
				query.setParameter(2, opType);
				
				result = (ObservingPlan) query.setMaxResults(1).getSingleResult();
				
			}catch(NoResultException ex){
				//All right...
			}
				
			return result;
				
	}
	
	public void create(EntityManager em, ObservingPlan elem) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			if (localEm) DBUtil.beginTransaction(em);

			em.persist(elem);

			if (localEm) DBUtil.commit(em);

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error creating an observing plan: " + ex.getMessage());
			if (localEm) DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
	}
	
	public void delete(EntityManager em, long id) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}
			
			ObservingPlan elem = (ObservingPlan)em.find(ObservingPlan.class , id);

			if (localEm) DBUtil.beginTransaction(em);

			if (elem != null) em.remove(elem);

			if (localEm) DBUtil.commit(em);

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error deleting an observing plan: " + ex.getMessage());
			if (localEm) DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
	}
	
	public long getCountByFilter(
			EntityManager em,
			DBPaginationSearch pagInfo,
			String user,
			List<ObservingPlanType> planTypes,
			List<ObservingPlanState> planStates,
			Date execPredictedDateIntevalIni,
			Date execPredictedDateIntevalEnd,
			Date execBeginDateIntevalIni,
			Date execBeginDateIntevalEnd,
			Date execEndDateIntevalIni,
			Date execEndDateIntevalEnd,
			Date observationSession
			) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			DBUtil.beginTransaction(em);
			
			StringBuilder sb = new StringBuilder();
			int paramCount = 1;
			List<Object> params = new ArrayList<Object>();
			
			sb.append(" SELECT COUNT(op.id) FROM ObservingPlan op where 0=0 ");
			
			if (user != null && !user.trim().isEmpty()){
				sb.append(" AND user='").append(user).append("'");
			}
			
			if (planTypes != null && !planTypes.isEmpty()){
				sb.append(" AND type in (");
				for (int x = 0; x < planTypes.size(); x++){
					if (x > 0) sb.append(",");
					sb.append(planTypes.get(x).ordinal());
				}
				sb.append(") ");
			}
			
			if (planStates != null && !planStates.isEmpty()){
				sb.append(" AND state in (");
				for (int x = 0; x < planStates.size(); x++){
					if (x > 0) sb.append(",");
					sb.append(planStates.get(x).ordinal());
				}
				sb.append(") ");
			}
			
			if (execPredictedDateIntevalIni != null) {
				sb.append(" AND predAstr>=?" + paramCount + " ");
				paramCount++;
				params.add(execPredictedDateIntevalIni);
			}
			
			if (execPredictedDateIntevalEnd != null) {
				sb.append(" AND predAstr<=?" + paramCount + " ");
				paramCount++;
				params.add(execPredictedDateIntevalEnd);
			}
			
			if (execBeginDateIntevalIni != null) {
				sb.append(" AND execDateIni>=?" + paramCount + " ");
				paramCount++;
				params.add(execBeginDateIntevalIni);
			}
			
			if (execBeginDateIntevalEnd != null) {
				sb.append(" AND execDateIni<=?" + paramCount + " ");
				paramCount++;
				params.add(execBeginDateIntevalEnd);
			}
			
			if (execEndDateIntevalIni != null) {
				sb.append(" AND execDateEnd>=?" + paramCount + " ");
				paramCount++;
				params.add(execEndDateIntevalIni);
			}
			
			if (execEndDateIntevalEnd != null) {
				sb.append(" AND execDateEnd<=?" + paramCount + " ");
				paramCount++;
				params.add(execEndDateIntevalEnd);
			}
			
			if (observationSession!= null){
				sb.append(" AND execDateObservationSession=?" + paramCount + " ");
				paramCount++;
				params.add(observationSession);
			}
			
			Query query=em.createQuery(sb.toString());
			
			for (int tmpCount = 0; tmpCount < params.size(); tmpCount++) {
				query.setParameter(tmpCount+1, params.get(tmpCount));
			}
			
			Number countResult = (Number) query.getSingleResult();
			
			DBUtil.commit(em);
			
			return countResult.longValue();

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error recovering the CountByFilter : " + ex.getMessage());
			DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
		
		
	}
	
	public List<ObservingPlan> getByFilter(
			EntityManager em, 
			DBPaginationSearch pagInfo, 
			String user,
			List<ObservingPlanType> planTypes,
			List<ObservingPlanState> planStates,
			Date execPredictedDateIntevalIni,
			Date execPredictedDateIntevalEnd,
			Date execBeginDateIntevalIni,
			Date execBeginDateIntevalEnd,
			Date execEndDateIntevalIni,
			Date execEndDateIntevalEnd,
			Date observationSession
			) throws Exception{
		
		boolean localEm = false;
		List<ObservingPlan> result = new ArrayList<ObservingPlan>();
			
		try{
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			DBUtil.beginTransaction(em);
			
			StringBuilder sb = new StringBuilder();
			int paramCount = 1;
			List<Object> params = new ArrayList<Object>();
			
			sb.append(" SELECT op FROM ObservingPlan op where 0=0 ");
			
			if (user != null && !user.trim().isEmpty()){
				sb.append(" AND user='").append(user).append("'");
			}
			
			if (planTypes != null && !planTypes.isEmpty()){
				sb.append(" AND type in (");
				for (int x = 0; x < planTypes.size(); x++){
					if (x > 0) sb.append(",");
					sb.append(planTypes.get(x).ordinal());
				}
				sb.append(") ");
			}
			
			if (planStates != null && !planStates.isEmpty()){
				sb.append(" AND state in (");
				for (int x = 0; x < planStates.size(); x++){
					if (x > 0) sb.append(",");
					sb.append(planStates.get(x).ordinal());
				}
				sb.append(") ");
			}
			
			if (execPredictedDateIntevalIni != null) {
				sb.append(" AND predAstr>=?" + paramCount + " ");
				paramCount++;
				params.add(execPredictedDateIntevalIni);
			}
			
			if (execPredictedDateIntevalEnd != null) {
				sb.append(" AND predAstr<=?" + paramCount + " ");
				paramCount++;
				params.add(execPredictedDateIntevalEnd);
			}
			
			if (execBeginDateIntevalIni != null) {
				sb.append(" AND execDateIni>=?" + paramCount + " ");
				paramCount++;
				params.add(execBeginDateIntevalIni);
			}
			
			if (execBeginDateIntevalEnd != null) {
				sb.append(" AND execDateIni<=?" + paramCount + " ");
				paramCount++;
				params.add(execBeginDateIntevalEnd);
			}
			
			if (execEndDateIntevalIni != null) {
				sb.append(" AND execDateEnd>=?" + paramCount + " ");
				paramCount++;
				params.add(execEndDateIntevalIni);
			}
			
			if (execEndDateIntevalEnd != null) {
				sb.append(" AND execDateEnd<=?" + paramCount + " ");
				paramCount++;
				params.add(execEndDateIntevalEnd);
			}
			
			if (observationSession!= null){
				sb.append(" AND execDateObservationSession=?" + paramCount + " ");
				paramCount++;
				params.add(observationSession);
			}
				
			
			sb.append(" ORDER BY op.id DESC ");
			
			/*if (pagInfo != null){
				
				sb.append(" LIMIT ");
				sb.append(pagInfo.getFirstPageRow());
				sb.append(",");
				sb.append(pagInfo.getPageSize());
			}*/
			
			String queryString = sb.toString();
			LogUtil.info(this, "ObservingPlansByFilter query= " + queryString);
			Query query=em.createQuery(queryString);
			
			if (pagInfo != null){
				query.setFirstResult(pagInfo.getFirstPageRowInt());
				query.setMaxResults(pagInfo.getPageSize());
			}else{
				query.setMaxResults(20); //No more...
			}
			
			for (int tmpCount = 0; tmpCount < params.size(); tmpCount++) {
				query.setParameter(tmpCount+1, params.get(tmpCount));
			}
				
			List items = query.getResultList();
			if (items != null){
				for (int x = 0; x < items.size(); x++){
					result.add((ObservingPlan)items.get(x));
				}
			}
			
			DBUtil.commit(em);
				
		}catch(NoResultException ex){
			//All right...
		}catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error recovering the ObservingPlansByFilter : " + ex.getMessage());
			DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
				
		return result;
				
	}
	
	public long getCountByScheduleDate(EntityManager em, Date ini, Date end) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			DBUtil.beginTransaction(em);

			Query query=em.createQuery("SELECT COUNT(op.id) FROM ObservingPlan op where scheduleDateIni>=?1 AND scheduleDateEnd<=?2");
			query.setParameter(1, ini);
			query.setParameter(2, end);
			Number countResult = (Number) query.getSingleResult();
			
			DBUtil.commit(em);
			
			return countResult.longValue();

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error recovering the CountByScheduleDate : " + ex.getMessage());
			DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
		
	}
	
	public long getCountByScheduleDate(EntityManager em, Date ini, Date end, String user) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			DBUtil.beginTransaction(em);

			Query query=em.createQuery("SELECT COUNT(op.id) FROM ObservingPlan op where scheduleDateIni>=?1 AND scheduleDateEnd<=?2 AND user=?3");
			query.setParameter(1, ini);
			query.setParameter(2, end);
			query.setParameter(3, user);
			Number countResult = (Number) query.getSingleResult();
			
			DBUtil.commit(em);
			
			return countResult.longValue();

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error recovering the CountByScheduleDate : " + ex.getMessage());
			DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
		
	}
	
	public long getObservationTimeByScheduleDate(EntityManager em, Date ini, Date end) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			DBUtil.beginTransaction(em);

			Query query=em.createQuery("SELECT sum(op.predDuration) FROM ObservingPlan op where scheduleDateIni>=?1 AND scheduleDateEnd<=?2");
			query.setParameter(1, ini);
			query.setParameter(2, end);
			System.out.println("data=" + query.getSingleResult());
			Number countResult = (Number) query.getSingleResult();
			
			DBUtil.commit(em);
			
			if (countResult == null) return 0;
			return countResult.longValue();

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error recovering the TimeByScheduleDate : " + ex.getMessage());
			DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
		
	}
	
	public long getObservationTimeByScheduleDate(EntityManager em, Date ini, Date end, String user) throws Exception{
		
		boolean localEm = false;
		
		try {
			
			if (em == null){
				localEm = true;
				em = DBUtil.getEntityManager();
			}

			DBUtil.beginTransaction(em);

			Query query=em.createQuery("SELECT sum(op.predDuration) FROM ObservingPlan op where scheduleDateIni>=?1 AND scheduleDateEnd<=?2 AND user=?3");
			query.setParameter(1, ini);
			query.setParameter(2, end);
			query.setParameter(3, user);
			System.out.println("data=" + query.getSingleResult());
			Number countResult = (Number) query.getSingleResult();
			
			DBUtil.commit(em);
			
			if (countResult == null) return 0;
			return countResult.longValue();

		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.severe(this, "Error recovering the TimeByScheduleDate : " + ex.getMessage());
			DBUtil.rollback(em);
			throw ex;
		} finally {
			if (localEm) DBUtil.close(em);
		}
		
	}
	
	public ObservingPlan getToOffshoreConfirmation(EntityManager em){
		
		ObservingPlan result = null;
			
			try{
				
				Date now = new Date();
				
				Query query = em.createQuery("SELECT op FROM ObservingPlan op where op.eventOffshoreConfirmDate is null AND op.advertOffshoreDeadline <= op.advertDeadline AND op.state=?1 AND  op.advertDeadline>?2 AND op.advertOffshoreDeadline<=?3 ");
				query.setParameter(1, ObservingPlanState.OFFSHORE);
				query.setParameter(2, now);
				query.setParameter(3, now);
				
				LogUtil.severe(this, "now= " + now);
				LogUtil.severe(this, "state= " + ObservingPlanState.OFFSHORE.ordinal());
				
				result = (ObservingPlan) query.setMaxResults(1).getSingleResult();
				
			}catch(NoResultException ex){
				//All right...
			}
				
			return result;
				
	}

}
