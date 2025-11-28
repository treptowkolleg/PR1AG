package pr1.helper.extension;

import pr1.helper.core.Delimiter;

public class Formatter {

    public static String slugify(Object string) {
        return convert(Delimiter.WHITESPACE.getRegex(), "_", string.toString())
                .replace("ä", "&auml")
                .replace("ö", "&ouml")
                .replace("ü", "&uuml")
                .replace("Ä", "&Auml")
                .replace("Ö", "&Ouml")
                .replace("Ü", "&Uuml")
                .replace("ß", "&szlig");
    }

    public static String humanize(Object string) {
        return convert("_", " ", string.toString())
                .replace("&auml", "ä")
                .replace("&ouml", "ö")
                .replace("&uuml", "ü")
                .replace("&Auml", "Ä")
                .replace("&Ouml", "Ö")
                .replace("&Uuml", "Ü")
                .replace("&szlig", "ß");
    }

    private static String convert(String needle, String replacement,
                                  String haystack) {
        return haystack.replaceAll(needle, replacement).trim();
    }
}
