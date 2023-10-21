import {init} from './appFetch';
import * as userService from './userService';
import * as productService from './productService';
import * as bidService from './bidService';

export {default as NetworkError} from "./NetworkError";

// eslint-disable-next-line
export default {init, userService, productService, bidService};
