import React from 'react';
import styled from 'styled-components';

const List = styled.li`
  font-size: 14px;
  line-height: 1.3;
`;

const BallCount = styled.span`
  margin-left: 5px;
  > span {
    color: ${(props) => props.theme.darkGreen};
    margin-right: 3px;
    &:last-child {
      margin-left: 5px;
    }
  }
`;

const BatterList = ({ battings }) => {
  let count = 0;
  return (
    <ul>
      {[...battings].reverse().map((batting) => (
        <List key={++count}>
          {batting.judgement}
          {batting.judgement !== 'HIT' && batting.judgement !== 'OUT' && (
            <BallCount>
              <span>S</span>
              {batting.strike}
              <span>B</span>
              {batting.ball}
            </BallCount>
          )}
        </List>
      ))}
    </ul>
  );
};

export default BatterList;
