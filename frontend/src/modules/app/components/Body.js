import {useSelector} from 'react-redux';
import {Route, Switch} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout} from '../../users';
import users from '../../users';
import {FindMyProducts, FindMyProductsResult, FindProductsResult, ProductDetails, AddProductForm} from '../../product';
import {FindBidsResult, FindBids} from '../../bid';


const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    
   return (

        <div className="container">
            <br/>
            <AppGlobalComponents/>
            <Switch>
                <Route exact path="/"><Home/></Route>
                <Route exact path="/product/find-products-result"><FindProductsResult/></Route>
                <Route exact path="/product/product-details/:id"><ProductDetails/></Route>
                {loggedIn && <Route exact path="/users/update-profile"><UpdateProfile/></Route>}
                {loggedIn && <Route exact path="/users/change-password"><ChangePassword/></Route>}
                {loggedIn && <Route exact path="/users/logout"><Logout/></Route>}
                {!loggedIn && <Route exact path="/users/login"><Login/></Route>}
                {!loggedIn && <Route exact path="/users/signup"><SignUp/></Route>}
                {loggedIn && <Route exact path="/bid/find-bids"><FindBids/></Route>}
                {loggedIn && <Route exact path="/bid/find-bids-result"><FindBidsResult/></Route>}
                {loggedIn && <Route exact path="/products/addProducts"><AddProductForm/></Route>}
                {loggedIn && <Route exact path="/products/find-my-products"><FindMyProducts/></Route>}
                {loggedIn && <Route exact path="/products/find-my-products-result"><FindMyProductsResult/></Route>}
                <Route><Home/></Route>
            </Switch>
        </div>

    );

};

export default Body;
