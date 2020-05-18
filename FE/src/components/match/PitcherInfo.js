import React, { useContext } from 'react';
import styled from 'styled-components';
import PitchContext from '../../contexts/pitchContext';

const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 20px;
  background: ${(props) => props.theme.darkGreen};
  color: #fff;
`;

const Pitcher = styled.dl`
  font-size: 15px;
  margin: 5px 0;
  > dt {
    margin-bottom: 5px;
    font-size: 20px;
    color: ${(props) => props.theme.midGreen};
  }
  > dd {
    display: inline-block;
    margin-left: 3px;
    color: ${(props) => props.theme.softGreen};
  }
`;

const Batter = styled(Pitcher)``;

const PitcherInfo = ({ game }) => {
  const { pitchState } = useContext(PitchContext);
  const state = pitchState ? pitchState : game;
  return (
    <Wrap>
      <Pitcher>
        <dt>투수</dt>
        <dd>{state.defenseTeam.pitcher.playerName}</dd>
        <dd># {state.defenseTeam.pitcher.pitchingCount}</dd>
      </Pitcher>
      <Batter>
        <dt>타자</dt>
        <dd>{state.offenseTeam.batter.playerName}</dd>
        <dd>
          {state.offenseTeam.batter.plateAppearance}
          타석
          {state.offenseTeam.batter.hitCount}
          안타
        </dd>
      </Batter>
    </Wrap>
  );
};

export default PitcherInfo;
