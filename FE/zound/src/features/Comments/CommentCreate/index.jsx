import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Button } from 'reactstrap';
import "./CommentCreate.css";
// import PropTypes from 'prop-types';
import commentApi from '../../../api/commentApi';
import profileApi from '../../../api/profileApi';

// CommentCreate.propTypes = {};

function CommentCreate(props) {

    const {articleId} = props;
    
    const [content, setContent] = useState("");

    const changeInputValue = (e) => {
        setContent(e.target.value);
    }

    const fetchCreateComment = async () => {
        try {
            const commentData = {
                author: localStorage.getItem("username"),
                content: content
            };

            await commentApi.post(articleId, commentData);

            // console.log("Write comment successfully: ", response);

            // props.onHandleChange();

        } catch(error) {
            console.log("Failed to fetch create comment: ", error);
        }
    }

    const [avatar, setAvatar] = useState("");

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const username = localStorage.getItem("username");

                const response = await profileApi.get(username);

                // setProfileInfo(response);

                setAvatar(response.avatar);

                // console.log("Fetch profile successfully: ", response);
                
            } catch (error) {
                console.log("Failed to fetch profile info: ", error);
            }
        }

        fetchProfile();
    }, []);

    return (
        <div className="leave-comment">
            <img src={(avatar) ? (process.env.REACT_APP_BE_API_V1_URL + "/files/downloadFile/" + avatar) : "http://www.vov.edu.vn/frontend/home/images/no-avatar.png"} alt="Avatar" />
            <textarea
                name="comment"
                rows="4"
                placeholder="Leave comment here"
                value={content}
                onChange={(e) => changeInputValue(e)}
            ></textarea>
            <Button
                color="primary"
                type="button"
                onClick={() => {
                    fetchCreateComment();
                    setContent("");
                }}
            >
                <FontAwesomeIcon icon={faPaperPlane} />
            </Button>
        </div>
    );
}

export default CommentCreate;