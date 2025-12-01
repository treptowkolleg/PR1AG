package pr1.helper.extension;

public enum WesternReplacementRule implements ReplacementRuleInterface {
    AE_C            ("Ä", "&Auml"),
    AE_L            ("ä", "&auml"),
    OE_C            ("Ö", "&Ouml"),
    OE_L            ("ö", "&ouml"),
    UE_C            ("Ü", "&Uuml"),
    UE_L            ("ü", "&uuml"),
    A_Akut_C        ("Á", "&Aa"),
    A_Akut_L        ("á", "&aa"),
    E_Akut_C        ("É", "&Ea"),
    E_Akut_L        ("é", "&ea"),
    I_Akut_C        ("Í", "&Ia"),
    I_Akut_L        ("í", "&ia"),
    O_Akut_C        ("Ó", "&Oa"),
    O_Akut_L        ("ó", "&oa"),
    U_Akut_C        ("Ú", "&Ua"),
    U_Akut_L        ("ú", "&ua"),
    L_Akut_C        ("Ĺ", "&La"),
    L_Akut_L        ("ĺ", "&la"),
    R_Akut_C        ("Ŕ", "&Ra"),
    R_Akut_L        ("ŕ", "&ra"),
    Y_Akut_C        ("Ý", "&Ya"),
    Y_Akut_L        ("ý", "&ya"),
    E_Gravis_C      ("È", "&Eg"),
    E_Gravis_L      ("è", "&eg"),
    A_Zirkumflex_C  ("Â", "&Az"),
    A_Zirkumflex_L  ("â", "&az"),
    E_Zirkumflex_C  ("Ê", "&Ez"),
    E_Zirkumflex_L  ("ê", "&ez"),
    I_Zirkumflex_C  ("Î", "&Iz"),
    I_Zirkumflex_L  ("î", "&iz"),
    O_Zirkumflex_C  ("Ô", "&Oz"),
    O_Zirkumflex_L  ("ô", "&oz"),
    U_Zirkumflex_C  ("Û", "&Uz"),
    U_Zirkumflex_L  ("û", "&uz"),
    C_Hacek_C       ("Č", "&Ch"),
    C_Hacek_L       ("č", "&ch"),
    D_Hacek_C       ("Ď", "&Dh"),
    D_Hacek_L       ("ď", "&dh"),
    L_Hacek_C       ("Ľ", "&Lh"),
    L_Hacek_L       ("ľ", "&lh"),
    N_Hacek_C       ("Ň", "&Nh"),
    N_Hacek_L       ("ň", "&nh"),
    S_Hacek_C       ("Š", "&Sh"),
    S_Hacek_L       ("š", "&sh"),
    T_Hacek_C       ("Ť", "&Th"),
    T_Hacek_L       ("ť", "&th"),
    Z_Hacek_C       ("Ž", "&Zh"),
    Z_Hacek_L       ("ž", "&zh"),
    R_Hacek_C       ("Ř", "&Rh"),
    R_Hacek_L       ("ř", "&rh"),
    E_Trema         ("ë", "&et"),
    I_Trema         ("ï", "&it"),
    Y_Trema_C       ("Ÿ", "&Yt"),
    Y_Trema_L       ("ÿ", "&yt"),
    A_Ogonek_C      ("Ą", "&Ao"),
    A_Ogonek_L      ("ą", "&ao"),
    E_Ogonek_C      ("Ę", "&Eo"),
    E_Ogonek_L      ("ę", "&eo"),
    C_Cedille_C     ("Ç", "&Cc"),
    C_Cedille_L     ("ç", "&cc"),
    A_Tilde_C       ("Ã", "&Atl"),
    A_Tilde_L       ("ã", "&atl"),
    O_Tilde_C       ("Õ", "&Otl"),
    O_Tilde_L       ("õ", "&otl"),
    N_Tilde_C       ("Ñ", "&Ntl"),
    N_Tilde_L       ("ñ", "&ntl"),
    AE_Ligatur_C    ("Æ", "&AElig"),
    AE_Ligatur_L    ("æ", "&aelig"),
    SZ_Ligatur_C    ("ẞ", "&Szlig"),
    SZ_Ligatur_L    ("ß", "&szlig"),
    O_Slash_C       ("Ø", "&Osl"),
    O_Slash_L       ("ø", "&osl"),
    A_Ring_C        ("Å", "&Arn"),
    A_Ring_L        ("å", "&arn");

    private final String plainLetter;
    private final String encodedLetter;

    WesternReplacementRule(String plainLetter, String encodedLetter) {
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
