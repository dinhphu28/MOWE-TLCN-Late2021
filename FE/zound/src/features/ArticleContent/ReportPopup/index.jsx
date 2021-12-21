import React from 'react';
import { Button, Form, FormGroup, Input, Label } from 'reactstrap';
import "./styles.css";
// import PropTypes from 'prop-types';
import { useState } from 'react';
import reportsApi from '../../../api/reportsApi';

// ReportPopup.propTypes = {};

function ReportPopup(props) {

    const {articleId} = props;

    const [content, setContent] = useState("");

    const changeContentInputValue = (e) => {
        setContent(e.target.value);
    }

    const validationForm = () => {
        let returnData = {
            error: false,
            msg: ""
        }

        if(content.length < 1) {
            returnData = {
                error: true,
                msg: "Content can't be empty"
            }
        }

        return returnData;
    }

    const fetchCreateReport = async () => {
        if(localStorage.getItem("username") !== null) {
            try {
                const data = {
                    articleId: articleId,
                    author: localStorage.getItem("username"),
                    content: content
                }

                const reponse = await reportsApi.post(data);

                console.log("Fetch create report successfully: ", reponse);

            } catch (error) {
                console.log("Failed to fetch create report: ", error);
            }
        }
    }

    const submitForm = (e) => {
        e.preventDefault();

        const validation = validationForm();

        if(validation.error) {
            alert(validation.msg);
        } else {
            alert("Submit form report success")

            // call fetch API function here
            fetchCreateReport()

            props.onHandleChange();
        }
    }

    return (
        <div>
            <div className="layer"></div>
            <div className="my-report-popup">
                <Form
                    onSubmit={e => {
                        submitForm(e);
                    }}
                >
                    <FormGroup>
                        <Label>
                            Report's content:
                        </Label>
                        <Input
                            type="textarea"
                            name="content"
                            placeholder="Your report content here"
                            onChange={e => changeContentInputValue(e)}
                        />
                    </FormGroup>
                    <Button
                        className='rp-nav-btn float-end'
                        color='primary'
                        type='submit'
                    >
                        Report
                    </Button>
                    <Button
                        className='rp-nav-btn float-end'
                        color='secondary'
                        type='button'
                        onClick={() => {
                            props.onHandleChange();
                        }}
                    >
                        Cancel
                    </Button>
                </Form>
            </div>
        </div>
    );
}

export default ReportPopup;