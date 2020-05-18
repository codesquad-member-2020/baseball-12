import React, { useContext, useState, useReducer } from 'react';
import styled from 'styled-components';
import groundBg from '../../images/bg_ground.jpg';
import Score from './Score';
import PitcherInfo from './PitcherInfo';
import SelectTeamContext from '../../contexts/selectTeam';
import Ground from './Ground';
import BatterHistory from './BatterHistory';
import usePostFetch from '../usePostFetch';
import PitchContext from '../../contexts/pitchContext';
import pitchReducer from '../../reducer/pitchReducer';

const Wrap = styled.div`
  display: grid;
  width: 100%;
  min-width: 1000px;
  height: 100vh;
  grid-template-columns: 3fr 1fr;
  grid-template-rows: 150px 1fr;
  background: url(${groundBg}) repeat-x;
  background-size: auto 100%;
`;

const Match = () => {
  const { state } = useContext(SelectTeamContext);
  const [pitchState, pitchDispatch] = useReducer(pitchReducer, null);
  const [game, setGame] = useState({});
  const loading = usePostFetch(setGame, process.env.REACT_APP_START_GAME, {
    matchId: state.matchId,
  });

  if (loading) {
    return <Wrap />;
  }
  return (
    <PitchContext.Provider value={{ pitchState, pitchDispatch }}>
      <Wrap>
        <Score
          player={state.type}
          team={{ away: state.awayTeam, home: state.homeTeam }}
          game={game}
        />
        <PitcherInfo game={game} />
        <Ground gameId={game.gameId} />
        <BatterHistory />
      </Wrap>
    </PitchContext.Provider>
  );
};

export default Match;
