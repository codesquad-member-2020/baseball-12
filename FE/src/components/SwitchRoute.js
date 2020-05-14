import React from 'react';
import { Route, Switch, useLocation } from 'react-router-dom';
import Login from './login/Login';
import TeamSelect from './select/TeamSelect';
import Match from './match/Match';
import Modal from './modal/Modal';

const SwitchRoute = () => {
  const location = useLocation();
  let background = location.state && location.state.background;
  return (
    <div>
      <Switch location={background || location}>
        <Route path="/" exact={true} component={Login} />
        <Route path="/team" component={TeamSelect} />
        <Route path="/match" component={Match} />
      </Switch>
      {background && <Route path="/test/:id" children={<Modal />} />}
    </div>
  );
};

export default SwitchRoute;
