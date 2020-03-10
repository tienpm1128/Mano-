package com.viettel.nfv.vdashboardx.config;

import com.viettel.nfv.vdashboardx.websocket_handler.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@PropertySource(value = "classpath:/config.properties", ignoreResourceNotFound = true)
public class WebFluxConfig implements WebFluxConfigurer {

    @Value("${notification.nsd}")
    private String nsdNotification;

    @Value("${notification.nslcm}")
    private String nslcmNotification;

    @Value("${notification.vnflcm}")
    private String vnflcmNotification;

    @Value("${notification.nspm.performance}")
    private String nspmPerformanceNotification;

    @Value("${notification.nspm.threshold}")
    private String nspmThresholdNotification;

    @Value("${notification.vim}")
    private String vimNotification;

    @Value("${notification.service}")
    private String serviceNotification;

    @Value("${notification.hppm.performance}")
    private String hppmPerformanceNotification;

    @Value("${notification.hppm.threshold}")
    private String hppmThresholdNotification;


}