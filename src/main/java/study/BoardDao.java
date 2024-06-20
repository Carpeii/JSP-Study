package study;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDao {
    private DataSource dataSource;

    public BoardDao() {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup( "java:comp/env" );
            this.dataSource = (DataSource)envCtx.lookup( "jdbc/mariadb2" );
        } catch (NamingException e) {
            System.out.println( "[에러] " + e.getMessage() );
        }
    }
}
