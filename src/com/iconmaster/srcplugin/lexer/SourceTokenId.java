package com.iconmaster.srcplugin.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

public class SourceTokenId implements TokenId {

	private final String name;
	private final String primaryCategory;
	private final int id;

	SourceTokenId(String name, String primaryCategory, int id) {
		this.name = name;
		this.primaryCategory = primaryCategory;
		this.id = id;
	}

	@Override
	public String primaryCategory() {
		return primaryCategory;
	}

	@Override
	public int ordinal() {
		return id;
	}

	@Override
	public String name() {
		return name;
	}
	
	private static final Language<SourceTokenId> language = new SourceLanguageHierarchy ().language ();

    public static final Language<SourceTokenId> getLanguage () {
        return language;
    }
}
