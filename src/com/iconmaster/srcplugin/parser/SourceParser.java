package com.iconmaster.srcplugin.parser;

import com.iconmaster.source.Source;
import com.iconmaster.source.SourceOutput;
import com.iconmaster.source.element.Element;
import com.iconmaster.source.exception.SourceException;
import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

public class SourceParser extends Parser {

	private Snapshot snapshot;
	private String input;
	private ArrayList<Element> parsed;
	
	private ArrayList<SourceException> ex = new ArrayList<SourceException>();

	@Override
	public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) {
		this.snapshot = snapshot;
		input = snapshot.getText().toString();
		parsed = null;
		ex.clear();
		SourceOutput so = Source.compile(input, "HPPL", System.out);
		ex.addAll(so.errs);
	}

	@Override
	public Result getResult(Task task) throws ParseException {
		return new SourceParserResult(snapshot, input, parsed, ex);
	}

	@Override
	public void addChangeListener(ChangeListener changeListener) {

	}

	@Override
	public void removeChangeListener(ChangeListener changeListener) {

	}

}
