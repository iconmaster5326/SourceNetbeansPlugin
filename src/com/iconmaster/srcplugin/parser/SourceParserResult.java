package com.iconmaster.srcplugin.parser;

import com.iconmaster.source.element.Element;
import com.iconmaster.source.exception.SourceException;
import java.util.ArrayList;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser.Result;

/**
 *
 * @author iconmaster
 */
public class SourceParserResult extends Result {
	public final String input;
	public final ArrayList<Element> parsed;
	public boolean valid = true;
	public final ArrayList<SourceException> ex;
	
	public SourceParserResult(Snapshot snapshot, String input, ArrayList<Element> parsed, ArrayList<SourceException> ex) {
		super(snapshot);
		this.input = input;
		this.parsed = parsed;
		this.ex = ex;
	}

	@Override
	protected void invalidate() {
		valid = false;
	}
	
}
