import { faKey, faLock, faUnlock } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Button } from 'reactstrap';
import "./UserModCard.css";
// import PropTypes from 'prop-types';
import usersRoleApi from '../../api/usersRoleApi';

// UserModCard.propTypes = {};

function UserModCard(props) {

    const {userInfo} = props;

    const [active, setActive] = useState(true);

    useEffect(() => {
        setActive(userInfo.active);
    }, [userInfo.active]);

    const fetchChangeActiveState = async () => {
        try {
            const data = {
                username: userInfo.username,
                active: !active
            };

            await usersRoleApi.putChangeActiveState(data);

            // console.log("Fetch change active state successfully: ", response);

            setActive(!active);

        } catch (error) {
            console.log("Failed to fetch change active state: ", error);
        }
    };

    return (
        <div className="my-card">
            <img src={(userInfo.avatar) ? userInfo.avatar : "http://www.vov.edu.vn/frontend/home/images/no-avatar.png"} alt="Avatar" />
            <div className="info" >
                <span className="username">{userInfo.username}</span>
            </div>
            <div className="man-tools">
                <Button
                    color={active ? "success" : "secondary"}
                    onClick={() => {
                        fetchChangeActiveState();
                    }}
                >
                    {active ? <FontAwesomeIcon icon={faUnlock} /> : <FontAwesomeIcon icon={faLock} />}
                </Button>
                <Button
                    color="primary"
                    onClick={() => {
                        props.onHandlePopupStateChange(userInfo.username);
                    }}
                >
                    <FontAwesomeIcon icon={faKey} id="change-password" /> Change password 
                </Button>
            </div>
        </div>
    );
}

export default UserModCard;