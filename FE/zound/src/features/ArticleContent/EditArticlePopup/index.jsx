import React, { useEffect, useState } from 'react';
import { Button, Input, Label } from 'reactstrap';
import articleApi from '../../../api/articleApi';
import { ARTICLE_CATEGORIES } from '../../../constants/global';
import "./EditArticlePopup.css";
// import PropTypes from 'prop-types';

// EditArticlePopup.propTypes = {};

function EditArticlePopup(props) {

    const {article} = props;

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [content, setContent] = useState("");
    const [thumbnailUrl, setThumbnailUrl] = useState("");
    const [category, setCategory] = useState(ARTICLE_CATEGORIES.front_end.queryValue);
    const [newArticle, setNewArticle] = useState({});

    useEffect(() => {
        setNewArticle(article);
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
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
            updateArticleToBE();

            props.onHandleChange();
        }
    }

    const updateArticleToBE = async () => {
        try {
            const data = {
                userAgent: localStorage.getItem("username"),
                title: title,
                description: description,
                content: content,
                thumbnailUrl: thumbnailUrl,
                category: category
            };

            const response = await articleApi.put(article.id, data);

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

    const getLabelFromQueryValue = (label) => {
        if(label === ARTICLE_CATEGORIES.front_end.queryValue) {

            return ARTICLE_CATEGORIES.front_end.label

        } else if(label === ARTICLE_CATEGORIES.back_end.queryValue) {

            return ARTICLE_CATEGORIES.back_end.label

        } else if(label === ARTICLE_CATEGORIES.ios.queryValue) {

            return ARTICLE_CATEGORIES.ios.label

        } else if(label === ARTICLE_CATEGORIES.android.queryValue) {

            return ARTICLE_CATEGORIES.android.label

        } else if(label === ARTICLE_CATEGORIES.tips_tricks.queryValue) {

            return ARTICLE_CATEGORIES.tips_tricks.label
        }
    };

    return (
        <div>
            <div className="layer"></div>
            <div className="my-popup2">
                <form
                    className="container"
                    // style={{paddingTop: "2%"}}
                    onSubmit={e => {
                        submitForm(e);
                    }}
                >
                    <div className="title-area2 my-glob2">
                        <Label>
                            Title:
                        </Label>
                        <Input
                            type="textarea"
                            name="title"
                            onChange={e => changeInputValueTitle(e)}
                            defaultValue={newArticle.title}
                        />
                    </div>
                    <div className="description-area2 my-glob2">
                        <Label>
                            Description:
                        </Label>
                        <Input
                            type="textarea"
                            name="description"
                            onChange={e => changeInputValueDescription(e)}
                            defaultValue={newArticle.description}
                        />
                    </div>
                    <div className="content-area2 my-glob2">
                        <Label>
                            Content:
                        </Label>
                        <Input
                            type="textarea"
                            name="content"
                            onChange={e => changeInputValueContent(e)}
                            defaultValue={newArticle.content}
                        />
                    </div>
                    <div className="thumbnail-area2 my-glob2">
                        <Label>
                            Thumbnail URL:
                        </Label>
                        <Input
                            type="url"
                            name="thumbnail"
                            onChange={e => changeInputValueThumbnailUrl(e)}
                            defaultValue={newArticle.thumbnailUrl}
                        />
                    </div>
                    <div className="category-area2 my-glob2">
                        <Label>
                            Category:
                        </Label>
                        <Input
                            type="select"
                            name="category"
                            onChange={e => changeInputValueCategory(e)}
                            defaultValue={getLabelFromQueryValue(article.category)}
                        >
                            <option
                                // selected={(article.category === "front-end") ? true : false}
                                // selected={article.category === getQueryValueFromLabel(ARTICLE_CATEGORIES.front_end.label)}
                            >
                                {ARTICLE_CATEGORIES.front_end.label}
                            </option>
                            <option
                                // selected={article.category === getQueryValueFromLabel(ARTICLE_CATEGORIES.back_end.label)}
                            >
                                {ARTICLE_CATEGORIES.back_end.label}
                            </option>
                            <option
                                // selected={article.category === getQueryValueFromLabel(ARTICLE_CATEGORIES.ios.label)}
                            >
                                {ARTICLE_CATEGORIES.ios.label}
                            </option>
                            <option
                                // selected={article.category === getQueryValueFromLabel(ARTICLE_CATEGORIES.android.label)}
                            >
                                {ARTICLE_CATEGORIES.android.label}
                            </option>
                            <option
                                // selected={article.category === getQueryValueFromLabel(ARTICLE_CATEGORIES.tips_tricks.label)}
                            >
                                {ARTICLE_CATEGORIES.tips_tricks.label}
                            </option>
                        </Input>
                    </div>
                    <div className="confirm-btn2">
                        <Button id="btn-update2"
                            block
                            color="primary"
                            type="submit"
                        >
                            Update article
                        </Button>
                        <Button id="btn-cancel2"
                            block
                            color="secondary"
                            type="button"
                            onClick={() => {
                                props.onHandleChange();
                            }}
                        >
                            Cancel
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default EditArticlePopup;