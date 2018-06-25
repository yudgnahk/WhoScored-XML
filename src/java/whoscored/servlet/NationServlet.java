/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import whoscored.jaxb.TopPlayer;
import whoscored.jaxb.TopPlayers;
import whoscored.listener.RequestListener;
import whoscored.utils.MysqlUtils;
import whoscored.utils.XMLUtils;

/**
 *
 * @author haleduykhang
 */
public class NationServlet extends HttpServlet {

    private final String nationPage = "nation.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection con = null;
        System.out.println("aaa");
        try (PrintWriter out = response.getWriter()) {
            String type = request.getParameter("nation");
            System.out.println("aaa");
            int leagueID = 0;
            switch (type) {
                case "Eng":
                    leagueID = 36;
                    break;
                case "Ita":
                    leagueID = 37;
                    break;
                case "Spa":
                    leagueID = 38;
                    break;
                case "Ger":
                    leagueID = 39;
                    break;
                case "Fra":
                    leagueID = 40;
                    break;
            }

            MysqlUtils utils = new MysqlUtils();
            con = utils.connect();

            CallableStatement cStmt = con.prepareCall("{CALL top20League(?)}");

            cStmt.setInt("leaID", leagueID);
            cStmt.execute();

            ResultSet rs = cStmt.getResultSet();

            List<TopPlayer> list = new ArrayList<>();

            while (rs.next()) {
                TopPlayer tp = new TopPlayer();
                tp.setMatches(rs.getInt(1));
                tp.setName(rs.getString(2));
                tp.setClubName(rs.getString(3));
                tp.setAvgRatings(rs.getDouble(4));
                tp.setAvgRatings((double) Math.round(tp.getAvgRatings() * 100) / 100);
                list.add(tp);
            }

            TopPlayers tps = new TopPlayers();
            tps.getTopPlayer().addAll(list);
            System.out.println(list.size());
            String xmlTop = XMLUtils.marshalToString(
                    tps,
                    TopPlayers.class, true, "UTF-8");

            request.getSession().setAttribute("xmlTopLeague", xmlTop);
            RequestDispatcher rd = request.getRequestDispatcher(nationPage);
            rd.forward(request, response);

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
