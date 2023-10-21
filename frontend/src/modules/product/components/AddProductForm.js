import React, {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import users from '../../users';

import {Errors} from '../../common';
import * as actions from '../actions';
import {useHistory} from "react-router-dom";
import CategorySelector from "./CategorySelector";

const AddProductForm = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    const user = useSelector(users.selectors.getUser);

    const [productName, setProductName] = useState('');
    const [description, setDescription] = useState('');
    const [bidMinutes, setBidMinutes] = useState('');
    const [startingPrice, setStartingPrice] = useState('');
    const [deliveryInformation, setDeliveryInformation] = useState('');
    const [categoryId, setCategoryId] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);

    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {
            dispatch(actions.addProduct(productName, description.trim(), bidMinutes, startingPrice,
                deliveryInformation.trim(), toNumber(categoryId), user.id,
                () => history.push('/home'),
                errors => setBackendErrors(errors)));

        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }
    }
    const toNumber = value => value.length > 0 ? Number(value) : null;

    return (
        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>

            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    <FormattedMessage id='project.product.add-to-product'/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={(e) => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="productName" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.productName"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="productName" className="form-control"
                                       value={productName}
                                       onChange={e => setProductName(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="description" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.description"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="description" className="form-control"
                                       value={description}
                                       onChange={e => setDescription(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="bidMinutes" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.bidMinutes"/>
                            </label>
                            <div className="col-md-4">
                                <input type="number" id="bidMinutes" className="form-control"
                                       value={bidMinutes}
                                       onChange={e => setBidMinutes(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="startingPrice" className="col-md-3 col-form-label">
                                <FormattedMessage id='project.global.fields.startingPrice'/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="startingPrice" className="form-control" role="startingPrice"
                                       value={startingPrice}
                                       onChange={e => setStartingPrice(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="deliveryInformation" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.deliveryInformation"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="deliveryInformation" className="form-control"
                                       value={deliveryInformation}
                                       onChange={e => setDeliveryInformation(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="category" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.category"/>
                            </label>
                            <div className="col-md-4">
                                <CategorySelector id="categoryId2" className="custom-select my-1 mr-sm-2"
                                                  value={categoryId}
                                                  onChange={e => setCategoryId(e.target.value)}
                                                  required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-1">
                                <button id="botonAddProduct" type="submit" className="btn btn-primary">
                                    <FormattedMessage id='project.button.addProduct'/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default AddProductForm;