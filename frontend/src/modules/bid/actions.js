import backend from '../../backend';
import * as actionTypes from './actionTypes';

export const biddingCompleted = lastBidding => ({
    type: actionTypes.BIDDING_COMPLETED,
    lastBidding
});

export const bidding = (userId, productId, price, onSuccess, onErrors) => dispatch => {
    backend.bidService.bidding(userId,productId, price,
        lastBidding => {
            dispatch(biddingCompleted(lastBidding));
            onSuccess(lastBidding);
        },
        onErrors);
}

const findBidsCompleted = bidSearch => ({
    type: actionTypes.FIND_BIDS_COMPLETED,
    bidSearch
});

const clearBidSearch = () => ({
    type: actionTypes.CLEAR_BID_SEARCH
});

export const findBidsByUserId = criteria => dispatch => {

    dispatch(clearBidSearch());
    backend.bidService.findBidsByUserId(criteria,
        result => dispatch(findBidsCompleted({criteria, result})));

}

export const previousFindBidsResultPage = criteria =>
    findBidsByUserId({page: criteria.page-1});

export const nextFindBidsResultPage = criteria =>
    findBidsByUserId({page: criteria.page+1});



