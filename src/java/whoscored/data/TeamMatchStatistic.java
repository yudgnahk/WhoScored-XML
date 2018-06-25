/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.data;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author haleduykhang
 */
@Entity
@Table(name = "TeamMatchStatistic", catalog = "XMLProject", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamMatchStatistic.findAll", query = "SELECT t FROM TeamMatchStatistic t")
    , @NamedQuery(name = "TeamMatchStatistic.findById", query = "SELECT t FROM TeamMatchStatistic t WHERE t.id = :id")
    , @NamedQuery(name = "TeamMatchStatistic.findByShots", query = "SELECT t FROM TeamMatchStatistic t WHERE t.shots = :shots")
    , @NamedQuery(name = "TeamMatchStatistic.findByShotsOnTarget", query = "SELECT t FROM TeamMatchStatistic t WHERE t.shotsOnTarget = :shotsOnTarget")
    , @NamedQuery(name = "TeamMatchStatistic.findByPassSuccess", query = "SELECT t FROM TeamMatchStatistic t WHERE t.passSuccess = :passSuccess")
    , @NamedQuery(name = "TeamMatchStatistic.findByAerialDuelSuccess", query = "SELECT t FROM TeamMatchStatistic t WHERE t.aerialDuelSuccess = :aerialDuelSuccess")
    , @NamedQuery(name = "TeamMatchStatistic.findByDribblesWon", query = "SELECT t FROM TeamMatchStatistic t WHERE t.dribblesWon = :dribblesWon")
    , @NamedQuery(name = "TeamMatchStatistic.findByTackles", query = "SELECT t FROM TeamMatchStatistic t WHERE t.tackles = :tackles")
    , @NamedQuery(name = "TeamMatchStatistic.findByPosession", query = "SELECT t FROM TeamMatchStatistic t WHERE t.posession = :posession")
    , @NamedQuery(name = "TeamMatchStatistic.findByFormation", query = "SELECT t FROM TeamMatchStatistic t WHERE t.formation = :formation")
    , @NamedQuery(name = "TeamMatchStatistic.findByMatchAndClub", query = "SELECT t FROM TeamMatchStatistic t WHERE t.matchID = :matchID and t.clubID = :clubID")})
public class TeamMatchStatistic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "shots")
    private Integer shots;
    @Column(name = "shotsOnTarget")
    private Integer shotsOnTarget;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "passSuccess", precision = 12)
    private Float passSuccess;
    @Column(name = "aerialDuelSuccess", precision = 12)
    private Float aerialDuelSuccess;
    @Column(name = "dribblesWon")
    private Integer dribblesWon;
    @Column(name = "tackles")
    private Integer tackles;
    @Column(name = "posession", precision = 12)
    private Float posession;
    @Column(name = "formation", length = 20)
    private String formation;
    @JoinColumn(name = "clubID", referencedColumnName = "id")
    @ManyToOne
    private Club clubID;
    @JoinColumn(name = "matchID", referencedColumnName = "id")
    @ManyToOne
    private Match matchID;

    public TeamMatchStatistic() {
    }

    public TeamMatchStatistic(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getShotsOnTarget() {
        return shotsOnTarget;
    }

    public void setShotsOnTarget(Integer shotsOnTarget) {
        this.shotsOnTarget = shotsOnTarget;
    }

    public Float getPassSuccess() {
        return passSuccess;
    }

    public void setPassSuccess(Float passSuccess) {
        this.passSuccess = passSuccess;
    }

    public Float getAerialDuelSuccess() {
        return aerialDuelSuccess;
    }

    public void setAerialDuelSuccess(Float aerialDuelSuccess) {
        this.aerialDuelSuccess = aerialDuelSuccess;
    }

    public Integer getDribblesWon() {
        return dribblesWon;
    }

    public void setDribblesWon(Integer dribblesWon) {
        this.dribblesWon = dribblesWon;
    }

    public Integer getTackles() {
        return tackles;
    }

    public void setTackles(Integer tackles) {
        this.tackles = tackles;
    }

    public Float getPosession() {
        return posession;
    }

    public void setPosession(Float posession) {
        this.posession = posession;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Club getClubID() {
        return clubID;
    }

    public void setClubID(Club clubID) {
        this.clubID = clubID;
    }

    public Match getMatchID() {
        return matchID;
    }

    public void setMatchID(Match matchID) {
        this.matchID = matchID;
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
        if (!(object instanceof TeamMatchStatistic)) {
            return false;
        }
        TeamMatchStatistic other = (TeamMatchStatistic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.TeamMatchStatistic[ id=" + id + " ]";
    }
    
}
