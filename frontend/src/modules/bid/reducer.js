import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    lastBidding: null,
    bidSearch: null
};

const lastBidding = (state = initialState.lastBidding, action) => {
    switch (action.type) {

        case actionTypes.BIDDING_COMPLETED:
            return action.lastBidding;

        default:
            return state;
    }
}

const bidSearch = (state = initialState.bidSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_BIDS_COMPLETED:
            return action.bidSearch;

        case actionTypes.CLEAR_BID_SEARCH:
            return initialState.bidSearch;

        default:
            return state;

    }

}

const reducer = combineReducers({
    lastBidding,
    bidSearch
});

export default reducer;