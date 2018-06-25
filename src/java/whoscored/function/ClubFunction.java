/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.Club;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class ClubFunction {

    public static void addClub(Club club) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Club> list = getClubByName(club.getName());
            if (list.isEmpty()) {
                em.persist(club);
                em.flush();
            } else {
                club.setId(list.get(0).getId());
                em.merge(club);
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

    public static List<Club> getClubByName(String cName) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Club> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Club.findByName");
            query.setParameter("name", "%" + cName + "%");
            result = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public static List getClubs() {
        EntityManager em = JPAUtility.getEntityManager();
        List<Club> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Club.findAll");
            result = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

}
