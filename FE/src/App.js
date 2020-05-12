import React, { useState, Fragment } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GrobalStyle from './styles/globalStyles';
import theme from './styles/theme';
import SwitchRoute from './components/SwitchRoute';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <GrobalStyle />
      <Router>
        <SwitchRoute />
      </Router>
    </ThemeProvider>
  );
}

export default App;
