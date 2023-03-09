package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                ".___  ___.      ___           _______.___________. _______ .______      \n" +
                "|   \\/   |     /   \\         /       |           ||   ____||   _  \\     \n" +
                "|  \\  /  |    /  ^  \\       |   (----`---|  |----`|  |__   |  |_)  |    \n" +
                "|  |\\/|  |   /  /_\\  \\       \\   \\       |  |     |   __|  |      /     \n" +
                "|  |  |  |  /  _____  \\  .----)   |      |  |     |  |____ |  |\\  \\----.\n" +
                "|__|  |__| /__/     \\__\\ |_______/       |__|     |_______|| _| `._____|");
    }
}
