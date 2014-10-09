package com.iconmaster.srcplugin.editor;

import javax.swing.text.BadLocationException;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.spi.editor.typinghooks.TypedBreakInterceptor;

/**
 *
 * @author iconmaster
 */
public class SourceBreakInterceptorFactory implements TypedBreakInterceptor.Factory {
	
	public class SourceBreakInterceptor implements TypedBreakInterceptor {

	@Override
	public boolean beforeInsert(TypedBreakInterceptor.Context context) throws BadLocationException {
		return false;
	}

	@Override
	public void insert(TypedBreakInterceptor.MutableContext context) throws BadLocationException {
		if (context.getDocument().getText(context.getCaretOffset()-1,1).equals("{")) {
			context.setText("\n\t\n}", -1, 2, 0, 3);
		} else {
			context.setText("\n", -1, 1, 0, 1);
		}
	}

	@Override
	public void afterInsert(TypedBreakInterceptor.Context context) throws BadLocationException {
	}

	@Override
	public void cancelled(TypedBreakInterceptor.Context context) {
	}
}

	@Override
	public TypedBreakInterceptor createTypedBreakInterceptor(MimePath mimePath) {
		return new SourceBreakInterceptor();
	}
	
}
