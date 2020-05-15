import React, { useContext } from 'react';
import styled from 'styled-components';
import { Link, useLocation } from 'react-router-dom';
import PitchContext from '../../contexts/pitchContext';

const Wrap = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: ${(props) => props.theme.opacityNavy};
`;

const ModalLink = styled(Link)`
  position: absolute;
  right: 10px;
  top: 10px;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
  color: ${(props) => props.theme.white};
  font-size: 15px;
  background: ${(props) => props.theme.darkGreen};
`;

const AwayTeam = styled.dl`
  display: flex;
  align-items: center;
  font-size: 50px;
  color: #fff;
  &.player {
    dt {
      &:after {
        position: absolute;
        top: 110%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 15px;
        color: ${(props) => props.theme.mainFontColor};
        content: 'player';
      }
    }
  }
  > dt {
    position: relative;
    margin: 0 30px;
    text-align: center;
  }
  > dd {
    padding: 0 5px;
  }
`;

const HomeTeam = styled(AwayTeam)`
  flex-direction: row-reverse;
`;

const Versus = styled.span`
  display: block;
  margin: 0 20px;
  font-size: 30px;
  color: ${(props) => props.theme.gray};
`;

const Score = ({ player, team, game }) => {
  const location = useLocation();
  const { pitchState } = useContext(PitchContext);
  const state = pitchState ? pitchState : game;
  const isAwayPlayer = player === 'away' ? true : false;

  return (
    <Wrap>
      <AwayTeam className={isAwayPlayer && 'player'}>
        <dt>{team.away}</dt>
        <dd>
          {state.statusBoard.firsthalf
            ? state.offenseTeam.totalScore
            : state.defenseTeam.totalScore}
        </dd>
      </AwayTeam>
      <Versus>vs</Versus>
      <HomeTeam className={!isAwayPlayer && 'player'}>
        <dt>{team.home}</dt>
        <dd>
          {!state.statusBoard.firsthalf
            ? state.offenseTeam.totalScore
            : state.defenseTeam.totalScore}
        </dd>
      </HomeTeam>
      <ModalLink
        to={{
          pathname: `/modal/score/${game.gameId}`,
          state: { background: location },
        }}
      >
        점수 상세보기
      </ModalLink>
    </Wrap>
  );
};

export default Score;
