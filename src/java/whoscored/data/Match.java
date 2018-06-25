/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author haleduykhang
 */
@Entity
@Table(name = "Match", catalog = "XMLProject", schema = "")
@NamedQueries({
    @NamedQuery(name = "Match.findAll", query = "SELECT m FROM Match m")
    , @NamedQuery(name = "Match.findById", query = "SELECT m FROM Match m WHERE m.id = :id")
    , @NamedQuery(name = "Match.findByHomeScore", query = "SELECT m FROM Match m WHERE m.homeScore = :homeScore")
    , @NamedQuery(name = "Match.findByAwayScore", query = "SELECT m FROM Match m WHERE m.awayScore = :awayScore")
    , @NamedQuery(name = "Match.findByTime", query = "SELECT m FROM Match m WHERE m.time = :time")
    , @NamedQuery(name = "Match.findBySeason", query = "SELECT m FROM Match m WHERE m.season = :season")
    , @NamedQuery(name = "Match.findMatch", query = "SELECT m FROM Match m WHERE m.season = :season and m.homeTeamID = :homeTeamID and m.awayTeamID = :awayTeamID")})

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NovelVersion", propOrder = {
    "leagueID",
    "homeTeamID",
    "awayTeamID",
    "homeScore",
    "awayScore",
    "time",
    "season"
})
public class Match implements Serializable {

    @OneToMany(mappedBy = "matchID")
    private Collection<PlayerMatchStatistic> playerMatchStatisticCollection;

    @OneToMany(mappedBy = "matchID")
    private Collection<Goal> goalCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @XmlAttribute(name = "id")
    private Integer id;
    @XmlElement(required = true)
    @Column(name = "homeScore")
    private Integer homeScore;
    @XmlElement(required = true)
    @Column(name = "awayScore")
    private Integer awayScore;
    @XmlElement(required = true)
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @XmlElement(required = true)
    @Column(name = "season", length = 10)
    private String season;
    @XmlElement(required = true)
    @JoinColumn(name = "awayTeamID", referencedColumnName = "id")
    @ManyToOne
    private Club awayTeamID;
    @XmlElement(required = true)
    @JoinColumn(name = "homeTeamID", referencedColumnName = "id")
    @ManyToOne
    private Club homeTeamID;
    @XmlElement(required = true)
    @JoinColumn(name = "leagueID", referencedColumnName = "id")
    @ManyToOne
    private League leagueID;

    public Match() {
    }

    public Match(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Club getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(Club awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

    public Club getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(Club homeTeamID) {
        this.homeTeamID = homeTeamID;
    }

    public League getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(League leagueID) {
        this.leagueID = leagueID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Match[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Goal> getGoalCollection() {
        return goalCollection;
    }

    public void setGoalCollection(Collection<Goal> goalCollection) {
        this.goalCollection = goalCollection;
    }

    @XmlTransient
    public Collection<PlayerMatchStatistic> getPlayerMatchStatisticCollection() {
        return playerMatchStatisticCollection;
    }

    public void setPlayerMatchStatisticCollection(Collection<PlayerMatchStatistic> playerMatchStatisticCollection) {
        this.playerMatchStatisticCollection = playerMatchStatisticCollection;
    }
    
}
