# OAuth2 Code Grant 방식의 동작 순서

1. 로그인 페이지
2. 성공 후 코드 발급 (redirect_url)
3. 코드를 통해 Access 토큰 요청
4. Access 토큰 발급 완료
5. Access 토큰을 통해 유저 정보 요청
6. 유저 정보 획득 완료


# 세션 방식에서 OAuth2 클라이언트 동작 원리
![image](https://github.com/user-attachments/assets/c918269c-daf3-4b8a-af6e-05aaeada36cb)
세션 방식에서는 로직을 구성하는데 큰 고민 없이 진행할 수 있다. 로그인이 성공하면 세션을 생성하면 앞으로의 요청에 대해서도 사용자를 알아챌 수 있다.

# 양뱡향 키로 설정시 access 토큰은 헤더에다가 보내면 redirect되어 헤더가 유지되지않는다!
[헤더 유지 및 프래그먼트 고찰 글](https://blush-winter-59b.notion.site/1bbeac468a2d80b7a759cdd7205b2bb3?pvs=4)



# JWT 방식에서 OAuth2 클라이언트 구성시 고민점
JWT 방식에서는 로그인(인증)이 성공하면 JWT 발급 문제와 웹/하이브리드/네이티브앱별 특징에 의해 OAuth2 Code Grant 방식 동작의 책임을 프론트엔드 측에 둘 것인지 백엔드 측에 둘 것인지 많은 고민을 한다.

- 로그인(인증)이 성공하면 JWT를 발급해야 하는 문제
  프론트단에서 로그인 경로에 대한 하이퍼링크를 실행하면 소셜 로그인창이 등장하고 로그인 로직이 수행된다.
  로그인이 성공되면 JWT가 발급되는데 하이퍼링크로 실행했기 때문에 JWT를 받을 로직이 없다. (해당 부분에 대해 redirect_url 설정에 따라 많은 고민이 필요합니다.)
  API Client(axios, fetch)로 요청 보내면 백엔드측으로 요청이 전송되지만 외부 서비스 로그인 페이지를 확인할 수 없다.


# 웹/하이브리드/네이티브앱별 특징
  웹에서 편하게 사용할 수 있는 웹페이지가 앱에서는 웹뷰로 보이기 때문에 UX적으로 안좋은 경험을 가질 수 있다.
  앱 환경에서 쿠키 소멸 현상

위와 같은 문제로 OAuth2 Code Grant 방식 동작에 대한 redirect_url, Access 토큰 발급 문제를 어느단에서 처리해야 하는지에 대한 구현이 많고 넷상에 잘못된 구현 방법도 많이 있다.
잘못된 구현 방법과 구현되어 있는 모든 방법을 아래에서 알아보자.


# 프론트/ 백 책임 분배 
- 모든 책임을 프론트가 맡음
  ![image](https://github.com/user-attachments/assets/b1c59332-1ad1-4891-adbd-ce66cd710139)
프론트단에서 (로그인 → 코드 발급 → Access 토큰 → 유저 정보 획득) 과정을 모두 수행한 뒤 백엔드단에서 (유저 정보 → JWT 발급) 방식으로 주로 네이티브앱에서 사용하는 방식.
→ 프론트에서 보낸 유저 정보의 진위 여부를 따지기 위해 추가적인 보안 로직이 필요하다.

책임을 프론트와 백엔드가 나누어 가짐 : 잘못된 방식 (대부분의 웹 블로그가 이 방식으로 구현)
- 프론트단에서 (로그인 → 코드 발급) 후 코드를 백엔드로 전송 백엔드단에서 (코드 → 토큰 발급 → 유저 정보 획득 → JWT 발급)
- ![image](https://github.com/user-attachments/assets/92626f54-4dd0-4bd1-b774-43d8d6972070)

- 프론트단에서 (로그인 → 코드 발급 → Access 토큰) 후 Access 토큰을 백엔드로 전송 백엔드단에서 (Access 토큰 → 유저 정보 획득 → JWT 발급)
 ![image](https://github.com/user-attachments/assets/84497544-2281-4351-8e4c-5fe71f80af74)

카카오와 같은 대형 서비스 개발 포럼 및 보안 규격에서 위와 같은 코드/Access 토큰을 전송하는 방법을 지양함. (하지만 토이로 구현하기 쉬워 자주 사용한다.)


# 모든 책임을 백엔드가 맡음
![image](https://github.com/user-attachments/assets/89662cc7-6fe7-4fce-951f-715192f8dc76)

프론트단에서 백엔드의 OAuth2 로그인 경로로 하이퍼링킹을 진행 후 백엔드단에서 (로그인 페이지 요청 → 코드 발급 → Access 토큰 → 유저 정보 획득 → JWT 발급) 방식으로 주로 웹앱/모바일앱 통합 환경 서버에서 사용하는 방식.

→ 백엔드에서 JWT를 발급하는 방식의 고민과 프론트측에서 받는 로직을 처리해야 한다.


# 카카오 dev톡에 적혀 있는 프론트/백 책임 분배
구글링을 통해 카카오 dev 톡에 적혀 있는 프론트와 백엔드가 책임을 나눠 가지는 질문에 대한 카카오 공식 답변입니다
![image](https://github.com/user-attachments/assets/e19401b8-1873-4553-9202-97b76597f24a)

![image](https://github.com/user-attachments/assets/8fd891a2-2e10-4445-9906-81797dc3cbe1)

앱에 대해서는 모든 책임을 프론트가 일임하고 코드나 Access 토큰을 전달하는 행위 자체를 지양합니다.
추가적으로 다른 자료들에도 코드나 Access 토큰을 전달하는 행위를 금지하고 있습니다.

 

----
# 동작 모식도 
![image](https://github.com/user-attachments/assets/86071c68-0e6d-4c0e-a914-a81b999aff39)


# 각각의 필터가 동작하는 주소

JWTFilter : 우리가 커스텀해서 등록해야 함
```
모든 주소에서 동작
```

OAuth2AuthorizationRequestRedirectFilter 
```
/oauth2/authorization/서비스명

/oauth2/authorization/naver
/oauth2/authorization/google
```

OAuth2LoginAuthenticationFilter : 외부 인증 서버에 설정할 redirect_uri
```
/login/oauth2/code/서비스명

/login/oauth2/code/naver
/login/oauth2/code/google
```



# OAuth2 클라이언트에서 우리가 구현해야 할 부분
OAuth2UserDetailsService
OAuth2UserDetails
LoginSuccessHandler



# registration과 provider
registration은 외부 서비스에서 우리 서비스를 특정하기 위해 등록하는 정보여서 등록이 필수적입니다.

하지만 provider의 경우 서비스별로 정해진 값이 존재하며 OAuth2 클라이언트 의존성이 유명한 서비스의 경우 내부적으로 데이터를 가지고 있습니다.

(구글, Okta, 페이스북, 깃허브, 등등)


-------------
# 어떻게 FE가 url로만 줘도 처리가 되는가 ?
# Spring Security OAuth2 인증 흐름 상세 설명

OAuth2 인증은 사용자가 제3자 서비스(Google, Facebook 등)의 계정으로 여러분의 애플리케이션에 로그인할 수 있게 해주는 프로토콜입니다. Spring Security에서 이 과정이 어떻게 처리되는지 단계별로 설명해드리겠습니다.

## 1. 인증 흐름 개요

OAuth2의 인증 코드 그랜트 방식은 다음과 같은 단계로 진행됩니다:

1. 사용자가 "Google로 로그인" 버튼 클릭
2. 인증 요청을 Google로 리다이렉트
3. Google에서 인증 후 authorization code와 함께 리다이렉트
4. 백엔드에서 authorization code로 access token 요청
5. 백엔드에서 access token으로 사용자 정보 요청
6. 사용자 정보를 토대로 인증 객체 생성 및 JWT 발급

## 2. Spring Security 필터 체인에서의 처리

Spring Security는 여러 필터들의 체인으로 인증과 인가를 처리합니다. OAuth2 인증에서 중요한 필터는 두 가지입니다:

### 2.1. OAuth2AuthorizationRequestRedirectFilter

이 필터는 `/oauth2/authorization/{provider}`(예: `/oauth2/authorization/google`) 경로에 대한 요청을 감지합니다.

```java
// SecurityConfig.java에서 설정
http.oauth2Login(oauth2 -> oauth2
    .authorizationEndpoint(endpoint -> 
        endpoint.baseUri("/oauth2/authorization"))
    // 다른 설정들...
);
```

처리 과정:

1. 사용자가 `/oauth2/authorization/google`에 접근하면 이 필터가 작동
2. OAuth2 인증 서버 URL을 생성 (scope, client_id, redirect_uri 등 파라미터 포함)
3. 해당 URL로 사용자를 리다이렉트

예를 들어 다음과 같은 URL로 리다이렉트 됩니다:
```
https://accounts.google.com/o/oauth2/auth?
  response_type=code&
  client_id=YOUR_CLIENT_ID&
  scope=profile+email&
  redirect_uri=http://localhost:8080/login/oauth2/code/google&
  state=random_state_value
```

### 2.2. OAuth2LoginAuthenticationFilter

이 필터는 `/login/oauth2/code/{provider}`(예: `/login/oauth2/code/google`) 경로에 대한 요청을 감지합니다. 이는 OAuth2 제공자가 인증 후 리다이렉트하는 경로입니다.

```java
// SecurityConfig.java에서 설정
http.oauth2Login(oauth2 -> oauth2
    .redirectionEndpoint(endpoint -> 
        endpoint.baseUri("/login/oauth2/code/*"))
    // 다른 설정들...
);
```

처리 과정:

1. 사용자가 Google에서 인증 완료 후 `/login/oauth2/code/google?code=인증코드&state=상태값`으로 리다이렉트됨
2. 이 필터가 요청을 가로채서 인증 코드 추출
3. 백엔드에서 이 코드로 Google에 access token 요청
4. access token으로 Google에서 사용자 정보 요청
5. 사용자 정보를 CustomOAuth2UserService로 전달하여 사용자 정보 매핑

## 3. 핵심 컴포넌트 상세 설명

### 3.1. CustomOAuth2UserService

이 서비스는 OAuth2 제공자로부터 받은 사용자 정보를 우리 시스템의 사용자로 변환합니다.

```java
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 1. 기본 OAuth2User 로드 (OAuth2 제공자로부터 사용자 정보 가져옴)
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        // 2. 제공자 구분 (google, facebook 등)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        
        // 3. 제공자별 사용자 정보 처리
        if ("google".equals(provider)) {
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            // ... DB에 저장하거나 조회하는 로직
            
            // 4. 커스텀 OAuth2User 객체 생성해서 반환
            return new CustomOAuth2User(userDto);
        }
        
        return null;
    }
}
```

OAuth2 인증 흐름에서 이 서비스는 다음 시점에 호출됩니다:

- 인증 코드로 access token을 받은 후
- access token으로 사용자 정보를 받은 후
- Authentication 객체를 생성하기 전

### 3.2. CustomSuccessHandler

이 핸들러는 OAuth2 인증이 성공한 후의 처리를 담당합니다.

```java
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final JWTUtil jwtUtil;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                      HttpServletResponse response, 
                                      Authentication authentication) throws IOException {
        
        // 1. 인증된 사용자 정보 추출
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String username = oauth2User.getUsername();
        String role = oauth2User.getAuthorities().iterator().next().getAuthority();
        
        // 2. JWT 토큰 생성
        String token = jwtUtil.createJwt(username, role);
        
        // 3. 쿠키에 JWT 저장
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        // 4. 프론트엔드로 리다이렉트
        response.sendRedirect("http://localhost:3000/");
    }
}
```

이 핸들러는 인증이 성공적으로 완료된 후 다음과 같은 작업을 수행합니다:

- 사용자 정보를 기반으로 JWT 생성
- JWT를 쿠키나 응답 헤더에 포함
- 사용자를 프론트엔드 페이지로 리다이렉트

## 4. 요청과 응답의 처리 방식

이제 OAuth2 인증 과정에서 발생하는 각각의 HTTP 요청과 응답이 어떻게 처리되는지 살펴보겠습니다.

### 4.1. 인증 시작 요청

프론트엔드:
```javascript
function onGoogleLogin() {
  window.location.href = "http://localhost:8080/oauth2/authorization/google";
}
```

백엔드 처리:

1. OAuth2AuthorizationRequestRedirectFilter가 요청을 가로챔
2. 클라이언트 설정에서 Google OAuth2 파라미터를 읽음
3. 인증 URL 생성 후 리다이렉트 응답 반환
4. 브라우저는 자동으로 Google 로그인 페이지로 이동

중요: 이 과정은 컨트롤러에서 처리되지 않고 Spring Security 필터에서 자동으로 처리됩니다.

### 4.2. 인증 코드 콜백 처리

Google 로그인 후, Google은 사용자를 다음 URL로 리다이렉트합니다:
```
http://localhost:8080/login/oauth2/code/google?code=4/abc123...&state=xyz789...
```

백엔드 처리:

1. OAuth2LoginAuthenticationFilter가 요청을 가로챔
2. URL에서 인증 코드(code 파라미터) 추출
3. 인증 코드로 Google에 access token 요청
4. access token으로 Google에서 사용자 정보 요청
5. CustomOAuth2UserService.loadUser() 호출하여 사용자 정보 처리
6. CustomSuccessHandler.onAuthenticationSuccess() 호출하여 인증 완료 처리

### 4.3. 사용자 정보 요청 및 JWT 발급

CustomSuccessHandler에서:
```java
// JWT 생성
String token = jwtUtil.createJwt(username, role);

// 쿠키 생성 및 설정
Cookie cookie = new Cookie("Authorization", token);
cookie.setHttpOnly(true);
cookie.setPath("/");
response.addCookie(cookie);

// 프론트엔드로 리다이렉트
response.sendRedirect("http://localhost:3000/");
```

브라우저는 이제 JWT가 포함된 쿠키와 함께 프론트엔드 페이지로 리다이렉트됩니다.

## 5. application.yml 설정 파일 상세 설명

OAuth2 클라이언트를 설정하는 application.yml 파일:
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            redirect-uri: ${GOOGLE_REDIRECT_URI}
            scope:
              - email
              - profile
```

이 설정은:

- client-id와 client-secret은 Google에 등록된 애플리케이션 식별자
- redirect-uri는 인증 후 Google이 리다이렉트할 URL (일반적으로 /login/oauth2/code/google)
- scope는 Google에 요청할 사용자 정보 범위

## 6. 후속 요청 처리

인증 완료 후 클라이언트가 보내는 API 요청은 JWT를 통해 인증됩니다:
```javascript
// 프론트엔드에서 API 요청 시 쿠키 포함
axios.get('/api/my', {
  withCredentials: true // 쿠키 포함하여 요청
})
```

백엔드에서는 JWTFilter가 이 요청을 처리합니다:
```java
public class JWTFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        // 쿠키에서 토큰 추출
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        
        // 토큰 검증 및 사용자 인증 처리
        if (token != null && !jwtUtil.isExpired(token)) {
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);
            
            // 인증 객체 생성 및 SecurityContext에 저장
            // ...
        }
        
        filterChain.doFilter(request, response);
    }
}
```

이렇게 하면 OAuth2로 최초 인증한 후에는 JWT를 통해 사용자의 인증 상태를 유지할 수 있습니다.

## 요약: 백엔드에서 OAuth2 요청 처리 방식

- 프론트엔드에서 OAuth2 인증 시작 요청은 OAuth2AuthorizationRequestRedirectFilter에서 처리
- OAuth2 제공자로부터의 콜백은 OAuth2LoginAuthenticationFilter에서 처리
- 사용자 정보 변환은 CustomOAuth2UserService에서 처리
- 인증 성공 후 처리는 CustomSuccessHandler에서 처리
- 후속 API 요청은 JWTFilter에서 처리

Spring Security가 이러한 필터들을 자동으로 등록하고 관리하기 때문에, 명시적인 컨트롤러 없이도 OAuth2 인증 흐름이 작동할 수 있습니다.



---
# cors 및 prefilght 
[cors 설정 || prefilght 설명](https://github.com/PyeonMinjun/inflearn_Spring/commit/f2aaf8f8c2a7521385e17d3d828174d93ec6eb9d)
