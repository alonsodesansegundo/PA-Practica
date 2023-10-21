import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import backend from '../../backend';

const findAllCategoriesCompleted = categories => ({
    type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
    categories
});

export const findAllCategories = () => (dispatch, getState) => {

    const categories = selectors.getCategories(getState());

    if (!categories) {

        backend.productService.findAllCategories(
            categories => dispatch(findAllCategoriesCompleted(categories))
        );

    }

}

const addProductCompleted = ProductId => ({
    type: actionTypes.ADD_PRODUCT_COMPLETED,
    ProductId
});

export const addProduct = (name, description, bidMinutes, startingPrice, deliveryInformation, categoryId, userId,
                           onSuccess, onErrors) => dispatch => {
    backend.productService.addProduct(name, description, bidMinutes, startingPrice, deliveryInformation, categoryId, userId,
        ({id}) => {
            dispatch(addProductCompleted(id));
            onSuccess();
        },
        onErrors);
}


const findProductsCompleted = productSearch => ({
    type: actionTypes.FIND_PRODUCTS_COMPLETED,
    productSearch
});

export const findProducts = criteria => dispatch => {

    dispatch(clearProductSearch());
    backend.productService.findProducts(criteria,
        result => dispatch(findProductsCompleted({criteria, result})));

}

export const previousFindProductsResultPage = criteria =>
    findProducts({...criteria, page: criteria.page - 1});

export const nextFindProductsResultPage = criteria =>
    findProducts({...criteria, page: criteria.page + 1});

const clearProductSearch = () => ({
    type: actionTypes.CLEAR_PRODUCT_SEARCH
});

export const clearProduct = () => ({
    type: actionTypes.CLEAR_PRODUCT
});

const findProductByIdCompleted = product => ({
    type: actionTypes.FIND_PRODUCT_BY_ID_COMPLETED,
    product
});

export const findProductById = id => dispatch => {
    backend.productService.findByProductId(id,
        product => dispatch(findProductByIdCompleted(product)));
}

const findMyProductsCompleted = myProductsSearch => ({
    type: actionTypes.FIND_MY_PRODUCTS_COMPLETED,
    myProductsSearch
});

const clearMyProductsSearch = () => ({
    type: actionTypes.CLEAR_MY_PRODUCTS_SEARCH
});

export const findMyProducts = criteria => dispatch => {

    dispatch(clearMyProductsSearch());
    backend.productService.findMyProducts(criteria,
        result => dispatch(findMyProductsCompleted({criteria, result})));

}

export const previousFindMyProductsResultPage = criteria =>
    findMyProducts({page: criteria.page - 1});

export const nextFindMyProductsResultPage = criteria =>
    findMyProducts({page: criteria.page + 1});