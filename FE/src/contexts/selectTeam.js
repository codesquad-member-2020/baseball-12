import { createContext } from 'react';

const SelectTeamContext = createContext({
  type: null,
  team: null,
});

export default SelectTeamContext;
