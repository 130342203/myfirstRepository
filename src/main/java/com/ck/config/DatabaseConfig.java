package com.ck.config;

import com.alibaba.druid.pool.DruidDataSource;
//import com.com.config.plugins.SqlPluginsConfig;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018/6/6.
 */
@Configuration
@ConfigurationProperties(prefix = "spring")
@EnableTransactionManagement
public class DatabaseConfig {
    String localdb;
    String userName;
    String driverClassName;
    String password;

    String destDb;
    String destuserName;
    String destdriverClassName;
    String destpassword;
    @Value("${validationQuery:''}")
    String validationQuery;
    //<!-- 连接池启动时的初始值 -->
    @Value("${initialSize:3}")
    int initialSize;
    //<!-- 最大活动连接的最大值 -->
    @Value("${maxActive:5}")
    int maxActive;
    //<!-- 最大空闲值.当经过一个高峰时间后，连接池可以慢慢将已经用不到的连接慢慢释放一部分，一直减少到maxIdle为止 -->
    @Value("${maxIdle:8}")
    int maxIdle;
    //<!-- 最小空闲值.当空闲的连接数少于阀值时，连接池就会预申请去一些连接，以免洪峰来时来不及申请 -->
    @Value("${minIdle:1}")
    int minIdle;
    //最大等待时间:当没有可用连接时,连接池等待连接被归还的最大时间(以毫秒计数),超过时间则抛出异常,如果设置为-1表示无限等待
    @Value("${maxWait:1000}")
    int maxWait;
    @Value("${validationQuery:''}")
    String destvalidationQuery;
    //<!-- 连接池启动时的初始值 -->
    @Value("${initialSize:3}")
    int destinitialSize;
    //<!-- 最大活动连接的最大值 -->
    @Value("${maxActive:5}")
    int destmaxActive;
    //<!-- 最大空闲值.当经过一个高峰时间后，连接池可以慢慢将已经用不到的连接慢慢释放一部分，一直减少到maxIdle为止 -->
    @Value("${maxIdle:8}")
    int destmaxIdle;
    //<!-- 最小空闲值.当空闲的连接数少于阀值时，连接池就会预申请去一些连接，以免洪峰来时来不及申请 -->
    @Value("${minIdle:1}")
    int destminIdle;
    //最大等待时间:当没有可用连接时,连接池等待连接被归还的最大时间(以毫秒计数),超过时间则抛出异常,如果设置为-1表示无限等待
    @Value("${maxWait:1000}")
    int destmaxWait;
  /*  @Resource
    private SqlPluginsConfig sqlPluginConfig;*/

    //todo servlet配置
    /*public ServletRegistrationBean druidServlet(){
        return  new ServletRegistrationBean(new )
    }*/
    /*基础数据源*/
    @Bean(name = ConfigConstants.PRIMARY_DATA_SOURCE)
    public javax.sql.DataSource getBasicDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(this.localdb);
        dataSource.setUsername(this.userName);
        dataSource.setPassword(this.password);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMaxActive(this.maxActive);
        dataSource.setMinIdle(this.minIdle);
        dataSource.setMaxWait(this.maxWait);
        dataSource.setValidationQuery(this.validationQuery);
        return dataSource;
    }
    @Bean(name = ConfigConstants.PRIMARY_JDBC_TEMPLATE)
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getBasicDataSource());
        return jdbcTemplate;
    }
    @Bean(name = ConfigConstants.PRIMARY_TRANSACTION_MANAGER)
    public DataSourceTransactionManager getDataSourceTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(getBasicDataSource());
        return transactionManager;
    }
    @Primary
    @Bean(name = ConfigConstants.PRIMARY_SESSION_FACTORY)
    public SqlSessionFactory getSqlSessionFactoryBean() throws Exception {
        List<Interceptor> list = new ArrayList<Interceptor>();
        //todo 分页判定需要否？
        /*if(sqlPluginConfig.isPageHelperPlugin()){
            list.add(pageHelper());
        }*/
        list.add(pageHelper());
        Interceptor[] plugins = new Interceptor[list.size()];
        for (int i = 0; i < list.size(); i++) {
            plugins[i] = list.get(i);
        }
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(getBasicDataSource());
        ssf.setPlugins(plugins);
        org.springframework.core.io.Resource[] resources = null;
        /*resources =
        ssf.setMapperLocations(resource);*/
        SqlSessionFactory sf = ssf.getObject();
        sf.getConfiguration().setMapUnderscoreToCamelCase(true);
        sf.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        return sf;
    }

    /*目标数据源*/
    @Bean(name = ConfigConstants.DEST_DATA_SOURCE)
    public javax.sql.DataSource getDestDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(this.destDb);
        dataSource.setUsername(this.destuserName);
        dataSource.setPassword(this.destpassword);
        dataSource.setInitialSize(this.destinitialSize);
        dataSource.setMaxActive(this.destmaxActive);
        dataSource.setMinIdle(this.destminIdle);
        dataSource.setMaxWait(this.destmaxWait);
        dataSource.setValidationQuery(this.destvalidationQuery);
        return dataSource;
    }
    @Bean(name = ConfigConstants.DEST_JDBC_TEMPLATE)
    public JdbcTemplate getDestJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDestDataSource());
        return jdbcTemplate;
    }
    @Bean(name = ConfigConstants.DEST_TRANSACTION_MANAGER)
    public DataSourceTransactionManager getDestDataSourceTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(getBasicDataSource());
        return transactionManager;
    }
    @Bean(name = ConfigConstants.DEST_SESSION_FACTORY)
    public SqlSessionFactory getDestSqlSessionFactoryBean() throws Exception {
        List<Interceptor> list = new ArrayList<Interceptor>();
        //todo 分页判定需要否？
        /*if(sqlPluginConfig.isPageHelperPlugin()){
            list.add(pageHelper());
        }*/
        list.add(pageHelper());
        Interceptor[] plugins = new Interceptor[list.size()];
        for (int i = 0; i < list.size(); i++) {
            plugins[i] = list.get(i);
        }
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(getDestDataSource());
        ssf.setPlugins(plugins);
        org.springframework.core.io.Resource[] resources = null;
        /*resources =
        ssf.setMapperLocations(resource);*/
        SqlSessionFactory sf = ssf.getObject();
        sf.getConfiguration().setMapUnderscoreToCamelCase(true);
        sf.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        return sf;
    }
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "mysql");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("pageSizeZero", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("params", "pageNum=start;pageSize=limit;");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    public String getLocaldb() {
        return localdb;
    }

    public void setLocaldb(String localdb) {
        this.localdb = localdb;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public String getDestDb() {
        return destDb;
    }

    public void setDestDb(String destDb) {
        this.destDb = destDb;
    }

    public String getDestuserName() {
        return destuserName;
    }

    public void setDestuserName(String destuserName) {
        this.destuserName = destuserName;
    }

    public String getDestdriverClassName() {
        return destdriverClassName;
    }

    public void setDestdriverClassName(String destdriverClassName) {
        this.destdriverClassName = destdriverClassName;
    }

    public String getDestpassword() {
        return destpassword;
    }

    public void setDestpassword(String destpassword) {
        this.destpassword = destpassword;
    }

    public String getDestvalidationQuery() {
        return destvalidationQuery;
    }

    public void setDestvalidationQuery(String destvalidationQuery) {
        this.destvalidationQuery = destvalidationQuery;
    }

    public int getDestinitialSize() {
        return destinitialSize;
    }

    public void setDestinitialSize(int destinitialSize) {
        this.destinitialSize = destinitialSize;
    }

    public int getDestmaxActive() {
        return destmaxActive;
    }

    public void setDestmaxActive(int destmaxActive) {
        this.destmaxActive = destmaxActive;
    }

    public int getDestmaxIdle() {
        return destmaxIdle;
    }

    public void setDestmaxIdle(int destmaxIdle) {
        this.destmaxIdle = destmaxIdle;
    }

    public int getDestminIdle() {
        return destminIdle;
    }

    public void setDestminIdle(int destminIdle) {
        this.destminIdle = destminIdle;
    }

    public int getDestmaxWait() {
        return destmaxWait;
    }

    public void setDestmaxWait(int destmaxWait) {
        this.destmaxWait = destmaxWait;
    }
}
