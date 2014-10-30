package com.iconmaster.srcplugin.parser;

import com.iconmaster.source.compile.SourceCompiler;
import com.iconmaster.source.element.Element;
import com.iconmaster.source.exception.SourceException;
import com.iconmaster.source.link.Linker;
import com.iconmaster.source.prototype.Prototyper;
import com.iconmaster.source.tokenize.Tokenizer;
import com.iconmaster.source.util.Range;
import com.iconmaster.source.validate.Validator;
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
		try {
			parsed = com.iconmaster.source.parse.Parser.parse(Tokenizer.tokenize(input));
		} catch (SourceException ex2) {
			ex.add(ex2);
		}
		
		if (parsed != null) {
			ArrayList<SourceException> valErr = Validator.validate(parsed);
			ex.addAll(valErr);
			if (valErr.isEmpty()) {
				Prototyper.PrototypeResult pres = Prototyper.prototype(parsed);
				ex.addAll(pres.errors);
				Linker linker = Linker.link("HPPL", pres.result);
				if (!linker.unresolvedImports.isEmpty()) {
					for (String str : linker.unresolvedImports) {
						ex.add(new SourceException(new Range(0,1), "Unresolved import "+str));
					}
				}
				ex.addAll(SourceCompiler.compile(linker.pkg));
			}
		}
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
