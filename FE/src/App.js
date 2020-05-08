import React, { useState, Fragment } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Login from './components/Login';
import TeamSelect from './components/TeamSelect';
import Match from './components/Match';
import { ThemeProvider } from 'styled-components';
import GrobalStyle from './styles/globalStyles';
import theme from './styles/theme';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <GrobalStyle />
      <Router>
        <Route path="/" exact={true} component={Login} />
        <Route path="/team" component={TeamSelect} />
        <Route path="/match" component={Match} />
      </Router>
    </ThemeProvider>
  );
}

export default App;
