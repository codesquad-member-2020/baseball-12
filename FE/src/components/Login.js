import React, { useState, useEffect } from 'react';
import { Redirect, Link } from 'react-router-dom';
import styled from 'styled-components';
import BackgroundImg from '../images/baseball_field.png';

const Wrap = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: url(${BackgroundImg}) no-repeat;
  background-size: 100% auto;
`;

const Title = styled.h1`
  font-size: 100px;
  text-align: center;
  color: ${(props) => props.theme.mainFontColor};
  span {
    display: block;
    font-size: 85px;
  }
`;

const ButtonWrap = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 10vh;
`;

const LinkBtn = ({ className, path, children }) => (
  <Link className={className} to={path}>
    {children}
  </Link>
);
const MainButton = styled(LinkBtn)`
  width: 150px;
  padding: 20px 0;
  margin: 5px 0;
  background: ${(props) => props.theme.midGreen};
  border-bottom: 4px solid ${(props) => props.theme.darkGreen};
  border-radius: 10px;
  color: ${(props) => props.theme.white};
  text-align: center;

  &:hover {
    box-shadow: 0px 0px 13px rgba(0, 0, 0, 0.5);
  }
`;

const Login = () => {
  const [isLogin, setIsLogin] = useState(true);
  // localStorage.setItem('id', 'ari');
  // const getIsLogin = () => {
  //   if (!localStorage.getItem('id')) return setIsLogin(false);
  //   return setIsLogin(true);
  // };
  // useEffect(() => {
  //   getIsLogin();
  // }, []);

  return (
    <Wrap>
      <Title>
        <span>온라인</span>야구게임
      </Title>
      {/* {isLogin && <Redirect to="/team" />} */}
      {isLogin ? (
        <ButtonWrap>
          <MainButton path={'/'}>로그아웃</MainButton>
          <MainButton path={'/team'}>게임시작!</MainButton>
        </ButtonWrap>
      ) : (
        <ButtonWrap>
          <MainButton path={'/'}>로그인</MainButton>
        </ButtonWrap>
      )}
    </Wrap>
  );
};

export default Login;
