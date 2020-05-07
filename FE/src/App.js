import React, { useState, Fragment } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Login from './components/Login';
import TeamSelect from './components/TeamSelect';
import Match from './components/Match';

function App() {
  return (
    <div>
      <Router>
        <Route path="/" exact={true} component={Login} />
        <Route path="/team" component={TeamSelect} />
        <Route path="/match" component={Match} />
      </Router>
    </div>
  );
}

export default App;
