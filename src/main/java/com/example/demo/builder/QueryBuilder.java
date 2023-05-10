package com.example.demo.builder;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;

@Slf4j
public class QueryBuilder implements ProviderMethodResolver {
    public String selectOnly3() {
        return new SQL(){{
            SELECT("*");
            FROM("EMP");
            LIMIT(3);
        }}.toString();
//        String sql = "SELECT * FROM emp limit 3";
//        return sql;
    }

    /**
     * generate "where" condition only when parameter is not null
     */
    public String selectWithCondition(final Long depId, final String career) {
        String sql = new SQL(){{
            SELECT("*");
            FROM("EMP");
            if(depId!=null) {
                WHERE("dep_id=#{depId}");
            }
            if(career!=null) {
                WHERE("career=#{career}");
            }
        }}.toString();
        log.info("sql ==> {}", sql);
        return sql;
    }

}
