/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author haleduykhang
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "topplayer"
})
@XmlRootElement(name = "topplayers")
public class TopPlayers {
    
    @XmlElement(required = true)
    protected List<TopPlayer> topplayer;
    
    public List<TopPlayer> getTopPlayer() {
        if (topplayer == null) {
            topplayer = new ArrayList<TopPlayer>();
        }
        return this.topplayer;
    }
    
}
