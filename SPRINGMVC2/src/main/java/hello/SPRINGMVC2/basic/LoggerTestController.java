package hello.SPRINGMVC2.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoggerTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("log-test")
    public String logTest() {
        String name = "Spring";
        System.out.println("name = " + name);

        log.trace("trace log= {}",name);
        log.debug("debug log = {}", name); // 개발 서버
        log.info("info log = {}", name);  // 중요한 정, 비즈니스 정보 , 운영
        log.warn("warn log = {}", name); // 경고
        log.error("error log= {}" , name); // erreor 알람을 보고나 별도로 파일로 봄.

        return "ok";
    }
}
