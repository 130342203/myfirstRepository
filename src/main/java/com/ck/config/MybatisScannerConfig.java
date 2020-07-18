package com.ck.config;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.common.Mapper;

import java.util.Properties;

/**
 * Created by Administrator on 2018/6/7.
 */
@Configuration
@AutoConfigureAfter(DatabaseConfig.class)
public class MybatisScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(ConfigConstants.PRIMARY_SESSION_FACTORY);
        mapperScannerConfigurer.setBasePackage(ConfigConstants.SCAN_PACKAGE_PATH+".dao.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers", Mapper.class.getName());
        mapperScannerConfigurer.setProperties(properties);
        return  mapperScannerConfigurer;
    }
    @Bean
    public MapperScannerConfigurer destMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(ConfigConstants.DEST_SESSION_FACTORY);
        mapperScannerConfigurer.setBasePackage(ConfigConstants.SCAN_PACKAGE_PATH_DEST+".**.destDao.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers", Mapper.class.getName());
        mapperScannerConfigurer.setProperties(properties);
        return  mapperScannerConfigurer;
    }
}
