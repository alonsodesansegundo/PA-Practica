import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

const MyProducts = ({products}) => (


    <table id="myProductsList" className="table table-striped table-hover">

        <thead>
        <tr>
            <th scope="col">
                <FormattedMessage id='project.global.fields.name'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.actualPrice'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.remainingMinutes'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.mailWinner'/>
            </th>
        </tr>
        </thead>

        <tbody id="productList">
        {products.map(product =>
            <tr key={product.id}>
                <td id="myProductName">{product.name}</td>
                <td>{product.actualPrice}</td>
                <td>{product.remainingMinutes}</td>
                <td>{product.mailWinner}</td>
            </tr>
        )}
        </tbody>

    </table>

);

MyProducts.propTypes = {
    products: PropTypes.array.isRequired
};

export default MyProducts;

