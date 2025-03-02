import React from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const onNaverLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/naver";
  }

  const testApi = () => {
    console.log("API 요청 시작...");
    axios
    .get("http://localhost:8080/my", {withCredentials: true})
    .then((res) => {
      console.log("응답 받음:", res);
      console.log("응답 데이터:", res.data);
      console.log("응답 타입:", typeof res.data);
      alert(typeof res.data === 'string' ? res.data : JSON.stringify(res.data));
    })
    .catch((error) => {
      console.error("오류 발생:", error);
      alert(error.message ? error.message : error);
    });
  }

  return (
      <div className="App">
        <header className="App-header">
          <h1>Login</h1>
          <button onClick={onNaverLogin}>naver login</button>
          <button onClick={testApi} style={{marginTop: '10px'}}>Test API</button>
        </header>
      </div>
  );
}

export default App;