import {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedDate, FormattedMessage, FormattedNumber, FormattedTime} from 'react-intl';
import {useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import {BackLink} from '../../common';
import {BidForm} from '../../bid';

const ProductDetails = () => {
    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const user = useSelector(users.selectors.getUser);
    const product = useSelector(selectors.getProduct);
    const categories = useSelector(selectors.getCategories);
    const dispatch = useDispatch();
    const {id} = useParams();

    useEffect(() => {

        const productId = Number(id);

        if (!Number.isNaN(productId)) {
            dispatch(actions.findProductById(productId));
        }

        return () => dispatch(actions.clearProduct());

    }, [id, dispatch]);

    if (!product) {
        return null;
    }

    return (

        <div>

            <BackLink/>

            <div className="card text-center">
                <div className="card-body">
                    <h5 id="productNameDetails" className="card-title">{product.name}</h5>
                    <h6 id="productCategoryDetails" className="card-subtitle text-muted">
                        <FormattedMessage id='project.global.fields.category'/>:&nbsp;
                        {selectors.getCategoryName(categories, product.categoryId)}
                    </h6>
                    <p id="productDescriptionDetails" className="card-text">
                        <FormattedMessage id='project.global.fields.description'/>{': '+product.description}
                    </p>
                    <p id="productSellerDetails" className="card-text">
                        <FormattedMessage id='project.global.fields.sellerName'/>{': '+product.sellerName}
                    </p>
                    <p id="startingDateProductDetails" className="card-text">
                        <FormattedMessage id='project.global.fields.startingDate'/>{': '}
                        <FormattedDate value={new Date(product.startingDate)}/>
                        --
                        <FormattedTime value={new Date(product.startingDate)}/>
                    </p>

                    <p id="remainingMinutesProductDetails" className="card-text">
                        <FormattedMessage id='project.global.fields.remainingMinutes'/>{': '+product.remainingMinutes}
                    </p>
                    <p id="startingPriceProductDetails" className="card-text">
                        <FormattedMessage id='project.global.fields.startingPrice'/>{': '}
                        {/* eslint-disable-next-line */}
                        <FormattedNumber value={product.startingPrice} style="currency" currency="EUR"/>
                    </p>
                    <p id="actualPriceProductDetails"className="card-text font-weight-bold">
                        <FormattedMessage id='project.global.fields.actualPrice'/>{': '}
                        {/* eslint-disable-next-line */}
                        <FormattedNumber value={product.actualPrice} style="currency" currency="EUR"/>
                    </p>
                    <p id="deliveryInformationProductDetails" className="card-text">
                        <FormattedMessage id='project.global.fields.deliveryInformation'/>{': '+product.deliveryInformation}
                    </p>
                </div>
            </div>

            {loggedIn && user.role ==="USER" && product.remainingMinutes !== 0 &&
            <div id="formularioPujaProductDetails">
                <br/>
                <BidForm productId={product.id}/>
            </div>
            }
        </div>

    );

};

export default ProductDetails;
