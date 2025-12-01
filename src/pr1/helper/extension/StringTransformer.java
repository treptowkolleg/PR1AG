package pr1.helper.extension;

import pr1.helper.core.Delimiter;

import java.io.Serializable;

public class StringTransformer {

    public static String slugify(Object obj, Class<?
            extends ReplacementRuleInterface> languageInterface) {
        checkIfIsSerializable(obj);
        String s = convert(Delimiter.WHITESPACE.getRegex(), "_",
                obj.toString());

        return applyReplacements(s, languageInterface, false);
    }

    public static String humanize(Object obj, Class<?
            extends ReplacementRuleInterface> languageInterface) {
        checkIfIsSerializable(obj);
        String s = normalize(obj.toString());

        return applyReplacements(s, languageInterface, true);
    }

    public static String normalize(String s) {
        return convert("_", " ", s);
    }

    private static String applyReplacements(String s, Class<?
            extends ReplacementRuleInterface> languageInterface,
                                            boolean reverse) {
        for (ReplacementRuleInterface l :
                languageInterface.getEnumConstants()) {
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
