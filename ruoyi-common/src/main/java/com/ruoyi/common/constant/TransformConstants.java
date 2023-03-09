package com.ruoyi.common.constant;

import java.util.Map;

/**
 * NC代码转换产量池
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformConstants {

    public static final String ORIGIN_M_PROGCAT = "(PROGCAT MAZAK HORIZONTAL)";

    public static final String[] NH6300_M_TO_DELETE = {"M58", "M37", "M38", "M39", "M100"};

    public static final Map<String, String> NH6300_M_TO_CHANGE = Map.of("M50", "M51", "M09", "M89", "M51", "M88");

    public static final String NH6300_M_PROGCAT = "(PROGCAT MORI SEIKI NH6300)";

    public static final String[] NV7000_M_TO_DELETE = {"M58", "M37", "M38", "M39", "M100"};

    public static final Map<String, String> NV7000_M_TO_CHANGE = Map.of("M50", "M51", "M99", "M30");

    public static final String NV7000_M_PROGCAT = "(PROGCAT MORI SEIKI NV7000)";

    public static final Map<String, String> MAZAK655_M_TO_CHANGE = Map.of("M51", "M08", "M99", "M30");

    public static final String MAZAK655_M_PROGCAT = "(PROGCAT MAZAK V655 PALLET)";

}
