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


----
# 심화

- 단일 토큰의 위험성
- Refresh 토큰의 발급
- Access 토큰 만료시 응답 설정
- Refresh 토큰을 통한 Access 갱신
- Refresh Rotate
- 서버측 주도권의 필요성
- 로그아웃 구현
- 메일 알림
- PKCE

---
# 1. 토큰 사용 추적
   “스프링 시큐리티 JWT” 시리즈를 통해 구현한 단일 토큰의 사용처를 추적하면 아래와 같다.



1. 로그인 성공 JWT 발급 : 서버측 → 클라이언트로 JWT 발급
2. 권한이 필요한 모든 요청 : 클라이언트 → 서버측 JWT 전송

권한이 필요한 요청은 서비스에서 많이 발생한다. (회원 CRUD, 게시글/댓글 CRUD, 주문 서비스, 등등)

따라서 JWT는 매시간 수많은 요청을 위해 클라이언트의 JS 코드로 HTTP 통신을 통해 서버로 전달된다.

해커는 클라이언트 측에서 XSS를 이용하거나 HTTP 통신을 가로채서 토큰을 훔칠 수 있기 때문에 여러 기술을 도입하여 탈취를 방지하고 탈취되었을 경우 대비 로직이 존재합니다.

<br>




# 2. 다중 토큰 : Refresh 토큰과 생명 주기
   위와 같은 문제가 발생하지 않도록 `Access/Refresh` 토큰 개념이 등장한다.



자주 사용되는 토큰의 생명주기는 짧게(약 10분), 이 토큰이 만료되었을 때 함께 받은 Refresh 토큰(24시간 이상)으로 토큰을 재발급.

(생명주기가 짧으면 만료시 매번 로그인을 진행하는 문제가 발생, 생명주기가 긴 Refresh도 함께 발급한다.)  




### 1. 로그인 성공시 생명주기와 활용도가 다른 토큰 2개 발급 : Access/Refresh

`Access 토큰` : 권한이 필요한 모든 요청 헤더에 사용될 JWT로 탈취 위험을 낮추기 위해 약 10분 정도의 짧은 생명주기를 가진다.

`Refresh 토큰` : Access 토큰이 만료되었을 때 재발급 받기 위한 용도로만 사용되며 약 24시간 이상의 긴 생명주기를 가진다.



### 2. 권한이 필요한 모든 요청 : Access 토큰을 통해 요청

Access 토큰만 사용하여 요청하기 때문에 Refresh 토큰은 호출 및 전송을 빈도가 낮음.



### 3. 권한이 알맞다는 가정하에 2가지 상황 : `데이터 응답`, `토큰 만료 응답`



### 4. 토큰이 만료된 경우 Refresh 토큰으로 Access 토큰 발급
[reissue 컨트롤러 작성](https://github.com/PyeonMinjun/inflearn_Spring/commit/8c5e95c233d6123925bc8ed07d09d8849c7e24ca)

Access 토큰이 만료되었다는 요청이 돌아왔을 경우 프론트엔드 로직에 의해 “1”에서 발급 받은 Refresh 토큰을 가지고 서버의 특정 경로(Refresh 토큰을 받는 경로)에 요청을 보내어 Access 토큰을 재발급 받는다.



### 5. 서버측에서는 Refresh 토큰을 검증 후 Access 토큰을 새로 발급한다
<br>



# 2-1. 다중 토큰 구현 포인트
   로그인이 완료되면 successHandler에서 Access/Refresh 토큰 2개를 발급해 응답한다.

각 토큰은 각기 다른 생명주기, payload 정보를 가진다.



Access 토큰 요청을 검증하는 JWTFilter에서 Access 토큰이 만료된 경우는 프론트 개발자와 협의된 상태 코드와 메시지를 응답한다.


프론트측 API 클라이언트 (axios, fetch) 요청시 Access 토큰 만료 요청이 오면 예외문을 통해 Refresh 토큰을 서버측으로 전송하고 Access 토큰을 발급 받는 로직을 수행한다. (기존 Access는 제거)


서버측에서는 Refresh 토큰을 받을 엔드포인트 (컨트롤러)를 구성하여 Refresh를 검증하고 Access를 응답한다.

<br>


# 3. Refresh 토큰이 탈취되는 경우



   단일 → 다중 토큰으로 전환하며 자주 사용되는 Access 토큰이 탈취되더라도 생명주기가 짧아 피해 확률이 줄었다.

하지만 Refresh 토큰 또한 사용되는 빈도만 적을뿐 탈취될 수 있는 확률이 존재한다. 따라서 Refresh 토큰에 대한 보호 방법도 필요하다.



### 1. Access/Refresh 토큰의 저장 위치 고려

로컬/세션 스토리지 및 쿠키에 따라 XSS, CSRF 공격의 여부가 결정되기 때문에 각 토큰 사용처에 알맞은 저장소 설정.


### 2. Refresh 토큰 Rotate
[Refresh rotate](https://github.com/PyeonMinjun/inflearn_Spring/commit/ef193555f624280997cb41237de52288fe8e7265)


Access 토큰을 갱신하기 위한 Refresh 토큰 요청 시 서버측에서에서 Refresh 토큰도 재발급을 진행하여 한 번 사용한 Refresh 토큰은 재사용하지 못하도록 한다.



###  3. Access/Refresh 토큰 저장 위치
   클라이언트에서 발급 받은 JWT를 저장하기 위해 로컬 스토리지와 쿠키에 대해 많은 고려를 한다. 각 스토리지에 따른 특징과 취약점은 아래와 같다.


> ### 로컬 스토리지 : XSS 공격에 취약함 : Access 토큰 저장
> ###  httpOnly 쿠키 : CSRF 공격에 취약함 : Refresh 토큰 저장

(위와 같은 설정은 필수적이지 않습니다. 주관적인 판단에 따라 편하신대로 커스텀하면 됩니다.)



## 고려
JWT의 탈취는 보통 XSS 공격으로 로컬 스토리지에 저장된 JWT를 가져갑니다. 그럼 쿠키 방식으로 저장하면 안전하지 않을까라는 의문이 들지만, 쿠키 방식은 CSRF 공격에 취약합니다. 그럼 각 상황에 알맞게 저장소를 선택합시다.



###  Access 토큰

- Access 토큰은 주로 로컬 스토리지에 저장됩니다. 짧은 생명 주기로 탈취에서 사용까지 기간이 매우 짧고, 에디터 및 업로더에서 XSS를 방어하는 로직을 작성하여 최대한 보호 할 수 있지만 CSRF 공격의 경우 클릭 한 번으로 단시간에 요청이 진행되기 때문입니다.

- 권한이 필요한 모든 경로에 사용되기 때문에 CSRF 공격의 위험보다는 XSS 공격을 받는 게 더 나은 선택일 수 있습니다.




### Refresh 토큰

- Refresh 토큰은 주로 쿠키에 저장됩니다. 쿠키는 XSS 공격을 받을 수 있지만 httpOnly를 설정하면 완벽히 방어할 수 있습니다. 그럼 가장 중요한 CSRF 공격에 대해 위험하지 않을까라는 의구심이 생깁니다.


- 하지만 Refresh 토큰의 사용처는 단 하나인 토큰 재발급 경로입니다.

    CSRF는 Access 토큰이 접근하는 회원 정보 수정, 게시글 CRUD에 취약하지만 토큰 재발급 경로에서는 크게 피해를 입힐 만한 로직이 없기 때문입니다.

#3. Refresh 토큰 Rotate
   위와 같이 저장소의 특징에 알맞은 JWT 보호 방법을 수행해도 탈취 당할 수 있는게 웹 세상입니다. 따라서 생명주기가 긴 Refresh 토큰에 대한 추가적인 방어 조치가 있습니다.

Access 토큰이 만료되어 Refresh 토큰을 가지고 서버 특정 엔드포인트에 재발급을 진행하면 Refresh 토큰 또한 재발급하여 프론트측으로 응답하는 방식이 Refresh Rotate 입니다.

<br>


# 4. 로그아웃과 Refresh 토큰 주도권
####   문제
   로그아웃을 구현하면 프론트측에 존재하는 Access/Refresh 토큰을 제거합니다. 그럼 프론트측에서 요청을 보낼 JWT가 없기 때문에 로그아웃이 되었다고 생각하지만 이미 해커가 JWT를 복제 했다면 요청이 수행됩니다.


위와 같은 문제가 존재하는 이유는 단순하게 JWT를 발급해준 순간 서버측의 주도권은 없기 때문입니다. (세션 방식은 상태를 STATE하게 관리하기 때문에 주도권이 서버측에 있음)



로그아웃 케이스뿐만 아니라 JWT가 탈취되었을 경우 서버 측 주도권이 없기 때문에 피해를 막을 방법은 생명주기가 끝이나 길 기다리는 방법입니다.



#### 방어 방법
위 문제의 해결법은 생명주기가 긴 Refresh 토큰은 발급과 함께 서버측 저장소에도 저장하여 요청이 올때마다 저장소에 존재하는지 확인하는 방법으로 서버측에서 주도권을 가질 수 있습니다.

만약 로그아웃을 진행하거나 탈취에 의해 피해가 진행되는 경우 서버측 저장소에서 해당 JWT를 삭제하여 피해를 방어할 수 있습니다.

(Refresh 토큰 `블랙리스팅`이라고도 부릅니다.)

<br>



# 5. 로그인시 메일 알림
   네이버 서비스를 사용하다 보면 평소에 사용하지 않던 IP나 브라우저에서 접근할 경우 사용자의 계정으로 메일 알림이 발생합니다.



이때 내가 아닐 경우 “아니요”를 클릭하게되면 서버측 토큰 저장소에서 해당 유저에 대한 Refresh 토큰을 모두 제거하여 앞으로의 인증을 막을 수 있습니다.

<br>

# +토큰의 발급 방법과 PKCE

<br>

---
# 추가 내용 

## 1. 웹스토리지, 세션스토리지, 쿠키

둘 다 브라우저에서 제공하는 웹 스토리지 API
 
localStorage:
```java
// 데이터 저장
localStorage.setItem('accessToken', 'eyJhbG...');

// 데이터 조회
const token = localStorage.getItem('accessToken');

특징:
- 브라우저를 닫아도 데이터 유지
- 도메인별로 독립적인 저장소
- XSS 공격에 취약할 수 있음
```

sessionStorage:
```java
// 데이터 저장
sessionStorage.setItem('refreshToken', 'eyJhbG...');

// 데이터 조회
const token = sessionStorage.getItem('refreshToken');

특징:
- 브라우저/탭을 닫으면 데이터 삭제
- 같은 탭에서만 데이터 공유
- 상대적으로 더 안전
```

쿠키(Cookie) 스토리지:

```java
// 서버에서 설정
response.setHeader("Set-Cookie", "token=123; HttpOnly");

특징:
- 서버와 클라이언트 모두 접근 가능
- 모든 HTTP 요청에 자동으로 포함
- 용량 제한: 약 4KB
- httpOnly 설정으로 JS 접근 차단 가능
- 만료일 설정 가능
```

로컬 스토리지
```java
// 클라이언트에서만 설정
localStorage.setItem('token', '123');

특징:
- 클라이언트(브라우저)에서만 접근 가능
- HTTP 요청에 자동 포함되지 않음
- 용량 제한: 약 5~10MB
- JS로만 접근 가능
- 영구 저장 (직접 삭제하기 전까지)
```

# HTTP Only
### HTTP-only 쿠키의 장점

HTTP-only 쿠키는 웹 애플리케이션에서 인증 토큰을 저장하는 가장 안전한 방법 중 하나입니다.

1. 자바스크립트 접근 불가 (XSS 공격 방어)
가장 중요한 장점은 클라이언트 측 자바스크립트로 쿠키 내용에 접근할 수 없다는 것입니다. 일반 쿠키는 document.cookie로 접근할 수 있지만, HTTP-only 쿠키는 불가능합니다.
```
일반 쿠키: 이런 코드로 쿠키 내용 확인 가능
console.log(document.cookie); // "name=value; otherCookie=otherValue"
```
```
// HTTP-only 쿠키: 자바스크립트로 읽을 수 없음
console.log(document.cookie); // HTTP-only 쿠키는 보이지 않음
이는 사이트에 XSS(Cross-Site Scripting) 취약점이 있더라도 공격자가 토큰을 탈취할 수 없게 합니다.
```

2.자동 전송 메커니즘
쿠키는 해당 도메인으로의 모든 HTTP 요청에 자동으로 포함됩니다. 이는 개발자가 토큰을 매 요청마다 수동으로 첨부할 필요가 없다는 의미입니다.
```
쿠키 사용 시: 추가 코드 필요 없음
fetch('/api/data');

// localStorage나 프래그먼트 사용 시: 매번 수동으로 추가해야 함
fetch('/api/data', {
  headers: {
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  }
});
```

# CSS, XSS
- [CSRF, XSS 설명](https://github.com/PyeonMinjun/inflearn_Spring/commit/c18e7c01dda5caea3823743e365fef5082592269)

# 401 status code
- [401 설명](https://github.com/PyeonMinjun/inflearn_Spring/commit/c98e4975ccddbad6b60476f34a8315b6644c1085)
