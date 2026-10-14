package com.huiyan.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;


@Configuration
@IntegrationComponentScan
public class MqttConfig {


    /**
     * MQTT连接配置
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();

        // 硬件同学的EMQX服务器
        options.setServerURIs(
                new String[]{"tcp://10.121.237.16:1883"}
        );


        // EMQX账号密码
        options.setUserName("admin");
        options.setPassword("public123".toCharArray());


        // MQTT连接参数
        options.setCleanSession(true);
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(60);


        return options;
    }



    /**
     * MQTT客户端工厂
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

        DefaultMqttPahoClientFactory factory =
                new DefaultMqttPahoClientFactory();

        factory.setConnectionOptions(
                mqttConnectOptions()
        );

        return factory;
    }



    /**
     * MQTT消息通道
     */
    @Bean
    public MessageChannel mqttInputChannel() {

        return new DirectChannel();

    }



    /**
     * 订阅硬件数据
     */
    @Bean
    public MessageProducer inbound() {


        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(

                        // 后端客户端ID
                        "backend_" + System.currentTimeMillis(),

                        mqttClientFactory(),

                        // 硬件发送的Topic
                        "sensors/data"

                );


        adapter.setConverter(
                new DefaultPahoMessageConverter()
        );


        // QoS等级
        adapter.setQos(1);


        adapter.setOutputChannel(
                mqttInputChannel()
        );


        return adapter;

    }

}