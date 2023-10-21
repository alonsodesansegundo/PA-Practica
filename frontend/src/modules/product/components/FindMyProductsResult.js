import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import {Pager} from '../../common';
import MyProducts from "./MyProducts";

const FindMyProductsResult = () => {
    const myProductsSearch = useSelector(selectors.getMyProductsSearch);
    const dispatch = useDispatch();
    if (!myProductsSearch) {
        return null;
    }

    if (myProductsSearch.result.items.length === 0) {

        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.product.FindMyProductsResult.noProducts'/>
            </div>
        );
    }

    return (

        <div>
            <MyProducts products={myProductsSearch.result.items}/>
            <Pager
                back={{
                    enabled: myProductsSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindMyProductsResultPage(myProductsSearch.criteria))}}
                next={{
                    enabled: myProductsSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindMyProductsResultPage(myProductsSearch.criteria))}}/>
        </div>

    );

}

export default FindMyProductsResult;