package com.iconmaster.srcplugin.editor;

import com.iconmaster.source.element.Element;
import com.iconmaster.source.exception.SourceException;
import com.iconmaster.source.parse.Parser;
import com.iconmaster.source.tokenize.Tokenizer;
import com.iconmaster.source.util.SourceDecompiler;
import java.util.ArrayList;
import javax.swing.text.BadLocationException;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ExtraLock;
import org.netbeans.modules.editor.indent.spi.ReformatTask;
import org.openide.util.Exceptions;

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
			try {
				String toFormat = ctx.document().getText(ctx.startOffset(), ctx.endOffset());
				ArrayList<Element> parse = Tokenizer.tokenize(toFormat);
				parse = Parser.parse(parse);
				String output = SourceDecompiler.elementsToString(parse);
				ctx.document().remove(ctx.startOffset(), ctx.endOffset()-ctx.startOffset());
				ctx.document().insertString(ctx.startOffset(), output, null);
			} catch (SourceException ex) {
				Exceptions.printStackTrace(ex);
			}
		}

		@Override
		public ExtraLock reformatLock() {
			return null;
		}
	}
}
