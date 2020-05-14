const TeamReducer = (state, { type, team }) => {
  switch (type) {
    case 'away':
      return {
        type: type,
        matchId: team.matchId,
        awayTeam: team.awayTeam.teamName,
        homeTeam: team.homeTeam.teamName,
      };
    case 'home':
      return {
        type: type,
        matchId: team.matchId,
        awayTeam: team.awayTeam.teamName,
        homeTeam: team.homeTeam.teamName,
      };
    default:
      break;
  }
};

export default TeamReducer;
