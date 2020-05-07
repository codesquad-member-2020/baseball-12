import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';

const Login = () => {
  const [isLogin, setIsLogin] = useState(false);
  localStorage.setItem('id', 'ari');
  const getIsLogin = () => {
    if (!localStorage.getItem('id')) return setIsLogin(false);
    return setIsLogin(true);
  };
  useEffect(() => {
    getIsLogin();
  }, []);

  return (
    <div>
      {isLogin && <Redirect to="/team" />}
      로그인 페이지
    </div>
  );
};

export default Login;
