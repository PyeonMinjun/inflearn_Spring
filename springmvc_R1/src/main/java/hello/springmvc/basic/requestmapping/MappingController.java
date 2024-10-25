package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    //hello-basic 에 매핑후 ok 찍기
    // 로거 출력

    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");

        return "ok";
    }

    @GetMapping(value = "/mapping-getV2")
    public String mappingGetV2(){
        log.info("helloBasic");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략
     *
     * @PathVariable("userId") String userId -> @PathVariable userId
     * /mapping/userA : url자체에 값이 들어가 있을때
     */
    @GetMapping(value = "/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("userId ={}" ,userId);
        return "ok";
    }


    @GetMapping(value = "/mapping/{userId}/{orderId}")
    public String mappingPathV2(@PathVariable String userId,
                                @PathVariable String orderId) {
        log.info("userId ={}" ,userId);
        log.info("orderId ={}" ,orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */

    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }
}
