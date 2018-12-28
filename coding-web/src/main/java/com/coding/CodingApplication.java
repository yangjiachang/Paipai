package com.coding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.coding.*.mapper")
public class CodingApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(CodingApplication.class, args);
        System.out.println("\n" +
                ".______        ___       __  .______        ___       __  \n" +
                "|   _  \\      /   \\     |  | |   _  \\      /   \\     |  | \n" +
                "|  |_)  |    /  ^  \\    |  | |  |_)  |    /  ^  \\    |  | \n" +
                "|   ___/    /  /_\\  \\   |  | |   ___/    /  /_\\  \\   |  | \n" +
                "|  |       /  _____  \\  |  | |  |       /  _____  \\  |  | \n" +
                "| _|      /__/     \\__\\ |__| | _|      /__/     \\__\\ |__| \n" +
                "                                                          \n");
        System.out.println("(♥◠‿◠)ﾉﾞ  后台管理启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}