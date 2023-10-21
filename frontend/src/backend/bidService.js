import {config, appFetch} from './appFetch';

export const bidding = (userId, productId, price, onSuccess, onErrors) => {
    appFetch('/bid',
        config('POST', {productId, price}), onSuccess, onErrors);
}
export const findBidsByUserId = ({page}, onSuccess) =>
    appFetch(`/bid/user?page=${page}`, config('GET'), onSuccess);

