import React from 'react';
import styled from 'styled-components';

const Title = styled.strong`
  display: block;
  margin-bottom: 5px;
  color: ${(props) => props.theme.darkGreen};
  font-size: 16px;
`;

const BatterTitle = ({ batter }) => {
  return (
    <Title>
      {batter.battingOrder}번 타자 {batter.playerName}
    </Title>
  );
};

export default BatterTitle;
