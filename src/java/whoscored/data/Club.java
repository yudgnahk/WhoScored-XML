/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;
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
@Table(name = "Club", catalog = "XMLProject", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})

@NamedQueries({
    @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c")
    , @NamedQuery(name = "Club.findById", query = "SELECT c FROM Club c WHERE c.id = :id")
    , @NamedQuery(name = "Club.findByName", query = "SELECT c FROM Club c WHERE c.name LIKE :name")
    , @NamedQuery(name = "Club.findByUrl", query = "SELECT c FROM Club c WHERE c.url = :url")})

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Club", propOrder = {
    "name",
    "leagueID"
})
public class Club implements Serializable {

    @OneToMany(mappedBy = "clubID")
    private Collection<TeamMatchStatistic> teamMatchStatisticCollection;
    @OneToMany(mappedBy = "homeTeamID")
    private Collection<Match> matchCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @XmlAttribute(name = "id")
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @XmlElement(required = true)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic(optional = false)
    @Column(name = "url", nullable = false, length = 255)
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currentTeamID")
    private Collection<Player> playerCollection;
    @JoinColumn(name = "leagueID", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    @XmlElement(required = true)
    private League leagueID;

    public Club() {
    }

    public Club(Integer id) {
        this.id = id;
    }

    public Club(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
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
        if (!(object instanceof Club)) {
            return false;
        }
        Club other = (Club) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Club[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TeamMatchStatistic> getTeamMatchStatisticCollection() {
        return teamMatchStatisticCollection;
    }

    public void setTeamMatchStatisticCollection(Collection<TeamMatchStatistic> teamMatchStatisticCollection) {
        this.teamMatchStatisticCollection = teamMatchStatisticCollection;
    }

    @XmlTransient
    public Collection<Match> getMatchCollection() {
        return matchCollection;
    }

    public void setMatchCollection(Collection<Match> matchCollection) {
        this.matchCollection = matchCollection;
    }
    
}
