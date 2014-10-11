package com.iconmaster.srcplugin.project;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author iconmaster
 */
public class SourceLogicalView implements LogicalViewProvider {

	private final class ProjectNode extends FilterNode {

		final SourceProject project;

		public ProjectNode(Node node, SourceProject project) 
			throws DataObjectNotFoundException {
			super(node,
					NodeFactorySupport.createCompositeChildren(project, "Projects/com-iconmaster-srcplugin/Nodes"),
					// new FilterNode.Children(node),
					new ProxyLookup(
					new Lookup[]{
						Lookups.singleton(project),
						node.getLookup()
					}));
			this.project = project;
		}

		@Override
		public Action[] getActions(boolean arg0) {
			return new Action[]{
				CommonProjectActions.newFileAction(),
				CommonProjectActions.setProjectConfigurationAction(),
				CommonProjectActions.setAsMainProjectAction(),
				CommonProjectActions.openSubprojectsAction(),
				CommonProjectActions.closeProjectAction(),
				CommonProjectActions.renameProjectAction(),
				CommonProjectActions.moveProjectAction(),
				CommonProjectActions.copyProjectAction(),
				CommonProjectActions.deleteProjectAction(),
				CommonProjectActions.customizeProjectAction(),
			};
		}

		@Override
		public Image getIcon(int type) {
			return ImageUtilities.loadImage("com/iconmaster/srcplugin/src-project-icon.png");
		}

		@Override
		public Image getOpenedIcon(int type) {
			return getIcon(type);
		}

		@Override
		public String getDisplayName() {
			return project.getProjectDirectory().getName();
		}

	}

	private final SourceProject project;

	public SourceLogicalView(SourceProject project) {
		this.project = project;
	}

	@Override
	public Node createLogicalView() {
		try {
			//Obtain the project directory's node:
			FileObject projectDirectory = project.getProjectDirectory();
			DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
			Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
			//Decorate the project directory's node:
			return new ProjectNode(nodeOfProjectFolder, project);
		} catch (DataObjectNotFoundException donfe) {
			Exceptions.printStackTrace(donfe);
            //Fallback-the directory couldn't be created -
			//read-only filesystem or something evil happened
			return new AbstractNode(Children.LEAF);
		}
	}

	@Override
	public Node findPath(Node root, Object target) {
		return null;
	}

}
