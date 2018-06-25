/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.PlayerMatchStatistic;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class PlayerMatchStatisticFunction {
    public static void addPlayerMatchStatistic(PlayerMatchStatistic pms) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<PlayerMatchStatistic> list = findPlayerMatchStatistic(pms);
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
    
    public static List<PlayerMatchStatistic> findPlayerMatchStatistic(PlayerMatchStatistic pms) {
        EntityManager em = JPAUtility.getEntityManager();
        List<PlayerMatchStatistic> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("PlayerMatchStatistic.findByMatchAndPlayer");
            query.setParameter("matchID", pms.getMatchID());
            query.setParameter("playerID", pms.getPlayerID());
            result = query.getResultList();
        } catch (Exception e) {
        }
        
        return result;
    }
}
