package hello.jdbc.repository;


import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;


/**
    jdbc : jdbcUtils
 **/
@Slf4j
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            closs(con, pstmt, null);
        }
    }

    /*
    jdbc 조회 : jdbc 드라이버 생성
    */
    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));

                return member;
            } else {
                throw new NoSuchElementException("member not found memberId= " + memberId);
            }
        } catch (SQLException e) {
            log.info("DB error", e);
            throw e;
        } finally {
            closs(con, pstmt, rs);
        }
    }



    /*
    jdbc 수정 : jdbc 드라이버 생성
    */
    public void update(String memberId, int money ) throws SQLException {
        String sql = "update member set money = ? where  member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(2, memberId);
            pstmt.setInt(1, money);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}" , resultSize);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            closs(con, pstmt, null);
        }
    }







    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id= ?";

        Connection con = null;
        PreparedStatement pstmt = null;


        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            closs(con,pstmt,null);
        }
    }



    private void closs(Connection con, PreparedStatement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        //주의!!!  트랜잭션 동기화를 사용하려면  DataSourceUtils를 사용해야함
        DataSourceUtils.releaseConnection(con, dataSource);

    }
    private Connection getConnection() throws SQLException {
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다
        Connection con = DataSourceUtils.getConnection(dataSource);
//        Connection con= dataSource.getConnection();
        log.info("get connection = {}, class = {}" , con ,con.getClass());
        return con;
    }
}