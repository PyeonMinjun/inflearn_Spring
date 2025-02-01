# 유미님의 스프링 시큐리티를 참조하여 JWT를 학습한 내용입니다.


### 로그인 모식도
![img.png](static/img.png)

### 스프링 시큐리티 필터 동작 원리
스프링 시큐리티는 클라이언트의 요청이 여러개의 필터를 거쳐 DispatcherServlet(Controller)으로 향하는 중간 필터에서 요청을 가로챈 후 검증(인증/인가)을 진행한다.

클라이언트 요청 -> 서블릿 필터 -> 서블릿(컨트롤러)

![img_1.png](static/img_1.png)


### Delegating Filter Proxy
- 서블릿 컨테이너 (톰캣)에 존재하는 필터 체인에 DelegatingFilter를 등록한 뒤 모든 요청을 가로챈다.

![image2.jpg](static/image2.jpg)


- 서블릿 필터 체인의 DelegatingFilter → Security 필터 체인 (내부 처리 후) → 서블릿 필터 체인의 DelegatingFilter

- 가로챈 요청은 SecurityFilterChain에서 처리 후 상황에 따른 거부, 리디렉션, 서블릿으로 요청 전달을 진행한다.