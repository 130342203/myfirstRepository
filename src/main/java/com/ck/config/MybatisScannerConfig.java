package com.ck.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by Administrator on 2018/6/7.
 */
@Configuration
@AutoConfigureAfter(DatabaseConfig.class)
public class MybatisScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer     mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(configConstants.PRIMARY_SESSION_FACTORY);
        mapperScannerConfigurer.setBasePackage(configConstants.SCAN_PACKAGE_PATH+".**.mapper");
        //Properties properties = new Properties();
       // properties.setProperty("mappers", Mapper.class.getName());
          return  mapperScannerConfigurer;
    }
}
