package com.iconmaster.srcplugin.parser;

import com.iconmaster.source.exception.SourceException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
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
			Document document = result.getSnapshot().getSource().getDocument(false);
			for (SourceException e : errs) {
				try {
					int low = e.getRange().low;
					int high = e.getRange().high;
					
					ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
							Severity.ERROR,
							e.getMessage(),
							document,
							document.createPosition(low),
							document.createPosition(high)
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
