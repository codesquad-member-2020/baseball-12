import React from 'react';
import { Redirect, Link } from 'react-router-dom';
import TEAMLIST from '../data/teamList';
import styled from 'styled-components';
import BackgroundImg from '../images/baseball_field.png';

const Wrap = styled.div`
  display: flex;
  width: 100%;
  height: 100vh;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  /* background: ${(props) => props.theme.softGreen}; */
  background: url(${BackgroundImg}) no-repeat 0 80%;
  background-size: 100% auto;
`;

const Title = styled.h2`
  color: ${(props) => props.theme.mainFontColor};
  font-size: 50px;
  margin-bottom: 15vh;
`;

const ListWrap = styled.div`
  width: 40%;
  max-width: 400px;
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
`;

const TeamSelect = ({ history }) => {
  const handleTeamSelect = () => {
    console.log('dosomething');
    history.push('/match');
  };
  return (
    <Wrap>
      <Title>팀을 선택하세요.</Title>
      <ListWrap>
        <ul>
          {TEAMLIST.map((team) => (
            <List>
              <TeamButton type="button" onClick={handleTeamSelect}>
                {team.away}
              </TeamButton>
              <span>vs</span>
              <TeamButton type="button" onClick={handleTeamSelect}>
                {team.home}
              </TeamButton>
            </List>
          ))}
        </ul>
      </ListWrap>
    </Wrap>
  );
};

export default TeamSelect;
