package com.fspdfcy.crm.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {

    private static final SqlSessionFactory factory;
    private static final ThreadLocal<SqlSession> t = new ThreadLocal<>();

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        factory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    private SqlSessionUtil() {
    }

    public static SqlSession getSqlSession() {

        SqlSession session = t.get();

        if (session == null) {
            session = factory.openSession();
            t.set(session);
        }
        return session;
    }

    public static void myClose(SqlSession session) {

        if (session != null) {
            session.close();
            t.remove();
        }
    }
}
