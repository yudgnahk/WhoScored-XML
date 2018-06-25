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
@XmlType(name = "topplayer", propOrder = {
    "matches",
    "name",
    "clubName",
    "avgRatings"
})
public class TopPlayer implements Serializable{
    
    @XmlElement(required = true)
    private int matches;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String clubName;
    @XmlElement(required = true)
    private double avgRatings;

    public TopPlayer() {
    }

    /**
     * @return the matches
     */
    public int getMatches() {
        return matches;
    }

    /**
     * @param matches the matches to set
     */
    public void setMatches(int matches) {
        this.matches = matches;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the clubName
     */
    public String getClubName() {
        return clubName;
    }

    /**
     * @param clubName the clubName to set
     */
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public double getAvgRatings() {
        return avgRatings;
    }

    public void setAvgRatings(double avgRatings) {
        this.avgRatings = avgRatings;
    }

    
    
    
    
}
