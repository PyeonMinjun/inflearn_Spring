import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [response, setResponse] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const onGoogleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  }

  const testApi = () => {
    console.log("API 요청 시작...");
    setLoading(true);
    setError(null);

    axios
    .get("http://localhost:8080/my", {
      withCredentials: true,
      headers: {
        'X-Requested-With': 'XMLHttpRequest'  // AJAX 요청임을 표시
      }// 쿠키를 요청에 포함
    })
    .then((res) => {
      console.log("응답 받음:", res);
      setResponse(res.data);
      setLoading(false);
    })
    .catch((error) => {
      if (error.response && error.response.status === 401) {
        // 인증 필요 시 로그인 페이지로 이동
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
        return;
      }
      // 다른 오류 처리
      setError(error.message || "알 수 없는 오류");
    });
  }

  return (
      <div className="App">
        <header className="App-header">
          <h1>OAuth2 & JWT 테스트</h1>
          <button onClick={onGoogleLogin} className="login-button">구글 로그인</button>
          <div className="button-group">
            <button onClick={testApi} disabled={loading}>
              {loading ? '요청 중...' : 'My API 테스트'}
            </button>
          </div>
          <div className="response-container">
            <h3>API 응답 결과:</h3>
            {loading && <p className="loading">로딩 중...</p>}
            {error && <p className="error">오류: {error}</p>}
            {response && (
                <div className="response-data">
                  <p>응답 타입: {typeof response}</p>
                  <pre>{typeof response === 'string' ? response : JSON.stringify(response, null, 2)}</pre>
                </div>
            )}
          </div>
        </header>
      </div>
  );
}

export default App;