import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'reactstrap';
import UserModCard from '../../features/UserCard/UserModCard';
import "./AdminMan.css";
// import PropTypes from 'prop-types';
import usersRoleApi from '../../api/usersRoleApi';
import OptionPopup from './OptionPopup/OptionPopup';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSyncAlt, faUserPlus } from '@fortawesome/free-solid-svg-icons';
import AddModPopup from './AddModPopup/AddModPopup';

// AdminMan.propTypes = {};

function AdminMan(props) {

    const [listMods, setListMods] = useState([]);
    const [loaded, setLoaded] = useState(false);
    const [popupOpen, setPopupOpen] = useState(false);
    const [addModPopupOpen, setAddModPopupOpen] = useState(false);
    const [currentPopupUser, setCurrentPopupUser] = useState("");
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        const fetchListMods = async () => {
            try {
                const params = {
                    role: "mod"
                };

                const response = await usersRoleApi.getAll(params);

                // console.log("Fetch list mods successfully: ", response);

                setListMods(response);

                setLoaded(true);

            } catch(error) {
                console.log("Failed to fetch list mods: ", error);
            }
        }

        fetchListMods();
    }, [popupOpen, addModPopupOpen, refresh]);

    const receivePopupState = (username) => {
        setCurrentPopupUser(username);
        setPopupOpen(true);
    }
    const receiveCancel = () => {
        setPopupOpen(false);
    }

    const receiveAddModCancel = () => {
        setAddModPopupOpen(false);
    }

    const loadListMods = () => {
        const listItems = listMods.map((item) => 
            <Col
                key={item.username}
                md={6}
            >
                <UserModCard userInfo={item} onHandlePopupStateChange={receivePopupState} />
            </Col>
        );

        return listItems;
    }
    
    const renListMods = () => {
        if(loaded) {
            // do something
            let listRen = [];

            const listModsCrude = loadListMods();

            let tmpChildList = [];

            const n = listModsCrude.length;
            for(let ii = 0; ii < n; ii++) {

                if(n % 2 === 0) {
                    if(ii % 2 === 1) {
                        tmpChildList[1] = (
                            listModsCrude[ii]
                        );

                        listRen[(ii+1)/2] = (
                            <Row key={(ii+1)/2}>
                                {tmpChildList}
                            </Row>
                        );

                        tmpChildList = [];
                    } else {
                        tmpChildList[0] = (
                            listModsCrude[ii]
                        );
                    }
                } else {
                    if(ii % 2 === 1) {
                        tmpChildList[1] = (
                            listModsCrude[ii]
                        );

                        listRen[(ii+1)/2] = (
                            <Row key={(ii+1)/2}>
                                {tmpChildList}
                            </Row>
                        );
                        tmpChildList = [];
                    } else {
                        if(ii === (n-1)) {
                            const specItem = [];
                            specItem[0] = (
                                listModsCrude[ii]
                            );
                            listRen[(ii+2)/2] = (
                                <Row key={(ii+2)/2}>
                                    {specItem}
                                </Row>
                            )
                        } else {
                            tmpChildList[0] = (
                                listModsCrude[ii]
                            );
                        }
                    }
                }
            }

            return listRen;
        }
    };

    return (
        <div className="container man-section">
            <div className="man-tools-section">
                <Button
                    type="button"
                    color="primary"
                    onClick={() => {
                        setAddModPopupOpen(true);
                    }}
                >
                    <FontAwesomeIcon icon={faUserPlus} /> Add mod user
                </Button>
                <Button
                    type="button"
                    color="success"
                    onClick={() => {
                        setRefresh(!refresh);
                    }}
                >
                    <FontAwesomeIcon icon={faSyncAlt} />
                </Button>
            </div>
            <div className="list-mod">
                {renListMods()}
            </div>
            {popupOpen ? <OptionPopup onHandleCancel={receiveCancel} username={currentPopupUser} /> : ""}
            {addModPopupOpen ? <AddModPopup onHandleCancel={receiveAddModCancel} /> : ""}
        </div>
    );
}

export default AdminMan;