import React, { useState, useContext, useEffect } from 'react';
import PitchContext from '../../contexts/pitchContext';
import BatterList from './BatterList';
import BatterTitle from './BatterTitle.jsx';
import styled from 'styled-components';

const Batter = styled.div`
  margin: 5px auto;
  width: 95%;
  padding: 5px;
  border: 3px solid ${(props) => props.theme.darkGreen};
  border-radius: 5px;
  background: ${(props) => props.theme.mainFontColor};
  text-align: center;
`;
const BatterInfo = () => {
  const { pitchState } = useContext(PitchContext);
  let count = 0;
  return (
    <div>
      {[...pitchState.offenseTeam.battingRecords].reverse().map((batter) => {
        return (
          <Batter
            key={`${batter.battingOrder}${
              batter.battings[0].judgement
            }${++count}`}
          >
            <BatterTitle batter={batter} />
            <BatterList battings={batter.battings} />
          </Batter>
        );
      })}
    </div>
  );
};

export default BatterInfo;
// battingRecords
