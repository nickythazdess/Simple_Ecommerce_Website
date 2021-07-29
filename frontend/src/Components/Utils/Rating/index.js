import React, {useMemo, useState} from 'react';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'

const Rating = ({ count, rating, color, onRate }) => {
    const [hoverRating, setHoverRating] = useState(0);
    const getColor = index => {
        if (hoverRating >= index) {
            return color.filled;
        } else if (!hoverRating && rating >= index) {
            return color.filled;
        }
        return color.unfilled;
    }

    const starRating = useMemo(() => {
        return Array(count)
                .fill(0)
                .map((_, i) => i + 1)
                .map(idx => (
                    <FontAwesomeIcon
                        key={idx}
                        className = "cursor-pointer"
                        icon={faStar}
                        onClick={() => onRate(idx)}
                        style={{color: getColor(idx)}}
                        onMouseEnter={() => setHoverRating(idx)}
                        onMouseLeave={() => setHoverRating(0)}
                    />
                ));
    }, [count, rating, hoverRating]);
    
    return (
        <div>
            {starRating}
        </div>
    )
}

Rating.propTypes = {
    count: PropTypes.number,
    rating: PropTypes.number,
    onRate: PropTypes.func,
    color: PropTypes.shape({
        filled: PropTypes.string,
        unfilled: PropTypes.string,
    }),
}

Rating.defaultProps = {
    count: 5,
    rating: 0,
    color: {
        filled: "#DFDFDF",
        unfilled: "#202020",
    },
}

export default Rating;
