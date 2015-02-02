package com.iconmaster.srcplugin.lexer;

import com.iconmaster.srcplugin.lexerjj.SourceParserConstants;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class SourceLanguageHierarchy extends LanguageHierarchy<SourceTokenId> {

	private static List<SourceTokenId> tokens;
	private static Map<Integer, SourceTokenId> idToToken;

	private static void init() {
		tokens = Arrays.<SourceTokenId>asList(new SourceTokenId[]{
			new SourceTokenId("EOF", "whitespace", SourceParserConstants.EOF),
			new SourceTokenId("WHITESPACE", "whitespace", SourceParserConstants.WHITESPACE),
			new SourceTokenId("COMMENT", "comment", SourceParserConstants.COMMENT),
			new SourceTokenId("BLOCK_COMMENT", "comment", SourceParserConstants.BLOCK_COMMENT),
			new SourceTokenId("DIRECTIVE", "directive", SourceParserConstants.DIRECTIVE),
			new SourceTokenId("KEYWORD", "keyword", SourceParserConstants.KEYWORD),
			new SourceTokenId("WORD", "word", SourceParserConstants.WORD),
			new SourceTokenId("WORD_B", "error", SourceParserConstants.WORD_B),
			new SourceTokenId("WORD_E", "error", SourceParserConstants.WORD_E),
			new SourceTokenId("NUMBER", "number", SourceParserConstants.NUMBER),
			new SourceTokenId("STRING", "string", SourceParserConstants.STRING),
			new SourceTokenId("CHAR", "string", SourceParserConstants.CHAR),
			new SourceTokenId("SYMBOL", "symbol", SourceParserConstants.SYMBOL),
		});
		idToToken = new HashMap<Integer, SourceTokenId>();
		for (SourceTokenId token : tokens) {
			idToToken.put(token.ordinal(), token);
		}
	}

	static synchronized SourceTokenId getToken(int id) {
		if (idToToken == null) {
			init();
		}
		return idToToken.get(id);
	}

	@Override
	protected synchronized Collection<SourceTokenId> createTokenIds() {
		if (tokens == null) {
			init();
		}
		return tokens;
	}

	@Override
	protected synchronized Lexer<SourceTokenId> createLexer(LexerRestartInfo<SourceTokenId> info) {
		return new SourceLexer(info);
	}

	@Override
	protected String mimeType() {
		return "text/x-srclang";
	}
}
