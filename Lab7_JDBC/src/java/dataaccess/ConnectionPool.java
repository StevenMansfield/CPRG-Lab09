package dataaccess;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;

public class ConnectionPool {
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    private ConnectionPool() {
        try {
            //retrieves the context.xml file
            InitialContext ic = new InitialContext();
            //looking for this environment variable. This must match the name in context.xml (after the env/). This will change from project to project.  
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/userdb");
        } catch (NamingException e) {
            System.out.println(e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        //singleton pattern to create a connection pool. If no pool, make one, otherwise use the existing pool.
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }
    
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
