package pr1.helper.core;

import java.util.regex.Pattern;

public enum MatchPattern {
    INTEGER("-?\\d+"),
    DOUBLE("[-+]?(\\d*\\.\\d+|\\d+\\.\\d*)([eE][-+]?\\d+)?"),
    NUMBER("[-+]?\\d*\\.?\\d+");

    private final String regex;
    private final Pattern pattern;

    MatchPattern(String regex) {
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
