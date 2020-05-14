import React from 'react';
import styled from 'styled-components';

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
    color: ${(props) => props.theme.softGreen};
  }
`;

const Batter = styled(Pitcher)``;

const PitcherInfo = ({ game }) => {
  return (
    <Wrap>
      <Pitcher>
        <dt>투수</dt>
        <dd>{game.defenseTeam.pitcher.playerName}</dd>
        <dd># {game.defenseTeam.pitcher.pitchingCount}</dd>
      </Pitcher>
      <Batter>
        <dt>타자</dt>
        <dd>{game.offenseTeam.batter.playerName}</dd>
        <dd>
          {game.offenseTeam.batter.plateAppearance} 이닝
          {game.offenseTeam.batter.hitCount} 안타
        </dd>
      </Batter>
    </Wrap>
  );
};

export default PitcherInfo;
