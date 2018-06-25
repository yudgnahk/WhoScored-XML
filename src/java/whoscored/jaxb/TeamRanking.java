/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author haleduykhang
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "teamraking", propOrder = {
    "name",
    "leagueID",
    "win",
    "due",
    "lose",
    "score"
})
public class TeamRanking implements Serializable, Comparable<TeamRanking> {
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private int leagueID;
    @XmlElement(required = true)
    private int win;
    @XmlElement(required = true)
    private int due;
    @XmlElement(required = true)
    private int lose;
    @XmlElement(required = true)
    private int score;

    public TeamRanking() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the leagueID
     */
    public int getLeagueID() {
        return leagueID;
    }

    /**
     * @param leagueID the leagueID to set
     */
    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    /**
     * @return the win
     */
    public int getWin() {
        return win;
    }

    /**
     * @param win the win to set
     */
    public void setWin(int win) {
        this.win = win;
    }

    /**
     * @return the due
     */
    public int getDue() {
        return due;
    }

    /**
     * @param due the due to set
     */
    public void setDue(int due) {
        this.due = due;
    }

    /**
     * @return the lose
     */
    public int getLose() {
        return lose;
    }

    /**
     * @param lose the lose to set
     */
    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore() {
        this.score = 3 * this.win + this.due;
    }
    
    

    @Override
    public int compareTo(TeamRanking o) {
        return o.getScore() - this.getScore();
    }

}
