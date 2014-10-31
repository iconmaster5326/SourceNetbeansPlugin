package com.iconmaster.srcplugin.parser;

import com.iconmaster.source.element.Element;
import com.iconmaster.source.exception.SourceException;
import com.iconmaster.source.exception.SourceSafeModeException;
import com.iconmaster.source.tokenize.Tokenizer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Position;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.util.Exceptions;

class SourceErrorTask extends ParserResultTask {

	public SourceErrorTask() {
	}

	@Override
	public void run(Result result, SchedulerEvent event) {
		SourceParserResult srcResult = (SourceParserResult) result;
		List<SourceException> errs = srcResult.ex;

		List<ErrorDescription> msgargs = new ArrayList<ErrorDescription>();
		if (!errs.isEmpty()) {
			final Document document = result.getSnapshot().getSource().getDocument(false);
			for (final SourceException e : errs) {
				try {
					int low = e.getRange().low;
					int high = e.getRange().high;
					
					final Position dlow = document.createPosition(low);
					final Position dhigh = document.createPosition(high);
					
					List<Fix> fixes = new ArrayList<Fix>();
					if (e instanceof SourceSafeModeException) {
						fixes.add(new Fix() {

							@Override
							public String getText() {
								return "Give data type to "+((SourceSafeModeException)e).var;
							}

							@Override
							public ChangeInfo implement() throws Exception {
								ChangeInfo ci = new ChangeInfo();
								String var = ((SourceSafeModeException)e).var;
								ArrayList<Element> tokens = Tokenizer.tokenize(document.getText(dlow.getOffset(), dhigh.getOffset()-dlow.getOffset()), false);
								int offset = -1;
								for (Element token : tokens) {
									if (token.args[0].equals(var)) {
										offset = token.range.high;
										break;
									}
								}
								if (offset !=-1) {
									document.insertString(dlow.getOffset()+offset, " as ?", null);
									ci.add(null, document.createPosition(dlow.getOffset()+offset+4), document.createPosition(dlow.getOffset()+offset+5));
								}
								return ci;
							}

						});
					}
					ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
							Severity.ERROR,
							e.getMessage(),
							fixes,
							document,
							dlow,
							dhigh
					);
					msgargs.add(errorDescription);
					HintsController.setErrors (document, "source", msgargs);
				} catch (BadLocationException ex) {
					Exceptions.printStackTrace(ex);
				}
			}
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Class<? extends Scheduler> getSchedulerClass() {
		return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
	}

	@Override
	public void cancel() {
	}
}
