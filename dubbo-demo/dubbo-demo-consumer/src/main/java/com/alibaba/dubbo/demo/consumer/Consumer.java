/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.demo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Consumer {

    static Object obj = new Object();

    public static void main(String[] args) {

        DemoService demoService = loadWithAPI();

        /*while (true) {
            try {
                Thread.sleep(1000);
                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }*/


        try {
            File file = new File("/Users/a002/Desktop/dubbo-cluster.jpeg");
            String res = null;
            res = demoService.readFile(new FileInputStream(file));
            System.out.println(res);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static DemoService loadWithXML() {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        // spring的形式
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy
        return demoService;
    }

    public static DemoService loadWithAPI() {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-comsumer-2");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        /*
        registry.setUsername("aaa");
        registry.setPassword("bbb");
        */

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        ReferenceConfig<DemoService> reference = new ReferenceConfig<DemoService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        // 应用
        reference.setApplication(application);
        // 多个注册中心可以用setRegistries()
        //reference.setRegistry(registry);
        // 不实用注册中心直接调用服务
        // 点对点直连的方式...想吐槽
        /*reference.setUrl("dubbo://127.0.0.1:20880/com.alibaba.dubbo.demo.DemoService");*/

        // 通过这个协议实现文件传输; 测试结果是ok的
        reference.setUrl("hessian://172.17.3.210:8080/com.alibaba.dubbo.demo.DemoService");

        // 没有设置协议
        //reference.setProtocol();
        reference.setInterface(DemoService.class);
        reference.setVersion("1.0.0");
        /*
        reference.setLazy();
        reference.setCheck(false);
        */

        // 和本地bean一样使用xxxService
        DemoService demoService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        return demoService;
    }


    /**
     * 输出bean加载的所有bean?
     * consumer的懒加载好像没有什么用
     */
    private static void pringBeans() {
        new Thread(() -> {
            SpringContextHolder.printAllBeans();
        }).start();
    }
}
