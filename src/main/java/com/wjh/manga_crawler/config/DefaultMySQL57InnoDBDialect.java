package com.wjh.manga_crawler.config;

import org.hibernate.dialect.MySQL57InnoDBDialect;

public class DefaultMySQL57InnoDBDialect extends MySQL57InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
