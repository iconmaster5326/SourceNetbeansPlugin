package com.iconmaster.srcplugin.editor;

import javax.swing.text.BadLocationException;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ExtraLock;
import org.netbeans.modules.editor.indent.spi.IndentTask;

/**
 *
 * @author iconmaster
 */
public class SourceIndentFactory implements IndentTask.Factory {

	@Override
	public IndentTask createTask(Context cntxt) {
		return new SourceIndent(cntxt);
	}
	
	public class SourceIndent implements IndentTask {
		Context ctx;
		
		public SourceIndent(Context c) {
			this.ctx = c;
		}

		@Override
		public void reindent() throws BadLocationException {
			int off = ctx.lineStartOffset(ctx.endOffset());
			int tabs = ctx.lineIndent(off);
			ctx.modifyIndent(ctx.endOffset()+1, tabs);
		}

		@Override
		public ExtraLock indentLock() {
			return null;
		}
	}
}
