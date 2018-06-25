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
@Table(name = "RedCard", catalog = "XMLProject", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RedCard.findAll", query = "SELECT r FROM RedCard r")
    , @NamedQuery(name = "RedCard.findById", query = "SELECT r FROM RedCard r WHERE r.id = :id")
    , @NamedQuery(name = "RedCard.findByMinute", query = "SELECT r FROM RedCard r WHERE r.minute = :minute")
    , @NamedQuery(name = "RedCard.findRedCard", query = "SELECT r FROM RedCard r "
            + "WHERE r.matchID = :matchID and r.playerID = :playerID and r.minute = :minute")})
public class RedCard implements Serializable {

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

    public RedCard() {
    }

    public RedCard(Integer id) {
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
        if (!(object instanceof RedCard)) {
            return false;
        }
        RedCard other = (RedCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.RedCard[ id=" + id + " ]";
    }
    
}
