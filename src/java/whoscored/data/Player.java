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
@Table(name = "Player", catalog = "XMLProject", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p")
    , @NamedQuery(name = "Player.findById", query = "SELECT p FROM Player p WHERE p.id = :id")
    , @NamedQuery(name = "Player.findByName", query = "SELECT p FROM Player p WHERE p.name = :name")
    , @NamedQuery(name = "Player.findByShirtNum", query = "SELECT p FROM Player p WHERE p.shirtNum = :shirtNum")
    , @NamedQuery(name = "Player.findByPosition", query = "SELECT p FROM Player p WHERE p.position = :position")
    , @NamedQuery(name = "Player.findByDob", query = "SELECT p FROM Player p WHERE p.dob = :dob")
    , @NamedQuery(name = "Player.findByHeight", query = "SELECT p FROM Player p WHERE p.height = :height")
    , @NamedQuery(name = "Player.findByWeight", query = "SELECT p FROM Player p WHERE p.weight = :weight")
    , @NamedQuery(name = "Player.findByNationality", query = "SELECT p FROM Player p WHERE p.nationality = :nationality")
    , @NamedQuery(name = "Player.findByNameAndClub", query = "SELECT p FROM Player p WHERE p.name = :name and p.currentTeamID = :currentTeamID")
    , @NamedQuery(name = "Player.findByClub", query = "SELECT p FROM Player p WHERE p.currentTeamID = :currentTeamID")})


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Player", propOrder = {
    "name",
    "currentTeamID",
    "shirtNum",
    "position",
    "dob",
    "height",
    "weight",
    "nationality"
})
public class Player implements Serializable {

    @OneToMany(mappedBy = "playerID")
    private Collection<PlayerMatchStatistic> playerMatchStatisticCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @XmlAttribute(name = "id")
    private Integer id;
    @Basic(optional = false)
    @XmlElement(required = true)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @XmlElement(required = true)
    @Column(name = "shirtNum")
    private Integer shirtNum;
    @XmlElement(required = true)
    @Column(name = "position", length = 100)
    private String position;
    @XmlElement(required = true)
    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @XmlElement(required = true)
    @Column(name = "height")
    private Integer height;
    @XmlElement(required = true)
    @Column(name = "weight")
    private Integer weight;
    @XmlElement(required = true)
    @Column(name = "nationality", length = 45)
    private String nationality;
    @XmlElement(required = true)
    @JoinColumn(name = "currentTeamID", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Club currentTeamID;

    public Player() {
    }

    public Player(Integer id) {
        this.id = id;
    }

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Integer getShirtNum() {
        return shirtNum;
    }

    public void setShirtNum(Integer shirtNum) {
        this.shirtNum = shirtNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Club getCurrentTeamID() {
        return currentTeamID;
    }

    public void setCurrentTeamID(Club currentTeamID) {
        this.currentTeamID = currentTeamID;
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
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Player[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<PlayerMatchStatistic> getPlayerMatchStatisticCollection() {
        return playerMatchStatisticCollection;
    }

    public void setPlayerMatchStatisticCollection(Collection<PlayerMatchStatistic> playerMatchStatisticCollection) {
        this.playerMatchStatisticCollection = playerMatchStatisticCollection;
    }
    
}
