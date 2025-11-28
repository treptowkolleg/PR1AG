package pr1.helper.extension;

import pr1.helper.core.Delimiter;

import java.io.Serializable;

public class Formatter {

    public static String slugify(Object obj) {
        checkIfIsSerializable(obj);
        return convert(Delimiter.WHITESPACE.getRegex(), "_", obj.toString())
                .replace("ä", "&auml")
                .replace("ö", "&ouml")
                .replace("ü", "&uuml")
                .replace("Ä", "&Auml")
                .replace("Ö", "&Ouml")
                .replace("Ü", "&Uuml")
                .replace("ß", "&szlig");
    }

    public static String humanize(Object obj) {
        checkIfIsSerializable(obj);
        return convert("_", " ", obj.toString())
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

    private static void checkIfIsSerializable(Object obj) {
        if (!(obj instanceof Serializable)) {
            throw new IllegalArgumentException("Object must implement " +
                    "Serializable interface.");
        }
    }
}
