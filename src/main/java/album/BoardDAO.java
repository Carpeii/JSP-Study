package album;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {
    private DataSource dataSource;

    public BoardDAO() {
        // TODO Auto-generated constructor stub
        // 데이터베이스 연결

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup( "java:comp/env" );
            this.dataSource = (DataSource)envCtx.lookup( "jdbc/mariadb2" );
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            System.out.println( "[에러] " + e.getMessage() );
        }
    }

    public void boardWrite() {}

    public int boardWriteOk(BoardTO to) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        int flag = 1;
        try {
            conn = this.dataSource.getConnection();

            String sql = "insert into album values ( 0, ?, ?, ?, password( ? ), ?, ?, 0, ?, now() )";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSubject() );
            pstmt.setString( 2, to.getWriter() );
            pstmt.setString( 3, to.getMail() );
            pstmt.setString( 4, to.getPassword() );
            pstmt.setString( 5, to.getFileName() );
            pstmt.setString( 6, to.getContent() );
            pstmt.setString( 7, to.getWip() );

            if( pstmt.executeUpdate() == 1 ) {
                flag = 0;
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }

        return flag;
    }

    public ArrayList<BoardTO> boardList() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<BoardTO> boardLists = new ArrayList<BoardTO>();

        try {
            conn = this.dataSource.getConnection();

            String sql = "select seq, subject, writer, date_format(wdate, '%Y-%m-%d') wdate, filename ,hit, datediff( now(), wdate ) wgap from album order by seq desc";
            pstmt = conn.prepareStatement( sql );

            rs = pstmt.executeQuery();

            while( rs.next() ) {
                BoardTO to = new BoardTO();
                to.setSeq( rs.getString( "seq" ) );
                to.setSubject( rs.getString( "subject" ) );
                to.setWriter( rs.getString( "writer" ) );
                to.setWdate( rs.getString( "wdate" ) );
                to.setHit( rs.getString( "hit" ) );
                to.setWgap( rs.getInt( "wgap" ) );
                to.setFileName(rs.getString( "filename" ) );
                boardLists.add( to );
            }

        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( rs != null ) try { rs.close(); } catch( SQLException e ) {}
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }
        return boardLists;
    }

    public BoardTO boardView(BoardTO to) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.dataSource.getConnection();

            String sql = "update album set hit=hit+1 where seq=?";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );

            pstmt.executeUpdate();

            sql = "select subject, writer, mail, wip, wdate, hit, filename, content from album where seq=?";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );

            rs = pstmt.executeQuery();

            if( rs.next() ) {
                to.setSubject( rs.getString( "subject" ) );
                to.setWriter( rs.getString( "writer" ) );
                to.setMail( rs.getString( "mail" ) );
                to.setWip( rs.getString( "wip" ) );
                to.setWdate( rs.getString( "wdate" ) );
                to.setHit( rs.getString( "hit" ) );
                to.setFileName(rs.getString( "filename" ) );
                to.setContent( rs.getString( "content" ).replaceAll( "\n", "<br />" ) );
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( rs != null ) try { rs.close(); } catch( SQLException e ) {}
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }

        return to;
    }

    public BoardTO boardModify(BoardTO to) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "select subject, writer, mail ,filename ,content from album where seq=?";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );

            rs = pstmt.executeQuery();

            if( rs.next() ) {
                to.setSubject( rs.getString( "subject" ) );
                to.setWriter( rs.getString( "writer" ) );
                to.setMail( rs.getString("mail") );
                to.setFileName(rs.getString("filename"));
                to.setContent( rs.getString( "content" ) );
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( rs != null ) try { rs.close(); } catch( SQLException e ) {}
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }

        return to;
    }
    public String getFileName(BoardTO to){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String filename = "";
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/mariadb2");

            conn = dataSource.getConnection();

            String sql = "select filename from album where seq=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, to.getSeq());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                filename = rs.getString("filename");
            }
        }catch (NamingException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return filename;
    }
    public int boardModifyOk(BoardTO to, String newFileName) {
        String uploadPath = "/Users/kimjiwoong/javaprojects/myJSP/src/main/webapp/upload";
        Connection conn = null;
        PreparedStatement pstmt = null;

        int flag = 2;

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup( "java:comp/env" );
            DataSource dataSource = (DataSource)envCtx.lookup( "jdbc/mariadb2" );

            conn = dataSource.getConnection();

            String oldFileName = this.getFileName(to);
            // update
            if( newFileName != null ) {
                String sql = "update album set subject=?, mail=?, content=?, filename=? where seq=? and password=password( ? )";

                pstmt = conn.prepareStatement( sql );
                pstmt.setString( 1, to.getSubject() );
                pstmt.setString( 2, to.getMail() );
                pstmt.setString( 3, to.getContent());
                pstmt.setString( 4, newFileName );
                pstmt.setString(5, to.getSeq());
                pstmt.setString(6, to.getPassword());

            } else {
                String sql = "update album set subject=?, mail=?, content=? where seq=? and password=password( ? )";

                pstmt = conn.prepareStatement( sql );
                pstmt.setString( 1, to.getSubject() );
                pstmt.setString( 2, to.getMail() );
                pstmt.setString( 3, to.getContent() );
                pstmt.setString(4, to.getSeq());
                pstmt.setString( 5, to.getPassword() );
            }

            int result = pstmt.executeUpdate();
            if( result == 0 ) {
                flag = 1;

                if( newFileName != null ) {
                    File file = new File( uploadPath, newFileName );
                    file.delete();
                }
            } else if( result == 1 ) {
                flag = 0;

                if( newFileName != null && oldFileName != null ) {
                    File file = new File( uploadPath, oldFileName );
                    file.delete();
                }
            }
        } catch( NamingException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( pstmt != null ) {
                try {pstmt.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
            if( conn != null ) {
                try { conn.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
        }
        return flag;
    }

    public BoardTO boardDelete(BoardTO to) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "select subject, writer from album where seq = ?";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );

            rs = pstmt.executeQuery();
            if( rs.next() ) {
                to.setSubject( rs.getString( "subject" ) );
                to.setWriter( rs.getString( "writer" ) );
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] : " + e.getMessage() );
        } finally {
            if( rs != null ) try { rs.close(); } catch( SQLException e ) {}
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }

        return to;
    }

    public int boardDeleteOk(BoardTO to) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        int flag = 2;
        try {
            conn = this.dataSource.getConnection();

            String sql = "delete from album where seq=? and password=password( ? )";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );
            pstmt.setString( 2, to.getPassword() );

            int result = pstmt.executeUpdate();
            if( result == 0 ) {
                flag = 1;
            } else if( result == 1 ) {
                flag = 0;
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }

        return flag;
    }
    public BoardTO getPreviousPost(BoardTO to) {
        BoardTO previousTo = new BoardTO();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.dataSource.getConnection();

            String sql = "SELECT seq, subject FROM album WHERE seq < ? ORDER BY seq DESC LIMIT 1;";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );

            rs = pstmt.executeQuery();
            if( rs.next() ) {
                previousTo.setSeq( rs.getString( "seq" ) );
                previousTo.setSubject( rs.getString( "subject" ) );
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] : " + e.getMessage() );
        } finally {
            if( rs != null ) try { rs.close(); } catch( SQLException e ) {}
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }
        return previousTo;
    }
    public BoardTO getNextPost(BoardTO to){
        BoardTO nextTo = new BoardTO();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.dataSource.getConnection();

            String sql = "SELECT seq, subject FROM album WHERE seq > ? ORDER BY seq ASC LIMIT 1;";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSeq() );

            rs = pstmt.executeQuery();
            if( rs.next() ) {
                nextTo.setSeq( rs.getString( "seq" ) );
                nextTo.setSubject( rs.getString( "subject" ) );
            }
        } catch( SQLException e ) {
            System.out.println( "[에러] : " + e.getMessage() );
        } finally {
            if( rs != null ) try { rs.close(); } catch( SQLException e ) {}
            if( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
            if( conn != null ) try { conn.close(); } catch( SQLException e ) {}
        }
        return nextTo;
    }
}
