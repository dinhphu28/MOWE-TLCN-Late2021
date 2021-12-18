import React, { useEffect, useState } from 'react';
import profileApi from '../../../api/profileApi';
import "./CommentCard.css";
// import PropTypes from 'prop-types';

// CommentCard.propTypes = {};

function CommentCard(props) {

    const {comment} = props;
    const [avatar, setAvatar] = useState("");
    const [loaded, setLoaded] = useState(false);

    useEffect(() => {
        const fetchProfile = async () => {
            let ld = false;
            try {
                // setLoaded(false);

                const username = comment.author;

                const response = await profileApi.get(username);

                // setProfileInfo(response);

                setAvatar(response.avatar);

                setLoaded(true);
                ld = true;

                // console.log("Fetch profile successfully: ", response);
                
            } catch (error) {
                if(ld) {
                    console.log("Failed to fetch avatar of comments card info: ", error);
                }
            }
        }

        fetchProfile();
    }, [comment.author]);

    return (
        <div className="comment-card">
            <div className="comment-info">
                <img src={loaded ? avatar : "http://www.vov.edu.vn/frontend/home/images/no-avatar.png"} alt="Avatar" />
                {/* https://bloganchoi.com/wp-content/uploads/2021/10/iu-solo.jpg */}
                <span className="username">{comment.author}</span>
                <span className="datetime">{comment.date} {comment.time}</span>
            </div>
            <p>
                {comment.content}
            </p>
        </div>
    );
}

export default CommentCard;