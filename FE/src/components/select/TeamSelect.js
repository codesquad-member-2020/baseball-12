import React, { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import BackgroundImg from '../../images/baseball_field.png';
import useFetch from '../useFetch';
import SelectTeamContext from '../../contexts/selectTeam';

const Wrap = styled.div`
  display: flex;
  width: 100%;
  height: 100vh;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: url(${BackgroundImg}) no-repeat 0 80%;
  background-size: 100% auto;
  transition: all 1s;
  opacity: 1;
  &.loading {
    opacity: 0.5;
    background-color: ${(props) => props.theme.mainFontColor};
  }
`;

const Title = styled.h2`
  color: ${(props) => props.theme.mainFontColor};
  font-size: 50px;
  margin-bottom: 15vh;
`;

const ListWrap = styled.div`
  width: 50%;
  max-width: 500px;
`;

const List = styled.li`
  margin-bottom: 15px;
  border-bottom: 4px solid ${(props) => props.theme.darkGreen};
  background: ${(props) => props.theme.midGreen};
  color: ${(props) => props.theme.white};
  padding: 10px 20px;
  font-size: 20px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-radius: 10px;
`;

const TeamButton = styled(Link)`
  cursor: pointer;
  padding: 10px 0;
  &:hover {
    color: ${(props) => props.theme.mainFontColor};
  }
  &.disable {
    color: ${(props) => props.theme.darkGreen};
    pointer-events: none;
  }
`;

const TeamSelect = ({ history }) => {
  const [teams, setTeams] = useState([]);
  const loading = useFetch(setTeams, process.env.REACT_APP_TEAMLIST);
  const { state, dispatch } = useContext(SelectTeamContext);
  const handleTeamSelect = (e, type, team) => {
    e.stopPropagation();
    dispatch({ type, team });

    history.push('/match');
  };
  return (
    <SelectTeamContext.Provider value={{ state, dispatch }}>
      <Wrap className={loading && 'loading'}>
        <Title>팀을 선택하세요.</Title>
        <ListWrap>
          <ul>
            {teams.map((team) => (
              <List key={team.matchId}>
                <TeamButton
                  to={'/match'}
                  onClick={(e) => handleTeamSelect(e, 'away', team)}
                  className={!team.awayUser && 'disable'}
                >
                  {team.awayTeam.teamName}
                </TeamButton>
                <span>vs</span>
                <TeamButton
                  to={'/match'}
                  onClick={(e) => handleTeamSelect(e, 'home', team)}
                  className={!team.homeUser && 'disable'}
                >
                  {team.homeTeam.teamName}
                </TeamButton>
              </List>
            ))}
          </ul>
        </ListWrap>
      </Wrap>
    </SelectTeamContext.Provider>
  );
};

export default TeamSelect;
