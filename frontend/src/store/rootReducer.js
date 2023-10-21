import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import product from "../modules/product";
import bids from "../modules/bid";

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    product: product.reducer,
    bid: bids.reducer
});

export default rootReducer;
