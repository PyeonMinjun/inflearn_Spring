package hello.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {


    @Test
    void UnChecked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void PrintEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex", e);

        }
    }



    class Controller{
        Service service = new Service();

        public void request(){
            service.logic();
        }
    }
    class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    class NetworkClient{

        public void call(){
            throw new RuntimeConnectException("연결 실패");
        }
    }

    class Repository{
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        private void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends java.lang.RuntimeException{

        public RuntimeConnectException(String message) {
            super(message);
        }
    }
    static class RuntimeSQLException extends java.lang.RuntimeException{
        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }

}
