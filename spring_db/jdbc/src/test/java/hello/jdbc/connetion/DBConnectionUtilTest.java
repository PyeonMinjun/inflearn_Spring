package hello.jdbc.connetion;

import hello.jdbc.connection.DBConnectionUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

public class DBConnectionUtilTest {


    @Test
    void test() {
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();

    }
}
