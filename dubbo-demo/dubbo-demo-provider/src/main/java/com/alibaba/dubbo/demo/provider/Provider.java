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


package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Provider {

    public static void main(String[] args) throws IOException {
        /// loadWithApi();
        loadWithXML();

        System.in.read(); // press any key to exit

    }


    // 使用API加载
    public static void loadWithApi() {
        // 服务实现
        DemoService demoService = new DemoServiceImpl();
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-provider-2");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        /*
        registry.setUsername("aaa");
        registry.setPassword("bbb");
        */

        // 服务提供者协议配置:通讯协议
        final ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setThreads(200);

        final ProtocolConfig protocol2 = new ProtocolConfig();
        protocol2.setName("hessian");
        protocol2.setPort(8080);
        protocol2.setServer("jetty");

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        // 服务提供者暴露服务配置
        // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
        // 应用的基本配置
        service.setApplication(application);
        // 多个注册中心可以用setRegistries()
        service.setRegistry(registry);
        // 多个协议可以用setProtocols()
        service.setProtocols(new ArrayList<ProtocolConfig>() {{
            add(protocol);
            add(protocol2);
        }});
        service.setInterface(DemoService.class);
        service.setRef(demoService);
        service.setVersion("1.0.0");
        // 暴露及注册服务
        service.export();

    }

    public static void loadWithXML() {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        // 记载XML启动
        // new的时候会刷新Context
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-provider.xml"});
        context.start();
    }

    public static void testMap() {
        ConcurrentHashMap<String, Integer> cMap = new ConcurrentHashMap<String, Integer>();
        cMap.put("key", 1);
    }


    public void testT() {
        List<String> ls = new ArrayList<String>();
        List<?> lx = new ArrayList<String>();
        // Class.forName()
    }

}
