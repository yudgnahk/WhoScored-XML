/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.Club;
import whoscored.data.Player;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class PlayerFunction {
    public static void addPlayer(Player player) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            Query query = em.createNamedQuery("Player.findByNameAndClub");
            query.setParameter("name", player.getName());
            query.setParameter("currentTeamID", player.getCurrentTeamID());
            
            List<Player> list = query.getResultList();
            em.getTransaction().begin();
            
            if (list.isEmpty()) {
                em.persist(player);
                em.flush();
            } else {
                player.setId(list.get(0).getId());
                em.merge(player);
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
    
    public static List<Player> findPlayersInClub(Club club) {
        List<Player> result = new ArrayList<>();
        EntityManager em = JPAUtility.getEntityManager();
        
        try {
            Query query = em.createNamedQuery("Player.findByClub");
            query.setParameter("currentTeamID", club);
            
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
    
    public static List<Player> findPlayersByName(String name) {
        List<Player> result = new ArrayList<>();
        EntityManager em = JPAUtility.getEntityManager();
        
        try {
            Query query = em.createNamedQuery("Player.findByName");
            query.setParameter("name", name);
            
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
    
    public static Player findByNameAndClub(String name, Club club) {
        Player result = new Player();
        EntityManager em = JPAUtility.getEntityManager();
        
        try {
            Query query = em.createNamedQuery("Player.findByNameAndClub");
            query.setParameter("name", name);
            query.setParameter("currentTeamID", club);
            
            result = (Player) query.getSingleResult();

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return result;
    }
    
}
