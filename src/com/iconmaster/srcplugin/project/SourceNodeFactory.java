package com.iconmaster.srcplugin.project;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

@NodeFactory.Registration(projectType = "com-iconmaster-srcplugin", position = 10)
public class SourceNodeFactory implements NodeFactory {

	@Override
	public NodeList<?> createNodes(Project project) {
		SourceProject p = project.getLookup().lookup(SourceProject.class);
		assert p != null;
		return new TextsNodeList(p);
	}

	private class TextsNodeList implements NodeList<Node> {

		SourceProject project;

		public TextsNodeList(SourceProject project) {
			this.project = project;
		}

		@Override
		public List<Node> keys() {
			FileObject textsFolder = project.getProjectDirectory();
			List<Node> result = new ArrayList<Node>();
			if (textsFolder != null) {
				for (FileObject textsFolderFile : textsFolder.getChildren()) {
					try {
						if (textsFolderFile.getName().equals("src")) {
							Node fold = DataObject.find(textsFolderFile).getNodeDelegate();
							fold.setDisplayName("Source Files");
							result.add(fold);
						}
					} catch (DataObjectNotFoundException ex) {
						Exceptions.printStackTrace(ex);
					}
				}
			}
			return result;
		}

		@Override
		public Node node(Node node) {
			return new FilterNode(node);
		}

		@Override
		public void addNotify() {
		}

		@Override
		public void removeNotify() {
		}

		@Override
		public void addChangeListener(ChangeListener cl) {
		}

		@Override
		public void removeChangeListener(ChangeListener cl) {
		}

	}

}
