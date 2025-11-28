package pr1.helper.extension;

import pr1.helper.core.Delimiter;

import java.io.Serializable;

public class Formatter {

    public static String slugify(Object obj, Class<?
            extends LanguageInterface> languageInterface) {
        checkIfIsSerializable(obj);
        String s = convert(Delimiter.WHITESPACE.getRegex(), "_",
                obj.toString());

        return applyReplacements(s, languageInterface, false);
    }

    public static String humanize(Object obj, Class<?
            extends LanguageInterface> languageInterface) {
        checkIfIsSerializable(obj);
        String s = convert("_", " ",
                obj.toString());

        return applyReplacements(s, languageInterface, true);
    }

    private static String applyReplacements(String s, Class<?
            extends LanguageInterface> languageInterface, boolean reverse) {
        for (LanguageInterface l : languageInterface.getEnumConstants()) {
            if (reverse) {
                s = s.replace(l.encoded(), l.plain());
            } else {
                s = s.replace(l.plain(), l.encoded());
            }
        }
        return s;
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
