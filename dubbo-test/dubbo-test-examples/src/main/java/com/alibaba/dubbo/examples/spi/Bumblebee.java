package com.alibaba.dubbo.examples.spi;

// com.alibaba.dubbo.examples.spi.Bumblebee

public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}