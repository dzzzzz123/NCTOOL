package com.ruoyi.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * NC代码转换产量池
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformConstants {

    public static final String ORIGIN_M_PROGCAT = "(PROGCAT MAZAK HORIZONTAL)";
    public static final String NH6300_M_PROGCAT = "(PROGCAT MORI SEIKI NH6300)";
    public static final String NV7000_M_PROGCAT = "(PROGCAT MORI SEIKI NV7000)";
    public static final String MAZAK655_M_PROGCAT = "(PROGCAT MAZAK V655 PALLET)";
    public static final String NH6300_FILENAME = "(FILENAME E:\\NH6300\\";
    public static final String NV7000_FILENAME = "(FILENAME E:\\NV7000\\";
    public static final String MAZAK655_FILENAME = "(FILENAME E:\\MZK502\\";
    public static final String[] NH6300_M_TO_DELETE = {"M58", "M37", "M38", "M39", "M100"};
    public static final String[] NV7000_M_TO_DELETE = {"M58", "M37", "M38", "M39", "M100"};
    public static final Map<String, String> NH6300_M_TO_CHANGE = Map.of(
            "M50", "M51",
            "M09", "M89",
            "M51", "M08");
    public static final Map<String, String> NH6300_H_TO_CHANGE = Map.ofEntries(
            Map.entry("H84", "H1")
    );
    public static final Map<String, String> NH6300_M_G_TO_CHANGE = Map.of(
            "G65P8771", "M98P8771(Z AXIS HEIGHT MEASUREMENT)",
            "G65P8881", "M98P8881(Z AXIS HEIGHT MEASUREMENT)"
    );
    public static final Map<String, String> NH6300_ALL_TO_CHANGE = new HashMap<>();
    static {
        NH6300_ALL_TO_CHANGE.putAll(NH6300_M_TO_CHANGE);
        NH6300_ALL_TO_CHANGE.putAll(NH6300_H_TO_CHANGE);
        NH6300_ALL_TO_CHANGE.putAll(NH6300_M_G_TO_CHANGE);
    }

    public static final Map<String, String> NV7000_M_TO_CHANGE = Map.ofEntries(
            Map.entry("M50", "M51"),
            Map.entry("M99", "M30"),
            Map.entry("M89", "M09"),
            Map.entry("M88", "M08"),
            Map.entry("M51", "M08")
    );

    public static final Map<String, String> NV7000_H_TO_CHANGE = Map.ofEntries(
            Map.entry("H16", "H43"),
            Map.entry("H49", "H59"),
            Map.entry("H60", "H54"),
            Map.entry("H67", "H56"),
            Map.entry("H77", "H44"),
            Map.entry("H81", "H57"),
            Map.entry("H84", "H40"),
            Map.entry("H96", "H44"),
            Map.entry("H108", "H33")
    );

    public static final Map<String, String> NV7000_G_TO_CHANGE = Map.ofEntries(
            Map.entry("G28", "G30")
    );
    public static final Map<String, String> NV7000_T_TO_CHANGE = Map.ofEntries(
            Map.entry("T10", "T9910"),
            Map.entry("T11", "T9911"),
            Map.entry("T15", "T9915"),
            Map.entry("T16", "T9943"),
            Map.entry("T18", "T9918"),
            Map.entry("T22", "T9922"),
            Map.entry("T25", "T9925"),
            Map.entry("T26", "T9926"),
            Map.entry("T27", "T9927"),
            Map.entry("T28", "T9928"),
            Map.entry("T34", "T9934"),
            Map.entry("T35", "T9935"),
            Map.entry("T36", "T9936"),
            Map.entry("T39", "T9939"),
            Map.entry("T40", "T9940"),
            Map.entry("T47", "T9947"),
            Map.entry("T49", "T9959"),
            Map.entry("T53", "T9953"),
            Map.entry("T54", "T9954"),
            Map.entry("T55", "T9955"),
            Map.entry("T57", "T9957"),
            Map.entry("T58", "T9958"),
            Map.entry("T60", "T54"),
            Map.entry("T67", "T9956"),
            Map.entry("T77", "T44"),
            Map.entry("T81", "T57"),
            Map.entry("T84", "T40"),
            Map.entry("T96", "T9944"),
            Map.entry("T100", "T99100"),
            Map.entry("T108", "T9933")
    );
    public static final Map<String, String> NV7000_ALL_TO_CHANGE = new HashMap<>();

    static {
        NV7000_ALL_TO_CHANGE.putAll(NV7000_M_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_H_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_G_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_T_TO_CHANGE);
    }

    public static final String[] MAZAK655_M_TO_DELETE = {};
    public static final Map<String, String> MAZAK655_M_TO_CHANGE = Map.ofEntries(
            Map.entry("M99", "M30")
    );
    public static final Map<String, String> MAZAK655_H_TO_CHANGE = Map.ofEntries(
            Map.entry("H16", "H43"),
            Map.entry("H49", "H59"),
            Map.entry("H60", "H56"),
            Map.entry("H67", "H54"),
            Map.entry("H81", "H57"),
            Map.entry("H84", "H40"),
            Map.entry("H96", "H44"),
            Map.entry("H108", "H33")
    );

    public static final Map<String, String> MAZAK655_G_TO_CHANGE = Map.ofEntries(
            Map.entry("G28", "G30")
    );
    public static final Map<String, String> MAZAK655_T_TO_CHANGE = Map.ofEntries(
            Map.entry("T16", "T43"),
            Map.entry("T49", "T59"),
            Map.entry("T60", "T56"),
            Map.entry("T67", "T54"),
            Map.entry("T81", "T57"),
            Map.entry("T84", "T40"),
            Map.entry("T96", "T44"),
            Map.entry("T108", "T33")
    );
    public static final Map<String, String> MAZAK655_ALL_TO_CHANGE = new HashMap<>();

    static {
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_M_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_H_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_G_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_T_TO_CHANGE);
    }

    public static final Map<String, String> WEAR_DETECTION = Map.of(
            "T1",
            "G325X-11.900Y1.400H01Q0.3T9002\n" +
                    "G325X7.200Y7.600H01Q0.3T9002\n" +
                    "G325X4.500Y-11.000H01Q0.3T9002\n",
            "T26",
            "G325X1.800Y-6.500H01Q0.3T9002\n" +
                    "G325X3.100Y3.500H01Q0.3T9002\n" +
                    "G325X-5.900Y0.500H01Q0.3T9002\n",
            "T30",
            "G325X2.400Y-8.100H01Q0.3T9002\n" +
                    "G325X5.300Y4.200H01Q0.3T9002\n" +
                    "G325X-6.600Y1.3000H01Q0.3T9002\n",
            "T84",
            "G325X7.300Y-0.500H01Q0.3T9002\n" +
                    "G325X-8.400Y0.500H01Q0.3T9002\n",
            "T104",
            "G325X1.800Y-4.900H01Q0.3T9002\n" +
                    "G325X-5.300Y0.000H01Q0.3T9002\n" +
                    "G325X2.200Y3.300H01Q0.3T9002\n"
    );

    public static final String[] TOOLS_TO_SET_DETECTION = {"T1", "T3", "T8", "T10", "T11", "T15", "T25", "T26", "T27", "T35", "T39", "T40", "T53", "T58", "T67", "T84", "T96", "T104"};
    public static final String TOOL_SET_DETECTION =
            "#991=-2\n" +
                    "#992=1\n" +
                    "#1133=4\n" +
                    "M67\n" +
                    "G04\n" +
                    "#1133=200\n" +
                    "M101\n" +
                    "M102\n" +
                    "IF[#1004NE1]GOTO9000\n";

    /**
     * 匹配以 S三位数字M两位数字 结尾的正则表达式
     */
    public static final String M_PATTERN = ".*S\\d{3}M\\d{2}$";
    public static final String TAPTEETH = "G95\n" +
            "M29";
}
