/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.League;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class LeagueFunction {
    public static void addLeague(League league) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            Query query = em.createNamedQuery("League.findByName");
            query.setParameter("name", league.getName());
            List<League> list = query.getResultList();
            em.getTransaction().begin();
            
            if (list.isEmpty()) {
                em.persist(league);
                em.flush();
            } else {
                league.setId(list.get(0).getId());
                em.merge(league);
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
    
    public static List getLeagues() {
        EntityManager em = JPAUtility.getEntityManager();
        List<League> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("League.findAll");
            result = query.getResultList();
        } catch (Exception e) {
        }
        return result;
    }
    
    public static void removeLeague(int ID) {

        EntityManager em = JPAUtility.getEntityManager();

        try {
            Query query = em.createNamedQuery("League.findById");
            query.setParameter("id", ID);
            
            League league = (League) query.getSingleResult();
            
            em.getTransaction().begin();
            em.remove(league);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
