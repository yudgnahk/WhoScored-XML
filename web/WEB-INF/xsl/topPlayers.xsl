<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : topPlayers.xsl.xsl
    Created on : November 1, 2017, 11:02 AM
    Author     : haleduykhang
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="topplayers">
        <xsl:apply-templates>
            
        </xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="topplayer">
        <tr class="small" style="margin-bottom: 0">
            <td>
                <xsl:value-of select="name"/>
            </td>
            <td>
                <xsl:value-of select="clubName"/>
            </td>
            <td>
                <xsl:value-of select="Matches"/>
            </td>
            <td>
                <xsl:value-of select="avgRatings"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
