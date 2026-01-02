package treptowkolleg.plot;

/**
 * Defines standardized positions for aligning the coordinate grid within a plot panel.
 * These constants are used to control where the origin (0,0) or reference point of the grid
 * appears relative to the drawing area—e.g., at the center for symmetric mathematical plots,
 * or in the south-east corner for time-series data that grows left-to-right and top-to-bottom.
 */
public enum GridPosition {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
    CENTER,
}
