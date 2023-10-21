import PropTypes from "prop-types";

import {Link} from "react-router-dom";

const ProductLink = ({id, name}) => {
    console.log("hola "+name);

    return (
        <Link to={`/product/product-details/${id}`}>
            {name}
        </Link>
    );
}

ProductLink.propTypes = {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
};

export default ProductLink;