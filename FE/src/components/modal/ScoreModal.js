import React, { useState } from 'react';
import styled from 'styled-components';
import useFetch from '../useFetch';

const Wrap = styled.div`
  position: absolute;
  top: 10%;
  left: 0;
  right: 0;
  width: 90%;
  margin: 0 auto;
  background: #fff;
`;

const Table = styled.table`
  display: flex;
  flex-direction: column;
  width: 80%;
  margin: 0 auto;
`;

const Tr = styled.tr`
  height: 70px;
`;

const Close = styled.button`
  position: absolute;
  top: 130%;
  left: 0;
  padding: 5px 10px;
  font-size: 16px;
  background: ${(props) => props.theme.mainFontColor};
  border: 3px solid #fff;
  border-radius: 5px;
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
                {[...Array(Number(process.env.REACT_APP_TOTAL_INNING) + 1)].map(
                  (n, index) => (
                    <>
                      {!index && <th>{score.awayTeam.teamName}</th>}
                      {score.awayTeam.scores.map((teamScore) => (
                        <td>{teamScore}</td>
                      ))}
                    </>
                  )
                )}
              </Tr>
              <Tr>
                {[...Array(Number(process.env.REACT_APP_TOTAL_INNING) + 1)].map(
                  (n, index) => (
                    <>
                      {!index && <th>{score.homeTeam.teamName}</th>}
                      {score.homeTeam.scores.map((teamScore) => (
                        <td>{teamScore}</td>
                      ))}
                    </>
                  )
                )}
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
