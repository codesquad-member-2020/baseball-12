import React, { useContext, useState, useEffect } from 'react';
import styled, { css, keyframes } from 'styled-components';
import PitchContext from '../../contexts/pitchContext';
import { POSITION } from '../../data/animationPosition';
import walker from '../../images/walker.png';

const Wrap = styled.div`
  width: 300px;
  height: 300px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const walk = keyframes`
  0% {
    background-position: 0px;
  }
  100% {
    background-position: 240px;
  }
`;

const Player = styled.div`
  position: absolute;
  width: 40px;
  height: 74px;
  background: url(${walker});
  background-position: 0;
  transition: all 0.5s;
  transform: translate(-50%, -50%);
  ${(props) => {
    if (props.active) {
      return css`
        animation: ${walk} 1s steps(6) infinite;
      `;
    }
  }}
`;

const AnimationFiled = () => {
  const { pitchState } = useContext(PitchContext);
  const initBase = {
    count: 0,
  };
  const [base, setBase] = useState(initBase);
  const [onBase, setOnBase] = useState(false);
  useEffect(() => {
    if (!pitchState) return;
    switch (pitchState.statusBoard.judgement) {
      case 'HIT': {
        setOnBase(true);
        setBase({ count: ++base.count });
      }
      case 'OUT': {
        setOnBase(false);
      }
      default:
        break;
    }
    if (
      pitchState.statusBoard.ball === 3 &&
      pitchState.statusBoard.judgement === 'BALL'
    ) {
      setOnBase(true);
      setBase({ count: ++base.count });
    }
  }, [pitchState]);
  if (!pitchState)
    return (
      <Wrap>
        <Player style={POSITION.base1.start} />
      </Wrap>
    );

  return (
    <Wrap>
      <Player active />
      {console.log(base)}
    </Wrap>
  );
};

export default AnimationFiled;
