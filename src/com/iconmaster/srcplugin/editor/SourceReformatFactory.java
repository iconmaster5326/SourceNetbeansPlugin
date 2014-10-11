package com.iconmaster.srcplugin.editor;

import javax.swing.text.BadLocationException;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ExtraLock;
import org.netbeans.modules.editor.indent.spi.ReformatTask;
import org.openide.awt.StatusDisplayer;

/**
 *
 * @author iconmaster
 */
public class SourceReformatFactory implements ReformatTask.Factory {

	@Override
	public ReformatTask createTask(Context cntxt) {
		return new SourceReformat(cntxt);
	}
	
	public class SourceReformat implements ReformatTask {
		Context ctx;
		
		public SourceReformat(Context c) {
			this.ctx = c;
		}

		@Override
		public void reformat() throws BadLocationException {
			String toFormat = ctx.document().getText(ctx.startOffset(), ctx.endOffset());
			StatusDisplayer.getDefault().setStatusText(toFormat);
		}

		@Override
		public ExtraLock reformatLock() {
			return null;
		}
	}
}
