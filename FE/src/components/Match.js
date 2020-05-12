import React from 'react';
import styled from 'styled-components';
import groundBg from '../images/bg_ground.jpg';
import { Link, useLocation } from 'react-router-dom';

const Wrap = styled.div`
  width: 100%;
  height: 100vh;
  background: ${(props) => props.theme.darkGreen};
`;

const Match = () => {
  const location = useLocation();
  return (
    <Wrap>
      <Link to={{ pathname: '/test/test1', state: { background: location } }}>
        test1
      </Link>
      <Link to={{ pathname: '/test/test2', state: { background: location } }}>
        test2
      </Link>
    </Wrap>
  );
};

export default Match;
