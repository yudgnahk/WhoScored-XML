/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.Goal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class GoalFunction {
    public static void addGoal(Goal goal) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Goal> list = findGoal(goal);
            if (list.isEmpty()) {
                em.persist(goal);
                em.flush();
            } else {
                goal.setId(list.get(0).getId());
                em.merge(goal);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public static List<Goal> findGoal(Goal goal) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Goal> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Goal.findGoal");
            query.setParameter("matchID", goal.getMatchID());
            query.setParameter("playerID", goal.getPlayerID());
            query.setParameter("minute", goal.getMinute());
            query.setParameter("second", goal.getSecond());
            result = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
