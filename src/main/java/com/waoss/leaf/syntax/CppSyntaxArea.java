/*
 *  Copyright (c) Waoss
 *
 *  Mail me at rahul29112002@gmail.com for any queries :)
 *
 *  This is free software licensed under the GNU General Public License.This license allows one to modify it on their will and also embed it or distribute it along with their own software.
 *  It is distributed in the hope that it shall be useful to whomsoever receives it,but does not provide ANY warranty or liability,not even the gurantee that the software will work in your certain usage.
 *  You receive a copy of the GNU General Public License version 3.0 when you download this software.See LICENSE.MD for more details.
 */

package com.waoss.leaf.syntax;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CppSyntaxArea extends SyntaxArea {
    private static final String[] KEYWORDS = new String[]{
            "abstract", "asm", "assert", "bool", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extern", "explicit", "final", "false", "finally", "float",
            "for", "friend", "goto", "if", "instanceof", "inline", "int", "long", "mutable",
            "new", "namespace", "operator", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "true", "synchronized", "signed", "static", "sizeof", "struct", "this", "throw", "throws",
            "transient", "template", "typename", "typeid", "typedef", "try", "register", "unsigned", "using",
            "void", "virtual", "volatile", "while",
            "include", "define", "ifdef",
            "ifndef", "undef", "error"
    };

    private static final String[] OPERATORS = new String[]{
            "\\+", "-", "\\*", "/", "=", "%", "&", "\\+=", "-=", "\\*=", "/=", "==", "%=", "&=", "\\|=", "^=", ">>", "<<",
            "\\+ ", "- ", "\\* ", "/ ", "= ", "% ", "& ", "\\+= ", "-= ", "\\*= ", "/= ", "== ", "%= ", "&= ", "\\|= ", "^= ", ">> ", "<< ",
            " \\+", " -", " \\*", " /", " =", " %", " &", " \\+=", " -=", " \\*=", " /=", " ==", " %=", " &=", " \\|=", " ^=", " >>", " <<",
            " \\+ ", " - ", " \\* ", " / ", " = ", " % ", " & ", " \\+= ", " -= ", " \\*= ", " /= ", " == ", " %= ", " &= ", " \\|= ", " ^= ", " >> ", " << "
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String OPERATOR_PATTERN = "\\b(" + String.join("|", OPERATORS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;|\\:\\:";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<OPERATOR>" + OPERATOR_PATTERN + ")"
    );

    @Override
    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKeywordPosition = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            matcher.group("OPERATOR") != null ? "operator" :
                                                                                    "null";
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKeywordPosition);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKeywordPosition = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKeywordPosition);
        return spansBuilder.create();
    }

    @Override
    public String getStylesheet() {
        return "/com/waoss/leaf/syntax/default.css";
    }
}
