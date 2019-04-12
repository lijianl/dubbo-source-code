package com.alibaba.dubbo.examples.spi;

// com.alibaba.dubbo.examples.spi.OptimusPrime

public class OptimusPrime implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}