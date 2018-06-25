<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : teamRankings.xsl
    Created on : November 1, 2017, 9:58 AM
    Author     : haleduykhang
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="teamrankings">
        <xsl:apply-templates>
            
        </xsl:apply-templates>
    </xsl:template>
    <xsl:template match="teamranking">
        <tr class="small" style="margin-bottom: 0">
            <td>
                <xsl:value-of select="name"/>
            </td>
            <td>
                <xsl:value-of select="win + due + lose"/>
            </td>
            <td>
                <xsl:value-of select="win"/>
            </td>
            <td>
                <xsl:value-of select="due"/>
            </td>
            <td>
                <xsl:value-of select="lose"/>
            </td>
            <td>
                <xsl:value-of select="score"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
