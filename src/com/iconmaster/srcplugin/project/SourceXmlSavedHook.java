package com.iconmaster.srcplugin.project;

import java.io.IOException;
import org.netbeans.spi.project.support.ant.ProjectXmlSavedHook;

/**
 *
 * @author iconmaster
 */
public class SourceXmlSavedHook extends ProjectXmlSavedHook {
	private final SourceProject p;
	
	public SourceXmlSavedHook(SourceProject p) {
		this.p = p;
	}

	@Override
	protected void projectXmlSaved() throws IOException {
		p.onSave();
	}
	
}
