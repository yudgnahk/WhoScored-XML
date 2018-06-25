/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.listener;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import whoscored.data.Club;
import whoscored.data.League;
import whoscored.function.ClubFunction;
import whoscored.function.LeagueFunction;
import whoscored.jaxb.TeamRanking;
import whoscored.jaxb.TeamRankings;
import whoscored.jaxb.TopPlayer;
import whoscored.jaxb.TopPlayers;
import whoscored.utils.MysqlUtils;
import whoscored.utils.XMLUtils;

/**
 * Web application lifecycle listener.
 *
 * @author haleduykhang
 */
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Destroy Request");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession();

        System.out.println("Start getting infos...");

        List<Club> listClub = (List<Club>) session.getAttribute("listClub");
        if (listClub == null || listClub.isEmpty()) {
            listClub = ClubFunction.getClubs();
            System.out.println(listClub.size());
            session.setAttribute("listClub", listClub);
        }

        List<League> listLeague = (List<League>) session.getAttribute("listLeague");
        if (listLeague == null || listLeague.isEmpty()) {
            listLeague = LeagueFunction.getLeagues();
            session.setAttribute("listLeague", listLeague);
        }

        List<TeamRanking> listEngland = (List<TeamRanking>) session.getAttribute("listEngland");
        List<TeamRanking> listSpain = (List<TeamRanking>) session.getAttribute("listSpain");
        List<TeamRanking> listItalia = (List<TeamRanking>) session.getAttribute("listItalia");
        List<TeamRanking> listGermany = (List<TeamRanking>) session.getAttribute("listGermany");
        List<TeamRanking> listFrance = (List<TeamRanking>) session.getAttribute("listFrance");

        Connection con = null;

        if (listEngland == null || listEngland.isEmpty()) {

            listEngland = new ArrayList<>();
            listItalia = new ArrayList<>();
            listSpain = new ArrayList<>();
            listGermany = new ArrayList<>();
            listFrance = new ArrayList<>();

            try {
                MysqlUtils utils = new MysqlUtils();
                con = utils.connect();

                CallableStatement cStmt = con.prepareCall("{CALL getRankingTable}");

                cStmt.execute();

                ResultSet rs = cStmt.getResultSet();

                while (rs.next()) {
                    TeamRanking t = new TeamRanking();
                    t.setName(rs.getString(1));
                    t.setLeagueID(rs.getInt(2));
                    t.setWin(rs.getInt(3));
                    t.setDue(rs.getInt(4));
                    t.setLose(rs.getInt(5));
                    t.setScore();
                    switch (t.getLeagueID()) {
                        case 36:
                            listEngland.add(t);
                            break;
                        case 37:
                            listItalia.add(t);
                            break;
                        case 38:
                            listSpain.add(t);
                            break;
                        case 39:
                            listGermany.add(t);
                            break;
                        case 40:
                            listFrance.add(t);
                            break;
                    }
                }
                rs.close();

                Collections.sort(listEngland);
                Collections.sort(listFrance);
                Collections.sort(listGermany);
                Collections.sort(listItalia);
                Collections.sort(listSpain);

                TeamRankings tr = new TeamRankings();
                tr.getTeamRanking().addAll(listEngland);

                String xmlEng = XMLUtils.marshalToString(
                        tr,
                        TeamRankings.class, true, "UTF-8");

                tr = new TeamRankings();
                tr.getTeamRanking().addAll(listFrance);
                String xmlFra = XMLUtils.marshalToString(
                        tr,
                        TeamRankings.class, true, "UTF-8");

                tr = new TeamRankings();
                tr.getTeamRanking().addAll(listGermany);
                String xmlGer = XMLUtils.marshalToString(
                        tr,
                        TeamRankings.class, true, "UTF-8");

                tr = new TeamRankings();
                tr.getTeamRanking().addAll(listItalia);
                String xmlIta = XMLUtils.marshalToString(
                        tr,
                        TeamRankings.class, true, "UTF-8");

                tr = new TeamRankings();
                tr.getTeamRanking().addAll(listSpain);
                String xmlSpa = XMLUtils.marshalToString(
                        tr,
                        TeamRankings.class, true, "UTF-8");

                session.setAttribute("xmlEng", xmlEng);
                session.setAttribute("xmlFra", xmlFra);
                session.setAttribute("xmlGer", xmlGer);
                session.setAttribute("xmlIta", xmlIta);
                session.setAttribute("xmlSpa", xmlSpa);

                session.setAttribute("listEngland", listEngland);
                session.setAttribute("listFrance", listFrance);
                session.setAttribute("listGermany", listGermany);
                session.setAttribute("listItalia", listItalia);
                session.setAttribute("listSpain", listSpain);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(RequestListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        String xmlTop = (String) session.getAttribute("xmlTop");

        if (xmlTop == null || xmlTop.isEmpty()) {
            try {
                MysqlUtils utils = new MysqlUtils();
                con = utils.connect();

                CallableStatement cStmt = con.prepareCall("{CALL getTop10PlayerRating}");
                
                cStmt.execute();

                ResultSet rs = cStmt.getResultSet();

                List<TopPlayer> list = new ArrayList<>();
                
                while (rs.next()) {
                    TopPlayer tp = new TopPlayer();
                    tp.setMatches(rs.getInt(1));
                    tp.setName(rs.getString(2));
                    tp.setClubName(rs.getString(3));
                    tp.setAvgRatings(rs.getDouble(4));
                    tp.setAvgRatings((double)Math.round(tp.getAvgRatings()*100)/100);
                    list.add(tp);
                }
                
                TopPlayers tps = new TopPlayers();
                tps.getTopPlayer().addAll(list);
                xmlTop = XMLUtils.marshalToString(
                        tps,
                        TopPlayers.class, true, "UTF-8");
                
                session.setAttribute("xmlTop", xmlTop);
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(RequestListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }
}
