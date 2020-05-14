import React from 'react';
import styled from 'styled-components';
import Filed from './canvas/Filed';

const Wrap = styled.div`
  position: relative;
  display: flex;
  width: 100%;
  min-width: 600px;
  align-items: center;
  justify-content: center;
`;

const Pitch = styled.button`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 10px 20px;
  color: #000;
  background: ${(props) => props.theme.mainFontColor};
`;

const Ground = () => {
  return (
    <Wrap>
      <Filed width={400} height={400} />
      <Pitch>Pitch!</Pitch>
    </Wrap>
  );
};

export default Ground;
