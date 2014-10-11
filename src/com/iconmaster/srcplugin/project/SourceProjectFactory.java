package com.iconmaster.srcplugin.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author iconmaster
 */
@ServiceProvider(service=ProjectFactory.class)
public class SourceProjectFactory implements ProjectFactory {

	@Override
	public boolean isProject(FileObject dir) {
		return dir.getFileObject("proj.src")!=null;
	}

	@Override
	public Project loadProject(FileObject dir, ProjectState state) throws IOException {
		return isProject(dir) ? new SourceProject(dir,state) : null;
	}

	@Override
	public void saveProject(Project project) throws IOException, ClassCastException {
		
	}
	
}
