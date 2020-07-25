package org.dev.framework.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * <p>
 * 自动生成代码
 * </p>
 */
public class GeneratorServiceEntity {

    public static void main(String[] args) {
        generateCode();
    }

    public static void generateCode() {
        String packageName = "org.dev.framework.modules.sys";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "sys_sequence_log","sys_sequence_queue");
    }

    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/springboot_vue?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&useSSL=false&nullCatalogMeansCurrent=true";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("bqcBQC123")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setSuperEntityClass("org.dev.framework.core.entity.TenantEntity")//自定义实体父类
                .setSuperEntityColumns("tenant_id","is_del","create_time","create_by","update_time","update_by","memo")
                .setLogicDeleteFieldName("is_del")
                .setCapitalMode(true)
                .setEntityLombokModel(true) // 【实体】是否为lombok模型（默认 false)
                //.setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true)//设置RestController
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("dean.x.liu")
                .setOutputDir("F://MpGenerator")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}