package com.iconmaster.srcplugin.lexer;

import com.iconmaster.srcplugin.lexerjj.JavaCharStream;
import com.iconmaster.srcplugin.lexerjj.SourceParserTokenManager;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

class SourceLexer implements Lexer<SourceTokenId> {

    private LexerRestartInfo<SourceTokenId> info;
    private SourceParserTokenManager javaParserTokenManager;


    SourceLexer (LexerRestartInfo<SourceTokenId> info) {
        this.info = info;
        JavaCharStream stream = new JavaCharStream(info.input());
        javaParserTokenManager = new SourceParserTokenManager (stream);
    }

    public org.netbeans.api.lexer.Token<SourceTokenId> nextToken() {
        com.iconmaster.srcplugin.lexerjj.Token token = javaParserTokenManager.getNextToken();
        if (info.input ().readLength () < 1) return null;
        return info.tokenFactory ().createToken (SourceLanguageHierarchy.getToken (token.kind));
    }

    public Object state () {
        return null;
    }

    public void release () {
    }
}