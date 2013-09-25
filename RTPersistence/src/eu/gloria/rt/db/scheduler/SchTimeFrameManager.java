package eu.gloria.rt.db.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class SchTimeFrameManager {
	
	public List<SchTimeFrame> getFreeTimeFrames(EntityManager em, Date init, long duration){
		
		List<SchTimeFrame> result = new ArrayList<SchTimeFrame>();
			
		try{
				
			//uuidOp == null  AND rec.dateIni < (rec.dateEnd - duration) 
			Query query = em.createQuery("SELECT tf FROM SchTimeFrame tf where (tf.uuidOp is null) AND (tf.dateIni <= TIMESTAMPADD(SECOND,-" + duration + "/1000,tf.dateEnd)); ");
				
			List items = query.getResultList();
			if (items != null){
				for (int x = 0; x < items.size(); x++){
					result.add((SchTimeFrame)items.get(x));
				}
			}
				
		}catch(NoResultException ex){
			//All right...
		}
				
		return result;
				
	}

}
