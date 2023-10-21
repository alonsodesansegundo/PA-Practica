import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';

import * as actions from '../actions';

const FindMyProducts = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    useEffect(() => {

        dispatch(actions.findMyProducts({page: 0}));
        history.push('/products/find-my-products-result');
    });


    return null;

}

export default FindMyProducts;