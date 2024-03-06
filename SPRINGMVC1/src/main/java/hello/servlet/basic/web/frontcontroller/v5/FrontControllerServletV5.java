package hello.servlet.basic.web.frontcontroller.v5;

import hello.servlet.basic.web.frontcontroller.ModelView;
import hello.servlet.basic.web.frontcontroller.MyView;
import hello.servlet.basic.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.basic.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.basic.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.basic.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.basic.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.basic.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

   public FrontControllerServletV5() {
       initHandlerMappingMap();
       initHandlerAdapters();
   }
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //v4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        MemberFormControllerV3
       Object handler = getHandler(request); // 핸들러 매핑정보 호 1. 핸들러 조회
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //ControllerV3HandlerAdapter
        MyHandlerAdapter adapter = getHandlerAdapter(handler); // 핸들러 어댑터 찾아오기. 2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회 v3핸들러찾음

        ModelView mv = adapter.handle(request, response, handler); // 핸들러 호출

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) { // 모든 핸들러를 뒤진다.
            if (adapter.supports(handler)) { // v3가 있으면 supports v3 호출
                return adapter;
            }
        }
        throw new IllegalStateException("handler adapter를 찾을 수 없다. handler="+handler);
    }
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


}

