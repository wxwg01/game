/**
 * 用一句话描述该文件做什么
 *
 * @title DataBaseConfiguration.java
 * @package com.igen.command.receive.factory
 * @author lvjian
 * @create 2017-6-19 下午4:26:45
 * @version V1.0
 */
package com.game.service.factory;


import org.springframework.context.annotation.Configuration;

/**
 * 载入druid数据源
 * @author wanggang
 * @create 2017-6-19 下午4:26:45
 * @version 1.0
 */
@Configuration
public class DataBaseConfiguration /*implements TransactionManagementConfigurer*/ {

   /* @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.mybatis.mapper-locations}")
    private String mapperLocations;

    @Value("${spring.datasource.mybatis.config-locations}")
    private String configLocation;

    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
    @Primary
    public DruidDataSource writeDataSource() {
        LogManager.getLogger("data").info("开始注入druid");
        System.out.println("开始注入druid");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);//用户名
        dataSource.setPassword(password);//密码
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(1);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnReturn(false);
        dataSource.setTimeBetweenConnectErrorMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(25200000);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);
       *//* try {
			dataSource.setFilters("mergeStat");
		} catch (SQLException e) {
			e.printStackTrace();
		}*//*
        LogManager.getLogger("data").info("注入druid完成");
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    @Primary
    public SqlSessionFactory sessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        sessionFactory.setMapperLocations(resources);
        sessionFactory.setTypeAliasesPackage(configLocation);
        sessionFactory.setTypeAliasesSuperType(Entity.class);
        return sessionFactory.getObject();
    }

 *//*   @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dtm1 = new DataSourceTransactionManager(writeDataSource());
        ChainedTransactionManager ctm = new ChainedTransactionManager(dtm1);
        return ctm;
       return null;
    }*/
}
