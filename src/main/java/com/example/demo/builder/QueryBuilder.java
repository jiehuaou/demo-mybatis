package com.example.demo.builder;

public class QueryBuilder {
    public String hello() {
        String sql = "SELECT * FROM emp limit 3";
        return sql;
    }
}
