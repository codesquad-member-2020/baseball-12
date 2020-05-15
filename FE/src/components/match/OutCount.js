import React, { useContext, useState, useEffect } from 'react';
import styled from 'styled-components';
import PitchContext from '../../contexts/pitchContext';

const Wrap = styled.div`
  position: absolute;
  top: 5%;
  left: 50px;
  li {
    display: flex;
    align-items: center;
    margin-bottom: 5px;
    font-size: 20px;
    > span {
      margin-right: 10px;
    }
    > .circle {
      display: inline-block;
      width: 20px;
      height: 20px;
      border-radius: 50%;
      margin-right: 3px;
      &.strike {
        background: ${(props) => props.theme.mainFontColor};
      }
      &.ball {
        background: ${(props) => props.theme.darkGreen};
      }
      &.out {
        background: red;
      }
    }
  }
`;

const makeCircle = (length, type) => {
  let count = 0;
  const html = [];
  while (length > count) {
    html.push('circle');
    count += 1;
  }
  return html.map((tag, idx) => (
    <span key={idx} className={`${tag} ${type}`}></span>
  ));
};
const OutCount = () => {
  const { pitchState } = useContext(PitchContext);

  return (
    <Wrap>
      <ul>
        <li key="s">
          <span>S</span>
          {!pitchState
            ? ''
            : makeCircle(pitchState.statusBoard.strike, 'strike')}
        </li>
        <li key="b">
          <span>B</span>
          {!pitchState ? '' : makeCircle(pitchState.statusBoard.ball, 'ball')}
        </li>
        <li key="o">
          <span>O</span>
          {!pitchState ? '' : makeCircle(pitchState.statusBoard.out, 'out')}
        </li>
      </ul>
    </Wrap>
  );
};

export default OutCount;
