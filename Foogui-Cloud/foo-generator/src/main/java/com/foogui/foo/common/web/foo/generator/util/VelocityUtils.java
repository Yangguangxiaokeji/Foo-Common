package com.foogui.foo.common.web.foo.generator.util;

import com.foogui.foo.common.web.foo.generator.domain.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板工具类
 *
 * @author ruoyi
 */
public class VelocityUtils {
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";


    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(Table table) {
        String moduleName = table.getModuleName();
        String businessName = table.getBusinessName();
        String packageName = table.getPackagePath();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", table.getTableName());
        String functionName = table.getFunctionName();
        velocityContext.put("functionName", StringUtils.isNotBlank(functionName) ? functionName : "XX功能");
        velocityContext.put("ClassName", table.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(table.getClassName()));
        velocityContext.put("moduleName", moduleName);
        velocityContext.put("BusinessName", StringUtils.capitalize(businessName));
        velocityContext.put("businessName", businessName);
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", table.getAuthor());
        velocityContext.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        velocityContext.put("pkColumn", table.getPk());
        // velocityContext.put("importList", getImportList(table));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("table", table);
        return velocityContext;
    }



    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList() {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        // templates.add("vm/sql/sql.vm");
        // templates.add("vm/js/api.js.vm");

        templates.add("vm/vue/index.vue.vm");

        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, Table table) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = table.getPackagePath();
        // 模块名
        String moduleName = table.getModuleName();
        // 大写类名
        String className = table.getClassName();
        // 业务名称
        String businessName = table.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;

        if (template.contains("domain.java.vm")) {

            fileName =javaPath+"/domain/"+ className+".java";
        }
         else if (template.contains("mapper.java.vm")) {
            fileName =javaPath+"/mapper/"+ className+"Mapper.java";
        } else if (template.contains("service.java.vm")) {
            fileName =javaPath+"/service/"+ className+"Service.java";
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName =javaPath+"/service/impl/"+ className+"ServiceImpl.java";
        } else if (template.contains("controller.java.vm")) {
            fileName =javaPath+"/controller/"+ className+"Controller.java";
        } else if (template.contains("mapper.xml.vm")) {
            fileName =mybatisPath+"/"+className+"Mapper.xml";
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, 0, lastIndex);
    }




}
