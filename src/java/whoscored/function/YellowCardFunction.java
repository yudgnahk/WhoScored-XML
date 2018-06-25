/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.YellowCard;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class YellowCardFunction {
    public static void addYellowCard(YellowCard yellowCard) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<YellowCard> list = findYellowCard(yellowCard);
            if (list.isEmpty()) {
                em.persist(yellowCard);
                em.flush();
            } else {
                yellowCard.setId(list.get(0).getId());
                em.merge(yellowCard);
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
    
    public static List<YellowCard> findYellowCard(YellowCard yellowCard) {
        EntityManager em = JPAUtility.getEntityManager();
        List<YellowCard> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("YellowCard.findYellowCard");
            query.setParameter("matchID", yellowCard.getMatchID());
            query.setParameter("playerID", yellowCard.getPlayerID());
            query.setParameter("minute", yellowCard.getMinute());
            result = query.getResultList();
        } catch (Exception e) {
        }
        
        return result;
    }
}
