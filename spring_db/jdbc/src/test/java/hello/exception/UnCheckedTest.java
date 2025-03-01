package hello.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_exception(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw(){
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크가 된다.
     */
    static class MyUncheckedException extends RuntimeException{
        public MyUncheckedException(String message) {
            super(message);
        }
    }
    static class  Service{
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아 처리할 수 있다.
         */
        public void callCatch(){
            try{
                repository.call();
            }catch (MyUncheckedException e){
                log.info("예외 처리 , message = {}" ,e.getMessage(),e );
            }
        }


        /**
         * 예외를 잡지 않아도 된다. 자연스럽게 상위로 넘어감
         * 체크 예외와는 다르게 throws 예외 선언을 하지 않아도 된다.
         */
        public void callThrow(){
            repository.call();
        }
    }






    static class Repository{
        public void call(){
            throw  new MyUncheckedException("ex");
        }
    }



}
