package com.template.chip.component.mybatis;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @author guozhenquan
 */
public class CodeGenerator {
   private static final String SUPER_SERVICE_CLASS = "com.baomidou.mybatisplus.extension.service.IService";
   private static final String SUPER_SERVICE_IMPL_CLASS = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
   private static final String SUPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

    private static final List<String> TABLES = Lists.newArrayList("t_a");
    private static final String PREFIX = "T";
    private static final String AUTHOR = "guozhenquan";
    private static final String OUTPUT_DIR = "D://a";

    private static final String URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

   private static final String PARENT_PACKAGE =  "com.template.chip.repository";


    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(URL, USERNAME, PASSWORD);




    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(OUTPUT_DIR); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(PARENT_PACKAGE)// 设置父包名// 设置父包模块名
                            .entity("po").pathInfo(Collections.singletonMap(OutputFile.mapperXml, OUTPUT_DIR)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLES)
                            .entityBuilder().convertFileName(entityName -> getReplace(entityName) + "PO")
                            .entityBuilder().enableLombok()

                            .controllerBuilder()
                            .convertFileName(fileName -> getReplace(fileName) + "Controller")

                            .serviceBuilder()
                            .superServiceClass(SUPER_SERVICE_CLASS)
                            .superServiceImplClass(SUPER_SERVICE_IMPL_CLASS)
                            .convertServiceFileName(serviceFileName -> "I" + getReplace(serviceFileName) + "Dao")
                            .convertServiceImplFileName(serviceImplFileName -> getReplace(serviceImplFileName) + "DaoImpl")

                            .mapperBuilder()
                            .superClass(SUPER_CLASS)
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .convertMapperFileName(mapperFileName -> "I" + getReplace(mapperFileName) + "Mapper")
                            .convertXmlFileName(mapperFileName -> getReplace(mapperFileName) + "Mapper")
                            .build();
                })
                .templateConfig(builder -> builder
                        .disable(TemplateType.ENTITY)
                        .entity("/templates/entity.java")
                        .service("/templates/service.java")
                        .serviceImpl("/templates/serviceImpl.java")
                        .mapper("/templates/mapper.java")
                        .mapperXml("/templates/mapper.xml")
                        .controller("/templates/controller.java"))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static String getReplace(String entityName) {
        return entityName.replace(PREFIX, "");
    }
}
