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
@Table(name = "YellowCard", catalog = "XMLProject", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "YellowCard.findAll", query = "SELECT y FROM YellowCard y")
    , @NamedQuery(name = "YellowCard.findById", query = "SELECT y FROM YellowCard y WHERE y.id = :id")
    , @NamedQuery(name = "YellowCard.findByMinute", query = "SELECT y FROM YellowCard y WHERE y.minute = :minute")
    , @NamedQuery(name = "YellowCard.findYellowCard", query = "SELECT y FROM YellowCard y WHERE y.matchID = :matchID and y.playerID = :playerID and y.minute = :minute")})
public class YellowCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "minute")
    private Integer minute;
    @JoinColumn(name = "matchID", referencedColumnName = "id")
    @ManyToOne
    private Match matchID;
    @JoinColumn(name = "playerID", referencedColumnName = "id")
    @ManyToOne
    private Player playerID;

    public YellowCard() {
    }

    public YellowCard(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Match getMatchID() {
        return matchID;
    }

    public void setMatchID(Match matchID) {
        this.matchID = matchID;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
        this.playerID = playerID;
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
        if (!(object instanceof YellowCard)) {
            return false;
        }
        YellowCard other = (YellowCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.YellowCard[ id=" + id + " ]";
    }
    
}
