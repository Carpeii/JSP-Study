package album;

import album.BoardTO;

import java.util.ArrayList;

public class BoardListTo {
    private int cpage;      //현재 페이지
    private int recordPerPage;    //한 페이지에 표시될 항목 수
    private int blockPerPage;   //페이지 네비게이션에서 한번에 표시될 번호의 개수
    private int totalPage;  //전체 페이지 개수
    private int totalRecord;    //전체 레코드의 수
    private int startBlock; //현재 보여지는 페이지 블록의 시작값
    private int endBlock;//현재 보여지는 페이지 블록의 마지막 페이지 번호

    private ArrayList<BoardTO> boardLists;

    public BoardListTo() {
        // TODO Auto-generated constructor stub
        this.cpage = 1;
        this.recordPerPage = 5;
        this.blockPerPage = 5;
        this.totalPage = 1;
        this.totalRecord = 0;
    }

    public int getCpage() {
        return cpage;
    }

    public void setCpage(int cpage) {
        this.cpage = cpage;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public int getBlockPerPage() {
        return blockPerPage;
    }

    public void setBlockPerPage(int blockPerPage) {
        this.blockPerPage = blockPerPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(int startBlock) {
        this.startBlock = startBlock;
    }

    public int getEndBlock() {
        return endBlock;
    }

    public void setEndBlock(int endBlock) {
        this.endBlock = endBlock;
    }

    public ArrayList<BoardTO> getBoardLists() {
         return boardLists;
    }

    public void setBoardLists(ArrayList<BoardTO> boardLists) {
        this.boardLists = boardLists;
    }
}
