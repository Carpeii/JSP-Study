package study;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
            Context envCtx = (Context)initCtx.lookup( "java:comp/env" );
            this.dataSource = (DataSource)envCtx.lookup( "jdbc/mariadb2" );
        } catch (NamingException e) {
            System.out.println( "[에러] " + e.getMessage() );
        }
    }

    public void boardDelete(){

    }
    public void boardDeleteOk(){

    }

    public ArrayList<BoardTo> boardList(int cpage){
        ArrayList<BoardTo> boardList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int totalRecord = 0;

        int recordPerPage = 10;
        int totalPage = 0;
        int blockPerPage =5;
        try {
            conn = dataSource.getConnection();
            String sql = "select seq, subject, writer, emot, date_format(wdate, '%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap from emot_board1 order by seq desc";
            pstmt = conn.prepareStatement( sql );

            rs = pstmt.executeQuery();

            rs.last();
            totalRecord = rs.getRow();
            rs.beforeFirst();

            rs = pstmt.executeQuery();

            //읽을 위치 지정
            int skip = (cpage-1)*recordPerPage;
            //읽을위치로 커서 이동
            if(skip!=0){
                rs.absolute(skip);
            }
            for(int i = 0; i < recordPerPage && rs.next(); i++) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return boardList;
    }

    public void boardModify(){

    }
    public void boarddModifyOk(){

    }

    public void boardView(){

    }

    public void boardWrite(){

    }
    public void boardWriteOk(){

    }
}
