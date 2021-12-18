import React, { useEffect, useState } from 'react';
import commentApi from '../../api/commentApi';
import CommentCard from './CommentCard';
import CommentCreate from './CommentCreate';
// import PropTypes from 'prop-types';

// CommentCard.propTypes = {};

function LeaveCommentNotLogin(props) {
    return (
        <div style={{
            marginTop: "2.5%"
        }}>
            <h6>
                Please <a href="/sign-in">sign in</a> or <a href="/sign-up">sign up</a> to write comments!
            </h6>
        </div>
    );
}

function Comments(props) {

    const {articleId} = props;

    const [commentsList, setCommentsList] = useState([]);
    const [loaded, setLoaded] = useState(false);

    useEffect(() => {
        const fetchComments = async () => {
            try{
                const response = await commentApi.getAll(articleId);

                const data = response;

                setCommentsList(data);

                setLoaded(true);

                // console.log("Fetch comments successfully: ", data);

            } catch(error) {
                console.log("Failed to fetch comments: ", error);
            }
        }

        fetchComments();
        setInterval(fetchComments, 2000); // reload comments after every 2s
    }, [articleId]);

    const loadListComments = () => {
        if(loaded) {
            const listItems = commentsList.map((item) =>
                <CommentCard key={item.id} comment={item} />
            );

            return listItems;
        }
    };

    return (
        <div>
            {/* <CommentCard articleId={articleId}/> */}
            {loaded ? loadListComments() : ""}

            {(localStorage.getItem("username") !== null) ? <CommentCreate articleId={articleId} /> : <LeaveCommentNotLogin />}
        </div>
    );
}

export default Comments;