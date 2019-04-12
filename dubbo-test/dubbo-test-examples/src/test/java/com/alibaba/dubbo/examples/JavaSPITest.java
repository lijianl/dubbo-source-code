package com.alibaba.dubbo.examples;

import com.alibaba.dubbo.examples.spi.Robot;
import org.junit.Test;

import java.util.ServiceLoader;

public class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        // 1. 会一次加载全部的服务实现类,只能加载一个service接口
        ServiceLoader<Robot> robotServiceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        // 2. 没法选择指定的实现
        // robotServiceLoader.iterator().forEachRemaining(Robot::sayHello);
        //robotServiceLoader.forEach(Robot::sayHello);
        //robotServiceLoader.reload();
        // 3. 通过代码实现默认调用第一个实现
        Robot first = robotServiceLoader.iterator().next();
        first.sayHello();
    }
}
