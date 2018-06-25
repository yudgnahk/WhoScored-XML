/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.function;

import whoscored.data.Club;
import whoscored.data.Match;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import whoscored.utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class MatchFunction {

    public static void addMatch(Match match) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Match> list = findMatch(match.getHomeTeamID(), match.getAwayTeamID(), match.getSeason());
            if (list.isEmpty()) {
                em.persist(match);
                em.flush();
            } else {
                match.setId(list.get(0).getId());
                em.merge(match);
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

    public static List<Match> findMatch(Club homeTeam, Club awayTeam, String season) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Match> result = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Match.findMatch");
            query.setParameter("season", season);
            query.setParameter("homeTeamID", homeTeam);
            query.setParameter("awayTeamID", awayTeam);

            result = query.getResultList();

        } catch (Exception e) {
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return result;
    }
}
