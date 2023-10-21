const getModuleState = state => state.bid;

export const getBid = state =>
    getModuleState(state).lastBidding;

export const getBidSearch = state =>
    getModuleState(state).bidSearch;
