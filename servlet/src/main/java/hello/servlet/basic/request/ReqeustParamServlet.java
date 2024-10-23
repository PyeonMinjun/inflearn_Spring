package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/request-param")
public class ReqeustParamServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));


        String name = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println(name);
        System.out.println(age);


        String[] usernames = request.getParameterValues("username");

        for (String username : usernames) {
            System.out.println(username);
        }

        }


}

