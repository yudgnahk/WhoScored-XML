/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.TeamMatchStatistic;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class TeamMatchStatisticFunction {
    public static void addTeamMatchStatistic(TeamMatchStatistic pms) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<TeamMatchStatistic> list = findTeamMatchStatistic(pms);
            if (list.isEmpty()) {
                em.persist(pms);
                em.flush();
            } else {
                pms.setId(list.get(0).getId());
                em.merge(pms);
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
    
    public static List<TeamMatchStatistic> findTeamMatchStatistic(TeamMatchStatistic pms) {
        EntityManager em = JPAUtility.getEntityManager();
        List<TeamMatchStatistic> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("TeamMatchStatistic.findByMatchAndClub");
            query.setParameter("matchID", pms.getMatchID());
            query.setParameter("clubID", pms.getClubID());
            result = query.getResultList();
        } catch (Exception e) {
        }
        
        return result;
    }
}
