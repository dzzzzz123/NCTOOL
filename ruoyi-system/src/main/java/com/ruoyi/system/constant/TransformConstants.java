package com.ruoyi.system.constant;

import com.ruoyi.system.domain.SysTools;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转换NC代码的常量类
 *
 * @author dz
 */
@Component
public class TransformConstants implements ApplicationRunner {
    public static final String NH6300_M_PROGCAT = "(PROGCAT MORI SEIKI NH6300)";
    public static final String NV7000_M_PROGCAT = "(PROGCAT MORI SEIKI NV7000)";
    public static final String MAZAK655_M_PROGCAT = "(PROGCAT MAZAK V655 PALLET)";
    public static final String NH6300_FILENAME = "(FILENAME E:\\NH6300\\";
    public static final String NV7000_FILENAME = "(FILENAME E:\\NV7000\\";
    public static final String MAZAK655_FILENAME = "(FILENAME E:\\MZK502\\";
    public static final String[] NH6300_M_TO_DELETE = {"M37", "M38", "M39", "M58", "M100"};
    public static final String[] NV7000_M_TO_DELETE = {"M37", "M38", "M39", "M58", "M100"};
    public static final String[] MAZAK655_M_TO_DELETE = {"M37", "M38", "M39", "M58", "M100"};
    public static final Map<String, String> NH6300_M_TO_CHANGE = Map.of(
            "M50", "M51",
            "M09", "M89",
            "M51", "M88");
    public static final Map<String, String> NH6300_H_TO_CHANGE = new HashMap<>(120);
    public static final Map<String, String> NH6300_M_G_TO_CHANGE = Map.of(
            "G65P8771", "M98P8771(Z AXIS HEIGHT MEASUREMENT)",
            "G65P8881", "M98P8881(Z AXIS HEIGHT MEASUREMENT)"
    );
    public static final Map<String, String> NH6300_ALL_TO_CHANGE = new HashMap<>();
    public static final Map<String, String> NV7000_M_TO_CHANGE = Map.ofEntries(
            Map.entry("M50", "M51"),
            Map.entry("M99", "M30"),
            Map.entry("M89", "M09"),
            Map.entry("M88", "M08"),
            Map.entry("M51", "M08")
    );
    public static final Map<String, String> NV7000_H_TO_CHANGE = new HashMap<>(120);
    public static final Map<String, String> NV7000_G_TO_CHANGE = Map.ofEntries(
            Map.entry("G28", "G30")
    );
    public static final Map<String, String> NV7000_T_TO_CHANGE = new HashMap<>(120);
    public static final Map<String, String> NV7000_BRACKETS_T_TO_CHANGE = new HashMap<>(20);
    public static final Map<String, String> NV7000_ALL_TO_CHANGE = new HashMap<>();
    public static final Map<String, String> MAZAK655_M_TO_CHANGE = Map.ofEntries(
            Map.entry("M99", "M30")
    );
    public static final Map<String, String> MAZAK655_H_TO_CHANGE = new HashMap<>();
    public static final Map<String, String> MAZAK655_G_TO_CHANGE = Map.ofEntries(
            Map.entry("G28", "G30")
    );
    public static final Map<String, String> MAZAK655_T_TO_CHANGE = new HashMap<>();
    public static final Map<String, String> MAZAK655_BRACKETS_T_TO_CHANGE = new HashMap<>(20);
    public static final Map<String, String> MAZAK655_ALL_TO_CHANGE = new HashMap<>();
    public static final Map<String, String> CIRCULATING_DRILLING_T_VALUE = Map.of(
            "T4", "Q2.0",
            "T7", "Q2.0",
            "T8", "Q2.0",
            "T9", "Q2.0",
            "T65", "Q2.0",
            "T86", "Q2.0",
            "T88", "Q2.0"
    );
    public static final Map<String, String> CIRCULATING_DRILLING_NV7000_T_VALUE = new HashMap<>();
    public static final Map<String, String> CIRCULATING_DRILLING_MAZAK655_T_VALUE = new HashMap<>();
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

    public static final Map<String, String> TOOL_BREAK_DETECTION = Map.of(
            "T9903",
            "G325H3\n" +
                    "G30G91Z0.\n" +
                    "G0Y0.",
            "T9910",
            "G325H10\n" +
                    "G30G91Z0.\n" +
                    "G0Y0.",
            "T9915",
            "G325H15\n" +
                    "G30G91Z0.\n" +
                    "G0Y0.",
            "T9917",
            "G325H17\n" +
                    "G30G91Z0.\n" +
                    "G0Y0.",
            "T9918",
            "G325H18\n" +
                    "G30G91Z0.\n" +
                    "G0Y0."
    );

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

    public static final String TAPTEETH = "G95\n" +
            "M29";
    private static ISysNcCodeTransformService transformService;

    @Autowired
    public void setTransformService(ISysNcCodeTransformService transformService) {
        TransformConstants.transformService = transformService;
    }

    public static List<SysTools> getSysTools() {
        return transformService.selectToolList();
    }

    private void init() {
        NH6300_ALL_TO_CHANGE.putAll(NH6300_M_TO_CHANGE);
        NH6300_ALL_TO_CHANGE.putAll(NH6300_H_TO_CHANGE);
        NH6300_ALL_TO_CHANGE.putAll(NH6300_M_G_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_M_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_H_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_G_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_T_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_M_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_H_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_G_TO_CHANGE);
        MAZAK655_ALL_TO_CHANGE.putAll(MAZAK655_T_TO_CHANGE);
    }

    /**
     * 在springboot初始化后首先执行的方法
     * 主要是为了动态初始化常量池
     *
     * @param args springboot初始化时传入的参数
     */
    @Override
    public void run(ApplicationArguments args) {
        getSysTools().forEach(sysTool -> {
            String nh6300 = sysTool.getNh6300().toString();
            String nv7000 = sysTool.getNv7000().toString();
            String mazak655 = sysTool.getMazak655().toString();
            String h1 = "H" + nh6300;
            String h2 = "H" + nv7000;
            String h3 = "H" + mazak655;
            String t1 = "T" + nh6300;
            String t2 = Integer.parseInt(nv7000) < Integer.parseInt("10") ? "T990" + nv7000 : "T99" + nv7000;
            String t3 = "T" + mazak655;
            if (!nh6300.equals(nv7000)) {
                NV7000_H_TO_CHANGE.put(h1, h2);
                NV7000_BRACKETS_T_TO_CHANGE.put(nh6300, nv7000);
                if (CIRCULATING_DRILLING_T_VALUE.containsKey(t1)) {
                    CIRCULATING_DRILLING_NV7000_T_VALUE.put(t2, CIRCULATING_DRILLING_T_VALUE.get(t1));
                }
            }
            if (!nh6300.equals(mazak655)) {
                MAZAK655_H_TO_CHANGE.put(h1, h3);
                MAZAK655_T_TO_CHANGE.put(t1, t3);
                MAZAK655_BRACKETS_T_TO_CHANGE.put(nh6300, mazak655);
                if (CIRCULATING_DRILLING_T_VALUE.containsKey(t1)) {
                    CIRCULATING_DRILLING_MAZAK655_T_VALUE.put(t3, CIRCULATING_DRILLING_T_VALUE.get(t1));
                }
            }
            NH6300_H_TO_CHANGE.put(h1, "H1");
            NV7000_T_TO_CHANGE.put(t1, t2);
            if(CIRCULATING_DRILLING_T_VALUE.containsKey(t1)){
                CIRCULATING_DRILLING_NV7000_T_VALUE.put(t2, CIRCULATING_DRILLING_T_VALUE.get(t1));
                CIRCULATING_DRILLING_MAZAK655_T_VALUE.put(t3, CIRCULATING_DRILLING_T_VALUE.get(t1));
            }
        });
        init();
    }
}