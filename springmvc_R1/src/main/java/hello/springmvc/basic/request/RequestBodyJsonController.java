package hello.springmvc.basic.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("msgBody ={}", msgBody);
        HelloData helloData = objectMapper.readValue(msgBody, HelloData.class);
        log.info("helloData = {}" , helloData);
        response.getWriter().write("ok");

    }
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String msgBody) throws IOException {


        log.info("msgBody ={}", msgBody);
        HelloData helloData = objectMapper.readValue(msgBody, HelloData.class);
        log.info("helloData = {}" , helloData);

        return "ok";

    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("usernamge = {}, age = {}" , helloData.getName(),helloData.getAge());

        return "ok";

    }




}
