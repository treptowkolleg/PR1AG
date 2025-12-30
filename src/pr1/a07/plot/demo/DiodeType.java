package pr1.a07.plot.demo;

public enum DiodeType {
    NONE,
    GERMANIUM,
    GERMANIUM_OR_SILICON,
    SILICON,
    RED_LED,
    YELLOW_LED,
    YELLOW_OR_GREEN_LED,
    GREEN_LED,
    BLUE_LED,
    BLUE_OR_WHITE_LED,
    WHITE_LED;

    private static final double THRESH_NONE               = 0.1;
    private static final double THRESH_GE                 = 0.5;
    private static final double THRESH_GE_OR_SI           = 0.54;
    private static final double THRESH_SI                 = 1.0;
    private static final double THRESH_RED_LED            = 1.8;
    private static final double THRESH_YELLOW_LED         = 1.85;
    private static final double THRESH_YELLOW_OR_GREEN    = 1.89;
    private static final double THRESH_GREEN_LED          = 2.0;
    private static final double THRESH_BLUE_LED           = 2.65;
    private static final double THRESH_BLUE_OR_WHITE      = 2.67;

    public static DiodeType classifyDiode(double u) {
        if (u < THRESH_NONE)                return DiodeType.NONE;
        if (u < THRESH_GE)                  return DiodeType.GERMANIUM;
        if (u < THRESH_GE_OR_SI)            return DiodeType.GERMANIUM_OR_SILICON;
        if (u < THRESH_SI)                  return DiodeType.SILICON;
        if (u < THRESH_RED_LED)             return DiodeType.RED_LED;
        if (u < THRESH_YELLOW_LED)          return DiodeType.YELLOW_LED;
        if (u < THRESH_YELLOW_OR_GREEN)     return DiodeType.YELLOW_OR_GREEN_LED;
        if (u < THRESH_GREEN_LED)           return DiodeType.GREEN_LED;
        if (u < THRESH_BLUE_LED)            return DiodeType.BLUE_LED;
        if (u < THRESH_BLUE_OR_WHITE)       return DiodeType.BLUE_OR_WHITE_LED;
        return DiodeType.WHITE_LED;
    }
}
