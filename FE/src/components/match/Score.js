import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Link, useLocation } from 'react-router-dom';

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
  const isAwayPlayer = player === 'away' ? true : false;

  return (
    <Wrap>
      <AwayTeam className={isAwayPlayer && 'player'}>
        <dt>{team.away}</dt>
        <dd>
          {game.statusBoard.firsthalf
            ? game.offenseTeam.totalScore
            : game.defenseTeam.totalScore}
        </dd>
      </AwayTeam>
      <Versus>vs</Versus>
      <HomeTeam className={!isAwayPlayer && 'player'}>
        <dt>{team.home}</dt>
        <dd>
          {!game.statusBoard.firsthalf
            ? game.offenseTeam.totalScore
            : game.defenseTeam.totalScore}
        </dd>
      </HomeTeam>
      <ModalLink
        to={{ pathname: '/test/test1', state: { background: location } }}
      >
        점수 상세보기
      </ModalLink>
      {/* <ModalLink
        to={{ pathname: '/test/test2', state: { background: location } }}
      >
        선수명단
      </ModalLink> */}
    </Wrap>
  );
};

export default Score;
