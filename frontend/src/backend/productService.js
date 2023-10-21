import {config, appFetch} from './appFetch';

export const findAllCategories = (onSuccess) =>
    appFetch('/products/categories', config('GET'), onSuccess);

export const findProducts = ({categoryId, keywords, page},
                             onSuccess) => {

    let path = `/products/products?page=${page}`;

    path += categoryId ? `&categoryId=${categoryId}` : "";
    path += keywords.length > 0 ? `&keywords=${encodeURIComponent(keywords)}` : "";

    appFetch(path, config('GET'), onSuccess);

}

export const findByProductId = (id, onSuccess) =>
    appFetch(`/products/products/${id}`, config('GET'), onSuccess);

export const findMyProducts = ({page}, onSuccess) =>
    appFetch(`/products/user?page=${page}`, config('GET'), onSuccess);

export const addProduct = (name, description, bidMinutes, startingPrice, deliveryInformation, categoryId,
                           userId, onSuccess, onErrors) => {
    appFetch('/products',
        config('POST', {name, description, bidMinutes, startingPrice, deliveryInformation, categoryId}),
        onSuccess, onErrors);
}