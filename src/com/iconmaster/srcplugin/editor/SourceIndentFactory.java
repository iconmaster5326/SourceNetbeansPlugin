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
//			int thisOff = ctx.caretOffset();
//			int lastOff = ctx.lineStartOffset(thisOff-1);
//			int tabs = ctx.lineIndent(lastOff);
//			ctx.modifyIndent(thisOff, tabs);
//			StatusDisplayer.getDefault().setStatusText("TABBIN "+thisOff+" "+lastOff+" "+tabs);
			
			new SourceReformatFactory().createTask(ctx).reformat();
		}

		@Override
		public ExtraLock indentLock() {
			return null;
		}
	}
}
