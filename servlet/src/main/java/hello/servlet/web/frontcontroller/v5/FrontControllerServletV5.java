package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
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

@WebServlet(urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> hadlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        hadlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        hadlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        hadlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        hadlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV3());
        hadlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV3());
        hadlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV3());

    }

    private void initHandlerAdapters() {
        // 어댑터 v3만 넣어둠
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //MemberFormControllerV3
        Object handle = getHandler(request);
        if (handle == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
//        ControllerV3HandlerAdapter 반환
        MyHandlerAdapter adapter = getHandlerAdapter(handle);
//        ControllerV3HandlerAdapter handle 호출
        ModelView mv = adapter.handle(request, response, handle);

        String viewName = mv.getViewName();
        MyView view= viewResolver(viewName);
        view.render(mv.getModel(),request, response);

    }

    private MyHandlerAdapter getHandlerAdapter(Object handle) {
//        MemberFormControllerV3
        for (MyHandlerAdapter adapter : handlerAdapters) {
            // 컨트롤러 v3 어댑터 호출 함
            if (adapter.supports(handle)) { // true로 반환
                return adapter;
            }
        }
        throw new IllegalArgumentException("핸들러어뎁터 찾을 수 없음 =" + handle);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return hadlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
