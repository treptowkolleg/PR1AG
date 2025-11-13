package pr1.helper.core;

import java.util.regex.Pattern;

public enum Delimiter {
    WHITESPACE("\\s+"),
    COMMA(","),
    WHITESPACE_OR_COMMA("[\\s,]+"),
    SEMICOLON(";"),
    PIPE("\\|"),
    TAB("\\t"),
    COLON(":"),
    DASH("-"),
    SLASH("/"),
    DOT("\\."),
    QUOTE("\""),
    LINEBREAK("\\r?\\n");

    private final String regex;
    private final Pattern pattern;

    Delimiter(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getRegex() {
        return regex;
    }

}
