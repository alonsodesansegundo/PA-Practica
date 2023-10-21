import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';

import * as actions from '../actions';

const FindBids = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    useEffect(() => {

        dispatch(actions.findBidsByUserId({page: 0}));
        history.push('/bid/find-bids-result');

    });

    return null;

}

export default FindBids;
