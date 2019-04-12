package com.alibaba.dubbo.examples;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.examples.spi.Robot;
import org.junit.Test;

public class DubboSPITest {

    @Test
    public void sayHello() throws Exception {
        // 1.使用指定的接口实例化
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        // 通过key调用获取指定的实例
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}
