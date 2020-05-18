import React, { useContext, useState, useEffect } from 'react';
import PitchContext from '../../contexts/pitchContext';
import styled from 'styled-components';
import BatterInfo from './BatterInfo';

const Wrap = styled.div`
  background: rgba(1, 68, 70, 0.5);
  overflow-y: auto;
`;

const BatterHistory = () => {
  const { pitchState } = useContext(PitchContext);

  if (!pitchState) return <Wrap />;
  if (!pitchState.statusBoard.judgement) return <Wrap />;
  return (
    <Wrap>
      <BatterInfo />
    </Wrap>
  );
};

export default BatterHistory;
