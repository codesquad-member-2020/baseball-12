import React, { useState } from 'react';
import styled from 'styled-components';
import useFetch from '../useFetch';

const Wrap = styled.div`
  position: absolute;
  top: 10%;
  left: 0;
  right: 0;
  width: 70%;
  margin: 0 auto;
  padding: 20px 0;
  background: ${(props) => props.theme.navy};
  color: #fff;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
`;

const Table = styled.table`
  width: 80%;
  margin: 0 auto;
`;

const Tr = styled.tr`
  height: 70px;
  &:first-child {
    border-bottom: 2px solid #fff;
  }
  > th,
  td {
    vertical-align: middle;
    text-align: center;
  }
`;

const Close = styled.button`
  position: absolute;
  top: 110%;
  left: 0;
  padding: 15px 20px;
  font-size: 16px;
  background: ${(props) => props.theme.softGreen};
  border-bottom: 5px solid ${(props) => props.theme.midGreen};
  border-radius: 7px;
  box-shadow: 0px 0px 10px ${(props) => props.theme.opacityNavy};
`;

const ScoreModal = ({ gameId, click }) => {
  const [score, setScore] = useState({});
  const loading = useFetch(
    setScore,
    `${process.env.REACT_APP_TEAMLIST}/${gameId}/team-score`
  );

  return (
    <>
      {loading ? (
        <Wrap />
      ) : (
        <Wrap>
          <Table>
            <tbody>
              <Tr>
                {[...Array(Number(process.env.REACT_APP_TOTAL_INNING) + 1)].map(
                  (n, index) => (
                    <th key={index}>{!index ? '' : index}</th>
                  )
                )}
              </Tr>
              <Tr>
                {score.awayTeam.scores.map((teamScore, idx) => {
                  return (
                    <>
                      {!idx && <th>{score.awayTeam.teamName}</th>}
                      {<td>{teamScore}</td>}
                    </>
                  );
                })}
              </Tr>
              <Tr>
                {score.homeTeam.scores.map((teamScore, idx) => {
                  return (
                    <>
                      {!idx && <th>{score.homeTeam.teamName}</th>}
                      {<td>{teamScore}</td>}
                    </>
                  );
                })}
              </Tr>
            </tbody>
          </Table>
          <Close onClick={() => click.goBack()}>돌아가기</Close>
        </Wrap>
      )}
    </>
  );
};

export default ScoreModal;
