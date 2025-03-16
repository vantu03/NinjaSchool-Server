package com.tgame.ninja.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);

    private final HikariDataSource dataSource;

    private HikariPoolMXBean poolProxy;

    public String username;

    public String dbname;

    public ConnectionPool(String host, String username, String passwd, String dbname, int maxPool, boolean isReadOnly, boolean registerMbean) {
        this.username = username;
        this.dbname = dbname;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + "/" + dbname + "?characterEncoding=utf-8");
        config.setUsername(username);
        config.setPassword(passwd);
        config.setMaximumPoolSize(maxPool);
        config.setReadOnly(isReadOnly);
        config.setPoolName(username + "_" + dbname);
        config.setConnectionTimeout(60000);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("cachePrepStmts", true);
        if (registerMbean) {
            config.setRegisterMbeans(true);
            try {
                MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
                ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool (" + username + "_" + dbname + ")");
                poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public long getIdleCount() {
        if (poolProxy != null) {
            return poolProxy.getIdleConnections();
        }
        return -1;
    }

    public long getActiveCount() {
        if (poolProxy != null) {
            return poolProxy.getActiveConnections();
        }
        return -1;
    }
}
