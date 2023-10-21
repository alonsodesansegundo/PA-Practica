import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import * as selectors from '../selectors';
import {ProductLink} from '../../common';

const Products = ({products, categories}) => (

    <table className="table table-striped table-hover">

        <thead>
        <tr>
            <th scope="col">
                <FormattedMessage id='project.global.fields.category'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.name'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.actualPrice'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.remainingMinutes'/>
            </th>
        </tr>
        </thead>

        <tbody id="listaProductos">
        {products.map(product =>
            <tr key={product.id}>
                <td>{selectors.getCategoryName(categories, product.categoryId)}</td>
                <td>
                    <ProductLink id={product.id} name={product.name}/>
                </td>
                <td>{product.actualPrice}</td>
                <td>{product.remainingTime}</td>

            </tr>
        )}
        </tbody>

    </table>

);

Products.propTypes = {
    products: PropTypes.array.isRequired,
    categories: PropTypes.array.isRequired
};

export default Products;
