import React , {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedNumber, useIntl} from 'react-intl';

import users from '../../users';

import {Errors, Success} from '../../common';
import * as selectors from "../../product/selectors";
import * as actions from '../actions';

const BidForm = ({productId}) => {

    const intl = useIntl();
    const user = useSelector(users.selectors.getUser);
    const product = useSelector(selectors.getProduct);

    const dispatch = useDispatch();
    const [price, setPrice] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {
            dispatch(actions.bidding(user.id, productId, price,
                lastBidding => {
                    if (lastBidding.statusBid === "LOSER"){
                        const message = intl.formatMessage({id: 'project.bid.bidding-success-loser'});
                        setSuccessMessage(message);
                    } else if (lastBidding.statusBid === "WINNIG"){
                        const message2 = intl.formatMessage({id: 'project.bid.bidding-success-winnig'});
                        setSuccessMessage(message2);
                    }
                },
                errors => setBackendErrors(errors)));
        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }
    }

    let miPrecio=0;
    if(product.bid==false)
        miPrecio=product.actualPrice
    else
        miPrecio=product.actualPrice+0.01

    return (
        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>

            <Success message={successMessage}
                     onClose={() => setSuccessMessage(null)}/>

            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    <FormattedMessage id='project.bid.bidding-to-product'/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={(e) => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="price" className="col-md-3 col-form-label">
                                <FormattedMessage id='project.global.fields.minPriceBid'/>{': '}
                                {/* eslint-disable-next-line */}
                                <FormattedNumber value={miPrecio} style="currency" currency="EUR"/>
                            </label>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="price" className="col-md-3 col-form-label">
                                <FormattedMessage id='project.bid.price'/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="price" className="form-control" role="price"
                                       value={price}
                                       onChange={e => setPrice(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-1">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id='project.button.bidding'/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default BidForm;