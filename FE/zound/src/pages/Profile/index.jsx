import React, { useEffect, useState } from 'react';
import { Button, Form, FormGroup, Input, Label } from 'reactstrap';
import profileApi from '../../api/profileApi';
// import PropTypes from 'prop-types';
import "./Profile.css";

// Profile.propTypes = {};

function Profile(props) {

    const [avatar, setAvatar] = useState("");
    const [email, setEmail] = useState("");
    
    // const [profileInfo, setProfileInfo] = useState({});

    const changeAvatarInputValue = (e) => {
        setAvatar(e.target.value);
    }
    const changeEmailInputValue = (e) => {
        setEmail(e.target.value);
    }

    const validationForm = () => {
        let returnData = {
            error: false,
            msg: ""
        };

        const re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        
        if(email.length > 0) {
            if(!re.test(email)) {
                returnData = {
                    error: true,
                    msg: "Not match email format"
                }
            }
        }

        if(avatar.length < 10) {
            returnData = {
                error: true,
                msg: "Length of url is invalid"
            }
        }

        return returnData;
    }

    const submitForm = (e) => {
        e.preventDefault();

        const validation = validationForm();

        if(validation.error) {
            alert(validation.msg);
        } else {
            alert("Submit form success");

            fetchUpdateProfile();

            // props.onHandleChange();
        }
    }

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const username = localStorage.getItem("username");

                const response = await profileApi.get(username);

                // setProfileInfo(response);

                setAvatar(response.avatar);
                setEmail(response.email);

                // console.log("Fetch profile successfully: ", response);
                
            } catch (error) {
                console.log("Failed to fetch profile info: ", error);
            }
        }

        fetchProfile();
    }, []);

    // call api update here
    const fetchUpdateProfile = async () => {
        try {
            const data = {
                avatar: avatar,
                email: email
            };

            await profileApi.put(localStorage.getItem("username"), data);

            // console.log("Update profile successfully: ", response);

        } catch (error) {
            console.log("Failed to fetch update profile: ", error);
        }
    }

    return (
        <div className="my-profile">
            <div className="username-section">
                <img src={avatar} alt="Avatar" />
                <h5>
                    Username: <span style={{color: "#20cc93"}}>{localStorage.getItem("username")}</span>
                </h5>
            </div>
            <Form className="my-form"
                onSubmit={e => {
                    submitForm(e);
                }}
            >
                <FormGroup>
                    <Label>
                        Avatar URL:
                    </Label>
                    <Input
                        type="url"
                        name="avatarUrl"
                        placeholder="Your avatar image's url"
                        onChange={e => changeAvatarInputValue(e)}
                        value={avatar}
                    />
                </FormGroup>
                <FormGroup>
                    <Label>
                        Email:
                    </Label>
                    <Input 
                        type="email"
                        name="email"
                        placeholder="example@example.com"
                        onChange={e => changeEmailInputValue(e)}
                        value={email}
                    />
                </FormGroup>
                <Button
                    color="primary"
                    type="submit"
                    block={true}
                >
                    Save
                </Button>
            </Form>
        </div>
    );
}

export default Profile;