import React, { useState, useReducer } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GrobalStyle from './styles/globalStyles';
import theme from './styles/theme';
import SwitchRoute from './components/SwitchRoute';
import SelectTeamContext from './contexts/selectTeam';
import teamReducer from './reducer/teamReducer';

function App() {
  const [state, dispatch] = useReducer(teamReducer, {
    type: null,
    team: null,
  });
  return (
    <ThemeProvider theme={theme}>
      <SelectTeamContext.Provider value={{ state, dispatch }}>
        <GrobalStyle />
        <Router>
          <SwitchRoute />
        </Router>
      </SelectTeamContext.Provider>
    </ThemeProvider>
  );
}

export default App;
