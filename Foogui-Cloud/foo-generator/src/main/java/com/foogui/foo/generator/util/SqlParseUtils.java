package com.foogui.foo.generator.util;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlParseUtils {

    public static void main(String[] args) throws JSQLParserException {
        String sql = "CREATE TABLE `t_user`  (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '数据表主键',\n" +
                "  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',\n" +
                "  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',\n" +
                "  `sex` tinyint NULL DEFAULT NULL COMMENT '性别',\n" +
                "  `age` tinyint NULL DEFAULT NULL COMMENT '年龄',\n" +
                "  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',\n" +
                "  `created` datetime NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `modified` datetime NULL DEFAULT NULL COMMENT '修改时间',\n" +
                "  `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',\n" +
                "  `operator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',\n" +
                "  `deleted` tinyint NULL DEFAULT 0 COMMENT '1:有效 -1：无效（删除时置为-1）',\n" +
                "  `version` bigint NULL DEFAULT NULL COMMENT '数据版本号',\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;\n";
        // // 检查sql合法不
        // System.out.println(checkSql(sql));
        // // 查询内容
        // System.out.println(getSelectItems(sql));
        // // 查询条件
        // System.out.println(getWhere(sql));
        // // 查询表和别名的对应关系
        // System.out.println(getTableRelationship(sql));
        System.out.println(getTableName(sql));
        System.out.println(getTableColumns(sql));

    }

    public static SelectBody getSelectBody(String sql) throws JSQLParserException {
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        return select.getSelectBody();
    }

    public static Select getSelect(String sql) throws JSQLParserException {
        return (Select) CCJSqlParserUtil.parse(sql);
    }

    public static Map<String,String> getTableRelationship(String sql) throws JSQLParserException {
        PlainSelect plainSelect = (PlainSelect)getSelectBody(sql);
        // 获取表明集合
        List<String> tableList = getTableList(sql);
        // 获取别名集合
        ArrayList<String> aliasList = new ArrayList<>();
        Alias alias = plainSelect.getFromItem().getAlias();
        aliasList.add(alias!=null?alias.getName():"");
        getJoins(sql).forEach(join->{
            Alias temp = join.getRightItem().getAlias();
            aliasList.add(temp!=null?temp.getName():"");
        });
        Map<String,String>  map = new HashMap<>();
        for (int i = 0; i < tableList.size(); i++) {
            map.put(tableList.get(i),aliasList.get(i));
        }
        return map;
    }


    /**
     * 获取tables的表名
     *
     * @param sql
     * @return
     */
    public static List<String> getTableList(String sql) throws JSQLParserException {
        Select select = getSelect(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(select);
    }

    /**
     * 获取join层级
     *
     * @param sql
     * @return
     */
    public static List<Join> getJoins(String sql) throws JSQLParserException {
        SelectBody selectBody = getSelectBody(sql);
        if (selectBody instanceof PlainSelect) {
            List<Join> joins = ((PlainSelect) selectBody).getJoins();
            // 这里可以进行解析joins foreach
            return joins;
        }
        return new ArrayList<Join>();
    }



    /**
     * 获取limit值
     *
     * @param sql
     * @return
     */
    public static Limit getLimit(String sql) throws JSQLParserException {
        SelectBody selectBody = getSelectBody(sql);
        if (selectBody instanceof PlainSelect) {
            return ((PlainSelect) selectBody).getLimit();
        }
        return null;
    }

    public static Expression getWhere(String sql) throws JSQLParserException {
        SelectBody selectBody = getSelectBody(sql);
        if (selectBody instanceof PlainSelect) {
            return ((PlainSelect) selectBody).getWhere();
        }
        return null;
    }


    /**
     * 获取查询内容
     *
     * @param sql
     * @return
     */
    public static List<SelectItem> getSelectItems(String sql) throws JSQLParserException {
        SelectBody selectBody = getSelectBody(sql);
        if (selectBody instanceof PlainSelect) {
            List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
            return selectItems;
        }
        return null;
    }
    public static boolean checkSql(String sql) {
        try {
            CCJSqlParserUtil.parse(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 得到创建的表名
     *
     * @param sql 创建表sql
     * @return {@link String}
     * @throws JSQLParserException jsqlparser例外
     */
    public static String getTableName(String sql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        CreateTable createTableStatement = (CreateTable) parserManager.parse(new StringReader(sql));
        return createTableStatement.getTable().getName();
    }

    /**
     * 得到字段名集合
     *
     * @param sql 创建表sql
     * @return {@link List}<{@link String}>
     * @throws JSQLParserException jsqlparser例外
     */
    public static List<String> getTableColumns(String sql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        CreateTable createTableStatement = (CreateTable) parserManager.parse(new StringReader(sql));

        String tableName = createTableStatement.getTable().getName();
        List<ColumnDefinition> columnDefinitions = createTableStatement.getColumnDefinitions();
        ArrayList<String> res = new ArrayList<>(columnDefinitions.size());
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            String columnName = columnDefinition.getColumnName();
            res.add(columnName);
        }
        return res;
    }

}
