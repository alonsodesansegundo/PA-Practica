import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';
import * as actionTypes2 from '../bid/actionTypes';


const initialState = {
    categories: null,
    productSearch: null,
    product: null,
    myProductsSearch: null,
    lastProductId: null
};

const categories = (state = initialState.categories, action) => {

    switch (action.type) {

        case actionTypes.FIND_ALL_CATEGORIES_COMPLETED:
            return action.categories;

        default:
            return state;

    }

}

const productSearch = (state = initialState.productSearch, action) => {

    switch (action.type) {
        case actionTypes.FIND_PRODUCTS_COMPLETED:
            return action.productSearch;

        case actionTypes.CLEAR_PRODUCT_SEARCH:
            return initialState.productSearch;

        default:
            return state;

    }

}

const product = (state = initialState.product, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCT_BY_ID_COMPLETED:
            return action.product;

        case actionTypes.CLEAR_PRODUCT:
            return initialState.product;

        case actionTypes2.BIDDING_COMPLETED:
            return {...state, actualPrice: action.lastBidding.actualPrice,
                remainingTime: action.lastBidding.remainingTime, bid: true}

        default:
            return state;
    }
}

const lastProductId = (state = initialState.lastProductId, action) => {

    switch (action.type) {

        case actionTypes.ADD_PRODUCT_COMPLETED:
            return {...state, lastProductId: action.payload};

        default:
            return state;

    }

}

const myProductsSearch = (state = initialState.myProductsSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_MY_PRODUCTS_COMPLETED:
            return action.myProductsSearch;

        case actionTypes.CLEAR_MY_PRODUCTS_SEARCH:
            return initialState.myProductsSearch;

        default:
            return state;

    }

}

const reducer = combineReducers({
    categories,
    productSearch,
    product,
    myProductsSearch,
    lastProductId
});

export default reducer;