import React from 'react';
import { useHistory, useParams, withRouter } from 'react-router';
import styled from 'styled-components';

const Wrap = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
`;

const Test = styled.p`
  position: absolute;
  top: 10%;
  left: 10%;
  width: 80%;
  height: 50px;
  background: #fff;
`;

const Modal = () => {
  const history = useHistory();
  const { id } = useParams();
  return (
    <Wrap>{id === 'test1' ? <Test>test1</Test> : <Test>test2</Test>}</Wrap>
  );
};

export default Modal;
