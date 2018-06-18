package com.ck.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/6/7.
 */
@Configuration
@AutoConfigureAfter(DatabaseConfig.class)
public class MybatisScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer     mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(ConfigConstants.PRIMARY_SESSION_FACTORY);
        mapperScannerConfigurer.setBasePackage(ConfigConstants.SCAN_PACKAGE_PATH+".**.mapper");
        //Properties properties = new Properties();
       // properties.setProperty("mappers", Mapper.class.getName());
          return  mapperScannerConfigurer;
    }
}
