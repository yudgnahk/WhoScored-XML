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
@Table(name = "PlayerMatchStatistic", catalog = "XMLProject", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerMatchStatistic.findAll", query = "SELECT p FROM PlayerMatchStatistic p")
    , @NamedQuery(name = "PlayerMatchStatistic.findById", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.id = :id")
    , @NamedQuery(name = "PlayerMatchStatistic.findByRating", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.rating = :rating")
    , @NamedQuery(name = "PlayerMatchStatistic.findByShots", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.shots = :shots")
    , @NamedQuery(name = "PlayerMatchStatistic.findByShotsOnTarget", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.shotsOnTarget = :shotsOnTarget")
    , @NamedQuery(name = "PlayerMatchStatistic.findByKeyPasses", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.keyPasses = :keyPasses")
    , @NamedQuery(name = "PlayerMatchStatistic.findByPa", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.pa = :pa")
    , @NamedQuery(name = "PlayerMatchStatistic.findByAerialsWon", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.aerialsWon = :aerialsWon")
    , @NamedQuery(name = "PlayerMatchStatistic.findByTouches", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.touches = :touches")
    , @NamedQuery(name = "PlayerMatchStatistic.findByIsSub", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.isSub = :isSub")
    , @NamedQuery(name = "PlayerMatchStatistic.findByMinIn", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.minIn = :minIn")
    , @NamedQuery(name = "PlayerMatchStatistic.findByMinOut", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.minOut = :minOut")
    , @NamedQuery(name = "PlayerMatchStatistic.findByPosition", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.position = :position")
    , @NamedQuery(name = "PlayerMatchStatistic.findByMatchAndPlayer", query = "SELECT p FROM PlayerMatchStatistic p WHERE p.matchID = :matchID and p.playerID = :playerID")})
public class PlayerMatchStatistic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating", precision = 12)
    private Float rating;
    @Column(name = "shots")
    private Integer shots;
    @Column(name = "shotsOnTarget")
    private Integer shotsOnTarget;
    @Column(name = "keyPasses")
    private Integer keyPasses;
    @Column(name = "PA", precision = 12)
    private Float pa;
    @Column(name = "aerialsWon")
    private Integer aerialsWon;
    @Column(name = "touches")
    private Integer touches;
    @Column(name = "isSub")
    private Boolean isSub;
    @Column(name = "minIn")
    private Integer minIn;
    @Column(name = "minOut")
    private Integer minOut;
    @Column(name = "position", length = 10)
    private String position;
    @JoinColumn(name = "playerID", referencedColumnName = "id")
    @ManyToOne
    private Player playerID;
    @JoinColumn(name = "matchID", referencedColumnName = "id")
    @ManyToOne
    private Match matchID;

    public PlayerMatchStatistic() {
    }

    public PlayerMatchStatistic(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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

    public Integer getKeyPasses() {
        return keyPasses;
    }

    public void setKeyPasses(Integer keyPasses) {
        this.keyPasses = keyPasses;
    }

    public Float getPa() {
        return pa;
    }

    public void setPa(Float pa) {
        this.pa = pa;
    }

    public Integer getAerialsWon() {
        return aerialsWon;
    }

    public void setAerialsWon(Integer aerialsWon) {
        this.aerialsWon = aerialsWon;
    }

    public Integer getTouches() {
        return touches;
    }

    public void setTouches(Integer touches) {
        this.touches = touches;
    }

    public Boolean getIsSub() {
        return isSub;
    }

    public void setIsSub(Boolean isSub) {
        this.isSub = isSub;
    }

    public Integer getMinIn() {
        return minIn;
    }

    public void setMinIn(Integer minIn) {
        this.minIn = minIn;
    }

    public Integer getMinOut() {
        return minOut;
    }

    public void setMinOut(Integer minOut) {
        this.minOut = minOut;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
        this.playerID = playerID;
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
        if (!(object instanceof PlayerMatchStatistic)) {
            return false;
        }
        PlayerMatchStatistic other = (PlayerMatchStatistic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getPlayerID().getName() + " " 
                + this.getPosition() + " "
                + this.getShots() + " "
                + this.getShotsOnTarget() + " "
                + this.getKeyPasses() + " "
                + this.getPa() + " "
                + this.getAerialsWon() + " "
                + this.getTouches() + " "
                + this.rating + " "
                + this.minOut + " "
                + this.minIn;
    }
    
}
