import React, { useState } from 'react';
import { Button, Input, Label } from 'reactstrap';
import articleApi from '../../api/articleApi';
import { ARTICLE_CATEGORIES } from '../../constants/global';
import "./AddArticle.css";
// import PropTypes from 'prop-types';

// AddArticle.propTypes = {};

function AddArticle(props) {

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [content, setContent] = useState("");
    const [thumbnailUrl, setThumbnailUrl] = useState("");
    const [category, setCategory] = useState(ARTICLE_CATEGORIES.front_end.queryValue);

    const changeInputValueTitle = (e) => {
        setTitle(e.target.value);
    };
    const changeInputValueDescription = (e) => {
        setDescription(e.target.value);
    };
    const changeInputValueContent = (e) => {
        setContent(e.target.value);
    };
    const changeInputValueThumbnailUrl = (e) => {
        setThumbnailUrl(e.target.value);
    };
    const changeInputValueCategory = (e) => {
        setCategory(getQueryValueFromLabel(e.target.value));
    };

    const validationForm = () => {
        let returnData = {
            error: false,
            msg: ""
        }

        if(title.length < 1) {
            returnData = {
                error: true,
                msg: "Title can't be empty"
            }
        }

        if(description.length < 1) {
            returnData = {
                error: true,
                msg: "Description can't be empty"
            }
        }

        if(content.length < 1) {
            returnData = {
                error: true,
                msg: "Content can't be empty"
            }
        }

        if(thumbnailUrl.length < 10) {
            returnData = {
                error: true,
                msg: "Wrong url length"
            }
        }

        return returnData;
    };

    const submitForm = (e) => {
        e.preventDefault();

        const validation = validationForm();

        if(validation.error) {
            alert(validation.msg);
        } else {
            alert("Submit success")
            // handle submit ok here
            createArticleToBE();
        }
    }

    const createArticleToBE = async () => {
        try {
            const data = {
                author: localStorage.getItem("username"),
                title: title,
                description: description,
                content: content,
                thumbnailUrl: thumbnailUrl,
                category: category
            };

            const response = await articleApi.post(data);

            console.log("Post article successfully: ", response);

        } catch(error) {
            console.log("Failed to post article to BE: ", error);
        }
    }

    const getQueryValueFromLabel = (label) => {
        if(label === ARTICLE_CATEGORIES.front_end.label) {

            return ARTICLE_CATEGORIES.front_end.queryValue

        } else if(label === ARTICLE_CATEGORIES.back_end.label) {

            return ARTICLE_CATEGORIES.back_end.queryValue

        } else if(label === ARTICLE_CATEGORIES.ios.label) {

            return ARTICLE_CATEGORIES.ios.queryValue

        } else if(label === ARTICLE_CATEGORIES.android.label) {

            return ARTICLE_CATEGORIES.android.queryValue

        } else if(label === ARTICLE_CATEGORIES.tips_tricks.label) {

            return ARTICLE_CATEGORIES.tips_tricks.queryValue
        }
    };

    return (
        <div>
            <form
                className="container"
                style={{paddingTop: "2%"}}
                onSubmit={e => {
                    submitForm(e);
                }}
            >
                <div className="title-area my-glob">
                    <Label>
                        Title:
                    </Label>
                    <Input
                        type="textarea"
                        name="title"
                        onChange={e => changeInputValueTitle(e)}
                    />
                </div>
                <div className="description-area my-glob">
                    <Label>
                        Description:
                    </Label>
                    <Input
                        type="textarea"
                        name="description"
                        onChange={e => changeInputValueDescription(e)}
                    />
                </div>
                <div className="content-area my-glob">
                    <Label>
                        Content:
                    </Label>
                    <Input
                        type="textarea"
                        name="content"
                        onChange={e => changeInputValueContent(e)}
                    />
                </div>
                <div className="thumbnail-area my-glob">
                    <Label>
                        Thumbnail URL:
                    </Label>
                    <Input
                        type="url"
                        name="thumbnail"
                        onChange={e => changeInputValueThumbnailUrl(e)}
                    />
                </div>
                <div className="category-area my-glob">
                    <Label>
                        Category:
                    </Label>
                    <Input
                        type="select"
                        name="category"
                        onChange={e => changeInputValueCategory(e)}
                    >
                        <option>
                            {ARTICLE_CATEGORIES.front_end.label}
                        </option>
                        <option>
                            {ARTICLE_CATEGORIES.back_end.label}
                        </option>
                        <option>
                            {ARTICLE_CATEGORIES.ios.label}
                        </option>
                        <option>
                            {ARTICLE_CATEGORIES.android.label}
                        </option>
                        <option>
                            {ARTICLE_CATEGORIES.tips_tricks.label}
                        </option>
                    </Input>
                </div>
                <div className="confirm-btn">
                    <Button id="btn-create"
                        block
                        color="primary"
                        type="submit"
                    >
                        Create article
                    </Button>
                </div>
            </form>
        </div>
    );
}

export default AddArticle;