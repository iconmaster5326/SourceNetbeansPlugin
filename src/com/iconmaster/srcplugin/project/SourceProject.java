package com.iconmaster.srcplugin.project;

import com.iconmaster.source.xml.XMLHelper;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.support.ant.AntBasedProjectRegistration;
import org.netbeans.spi.project.support.ant.AntBasedProjectType;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.GeneratedFilesHelper;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.w3c.dom.Element;

/**
 *
 * @author iconmaster
 */
@AntBasedProjectRegistration(iconResource="com/iconmaster/srcplugin/src-project-icon.png",type="Source",sharedNamespace="source-namespace",privateNamespace="source-namespace")
public class SourceProject implements Project,AntBasedProjectType {
	private AntProjectHelper anh;
	
	public SourceProject(AntProjectHelper aph) {
		dir = aph.getProjectDirectory();
		state = new ProjectState() {

			@Override
			public void markModified() {
				onSave();
			}

			@Override
			public void notifyDeleted() throws IllegalStateException {
				
			}
			
		};
	}

	@Override
	public String getType() {
		return "Source";
	}

	@Override
	public Project createProject(AntProjectHelper helper) throws IOException {
		return new SourceProject(helper);
	}

	@Override
	public String getPrimaryConfigurationDataElementName(boolean shared) {
		return "data";
	}

	@Override
	public String getPrimaryConfigurationDataElementNamespace(boolean shared) {
		return "source-namespace";
	}
	
	public class SourceInfo implements ProjectInformation {
		@Override
		public String getName() {
			return getProjectDirectory().getName();
		}

		@Override
		public String getDisplayName() {
			return getProjectDirectory().getName();
		}

		@Override
		public Icon getIcon() {
			return new ImageIcon(ImageUtilities.loadImage("com/iconmaster/srcplugin/src-project-icon.png"));
		}

		@Override
		public Project getProject() {
			return SourceProject.this;
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
		}
	}
	
	private final FileObject dir;
	private final ProjectState state;
	private Lookup lkp;
	
	public SourceProject(FileObject dir, ProjectState state) {
        this.dir = dir;
        this.state = state;
    }
	
	public SourceProject(AntProjectHelper anh, FileObject dir, ProjectState state) {
		this.anh = anh;
        this.dir = dir;
        this.state = state;
    }

	@Override
	public FileObject getProjectDirectory() {
		return dir;
	}

	@Override
	public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
				this,
				new SourceInfo(),
				new SourceLogicalView(this),
				new SourceProjectPropertiesCustomizer(this),
				new SourceXmlSavedHook(this),
			});
        }
        return lkp;
	}
	
	public void onSave() {
		StatusDisplayer.getDefault().setStatusText("SAVED");
		
		Element config = anh.getPrimaryConfigurationData(true);
		XMLHelper.addTag(config.getOwnerDocument(), config, "name", dir.getName());
		anh.putPrimaryConfigurationData(config, true);
		
		try {
			GeneratedFilesHelper gfh = new GeneratedFilesHelper(anh);
			gfh.refreshBuildScript("build.xml", new URL("com/iconmaster/srcplugin/project/BuildScriptFormat.xsl"), true);
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
	}
	
}
