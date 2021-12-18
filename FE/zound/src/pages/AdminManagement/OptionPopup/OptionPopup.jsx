import React, { useState } from 'react';
import { Button, Form, FormGroup, Input, Label } from 'reactstrap';
import usersRoleApi from '../../../api/usersRoleApi';
import "./OptionPopup.css";
// import PropTypes from 'prop-types';

// OptionPopup.propTypes = {};

function OptionPopup(props) {

    const {username} = props;

    const [newPassword, setNewPassword] = useState("");
    const [retypePassword, setRetypePassword] = useState("");

    const changeNewPasswordInputValue = (e) => {
        setNewPassword(e.target.value);
    }
    const changeRetypePasswordInputValue = (e) => {
        setRetypePassword(e.target.value);
    }

    const validationForm = () => {
        let returnData = {
            error: false,
            msg: ""
        }

        // Kiểm tra password dài ít nhất 8 ký tự và Retype password khớp với password
        if(newPassword.length < 8) {
            returnData = {
                error: true,
                msg: "Length of password must be greater than 8 character"
            }
        } else if(newPassword !== retypePassword) {
            returnData = {
                error: true,
                msg: "Password not match"
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
            alert("Submit form change password success")

            fetchForceChangePassword();
            props.onHandleCancel();
        }
    }

    const fetchForceChangePassword = async () => {
        try {
            const data = {
                username: username,
                newPassword: newPassword
            };

            await usersRoleApi.putForceChangePassword(data);

            // console.log("Fetch force change password successfully: ", response);

        } catch (error) {
            console.log("Failed to fetch force change password: ", error);
        }
    }

    // const fetchChangePassword = async () => {
    //     try {
    //         const data = {
    //             oldValue: password,
    //             newValue: newPassword
    //         };

    //         await authApi.patch(localStorage.getItem("username"), data);

    //         // console.log("Fetch change password successfully: ", response);

    //         alert("Password changed");

    //         navigate("/articles");

    //     } catch(error) {
    //         console.log("Failed to fetch change password: ", error);
    //     }
    // }

    return (
        <div>
            <div className="layer"></div>
            <div className="my-popup">
                <h5>User: {username}</h5>
                <Form
                    onSubmit={e => {
                        submitForm(e);
                    }}
                >
                    <FormGroup>
                        <Label>
                            Password:
                        </Label>
                        <Input
                            type="password"
                            name="password"
                            placeholder="Enter new password"
                            onChange={e => changeNewPasswordInputValue(e)}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label>
                            Password:
                        </Label>
                        <Input
                            type="password"
                            name="retypePassword"
                            placeholder="Retype new password"
                            onChange={e => changeRetypePasswordInputValue(e)}
                        />
                    </FormGroup>
                    <Button
                        id="btn-save"
                        type="submit"
                        color="primary"
                    >
                        Save
                    </Button>
                    <Button
                        id="btn-cancel"
                        type="button"
                        color="secondary"
                        onClick={() => {
                            props.onHandleCancel();
                        }}
                    >
                        Cancel
                    </Button>
                </Form>
            </div>
        </div>
    );
}

export default OptionPopup;