package pr1.helper.extension;

public enum GermanLanguage implements LanguageInterface {
    AE_C("Ä", "&Auml"),
    AE_L("ä", "&auml"),
    OE_C("Ö", "&Ouml"),
    OE_L("ö", "&ouml"),
    UE_C("Ü", "&Uuml"),
    UE_L("ü", "&uuml"),
    SZ_L("ß", "&szlig");

    private final String plainLetter;
    private final String encodedLetter;

    GermanLanguage(String plainLetter, String encodedLetter) {
        this.plainLetter = plainLetter;
        this.encodedLetter = encodedLetter;
    }

    public String plain() {
        return plainLetter;
    }

    public String encoded() {
        return encodedLetter;
    }
}
