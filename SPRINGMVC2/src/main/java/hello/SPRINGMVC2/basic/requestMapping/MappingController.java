package hello.SPRINGMVC2.basic.requestMapping;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 기본 요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     */

    @RequestMapping(value = {"/hello-basic", "/hello-go"}, method = RequestMethod.GET)
    public String helloBasic() {

        log.info("helloBasic");
        return "ok";
    }

    @GetMapping("/hello-basic")
    public String helloBasic2() {
        log.info("helloBasic2");
        return "ok";
    }

    @GetMapping("/hello-basic/{userId}")
    public String helloBasic3(@PathVariable String userId) {
        log.info("userid = {}", userId);
        return "ok";
    }

    @GetMapping("/hello-basic/{userId}/order/{orderId}")
    public String helloBasic4(@PathVariable String userId,
                              @PathVariable String orderId) {
        log.info("userId = {} orderId = {}", userId, orderId);
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

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    @PostMapping(value = "mapping-accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public String mappingAccept() {
        log.info("mappingAccept");
        return "ok";
    }
}
