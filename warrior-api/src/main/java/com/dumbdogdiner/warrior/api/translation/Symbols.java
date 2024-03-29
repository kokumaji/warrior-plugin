package com.dumbdogdiner.warrior.api.translation;

import com.dumbdogdiner.stickyapi.common.util.NumberUtil;
import com.dumbdogdiner.warrior.api.reflection.FieldUtil;

@SuppressWarnings("unused")
public class Symbols {

    public static final char HYPHEN = '‐';
    public static final char NON_BREAKING_HYPHEN = '‑';
    public static final char FIGURE_DASH = '‒';
    public static final char EN_DASH = '–';
    public static final char EM_DASH = '—';
    public static final char HORIZONTAL_BAR = '―';
    public static final char DOUBLE_VERTICAL_LINE = '‖';
    public static final char DOUBLE_LOW_LINE = '‗';

    public static final char LEFT_SINGLE_QUOTATION_MARK = '‘';
    public static final char RIGHT_SINGLE_QUOTATION_MARK = '’';
    public static final char SINGLE_LOW_9_QUOTATION_MARK = '‚';
    public static final char SINGLE_HIGH_REVERSED_9_QUOTATION_MARK = '‛';
    public static final char LEFT_DOUBLE_QUOTATION_MARK = '“';
    public static final char RIGHT_DOUBLE_QUOTATION_MARK = '”';
    public static final char DOUBLE_LOW_9_QUOTATION_MARK = '„';
    public static final char DOUBLE_HIGH_REVERSED_9_QUOTATION_MARK = '‟';

    /**
     * MUSIC RELATED SYMBOLS
     */

    public static final char QUARTER_NOTE = '♩';
    public static final char EIGHTH_NOTE = '♪';
    public static final char BEAMED_EIGHTH_NOTES = '♫';
    public static final char BEAMED_SIXTEENTH_NOTES = '♬';
    public static final char MUSIC_FLAT_SIGN = '♭';
    public static final char MUSIC_NATURAL_SIGN = '♮';
    public static final char MUSIC_SHARP_SIGN = '♯';

    public static final char COPYRIGHT_SIGN = '©';
    public static final char REGISTERED_SIGN = '®';
    public static final char NEW_SHEQEL_SIGN = '₪';
    public static final char TRADE_MARK_SIGN = '™';

    /**
     * GREEK ALPHABET SYMBOLS
     */

    public static final char GREEK_CAPITAL_LETTER_ALPHA = 'Α';
    public static final char GREEK_CAPITAL_LETTER_BETA = 'Β';
    public static final char GREEK_CAPITAL_LETTER_GAMMA = 'Γ';
    public static final char GREEK_CAPITAL_LETTER_DELTA = 'Δ';
    public static final char GREEK_CAPITAL_LETTER_EPSILON = 'Ε';
    public static final char GREEK_CAPITAL_LETTER_ZETA = 'Ζ';
    public static final char GREEK_CAPITAL_LETTER_ETA = 'Η';
    public static final char GREEK_CAPITAL_LETTER_THETA = 'Θ';
    public static final char GREEK_CAPITAL_LETTER_IOTA = 'Ι';
    public static final char GREEK_CAPITAL_LETTER_KAPPA = 'Κ';
    public static final char GREEK_CAPITAL_LETTER_LAMDA = 'Λ';
    public static final char GREEK_CAPITAL_LETTER_MU = 'Μ';
    public static final char GREEK_CAPITAL_LETTER_NU = 'Ν';
    public static final char GREEK_CAPITAL_LETTER_XI = 'Ξ';
    public static final char GREEK_CAPITAL_LETTER_OMICRON = 'Ο';
    public static final char GREEK_CAPITAL_LETTER_PI = 'Π';
    public static final char GREEK_CAPITAL_LETTER_RHO = 'Ρ';
    public static final char GREEK_CAPITAL_LETTER_SIGMA = 'Σ';
    public static final char GREEK_CAPITAL_LETTER_TAU = 'Τ';
    public static final char GREEK_CAPITAL_LETTER_UPSILON = 'Υ';
    public static final char GREEK_CAPITAL_LETTER_PHI = 'Φ';
    public static final char GREEK_CAPITAL_LETTER_CHI = 'Χ';
    public static final char GREEK_CAPITAL_LETTER_PSI = 'Ψ';
    public static final char GREEK_CAPITAL_LETTER_OMEGA = 'Ω';
    public static final char GREEK_SMALL_LETTER_ALPHA = 'α';
    public static final char GREEK_SMALL_LETTER_BETA = 'β';
    public static final char GREEK_SMALL_LETTER_GAMMA = 'γ';
    public static final char GREEK_SMALL_LETTER_DELTA = 'δ';
    public static final char GREEK_SMALL_LETTER_EPSILON = 'ε';
    public static final char GREEK_SMALL_LETTER_ZETA = 'ζ';
    public static final char GREEK_SMALL_LETTER_ETA = 'η';
    public static final char GREEK_SMALL_LETTER_THETA = 'θ';
    public static final char GREEK_SMALL_LETTER_IOTA = 'ι';
    public static final char GREEK_SMALL_LETTER_KAPPA = 'κ';
    public static final char GREEK_SMALL_LETTER_LAMDA = 'λ';
    public static final char GREEK_SMALL_LETTER_MU = 'μ';
    public static final char GREEK_SMALL_LETTER_NU = 'ν';
    public static final char GREEK_SMALL_LETTER_XI = 'ξ';
    public static final char GREEK_SMALL_LETTER_OMICRON = 'ο';
    public static final char GREEK_SMALL_LETTER_PI = 'π';
    public static final char GREEK_SMALL_LETTER_RHO = 'ρ';
    public static final char GREEK_SMALL_LETTER_FINAL_SIGMA = 'ς';
    public static final char GREEK_SMALL_LETTER_SIGMA = 'σ';
    public static final char GREEK_SMALL_LETTER_TAU = 'τ';
    public static final char GREEK_SMALL_LETTER_UPSILON = 'υ';
    public static final char GREEK_SMALL_LETTER_PHI = 'φ';
    public static final char GREEK_SMALL_LETTER_CHI = 'χ';
    public static final char GREEK_SMALL_LETTER_PSI = 'ψ';
    public static final char GREEK_SMALL_LETTER_OMEGA = 'ω';
    public static final char GREEK_SMALL_LETTER_KOPPA = 'ϟ';

    /**
     * OTHER LANGUAGES
     */

    public static final char LATIN_SMALL_LETTER_UPSILON = 'ʊ';
    public static final char ARABIC_START_OF_RUB_EL_HIZB = '۞';
    public static final char ARABIC_PLACE_OF_SAJDAH = '۩';
    public static final char GEORGIAN_LETTER_GHAN = 'ღ';

    /**
     * ROMAN NUMERALS
     */

    public static final char ROMAN_NUMERAL_ONE = 'Ⅰ';
    public static final char ROMAN_NUMERAL_TWO = 'Ⅱ';
    public static final char ROMAN_NUMERAL_THREE = 'Ⅲ';
    public static final char ROMAN_NUMERAL_FOUR = 'Ⅳ';
    public static final char ROMAN_NUMERAL_FIVE = 'Ⅴ';
    public static final char ROMAN_NUMERAL_SIX = 'Ⅵ';
    public static final char ROMAN_NUMERAL_SEVEN = 'Ⅶ';
    public static final char ROMAN_NUMERAL_EIGHT = 'Ⅷ';
    public static final char ROMAN_NUMERAL_NINE = 'Ⅸ';
    public static final char ROMAN_NUMERAL_TEN = 'Ⅹ';
    public static final char ROMAN_NUMERAL_ELEVEN = 'Ⅺ';
    public static final char ROMAN_NUMERAL_TWELVE = 'Ⅻ';
    public static final char ROMAN_NUMERAL_FIFTY = 'Ⅼ';
    public static final char ROMAN_NUMERAL_ONE_HUNDRED = 'Ⅽ';
    public static final char ROMAN_NUMERAL_FIVE_HUNDRED = 'Ⅾ';
    public static final char ROMAN_NUMERAL_ONE_THOUSAND = 'Ⅿ';
    public static final char SMALL_ROMAN_NUMERAL_ONE = 'ⅰ';
    public static final char SMALL_ROMAN_NUMERAL_TWO = 'ⅱ';
    public static final char SMALL_ROMAN_NUMERAL_THREE = 'ⅲ';
    public static final char SMALL_ROMAN_NUMERAL_FOUR = 'ⅳ';
    public static final char SMALL_ROMAN_NUMERAL_FIVE = 'ⅴ';
    public static final char SMALL_ROMAN_NUMERAL_SIX = 'ⅵ';
    public static final char SMALL_ROMAN_NUMERAL_SEVEN = 'ⅶ';
    public static final char SMALL_ROMAN_NUMERAL_EIGHT = 'ⅷ';
    public static final char SMALL_ROMAN_NUMERAL_NINE = 'ⅸ';
    public static final char SMALL_ROMAN_NUMERAL_TEN = 'ⅹ';
    public static final char SMALL_ROMAN_NUMERAL_ELEVEN = 'ⅺ';
    public static final char SMALL_ROMAN_NUMERAL_TWELVE = 'ⅻ';
    public static final char SMALL_ROMAN_NUMERAL_FIFTY = 'ⅼ';
    public static final char SMALL_ROMAN_NUMERAL_ONE_HUNDRED = 'ⅽ';
    public static final char SMALL_ROMAN_NUMERAL_FIVE_HUNDRED = 'ⅾ';
    public static final char SMALL_ROMAN_NUMERAL_ONE_THOUSAND = 'ⅿ';

    /**
     * ARROW SYMBOLS
     */

    public static final char LEFTWARDS_ARROW = '←';
    public static final char UPWARDS_ARROW = '↑';
    public static final char RIGHTWARDS_ARROW = '→';
    public static final char DOWNWARDS_ARROW = '↓';
    public static final char LEFT_RIGHT_ARROW = '↔';
    public static final char UP_DOWN_ARROW = '↕';
    public static final char NORTH_WEST_ARROW = '↖';
    public static final char NORTH_EAST_ARROW = '↗';
    public static final char SOUTH_EAST_ARROW = '↘';
    public static final char SOUTH_WEST_ARROW = '↙';
    public static final char LEFTWARDS_ARROW_WITH_STROKE = '↚';
    public static final char RIGHTWARDS_ARROW_WITH_STROKE = '↛';
    public static final char LEFTWARDS_WAVE_ARROW = '↜';
    public static final char RIGHTWARDS_WAVE_ARROW = '↝';
    public static final char LEFTWARDS_TWO_HEADED_ARROW = '↞';
    public static final char UPWARDS_TWO_HEADED_ARROW = '↟';
    public static final char RIGHTWARDS_TWO_HEADED_ARROW = '↠';
    public static final char DOWNWARDS_TWO_HEADED_ARROW = '↡';
    public static final char LEFTWARDS_ARROW_WITH_TAIL = '↢';
    public static final char RIGHTWARDS_ARROW_WITH_TAIL = '↣';
    public static final char LEFTWARDS_ARROW_FROM_BAR = '↤';
    public static final char UPWARDS_ARROW_FROM_BAR = '↥';
    public static final char RIGHTWARDS_ARROW_FROM_BAR = '↦';
    public static final char DOWNWARDS_ARROW_FROM_BAR = '↧';
    public static final char UP_DOWN_ARROW_WITH_BASE = '↨';
    public static final char LEFTWARDS_ARROW_WITH_HOOK = '↩';
    public static final char RIGHTWARDS_ARROW_WITH_HOOK = '↪';
    public static final char LEFTWARDS_ARROW_WITH_LOOP = '↫';
    public static final char RIGHTWARDS_ARROW_WITH_LOOP = '↬';
    public static final char LEFT_RIGHT_WAVE_ARROW = '↭';
    public static final char LEFT_RIGHT_ARROW_WITH_STROKE = '↮';
    public static final char DOWNWARDS_ZIGZAG_ARROW = '↯';
    public static final char UPWARDS_ARROW_WITH_TIP_LEFTWARDS = '↰';
    public static final char UPWARDS_ARROW_WITH_TIP_RIGHTWARDS = '↱';
    public static final char DOWNWARDS_ARROW_WITH_TIP_LEFTWARDS = '↲';
    public static final char DOWNWARDS_ARROW_WITH_TIP_RIGHTWARDS = '↳';
    public static final char RIGHTWARDS_ARROW_WITH_CORNER_DOWNWARDS = '↴';
    public static final char DOWNWARDS_ARROW_WITH_CORNER_LEFTWARDS = '↵';
    public static final char ANTICLOCKWISE_TOP_SEMICIRCLE_ARROW = '↶';
    public static final char CLOCKWISE_TOP_SEMICIRCLE_ARROW = '↷';
    public static final char NORTH_WEST_ARROW_TO_LONG_BAR = '↸';
    public static final char LEFTWARDS_ARROW_TO_BAR_OVER_RIGHTWARDS_ARROW_TO_BAR = '↹';
    public static final char ANTICLOCKWISE_OPEN_CIRCLE_ARROW = '↺';
    public static final char CLOCKWISE_OPEN_CIRCLE_ARROW = '↻';
    public static final char LEFTWARDS_HARPOON_WITH_BARB_UPWARDS = '↼';
    public static final char LEFTWARDS_HARPOON_WITH_BARB_DOWNWARDS = '↽';
    public static final char UPWARDS_HARPOON_WITH_BARB_RIGHTWARDS = '↾';
    public static final char UPWARDS_HARPOON_WITH_BARB_LEFTWARDS = '↿';
    public static final char RIGHTWARDS_HARPOON_WITH_BARB_UPWARDS = '⇀';
    public static final char RIGHTWARDS_HARPOON_WITH_BARB_DOWNWARDS = '⇁';
    public static final char DOWNWARDS_HARPOON_WITH_BARB_RIGHTWARDS = '⇂';
    public static final char DOWNWARDS_HARPOON_WITH_BARB_LEFTWARDS = '⇃';
    public static final char RIGHTWARDS_ARROW_OVER_LEFTWARDS_ARROW = '⇄';
    public static final char UPWARDS_ARROW_LEFTWARDS_OF_DOWNWARDS_ARROW = '⇅';
    public static final char LEFTWARDS_ARROW_OVER_RIGHTWARDS_ARROW = '⇆';
    public static final char LEFTWARDS_PAIRED_ARROWS = '⇇';
    public static final char UPWARDS_PAIRED_ARROWS = '⇈';
    public static final char RIGHTWARDS_PAIRED_ARROWS = '⇉';
    public static final char DOWNWARDS_PAIRED_ARROWS = '⇊';
    public static final char LEFTWARDS_HARPOON_OVER_RIGHTWARDS_HARPOON = '⇋';
    public static final char RIGHTWARDS_HARPOON_OVER_LEFTWARDS_HARPOON = '⇌';
    public static final char LEFTWARDS_DOUBLE_ARROW_WITH_STROKE = '⇍';
    public static final char LEFT_RIGHT_DOUBLE_ARROW_WITH_STROKE = '⇎';
    public static final char RIGHTWARDS_DOUBLE_ARROW_WITH_STROKE = '⇏';
    public static final char LEFTWARDS_DOUBLE_ARROW = '⇐';
    public static final char UPWARDS_DOUBLE_ARROW = '⇑';
    public static final char RIGHTWARDS_DOUBLE_ARROW = '⇒';
    public static final char DOWNWARDS_DOUBLE_ARROW = '⇓';
    public static final char LEFT_RIGHT_DOUBLE_ARROW = '⇔';
    public static final char UP_DOWN_DOUBLE_ARROW = '⇕';
    public static final char NORTH_WEST_DOUBLE_ARROW = '⇖';
    public static final char NORTH_EAST_DOUBLE_ARROW = '⇗';
    public static final char SOUTH_EAST_DOUBLE_ARROW = '⇘';
    public static final char SOUTH_WEST_DOUBLE_ARROW = '⇙';
    public static final char LEFTWARDS_TRIPLE_ARROW = '⇚';
    public static final char RIGHTWARDS_TRIPLE_ARROW = '⇛';
    public static final char LEFTWARDS_SQUIGGLE_ARROW = '⇜';
    public static final char RIGHTWARDS_SQUIGGLE_ARROW = '⇝';
    public static final char UPWARDS_ARROW_WITH_DOUBLE_STROKE = '⇞';
    public static final char DOWNWARDS_ARROW_WITH_DOUBLE_STROKE = '⇟';
    public static final char LEFTWARDS_DASHED_ARROW = '⇠';
    public static final char UPWARDS_DASHED_ARROW = '⇡';
    public static final char RIGHTWARDS_DASHED_ARROW = '⇢';
    public static final char DOWNWARDS_DASHED_ARROW = '⇣';
    public static final char LEFTWARDS_ARROW_TO_BAR = '⇤';
    public static final char RIGHTWARDS_ARROW_TO_BAR = '⇥';
    public static final char LEFTWARDS_WHITE_ARROW = '⇦';
    public static final char UPWARDS_WHITE_ARROW = '⇧';
    public static final char RIGHTWARDS_WHITE_ARROW = '⇨';
    public static final char DOWNWARDS_WHITE_ARROW = '⇩';
    public static final char UPWARDS_WHITE_ARROW_FROM_BAR = '⇪';

    public static final char UPWARDS_WHITE_ARROW_ON_PEDESTAL = '⇫';
    public static final char UPWARDS_WHITE_ARROW_ON_PEDESTAL_WITH_HORIZONTAL_BAR = '⇬';
    public static final char UPWARDS_WHITE_ARROW_ON_PEDESTAL_WITH_VERTICAL_BAR = '⇭';
    public static final char UPWARDS_WHITE_DOUBLE_ARROW = '⇮';
    public static final char UPWARDS_WHITE_DOUBLE_ARROW_ON_PEDESTAL = '⇯';
    public static final char RIGHTWARDS_WHITE_ARROW_FROM_WALL = '⇰';
    public static final char NORTH_WEST_ARROW_TO_CORNER = '⇱';
    public static final char SOUTH_EAST_ARROW_TO_CORNER = '⇲';
    public static final char UP_DOWN_WHITE_ARROW = '⇳';
    public static final char RIGHT_ARROW_WITH_SMALL_CIRCLE = '⇴';
    public static final char DOWNWARDS_ARROW_LEFTWARDS_OF_UPWARDS_ARROW = '⇵';
    public static final char THREE_RIGHTWARDS_ARROWS = '⇶';
    public static final char LEFTWARDS_ARROW_WITH_VERTICAL_STROKE = '⇷';
    public static final char RIGHTWARDS_ARROW_WITH_VERTICAL_STROKE = '⇸';
    public static final char LEFT_RIGHT_ARROW_WITH_VERTICAL_STROKE = '⇹';
    public static final char LEFTWARDS_ARROW_WITH_DOUBLE_VERTICAL_STROKE = '⇺';
    public static final char RIGHTWARDS_ARROW_WITH_DOUBLE_VERTICAL_STROKE = '⇻';
    public static final char LEFT_RIGHT_ARROW_WITH_DOUBLE_VERTICAL_STROKE = '⇼';
    public static final char LEFTWARDS_OPEN_HEADED_ARROW = '⇽';
    public static final char RIGHTWARDS_OPEN_HEADED_ARROW = '⇾';
    public static final char LEFT_RIGHT_OPEN_HEADED_ARROW = '⇿';

    public static final char INFINITY = '∞';
    public static final char CIRCLED_TIMES = '⊗';
    public static final char RIGHT_TRIANGLE = '⊿';

    /**
     * CIRCLED LATIN LETTERS
     */

    public static final char CIRCLED_LATIN_CAPITAL_LETTER_A = 'Ⓐ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_B = 'Ⓑ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_C = 'Ⓒ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_D = 'Ⓓ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_E = 'Ⓔ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_F = 'Ⓕ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_G = 'Ⓖ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_H = 'Ⓗ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_I = 'Ⓘ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_J = 'Ⓙ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_K = 'Ⓚ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_L = 'Ⓛ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_M = 'Ⓜ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_N = 'Ⓝ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_O = 'Ⓞ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_P = 'Ⓟ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_Q = 'Ⓠ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_R = 'Ⓡ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_S = 'Ⓢ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_T = 'Ⓣ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_U = 'Ⓤ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_V = 'Ⓥ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_W = 'Ⓦ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_X = 'Ⓧ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_Y = 'Ⓨ';
    public static final char CIRCLED_LATIN_CAPITAL_LETTER_Z = 'Ⓩ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_A = 'ⓐ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_B = 'ⓑ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_C = 'ⓒ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_D = 'ⓓ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_E = 'ⓔ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_F = 'ⓕ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_G = 'ⓖ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_H = 'ⓗ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_I = 'ⓘ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_J = 'ⓙ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_K = 'ⓚ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_L = 'ⓛ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_M = 'ⓜ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_N = 'ⓝ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_O = 'ⓞ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_P = 'ⓟ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_Q = 'ⓠ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_R = 'ⓡ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_S = 'ⓢ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_T = 'ⓣ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_U = 'ⓤ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_V = 'ⓥ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_W = 'ⓦ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_X = 'ⓧ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_Y = 'ⓨ';
    public static final char CIRCLED_LATIN_SMALL_LETTER_Z = 'ⓩ';

    /**
     * BOX DRAWING SYMBOLS
     */

    public static final char BOX_DRAWINGS_LIGHT_HORIZONTAL = '─';
    public static final char BOX_DRAWINGS_LIGHT_VERTICAL = '│';
    public static final char BOX_DRAWINGS_LIGHT_DOWN_AND_RIGHT = '┌';
    public static final char BOX_DRAWINGS_LIGHT_DOWN_AND_LEFT = '┐';
    public static final char BOX_DRAWINGS_LIGHT_UP_AND_RIGHT = '└';
    public static final char BOX_DRAWINGS_LIGHT_UP_AND_LEFT = '┘';
    public static final char BOX_DRAWINGS_LIGHT_VERTICAL_AND_RIGHT = '├';
    public static final char BOX_DRAWINGS_LIGHT_VERTICAL_AND_LEFT = '┤';
    public static final char BOX_DRAWINGS_LIGHT_DOWN_AND_HORIZONTAL = '┬';
    public static final char BOX_DRAWINGS_LIGHT_UP_AND_HORIZONTAL = '┴';
    public static final char BOX_DRAWINGS_LIGHT_VERTICAL_AND_HORIZONTAL = '┼';
    public static final char BOX_DRAWINGS_DOUBLE_HORIZONTAL = '═';
    public static final char BOX_DRAWINGS_DOUBLE_VERTICAL = '║';
    public static final char BOX_DRAWINGS_DOWN_SINGLE_AND_RIGHT_DOUBLE = '╒';
    public static final char BOX_DRAWINGS_DOWN_DOUBLE_AND_RIGHT_SINGLE = '╓';
    public static final char BOX_DRAWINGS_DOUBLE_DOWN_AND_RIGHT = '╔';
    public static final char BOX_DRAWINGS_DOWN_SINGLE_AND_LEFT_DOUBLE = '╕';
    public static final char BOX_DRAWINGS_DOWN_DOUBLE_AND_LEFT_SINGLE = '╖';
    public static final char BOX_DRAWINGS_DOUBLE_DOWN_AND_LEFT = '╗';
    public static final char BOX_DRAWINGS_UP_SINGLE_AND_RIGHT_DOUBLE = '╘';
    public static final char BOX_DRAWINGS_UP_DOUBLE_AND_RIGHT_SINGLE = '╙';
    public static final char BOX_DRAWINGS_DOUBLE_UP_AND_RIGHT = '╚';
    public static final char BOX_DRAWINGS_UP_SINGLE_AND_LEFT_DOUBLE = '╛';
    public static final char BOX_DRAWINGS_UP_DOUBLE_AND_LEFT_SINGLE = '╜';
    public static final char BOX_DRAWINGS_DOUBLE_UP_AND_LEFT = '╝';
    public static final char BOX_DRAWINGS_VERTICAL_SINGLE_AND_RIGHT_DOUBLE = '╞';
    public static final char BOX_DRAWINGS_VERTICAL_DOUBLE_AND_RIGHT_SINGLE = '╟';
    public static final char BOX_DRAWINGS_DOUBLE_VERTICAL_AND_RIGHT = '╠';
    public static final char BOX_DRAWINGS_VERTICAL_SINGLE_AND_LEFT_DOUBLE = '╡';
    public static final char BOX_DRAWINGS_VERTICAL_DOUBLE_AND_LEFT_SINGLE = '╢';
    public static final char BOX_DRAWINGS_DOUBLE_VERTICAL_AND_LEFT = '╣';
    public static final char BOX_DRAWINGS_DOWN_SINGLE_AND_HORIZONTAL_DOUBLE = '╤';
    public static final char BOX_DRAWINGS_DOWN_DOUBLE_AND_HORIZONTAL_SINGLE = '╥';
    public static final char BOX_DRAWINGS_DOUBLE_DOWN_AND_HORIZONTAL = '╦';
    public static final char BOX_DRAWINGS_UP_SINGLE_AND_HORIZONTAL_DOUBLE = '╧';
    public static final char BOX_DRAWINGS_UP_DOUBLE_AND_HORIZONTAL_SINGLE = '╨';
    public static final char BOX_DRAWINGS_DOUBLE_UP_AND_HORIZONTAL = '╩';
    public static final char BOX_DRAWINGS_VERTICAL_SINGLE_AND_HORIZONTAL_DOUBLE = '╪';
    public static final char BOX_DRAWINGS_VERTICAL_DOUBLE_AND_HORIZONTAL_SINGLE = '╫';
    public static final char BOX_DRAWINGS_DOUBLE_VERTICAL_AND_HORIZONTAL = '╬';

    /**
     * BLOCK SYMBOLS
     */

    public static final char UPPER_HALF_BLOCK = '▀';
    public static final char LOWER_ONE_EIGHTH_BLOCK = '▁';
    public static final char LOWER_ONE_QUARTER_BLOCK = '▂';
    public static final char LOWER_THREE_EIGHTHS_BLOCK = '▃';
    public static final char LOWER_HALF_BLOCK = '▄';
    public static final char LOWER_FIVE_EIGHTHS_BLOCK = '▅';
    public static final char LOWER_THREE_QUARTERS_BLOCK = '▆';
    public static final char LOWER_SEVEN_EIGHTHS_BLOCK = '▇';
    public static final char FULL_BLOCK = '█';
    public static final char LEFT_SEVEN_EIGHTHS_BLOCK = '▉';
    public static final char LEFT_THREE_QUARTERS_BLOCK = '▊';
    public static final char LEFT_FIVE_EIGHTHS_BLOCK = '▋';
    public static final char LEFT_HALF_BLOCK = '▌';
    public static final char LEFT_THREE_EIGHTHS_BLOCK = '▍';
    public static final char LEFT_ONE_QUARTER_BLOCK = '▎';
    public static final char LEFT_ONE_EIGHTH_BLOCK = '▏';
    public static final char RIGHT_HALF_BLOCK = '▐';
    public static final char LIGHT_SHADE = '░';
    public static final char MEDIUM_SHADE = '▒';
    public static final char DARK_SHADE = '▓';
    public static final char UPPER_ONE_EIGHTH_BLOCK = '▔';
    public static final char RIGHT_ONE_EIGHTH_BLOCK = '▕';
    public static final char QUADRANT_LOWER_LEFT = '▖';
    public static final char QUADRANT_LOWER_RIGHT = '▗';
    public static final char QUADRANT_UPPER_LEFT = '▘';
    public static final char QUADRANT_UPPER_LEFT_AND_LOWER_LEFT_AND_LOWER_RIGHT = '▙';
    public static final char QUADRANT_UPPER_LEFT_AND_LOWER_RIGHT = '▚';
    public static final char QUADRANT_UPPER_LEFT_AND_UPPER_RIGHT_AND_LOWER_LEFT = '▛';
    public static final char QUADRANT_UPPER_LEFT_AND_UPPER_RIGHT_AND_LOWER_RIGHT = '▜';
    public static final char QUADRANT_UPPER_RIGHT = '▝';
    public static final char QUADRANT_UPPER_RIGHT_AND_LOWER_LEFT = '▞';
    public static final char QUADRANT_UPPER_RIGHT_AND_LOWER_LEFT_AND_LOWER_RIGHT = '▟';

    /**
     * MISC GEOMETRY AND SYMBOLS
     */

    public static final char BLACK_SQUARE = '■';
    public static final char WHITE_SQUARE = '□';
    public static final char WHITE_SQUARE_WITH_ROUNDED_CORNERS = '▢';
    public static final char WHITE_SQUARE_CONTAINING_BLACK_SMALL_SQUARE = '▣';
    public static final char SQUARE_WITH_HORIZONTAL_FILL = '▤';
    public static final char SQUARE_WITH_VERTICAL_FILL = '▥';
    public static final char SQUARE_WITH_ORTHOGONAL_CROSSHATCH_FILL = '▦';
    public static final char SQUARE_WITH_UPPER_LEFT_TO_LOWER_RIGHT_FILL = '▧';
    public static final char SQUARE_WITH_UPPER_RIGHT_TO_LOWER_LEFT_FILL = '▨';
    public static final char SQUARE_WITH_DIAGONAL_CROSSHATCH_FILL = '▩';
    public static final char BLACK_SMALL_SQUARE = '▪';
    public static final char WHITE_SMALL_SQUARE = '▫';
    public static final char BLACK_RECTANGLE = '▬';
    public static final char WHITE_RECTANGLE = '▭';
    public static final char BLACK_VERTICAL_RECTANGLE = '▮';
    public static final char WHITE_VERTICAL_RECTANGLE = '▯';
    public static final char BLACK_PARALLELOGRAM = '▰';
    public static final char WHITE_PARALLELOGRAM = '▱';
    public static final char BLACK_UP_POINTING_TRIANGLE = '▲';
    public static final char WHITE_UP_POINTING_TRIANGLE = '△';
    public static final char BLACK_UP_POINTING_SMALL_TRIANGLE = '▴';
    public static final char WHITE_UP_POINTING_SMALL_TRIANGLE = '▵';
    public static final char BLACK_RIGHT_POINTING_TRIANGLE = '▶';
    public static final char WHITE_RIGHT_POINTING_TRIANGLE = '▷';
    public static final char BLACK_RIGHT_POINTING_SMALL_TRIANGLE = '▸';
    public static final char WHITE_RIGHT_POINTING_SMALL_TRIANGLE = '▹';
    public static final char BLACK_RIGHT_POINTING_POINTER = '►';
    public static final char WHITE_RIGHT_POINTING_POINTER = '▻';
    public static final char BLACK_DOWN_POINTING_TRIANGLE = '▼';
    public static final char WHITE_DOWN_POINTING_TRIANGLE = '▽';
    public static final char BLACK_DOWN_POINTING_SMALL_TRIANGLE = '▾';
    public static final char WHITE_DOWN_POINTING_SMALL_TRIANGLE = '▿';
    public static final char BLACK_LEFT_POINTING_TRIANGLE = '◀';
    public static final char WHITE_LEFT_POINTING_TRIANGLE = '◁';
    public static final char BLACK_LEFT_POINTING_SMALL_TRIANGLE = '◂';
    public static final char WHITE_LEFT_POINTING_SMALL_TRIANGLE = '◃';
    public static final char BLACK_LEFT_POINTING_POINTER = '◄';
    public static final char WHITE_LEFT_POINTING_POINTER = '◅';
    public static final char BLACK_DIAMOND = '◆';
    public static final char WHITE_DIAMOND = '◇';
    public static final char WHITE_DIAMOND_CONTAINING_BLACK_SMALL_DIAMOND = '◈';
    public static final char FISHEYE = '◉';
    public static final char LOZENGE = '◊';
    public static final char WHITE_CIRCLE = '○';
    public static final char DOTTED_CIRCLE = '◌';
    public static final char CIRCLE_WITH_VERTICAL_FILL = '◍';
    public static final char BULLSEYE = '◎';
    public static final char BLACK_CIRCLE = '●';
    public static final char CIRCLE_WITH_LEFT_HALF_BLACK = '◐';
    public static final char CIRCLE_WITH_RIGHT_HALF_BLACK = '◑';
    public static final char CIRCLE_WITH_LOWER_HALF_BLACK = '◒';
    public static final char CIRCLE_WITH_UPPER_HALF_BLACK = '◓';
    public static final char CIRCLE_WITH_UPPER_RIGHT_QUADRANT_BLACK = '◔';
    public static final char CIRCLE_WITH_ALL_BUT_UPPER_LEFT_QUADRANT_BLACK = '◕';
    public static final char LEFT_HALF_BLACK_CIRCLE = '◖';
    public static final char RIGHT_HALF_BLACK_CIRCLE = '◗';
    public static final char BLACK_LOWER_RIGHT_TRIANGLE = '◢';
    public static final char BLACK_LOWER_LEFT_TRIANGLE = '◣';
    public static final char BLACK_UPPER_LEFT_TRIANGLE = '◤';
    public static final char BLACK_UPPER_RIGHT_TRIANGLE = '◥';
    public static final char LARGE_CIRCLE = '◯';
    public static final char BLACK_SUN_WITH_RAYS = '☀';
    public static final char CLOUD = '☁';
    public static final char UMBRELLA = '☂';
    public static final char SNOWMAN = '☃';
    public static final char COMET = '☄';
    public static final char BLACK_STAR = '★';
    public static final char WHITE_STAR = '☆';
    public static final char BALLOT_BOX = '☐';
    public static final char BALLOT_BOX_WITH_CHECK = '☑';
    public static final char BALLOT_BOX_WITH_X = '☒';
    public static final char RADIOACTIVE_SIGN = '☢';
    public static final char BIOHAZARD_SIGN = '☣';
    public static final char CADUCEUS = '☤';
    public static final char ANKH = '☥';
    public static final char ORTHODOX_CROSS = '☦';
    public static final char CHI_RHO = '☧';
    public static final char CROSS_OF_LORRAINE = '☨';
    public static final char CROSS_OF_JERUSALEM = '☩';
    public static final char STAR_AND_CRESCENT = '☪';
    public static final char FARSI_SYMBOL = '☫';
    public static final char PEACE_SYMBOL = '☮';
    public static final char YIN_YANG = '☯';
    public static final char WHITE_SMILING_FACE = '☺';
    public static final char BLACK_SMILING_FACE = '☻';
    public static final char WHITE_SUN_WITH_RAYS = '☼';
    public static final char FIRST_QUARTER_MOON = '☽';
    public static final char LAST_QUARTER_MOON = '☾';
    public static final char MERCURY = '☿';
    public static final char FEMALE_SIGN = '♀';
    public static final char MALE_SIGN = '♂';
    public static final char AQUARIUS = '♒';
    public static final char WHITE_CHESS_KING = '♔';
    public static final char WHITE_CHESS_QUEEN = '♕';
    public static final char BLACK_CHESS_KING = '♚';
    public static final char BLACK_CHESS_QUEEN = '♛';
    public static final char BLACK_SPADE_SUIT = '♠';
    public static final char WHITE_HEART_SUIT = '♡';
    public static final char WHITE_DIAMOND_SUIT = '♢';
    public static final char BLACK_CLUB_SUIT = '♣';
    public static final char WHITE_SPADE_SUIT = '♤';
    public static final char BLACK_HEART_SUIT = '♥';
    public static final char BLACK_DIAMOND_SUIT = '♦';
    public static final char WHITE_CLUB_SUIT = '♧';
    public static final char BLACK_SCISSORS = '✂';
    public static final char WHITE_SCISSORS = '✄';
    public static final char TELEPHONE_LOCATION_SIGN = '✆';
    public static final char ENVELOPE = '✉';
    public static final char CHECK_MARK = '✓';
    public static final char HEAVY_CHECK_MARK = '✔';
    public static final char MULTIPLICATION_X = '✕';
    public static final char HEAVY_MULTIPLICATION_X = '✖';
    public static final char BALLOT_X = '✗';
    public static final char HEAVY_BALLOT_X = '✘';
    public static final char BLACK_FOUR_POINTED_STAR = '✦';
    public static final char WHITE_FOUR_POINTED_STAR = '✧';
    public static final char HEAVY_OUTLINED_BLACK_STAR = '✮';
    public static final char PINWHEEL_STAR = '✯';
    public static final char HEAVY_BLACK_HEART = '❤';
    public static final char ROTATED_HEAVY_BLACK_HEART_BULLET = '❥';
    public static final char FLORAL_HEART = '❦';
    public static final char ROTATED_FLORAL_HEART_BULLET = '❧';



    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_ONE = '➀';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_TWO = '➁';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_THREE = '➂';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_FOUR = '➃';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_FIVE = '➄';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_SIX = '➅';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_SEVEN = '➆';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_EIGHT = '➇';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_DIGIT_NINE = '➈';
    public static final char DINGBAT_CIRCLED_SANS_SERIF_NUMBER_TEN = '➉';

    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_ONE = '➊';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_TWO = '➋';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_THREE = '➌';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_FOUR = '➍';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_FIVE = '➎';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_SIX = '➏';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_SEVEN = '➐';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_EIGHT = '➑';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_DIGIT_NINE = '➒';
    public static final char DINGBAT_NEGATIVE_CIRCLED_SANS_SERIF_NUMBER_TEN = '➓';



    public static final char CIRCLED_IDEOGRAPH_ONE = '㊀';
    public static final char CIRCLED_IDEOGRAPH_TWO = '㊁';
    public static final char CIRCLED_IDEOGRAPH_THREE = '㊂';
    public static final char CIRCLED_IDEOGRAPH_FOUR = '㊃';
    public static final char CIRCLED_IDEOGRAPH_FIVE = '㊄';
    public static final char CIRCLED_IDEOGRAPH_SIX = '㊅';
    public static final char CIRCLED_IDEOGRAPH_SEVEN = '㊆';
    public static final char CIRCLED_IDEOGRAPH_EIGHT = '㊇';
    public static final char CIRCLED_IDEOGRAPH_NINE = '㊈';
    public static final char CIRCLED_IDEOGRAPH_TEN = '㊉';
    public static final char CIRCLED_IDEOGRAPH_MOON = '㊊';
    public static final char CIRCLED_IDEOGRAPH_FIRE = '㊋';
    public static final char CIRCLED_IDEOGRAPH_WATER = '㊌';
    public static final char CIRCLED_IDEOGRAPH_WOOD = '㊍';
    public static final char CIRCLED_IDEOGRAPH_METAL = '㊎';
    public static final char CIRCLED_IDEOGRAPH_EARTH = '㊏';
    public static final char CIRCLED_IDEOGRAPH_SUN = '㊐';
    public static final char CIRCLED_IDEOGRAPH_STOCK = '㊑';
    public static final char CIRCLED_IDEOGRAPH_HAVE = '㊒';
    public static final char CIRCLED_IDEOGRAPH_SOCIETY = '㊓';
    public static final char CIRCLED_IDEOGRAPH_NAME = '㊔';
    public static final char CIRCLED_IDEOGRAPH_SPECIAL = '㊕';
    public static final char CIRCLED_IDEOGRAPH_FINANCIAL = '㊖';
    public static final char CIRCLED_IDEOGRAPH_CONGRATULATION = '㊗';
    public static final char CIRCLED_IDEOGRAPH_LABOR = '㊘';
    public static final char CIRCLED_IDEOGRAPH_SECRET = '㊙';
    public static final char CIRCLED_IDEOGRAPH_MALE = '㊚';
    public static final char CIRCLED_IDEOGRAPH_FEMALE = '㊛';
    public static final char CIRCLED_IDEOGRAPH_SUITABLE = '㊜';
    public static final char CIRCLED_IDEOGRAPH_EXCELLENT = '㊝';
    public static final char CIRCLED_IDEOGRAPH_PRINT = '㊞';
    public static final char CIRCLED_IDEOGRAPH_ATTENTION = '㊟';
    public static final char CIRCLED_IDEOGRAPH_ITEM = '㊠';
    public static final char CIRCLED_IDEOGRAPH_REST = '㊡';
    public static final char CIRCLED_IDEOGRAPH_COPY = '㊢';
    public static final char CIRCLED_IDEOGRAPH_CORRECT = '㊣';
    public static final char CIRCLED_IDEOGRAPH_HIGH = '㊤';
    public static final char CIRCLED_IDEOGRAPH_CENTRE = '㊥';
    public static final char CIRCLED_IDEOGRAPH_LOW = '㊦';
    public static final char CIRCLED_IDEOGRAPH_LEFT = '㊧';
    public static final char CIRCLED_IDEOGRAPH_RIGHT = '㊨';
    public static final char CIRCLED_IDEOGRAPH_MEDICINE = '㊩';
    public static final char CIRCLED_IDEOGRAPH_RELIGION = '㊪';
    public static final char CIRCLED_IDEOGRAPH_STUDY = '㊫';
    public static final char CIRCLED_IDEOGRAPH_SUPERVISE = '㊬';
    public static final char CIRCLED_IDEOGRAPH_ENTERPRISE = '㊭';
    public static final char CIRCLED_IDEOGRAPH_RESOURCE = '㊮';
    public static final char CIRCLED_IDEOGRAPH_ALLIANCE = '㊯';
    public static final char CIRCLED_IDEOGRAPH_NIGHT = '㊰';

    /**
     * FULLWIDTH CHARACTERS
     */

    public static final char FULLWIDTH_EXCLAMATION_MARK = '！';
    public static final char FULLWIDTH_COMMA = '，';
    public static final char FULLWIDTH_FULL_STOP = '．';
    public static final char FULLWIDTH_DIGIT_ZERO = '０';
    public static final char FULLWIDTH_DIGIT_ONE = '１';
    public static final char FULLWIDTH_DIGIT_TWO = '２';
    public static final char FULLWIDTH_DIGIT_THREE = '３';
    public static final char FULLWIDTH_DIGIT_FOUR = '４';
    public static final char FULLWIDTH_DIGIT_FIVE = '５';
    public static final char FULLWIDTH_DIGIT_SIX = '６';
    public static final char FULLWIDTH_DIGIT_SEVEN = '７';
    public static final char FULLWIDTH_DIGIT_EIGHT = '８';
    public static final char FULLWIDTH_DIGIT_NINE = '９';
    public static final char FULLWIDTH_COLON = '：';
    public static final char FULLWIDTH_SEMICOLON = '；';
    public static final char FULLWIDTH_LESS_THAN_SIGN = '＜';
    public static final char FULLWIDTH_EQUALS_SIGN = '＝';
    public static final char FULLWIDTH_GREATER_THAN_SIGN = '＞';
    public static final char FULLWIDTH_QUESTION_MARK = '？';
    public static final char FULLWIDTH_COMMERCIAL_AT = '＠';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_A = 'Ａ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_B = 'Ｂ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_C = 'Ｃ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_D = 'Ｄ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_E = 'Ｅ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_F = 'Ｆ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_G = 'Ｇ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_H = 'Ｈ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_I = 'Ｉ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_J = 'Ｊ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_K = 'Ｋ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_L = 'Ｌ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_M = 'Ｍ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_N = 'Ｎ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_O = 'Ｏ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_P = 'Ｐ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_Q = 'Ｑ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_R = 'Ｒ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_S = 'Ｓ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_T = 'Ｔ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_U = 'Ｕ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_V = 'Ｖ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_W = 'Ｗ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_X = 'Ｘ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_Y = 'Ｙ';
    public static final char FULLWIDTH_LATIN_CAPITAL_LETTER_Z = 'Ｚ';
    public static final char FULLWIDTH_LEFT_SQUARE_BRACKET = '［';
    public static final char FULLWIDTH_REVERSE_SOLIDUS = '＼';
    public static final char FULLWIDTH_RIGHT_SQUARE_BRACKET = '］';
    public static final char FULLWIDTH_CIRCUMFLEX_ACCENT = '＾';
    public static final char FULLWIDTH_LOW_LINE = '＿';
    public static final char FULLWIDTH_GRAVE_ACCENT = '｀';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_A = 'ａ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_B = 'ｂ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_C = 'ｃ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_D = 'ｄ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_E = 'ｅ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_F = 'ｆ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_G = 'ｇ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_H = 'ｈ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_I = 'ｉ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_J = 'ｊ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_K = 'ｋ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_L = 'ｌ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_M = 'ｍ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_N = 'ｎ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_O = 'ｏ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_P = 'ｐ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_Q = 'ｑ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_R = 'ｒ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_S = 'ｓ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_T = 'ｔ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_U = 'ｕ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_V = 'ｖ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_W = 'ｗ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_X = 'ｘ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_Y = 'ｙ';
    public static final char FULLWIDTH_LATIN_SMALL_LETTER_Z = 'ｚ';
    public static final char FULLWIDTH_LEFT_CURLY_BRACKET = '｛';
    public static final char FULLWIDTH_VERTICAL_LINE = '｜';
    public static final char FULLWIDTH_RIGHT_CURLY_BRACKET = '｝';
    public static final char FULLWIDTH_TILDE = '～';
    public static final char FULLWIDTH_LEFT_WHITE_PARENTHESIS = '｟';
    public static final char FULLWIDTH_RIGHT_WHITE_PARENTHESIS = '｠';
    public static final char HALFWIDTH_IDEOGRAPHIC_FULL_STOP = '｡';
    public static final char HALFWIDTH_LEFT_CORNER_BRACKET = '｢';
    public static final char HALFWIDTH_RIGHT_CORNER_BRACKET = '｣';
    public static final char HALFWIDTH_IDEOGRAPHIC_COMMA = '､';
    public static final char HALFWIDTH_KATAKANA_MIDDLE_DOT = '･';


    public static final char MULTIPLICATION_SIGN = '×';
    public static final char LATIN_CAPITAL_LETTER_O_WITH_STROKE = 'Ø';
    public static final char DIVISION_SIGN = '÷';
    public static final char PERCENT_SIGN = '%';
    public static final char PER_MILLE_SIGN = '‰';
    public static final char PRIME = '′';
    public static final char DOUBLE_PRIME = '″';
    public static final char EQUALS_SIGN = '=';
    public static final char DEGREE_SIGN = '°';
    public static final char PLUS_MINUS_SIGN = '±';

    public static final char SUPERSCRIPT_ONE = '¹';
    public static final char SUPERSCRIPT_TWO = '²';
    public static final char SUPERSCRIPT_THREE = '³';
    public static final char SUPERSCRIPT_FOUR = '⁴';
    public static final char SUPERSCRIPT_FIVE = '⁵';
    public static final char SUPERSCRIPT_SIX = '⁶';
    public static final char SUPERSCRIPT_SEVEN = '⁷';
    public static final char SUPERSCRIPT_EIGHT = '⁸';
    public static final char SUPERSCRIPT_NINE = '⁹';
    public static final char SUPERSCRIPT_ZERO = '⁰';
    public static final char SUPERSCRIPT_PLUS_SIGN = '⁺';
    public static final char SUPERSCRIPT_MINUS = '⁻';
    public static final char SUPERSCRIPT_EQUALS_SIGN = '⁼';
    public static final char SUPERSCRIPT_LEFT_PARENTHESIS = '⁽';
    public static final char SUPERSCRIPT_RIGHT_PARENTHESIS = '⁾';
    public static final char SUPERSCRIPT_LATIN_SMALL_LETTER_I = 'ⁱ';
    public static final char SUPERSCRIPT_LATIN_SMALL_LETTER_N = 'ⁿ';

    public static final char SUBSCRIPT_ONE = '₁';
    public static final char SUBSCRIPT_TWO = '₂';
    public static final char SUBSCRIPT_THREE = '₃';
    public static final char SUBSCRIPT_FOUR = '₄';
    public static final char SUBSCRIPT_FIVE = '₅';
    public static final char SUBSCRIPT_SIX = '₆';
    public static final char SUBSCRIPT_SEVEN = '₇';
    public static final char SUBSCRIPT_EIGHT = '₈';
    public static final char SUBSCRIPT_NINE = '₉';
    public static final char SUBSCRIPT_ZERO = '₀';
    public static final char SUBSCRIPT_PLUS_SIGN = '₊';
    public static final char SUBSCRIPT_MINUS = '₋';
    public static final char SUBSCRIPT_EQUALS_SIGN = '₌';
    public static final char SUBSCRIPT_LEFT_PARENTHESIS = '₍';
    public static final char SUBSCRIPT_RIGHT_PARENTHESIS = '₎';
    public static final char LATIN_SUBSCRIPT_SMALL_LETTER_A = 'ₐ';
    public static final char LATIN_SUBSCRIPT_SMALL_LETTER_E = 'ₑ';
    public static final char LATIN_SUBSCRIPT_SMALL_LETTER_O = 'ₒ';
    public static final char LATIN_SUBSCRIPT_SMALL_LETTER_X = 'ₓ';
    public static final char LATIN_SUBSCRIPT_SMALL_LETTER_SCHWA = 'ₔ';

    public static final char VULGAR_FRACTION_ONE_QUARTER = '¼';
    public static final char VULGAR_FRACTION_ONE_HALF = '½';
    public static final char VULGAR_FRACTION_THREE_QUARTERS = '¾';
    public static final char VULGAR_FRACTION_ONE_SEVENTH = '⅐';
    public static final char VULGAR_FRACTION_ONE_NINTH = '⅑';
    public static final char VULGAR_FRACTION_ONE_TENTH = '⅒';
    public static final char VULGAR_FRACTION_ONE_THIRD = '⅓';
    public static final char VULGAR_FRACTION_TWO_THIRDS = '⅔';
    public static final char VULGAR_FRACTION_ONE_FIFTH = '⅕';
    public static final char VULGAR_FRACTION_TWO_FIFTHS = '⅖';
    public static final char VULGAR_FRACTION_THREE_FIFTHS = '⅗';
    public static final char VULGAR_FRACTION_FOUR_FIFTHS = '⅘';
    public static final char VULGAR_FRACTION_ONE_SIXTH = '⅙';
    public static final char VULGAR_FRACTION_FIVE_SIXTHS = '⅚';
    public static final char VULGAR_FRACTION_ONE_EIGHTH = '⅛';
    public static final char VULGAR_FRACTION_THREE_EIGHTHS = '⅜';
    public static final char VULGAR_FRACTION_FIVE_EIGHTHS = '⅝';
    public static final char VULGAR_FRACTION_SEVEN_EIGHTHS = '⅞';
    public static final char FRACTION_NUMERATOR_ONE = '⅟';
    public static final char VULGAR_FRACTION_ZERO_THIRDS = '↉';

    public static final char FOR_ALL = '∀';
    public static final char THERE_EXISTS = '∃';
    public static final char THERE_DOES_NOT_EXIST = '∄';
    public static final char ELEMENT_OF = '∈';
    public static final char NOT_AN_ELEMENT_OF = '∉';
    public static final char N_ARY_PRODUCT = '∏';
    public static final char SQUARE_ROOT = '√';
    public static final char PROPORTIONAL_TO = '∝';

    public static final char PARALLEL_TO = '∥';
    public static final char LOGICAL_OR = '∨';
    public static final char INTERSECTION = '∩';
    public static final char UNION = '∪';
    public static final char INTEGRAL = '∫';
    public static final char CONTOUR_INTEGRAL = '∮';
    public static final char THEREFORE = '∴';
    public static final char BECAUSE = '∵';
    public static final char APPROXIMATELY_EQUAL_TO = '≅';
    public static final char ALMOST_EQUAL_TO = '≈';
    public static final char DELTA_EQUAL_TO = '≜';
    public static final char NOT_EQUAL_TO = '≠';
    public static final char IDENTICAL_TO = '≡';
    public static final char LESS_THAN_OR_EQUAL_TO = '≤';
    public static final char GREATER_THAN_OR_EQUAL_TO = '≥';
    public static final char MUCH_LESS_THAN = '≪';
    public static final char MUCH_GREATER_THAN = '≫';
    public static final char SUBSET_OF = '⊂';
    public static final char SUPERSET_OF = '⊃';
    public static final char NOT_A_SUBSET_OF = '⊄';
    public static final char NOT_A_SUPERSET_OF = '⊅';
    public static final char SUBSET_OF_OR_EQUAL_TO = '⊆';
    public static final char SUPERSET_OF_OR_EQUAL_TO = '⊇';

    public static final char UP_TACK = '⊥';
    public static final char DOT_OPERATOR = '⋅';
    public static final char LEFT_CEILING = '⌈';
    public static final char RIGHT_CEILING = '⌉';
    public static final char LEFT_FLOOR = '⌊';
    public static final char RIGHT_FLOOR = '⌋';

    public static String parseSymbols(String msg) {
        String[] split = msg.split(" ");
        for(String str : split) {
            if(str.contains("%#")) {
                String sub = str.substring(str.indexOf("%#")).replace("%#", "");
                if(NumberUtil.isNumeric(sub)) {
                    Integer num = Integer.parseInt(sub);

                    msg = msg.replace("%#" + sub, FieldUtil.getStringConstant(num, Symbols.class));
                } else {
                    msg = msg.replace("%#" + sub, FieldUtil.getStringConstant(sub, Symbols.class));
                }
            }
        }
        return msg;
    }

}
