package com.alibaba.dubbo.examples.spi;



// com.alibaba.dubbo.examples.spi.Robot


import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Robot {
    @Adaptive("robot.type")
    void sayHello();
}