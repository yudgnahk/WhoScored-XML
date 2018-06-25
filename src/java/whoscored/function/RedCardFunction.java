/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.RedCard;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class RedCardFunction {
    public static void addRedCard(RedCard redCard) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<RedCard> list = findRedCard(redCard);
            if (list.isEmpty()) {
                em.persist(redCard);
                em.flush();
            } else {
                redCard.setId(list.get(0).getId());
                em.merge(redCard);
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
    
    public static List<RedCard> findRedCard(RedCard redCard) {
        EntityManager em = JPAUtility.getEntityManager();
        List<RedCard> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("RedCard.findRedCard");
            query.setParameter("matchID", redCard.getMatchID());
            query.setParameter("playerID", redCard.getPlayerID());
            query.setParameter("minute", redCard.getMinute());
            result = query.getResultList();
        } catch (Exception e) {
        }
        
        return result;
    }
}
