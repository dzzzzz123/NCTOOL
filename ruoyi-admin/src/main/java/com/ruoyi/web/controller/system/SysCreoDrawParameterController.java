package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysProgramNumber;
import com.ruoyi.system.service.ISysCreoDrawParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/**
 * Creo加工参数接口控制层
 *
 * @author dz
 * @date 2023-02-27
 */
@RestController
@RequestMapping("/system/CeroDraw")
public class SysCreoDrawParameterController extends BaseController {

    @Autowired
    private ISysCreoDrawParameterService creoService;

    @Anonymous
    @Log(title = "获取工序图参数", businessType = BusinessType.CREO)
    @GetMapping("/bookProgramNumber/{bookNumber}")
    public ArrayList<String> getParameter(@PathVariable Integer bookNumber) {
        // 先把booknumber的值变成定值为3
        bookNumber = 3;
        ArrayList<SysProgramNumber> programNumberList = creoService.getProgramNumbers(bookNumber);
        ArrayList<String> strings = new ArrayList<>();
        for (SysProgramNumber programNumber : programNumberList) {
            strings.add(programNumber.toString());
        }
        return strings;
    }
}
