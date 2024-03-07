package hello.SPRINGMVC2.basic.requestMapping.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@Slf4j
public class RequestParamV1 {

    @RequestMapping("/request-param-v1")
    public void requestParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {} , age = {}", name,age);

        response.getWriter().write("ok");

    }
}
