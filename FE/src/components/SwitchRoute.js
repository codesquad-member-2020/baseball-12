import React from 'react';
import { Route, Switch, useLocation } from 'react-router-dom';
import Login from './Login';
import TeamSelect from './TeamSelect';
import Match from './Match';
import Modal from './modal/Modal';

const SwitchRoute = () => {
  const location = useLocation();
  let background = location.state && location.state.background;
  console.log(background);
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
