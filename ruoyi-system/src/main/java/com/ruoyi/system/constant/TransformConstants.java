package com.ruoyi.system.constant;

import com.ruoyi.system.domain.SysTools;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    public static final Map<String, String> NV7000_H_TO_CHANGE = new HashMap<>(120);
    public static final Map<String, String> NV7000_G_TO_CHANGE = Map.ofEntries(
            Map.entry("G28", "G30")
    );
    public static final Map<String, String> NV7000_T_TO_CHANGE = new HashMap<>(120);
    public static final Map<String, String> NV7000_ALL_TO_CHANGE = new HashMap<>();
    public static final String[] MAZAK655_M_TO_DELETE = {};
    public static final Map<String, String> MAZAK655_M_TO_CHANGE = Map.ofEntries(
            Map.entry("M99", "M30")
    );
    public static final Map<String, String> MAZAK655_H_TO_CHANGE = new HashMap<>();
    public static final Map<String, String> MAZAK655_G_TO_CHANGE = Map.ofEntries(
            Map.entry("G28", "G30")
    );
    public static final Map<String, String> MAZAK655_T_TO_CHANGE = new HashMap<>();
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
    private static ISysNcCodeTransformService transformService;

    @Autowired
    public void setTransformService(ISysNcCodeTransformService transformService) {
        TransformConstants.transformService = transformService;
    }

    public static List<SysTools> getSysTools() {
        return transformService.selectToolList();
    }

    private void init(){
        NV7000_ALL_TO_CHANGE.putAll(NV7000_M_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_H_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_G_TO_CHANGE);
        NV7000_ALL_TO_CHANGE.putAll(NV7000_T_TO_CHANGE);
    }

    /**
     * 在springboot初始化后首先执行的方法
     * @param args springboot初始化时传入的参数
     */
    @Override
    public void run(ApplicationArguments args) {
        List<SysTools> sysTools = getSysTools();
        for (SysTools sysTool : sysTools) {
            String t1;
            String t2;
            String t3;
            String h1;
            String h2;
            String h3;
            if (!sysTool.getNv7000().equals(sysTool.getNh6300())) {
                h1 = "H" + sysTool.getNh6300();
                h2 = "H" + sysTool.getNv7000();
                NV7000_H_TO_CHANGE.put(h1, h2);
            }
            if (!sysTool.getNv7000().equals(sysTool.getMazak655())) {
                h1 = "H" + sysTool.getNh6300();
                h3 = "H" + sysTool.getMazak655();
                MAZAK655_H_TO_CHANGE.put(h1, h3);
            }
            if (sysTool.getNv7000().toString().compareTo("10") < 0) {
                t2 = "T990" + sysTool.getNv7000();
            } else {
                t2 = "T99" + sysTool.getNv7000();
            }
            if (sysTool.getNh6300().toString().compareTo(sysTool.getMazak655().toString()) < 0) {
                t1 = "T" + sysTool.getNh6300();
                t3 = "T" + sysTool.getMazak655();
                MAZAK655_T_TO_CHANGE.put(t1, t3);
            }
            t1 = "T" + sysTool.getNh6300();
            NV7000_T_TO_CHANGE.put(t1, t2);
        }
        init();
    }
}
