import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import {Pager} from '../../common';
import Bids from './Bids';

const FindBidsResult = () => {

    const bidSearch = useSelector(selectors.getBidSearch);
    const dispatch = useDispatch();

    if (!bidSearch) {
        return null;
    }

    if (bidSearch.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.bid.FindBidsResult.noBids'/>
            </div>
        );
    }

    return (

        <div>
            <Bids bids={bidSearch.result.items}/>
            <Pager
                back={{
                    enabled: bidSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindBidsResultPage(bidSearch.criteria))}}
                next={{
                    enabled: bidSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindBidsResultPage(bidSearch.criteria))}}/>
        </div>

    );

}

export default FindBidsResult;
