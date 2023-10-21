import {FormattedMessage,FormattedNumber, FormattedDate, FormattedTime} from 'react-intl';
import PropTypes from 'prop-types';

const Bids = ({bids}) => (

    <table className="table table-striped table-hover">

        <thead>
        <tr>
            <th scope="col">
                <FormattedMessage id='project.global.fields.dateBid'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.maxPrice'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.productName'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.statusBid'/>
            </th>
        </tr>
        </thead>

        <tbody>
        {bids.map(bid =>
            <tr key={bid.id}>
                <td>
                    <FormattedDate value={new Date(bid.dateBid)}/> - <FormattedTime value={new Date(bid.dateBid)}/>
                </td>
                <td>
                    <FormattedNumber value={bid.maxPrice} style="currency" currency="EUR"/>
                </td>
                <td>
                    {bid.productName}
                </td>
                <td>
                    {bid.statusBid}
                </td>

            </tr>
        )}
        </tbody>

    </table>

);

Bids.propTypes = {
    bids: PropTypes.array.isRequired
};

export default Bids;