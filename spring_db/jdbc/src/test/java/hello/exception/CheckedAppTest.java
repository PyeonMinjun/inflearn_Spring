package hello.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;



public class CheckedAppTest {


    @Test
    void Checked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }



    class Controller{
        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.logic();
        }


    }




    class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws SQLException, ConnectException {
            repository.call();
            networkClient.call();
        }
    }

    class NetworkClient{

        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    class Repository{
        public void call() throws SQLException {
            throw new SQLException("ex");

        }
    }
}
