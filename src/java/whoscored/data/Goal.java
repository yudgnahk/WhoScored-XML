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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author haleduykhang
 */
@Entity
@Table(name = "Goal", catalog = "XMLProject", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})

@NamedQueries({
    @NamedQuery(name = "Goal.findAll", query = "SELECT g FROM Goal g")
    , @NamedQuery(name = "Goal.findById", query = "SELECT g FROM Goal g WHERE g.id = :id")
    , @NamedQuery(name = "Goal.findByIsOwnGoad", query = "SELECT g FROM Goal g WHERE g.isOwnGoad = :isOwnGoad")
    , @NamedQuery(name = "Goal.findByMinute", query = "SELECT g FROM Goal g WHERE g.minute = :minute")
    , @NamedQuery(name = "Goal.findBySecond", query = "SELECT g FROM Goal g WHERE g.second = :second")
    , @NamedQuery(name = "Goal.findGoal", query = "SELECT g FROM Goal g WHERE g.matchID = :matchID "
            + "and g.playerID = :playerID and g.minute = :minute and g.second = :second")
    })

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Goal", propOrder = {
    "matchID",
    "playerID",
    "assistPlayerID",
    "isOwnGoad",
    "minute",
    "second"
})
public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @XmlAttribute(name = "id")
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "isOwnGoad")
    @XmlElement(required = true)
    private Boolean isOwnGoad;
    @XmlElement(required = true)
    @Column(name = "minute")
    private Integer minute;
    @XmlElement(required = true)
    @Column(name = "second")
    private Integer second;
    @JoinColumn(name = "assistPlayerID", referencedColumnName = "id")
    @ManyToOne
    @XmlElement(required = true)
    private Player assistPlayerID;
    @JoinColumn(name = "matchID", referencedColumnName = "id")
    @ManyToOne
    @XmlElement(required = true)
    private Match matchID;
    @JoinColumn(name = "playerID", referencedColumnName = "id")
    @ManyToOne
    @XmlElement(required = true)
    private Player playerID;

    public Goal() {
    }

    public Goal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsOwnGoad() {
        return isOwnGoad;
    }

    public void setIsOwnGoad(Boolean isOwnGoad) {
        this.isOwnGoad = isOwnGoad;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Player getAssistPlayerID() {
        return assistPlayerID;
    }

    public void setAssistPlayerID(Player assistPlayerID) {
        this.assistPlayerID = assistPlayerID;
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
        if (!(object instanceof Goal)) {
            return false;
        }
        Goal other = (Goal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Goal[ id=" + id + " ]";
    }
    
}
