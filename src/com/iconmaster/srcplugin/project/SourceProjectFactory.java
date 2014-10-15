package com.iconmaster.srcplugin.project;

import com.iconmaster.source.xml.XMLHelper;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author iconmaster
 */
@ServiceProvider(service=ProjectFactory2.class)
public class SourceProjectFactory implements ProjectFactory2 {

	@Override
	public boolean isProject(FileObject dir) {
		if (dir.getFileObject("nbproject")!=null&&dir.getFileObject("nbproject/project.xml")!=null) {
			try {
				Document doc = XMLHelper.fromString(dir.getFileObject("nbproject/project.xml").asText());
				String value = XMLHelper.getTag((Element)doc.getDocumentElement().getElementsByTagName("type").item(0), "project");
				return "Source".equals(value);
			} catch (IOException ex) {
				StatusDisplayer.getDefault().setStatusText(ex.toString());
				return false;
			}
		}
		return false;
	}

	@Override
	public Project loadProject(FileObject dir, ProjectState state) throws IOException {
		return isProject(dir) ? new SourceProject(dir,state) : null;
	}

	@Override
	public void saveProject(Project project) throws IOException, ClassCastException {
		((SourceProject)project).onSave();
	}

	@Override
	public ProjectManager.Result isProject2(FileObject dir) {
		return isProject(dir) ? new ProjectManager.Result("Source","Source", new ImageIcon(ImageUtilities.loadImage("com/iconmaster/srcplugin/src-project-icon.png"))) : null;
	}
	
}
