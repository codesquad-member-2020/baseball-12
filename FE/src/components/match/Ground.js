import React, { useContext, useState, useEffect } from 'react';
import styled from 'styled-components';
import Filed from './canvas/Filed';
import PitchContext from '../../contexts/pitchContext';
import OutCount from './OutCount';
import AnimationFiled from './AnimationFiled';

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
  padding: 10px 20px;
  color: #000;
  background: ${(props) => props.theme.mainFontColor};
  transition: transform 0.5s;
  transform: translate(-50%, -50%) scale(1);
  transform-origin: center;
  &:hover {
    transform: translate(-50%, -50%) scale(1.2);
  }
`;

const Inning = styled.span`
  position: absolute;
  right: 5%;
  top: 5%;
  font-size: 15px;
`;

const Ground = ({ gameId }) => {
  const { pitchState, pitchDispatch } = useContext(PitchContext);
  const handlePitch = async () => {
    const response = await fetch(process.env.REACT_APP_PITCH, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ gameId }),
    });
    const fetchData = await response.json();
    if (!fetchData.status) return; //error
    pitchDispatch(fetchData);
  };
  return (
    <Wrap>
      <OutCount />
      {pitchState && (
        <Inning>
          {pitchState.statusBoard.inning}회{' '}
          {pitchState.statusBoard.firsthalf ? '초' : '말'}
        </Inning>
      )}
      <Filed width={400} height={400} />
      <AnimationFiled />
      <Pitch onClick={handlePitch}>Pitch!</Pitch>
    </Wrap>
  );
};

export default Ground;
