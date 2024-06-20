package study;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {
    private DataSource dataSource;

    public BoardDao() {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            this.dataSource = (DataSource) envCtx.lookup("jdbc/mariadb2");
        } catch (NamingException e) {
            System.out.println("[에러] " + e.getMessage());
        }
    }

    public BoardTo boardDelete(String seq) {
        BoardTo to = new BoardTo();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "select subject, writer from emot_board1 where seq=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                to.setSubject(rs.getString("subject"));
                to.setWriter(rs.getString("writer"));
            }

        } catch (SQLException e) {
            System.out.println("[에러] " + e.getMessage());
        } finally {
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
        return to;
    }

    public int boardDeleteOk(String seq, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        int flag = 2;
        try {
            conn = dataSource.getConnection();

            String sql = "delete from emot_board1 where seq=? and password=password(?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);
            pstmt.setString(2, password);

            int result = pstmt.executeUpdate();
            if (result == 0) {
                flag = 1;
            } else if (result == 1) {
                flag = 0;
            }

        } catch (SQLException e) {
            System.out.println("[에러] " + e.getMessage());
        } finally {
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
        return flag;
    }

    public BoardListTo boardList(BoardListTo listTo) {
        ArrayList<BoardTo> boardList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int cpage = listTo.getCpage();
        int recordPerPage = listTo.getRecordPerPage();
        int blockPerPage = listTo.getBlockPerPage();

        try {
            conn = dataSource.getConnection();
            String sql = "select seq, subject, writer, emot, date_format(wdate, '%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap from emot_board1 order by seq desc";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            rs.last();
            listTo.setTotalRecord( rs.getRow());
            rs.beforeFirst();

            listTo.setTotalPage( ( ( listTo.getTotalRecord() - 1 ) / recordPerPage ) + 1 );

            //읽을 위치 지정
            int skip = ( cpage - 1 ) * recordPerPage;
            //읽을위치로 커서 이동
            if( skip != 0 ) rs.absolute( skip );

            for (int i = 0; i < recordPerPage && rs.next(); i++) {
                BoardTo to = new BoardTo();
                to.setSeq(rs.getString("seq"));
                to.setSubject(rs.getString("subject"));
                to.setWriter(rs.getString("writer"));
                to.setEmot(rs.getString("emot"));
                to.setWdate(rs.getString("wdate"));
                to.setHit(rs.getString("hit"));
                to.setWgap(rs.getInt("wgap"));
                boardList.add(to);
            }
            listTo.setBoardLists(boardList);

            listTo.setStartBlock( cpage - ( cpage - 1 ) % blockPerPage );
            listTo.setEndBlock( cpage - ( cpage - 1 ) % blockPerPage + blockPerPage - 1 );
            if( listTo.getEndBlock() >= listTo.getTotalPage() ) {
                listTo.setEndBlock( listTo.getTotalPage() );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTo;
    }

    public BoardTo boardModify(String seq) {
        BoardTo to = new BoardTo();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String[] mail = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/mariadb2");

            conn = dataSource.getConnection();

            String sql = "select subject, writer, mail, content, emot from emot_board1 where seq=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                to.setSubject(rs.getString("subject"));
                to.setWriter(rs.getString("writer"));
                to.setMail(rs.getString("mail"));
                to.setContent(rs.getString("content"));
                to.setEmot(rs.getString("emot"));
            }

        } catch (NamingException e) {
            System.out.println("[에러] " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[에러] " + e.getMessage());
        } finally {
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
        return to;
    }
    public int boarddModifyOk(BoardTo to){
        Connection conn = null;
        PreparedStatement pstmt = null;

        int flag = 2;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup( "java:comp/env" );
            DataSource dataSource = (DataSource)envCtx.lookup( "jdbc/mariadb2" );

            conn = dataSource.getConnection();

            String sql = "update emot_board1 set subject=?, mail=?, content=?, emot=? where seq=? and password=password(?)";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSubject() );
            pstmt.setString( 2, to.getMail() );
            pstmt.setString( 3, to.getContent() );
            pstmt.setString( 4, to.getEmot() );
            pstmt.setString( 5, to.getSeq() );
            pstmt.setString( 6, to.getPassword() );

            int result = pstmt.executeUpdate();
            if( result == 0 ) {
                flag = 1;
            } else if( result == 1 ) {
                flag = 0;
            }

        } catch( NamingException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if( conn != null ) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    public BoardTo boardView(String seq){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardTo to = new BoardTo();

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/mariadb2");

            conn = dataSource.getConnection();

            String sql = "update emot_board1 set hit=hit+1 where seq=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            pstmt.executeUpdate();

            sql = "select subject, writer, mail, wip, wdate, hit, content, emot from emot_board1 where seq=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            rs = pstmt.executeQuery();
            if( rs.next() ) {
                to.setSubject(rs.getString( "subject" ));
                to.setWriter(rs.getString( "writer" ));
                to.setMail(rs.getString( "mail" ));
                to.setWip(rs.getString( "wip" ));
                to.setWdate(rs.getString( "wdate" ));
                to.setHit(rs.getString( "hit" ));
                to.setContent(rs.getString( "content" ));
                to.setEmot(rs.getString( "emot" ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NamingException e){
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return to;
    }

    public void boardWrite(){

    }
    public int boardWriteOk(BoardTo to){
        Connection conn = null;
        PreparedStatement pstmt = null;

        int flag = 1;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup( "java:comp/env" );
            DataSource dataSource = (DataSource)envCtx.lookup( "jdbc/mariadb2" );

            conn = dataSource.getConnection();

            String sql = "insert into emot_board1 values (0, ?, ?, ?, password(?), ?, ?, 0, ?, now())";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, to.getSubject() );
            pstmt.setString( 2, to.getWriter() );
            pstmt.setString( 3, to.getMail() );
            pstmt.setString( 4, to.getPassword() );
            pstmt.setString( 5, to.getContent() );
            pstmt.setString( 6, to.getEmot() );
            pstmt.setString( 7, to.getWip() );

            int result = pstmt.executeUpdate();
            if( result == 1 ) {
                flag = 0;
            }

        } catch( NamingException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } catch( SQLException e ) {
            System.out.println( "[에러] " + e.getMessage() );
        } finally {
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if( conn != null ) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }
}
