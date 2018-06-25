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
@Table(name = "League", catalog = "XMLProject", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})
    , @UniqueConstraint(columnNames = {"id"})})
@NamedQueries({
    @NamedQuery(name = "League.findAll", query = "SELECT l FROM League l")
    , @NamedQuery(name = "League.findById", query = "SELECT l FROM League l WHERE l.id = :id")
    , @NamedQuery(name = "League.findByName", query = "SELECT l FROM League l WHERE l.name = :name")
    , @NamedQuery(name = "League.findByRegion", query = "SELECT l FROM League l WHERE l.region = :region")
    , @NamedQuery(name = "League.findByUrl", query = "SELECT l FROM League l WHERE l.url = :url")})

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "League", propOrder = {
    "name",
    "region"
})
public class League implements Serializable {

    @OneToMany(mappedBy = "leagueID")
    private Collection<Match> matchCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @XmlAttribute(name = "id")
    private Integer id;
    @Basic(optional = false)
    @XmlElement(required = true)
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Basic(optional = false)
    @XmlElement(required = true)
    @Column(name = "region", nullable = false, length = 20)
    private String region;
    @Basic(optional = false)
    @Column(name = "url", nullable = false, length = 255)
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leagueID")
    private Collection<Club> clubCollection;

    public League() {
    }

    public League(Integer id) {
        this.id = id;
    }

    public League(Integer id, String name, String region, String url) {
        this.id = id;
        this.name = name;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public Collection<Club> getClubCollection() {
        return clubCollection;
    }

    public void setClubCollection(Collection<Club> clubCollection) {
        this.clubCollection = clubCollection;
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
        if (!(object instanceof League)) {
            return false;
        }
        League other = (League) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.League[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Match> getMatchCollection() {
        return matchCollection;
    }

    public void setMatchCollection(Collection<Match> matchCollection) {
        this.matchCollection = matchCollection;
    }
    
}
