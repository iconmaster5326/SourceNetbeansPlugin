package com.iconmaster.srcplugin.project;

import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author iconmaster
 */
public class SourceProject implements Project {
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
				new SourceProjectPropertiesCustomizer(this)
			});
        }
        return lkp;
	}
	
}
