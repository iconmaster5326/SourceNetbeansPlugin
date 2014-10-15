<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:ns="http://www.netbeans.org/ns/project/1" exclude-result-prefixes="ns">
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="/">
		<project default="default" basedir="." >
			<xsl:attribute name="name">
				<xsl:value-of select="ns:project/ns:configuration/ns:name"/>
			</xsl:attribute>
		</project>
	</xsl:template>

</xsl:stylesheet>
