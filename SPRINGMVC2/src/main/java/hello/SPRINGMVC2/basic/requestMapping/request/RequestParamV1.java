package hello.SPRINGMVC2.basic.requestMapping.request;

import hello.SPRINGMVC2.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
public class RequestParamV1 {

    @RequestMapping("/request-param-v1")
    public void requestParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {} , age = {}", name, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParam(
            @RequestParam("username") String memberId,
            @RequestParam("age") String memberAge
    ) {
        log.info("memberId = {}, memberAge ={}", memberId, memberAge);

        return "ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("userid = {}, age ={}", username, age);
        return "ok";

    }

    @ResponseBody
    @RequestMapping("/request-param-v4-required")
    public String requestParamV4(@RequestParam(required = true) String username,
                                 @RequestParam(required = false) Integer age) {

        log.info("userid = {}, age ={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamV5(@RequestParam(required = true, defaultValue = "guest") String username,
                                 @RequestParam(required = false, defaultValue = "-1") Integer age

    ) {
        log.info("username = {}, age ={}", username, age);
        return "ok";

    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestMap(@RequestParam Map<String, Object> ParamMap) {
        log.info("username = {}, age = {}", ParamMap.get("username"), ParamMap.get("age"));

        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때
     **/
    @ResponseBody
    @RequestMapping("/model-Attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {

        log.info("HelloData = {}", helloData);

        return "ok";
    }
    /**
     * @ModelAttribute 생략 가능
     * String, int 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute
     */
    @ResponseBody
    @RequestMapping("/model-Attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("HelloData = {}", helloData);
        return "ok";
    }
}
