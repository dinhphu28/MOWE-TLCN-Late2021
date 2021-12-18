import React, { useState } from 'react';
import { Button, Form, FormGroup, Input, Label } from 'reactstrap';
import usersRoleApi from '../../../api/usersRoleApi';
// import PropTypes from 'prop-types';

// AddModPopup.propTypes = {};

function AddModPopup(props) {

    const [username, setUsername] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [retypePassword, setRetypePassword] = useState("");

    const changeUsernameInputValue = (e) => {
        setUsername(e.target.value);
    }
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

            fetchAddMod();
            props.onHandleCancel();
        }
    }

    const fetchAddMod = async () => {
        try {
            const data = {
                username: username,
                password: newPassword
            };

            await usersRoleApi.post(data);

            // console.log("Fetch add mod successfully: ", response);
            
        } catch(error) {
            console.log("Failed to fetch add mod: ", error);
        }
    }

    return (
        <div>
            <div className="layer"></div>
            <div className="my-popup">
                <Form
                    onSubmit={e => {
                        submitForm(e);
                    }}
                >
                    <FormGroup>
                        <Label>
                            Username:
                        </Label>
                        <Input
                            type="text"
                            name="username"
                            placeholder="Type username"
                            onChange={e => changeUsernameInputValue(e)}
                        />
                    </FormGroup>
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

export default AddModPopup;