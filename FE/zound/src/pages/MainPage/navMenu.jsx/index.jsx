import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Dropdown, DropdownItem, DropdownMenu, DropdownToggle, Input, Modal, ModalBody, ModalFooter, ModalHeader, Nav, NavItem, NavLink } from 'reactstrap';
import "./NavMenu.css";
// import PropTypes from 'prop-types';
import { ARTICLE_CATEGORIES } from '../../../constants/global';
import categoryApi from '../../../api/categoryApi';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';

// NavMenu.propTypes = {};

function NavMenu(props) {

    const [category, setCategory] = useState(ARTICLE_CATEGORIES.default.queryValue);
    const [hidden, setHidden] = useState(false);
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [categoryList, setCategoryList] = useState([]);
    const [loaded, setLoaded] = useState(false);
    const [modalAddCategoryOpen, setModalCategoryOpen] = useState(false);
    const [newCatName, setNewCatName] = useState("");
    const [reloadToggle, setReloadToggle] = useState(false);

    useEffect(() => {
        const fetchCategory = async () => {
            try {
                const response = await categoryApi.getAll();

                // console.log("Fetch category successfully: ", response);

                setCategoryList(response);

                setLoaded(true);
            } catch (error) {
                console.log("Failed to fetch category: ", error);
            }
        }

        fetchCategory();
    }, [reloadToggle]);

    const isInDefaultCategory = (catNameParam) => {
        if(catNameParam === "front-end" ||
            catNameParam === "back-end" ||
            catNameParam === "ios" ||
            catNameParam === "android" ||
            catNameParam === "tips-tricks" ||
            catNameParam === undefined
        ) {
            return true;
        } else {
            return false;
        }
    };

    const loadListCategoryDropdown = () => {
        if(loaded) {
            const listItems = categoryList.map((item) => {
                if(!isInDefaultCategory(item.name)) {
                    return <DropdownItem
                        key={item.name}
                        onClick={() => {
                            if(category !== item.name) {
                                setCategory(item.name);
                                props.onHandleChangeCat(item.name);
                            }
                        }}
                    >
                        {item.name}
                    </DropdownItem>
                }
                
                return "";
            });

            return listItems;
        }
    };

    const fetchAddCategory = async (catNameParam) => {
        try {
            const data = {
                name: catNameParam
            };

            await categoryApi.post(data);

            // console.log("Fetch add category successfully: ", response);

        } catch(error) {
            console.log("Failed to fetch add category: ", error);
        }

        setReloadToggle(!reloadToggle);
    }

    const changeInputValueCategoryName = (e) => {
        setNewCatName(e.target.value.trim());
    }

    return (
        <div className="nav-menu">
            <Nav
                pills
            >
                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.default.queryValue}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.default.queryValue) {
                                setCategory(ARTICLE_CATEGORIES.default.queryValue);
                                props.onHandleChangeCat(ARTICLE_CATEGORIES.default.queryValue);
                                // props.onHandleChange({
                                //     categoryName: category,
                                //     hidden: hidden
                                // });
                            }
                        }}
                        href="#"
                    >
                        Newest
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.front_end.queryValue}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.front_end.queryValue) {
                                setCategory(ARTICLE_CATEGORIES.front_end.queryValue);
                                props.onHandleChangeCat(ARTICLE_CATEGORIES.front_end.queryValue);
                                // props.onHandleChange({
                                //     categoryName: category,
                                //     hidden: hidden
                                // });
                            }
                        }}
                        href="#"
                    >
                        Front-End
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.back_end.queryValue}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.back_end.queryValue) {
                                setCategory(ARTICLE_CATEGORIES.back_end.queryValue);
                                props.onHandleChangeCat(ARTICLE_CATEGORIES.back_end.queryValue);
                                // props.onHandleChange({
                                //     categoryName: category,
                                //     hidden: hidden
                                // });
                            }
                        }}
                        href="#"
                    >
                        Back-End
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.ios.queryValue}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.ios.queryValue) {
                                setCategory(ARTICLE_CATEGORIES.ios.queryValue);
                                props.onHandleChangeCat(ARTICLE_CATEGORIES.ios.queryValue);
                                // props.onHandleChange({
                                //     categoryName: category,
                                //     hidden: hidden
                                // });
                            }
                        }}
                        href="#"
                    >
                        iOS
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.android.queryValue}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.android.queryValue) {
                                setCategory(ARTICLE_CATEGORIES.android.queryValue);
                                props.onHandleChangeCat(ARTICLE_CATEGORIES.android.queryValue);
                                // props.onHandleChange({
                                //     categoryName: category,
                                //     hidden: hidden
                                // });
                            }
                        }}
                        href="#"
                    >
                        Android
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.tips_tricks.queryValue}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.tips_tricks.queryValue) {
                                setCategory(ARTICLE_CATEGORIES.tips_tricks.queryValue);
                                props.onHandleChangeCat(ARTICLE_CATEGORIES.tips_tricks.queryValue);
                                // props.onHandleChange({
                                //     categoryName: category,
                                //     hidden: hidden
                                // });
                            }
                        }}
                        href="#"
                    >
                        Tips & Tricks
                    </NavLink>
                </NavItem>

                <Dropdown
                    nav
                    toggle={() => {
                        setDropdownOpen(!dropdownOpen);
                    }}
                    isOpen={dropdownOpen}
                >
                    <DropdownToggle
                        caret
                        // nav
                        color={loaded ? (isInDefaultCategory(category) ? null : "primary") : null}
                    >
                        {loaded ? (isInDefaultCategory(category) ? "Others" : category) : "Others"}
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem header>
                            Others category
                        </DropdownItem>
                        <DropdownItem divider />

                        {loaded ? loadListCategoryDropdown() : ""}
                    </DropdownMenu>
                </Dropdown>

                {(localStorage.getItem("role") === "admin") ?
                    <NavItem id="nav-item-add-cat">
                        <Button
                            color='success'
                            onClick={() => {
                                setModalCategoryOpen(true);
                            }}
                        >
                            <FontAwesomeIcon icon={faPlus} /> Add category
                        </Button>
                        <Modal
                            isOpen={modalAddCategoryOpen}
                        >
                            <ModalHeader>
                                Add new category
                            </ModalHeader>
                            <ModalBody>
                                <Input
                                    type="text"
                                    name="category-name"
                                    placeholder="Type new category name"
                                    onChange={e => changeInputValueCategoryName(e)}
                                />
                            </ModalBody>
                            <ModalFooter>
                                <Button
                                    color="primary"
                                    onClick={() => {
                                        if(newCatName.length < 1) {
                                            alert("Category name can't be empty");
                                        } else {
                                            fetchAddCategory(newCatName);
                                            setModalCategoryOpen(false);
                                        }
                                    }}
                                >
                                    Add
                                </Button>
                                {' '}
                                <Button onClick={() => {
                                    setModalCategoryOpen(false);
                                }}>
                                    Cancel
                                </Button>
                            </ModalFooter>
                        </Modal>
                    </NavItem>
                : ""}

                {/* <NavItem>
                    <NavLink
                        disabled
                        href="#"
                    >
                        Others
                    </NavLink>
                </NavItem> */}

                {(localStorage.getItem("role") === "mod" || localStorage.getItem("role") === "admin") ? 
                    <NavItem className="ms-auto">
                        <ButtonGroup>
                            <Button
                                color={hidden ? "secondary" : "primary"}
                                onClick={() => {
                                    setHidden(false);
                                    props.onHandleChangeHid(false);
                                }}
                            >
                                Shown article
                            </Button>
                            <Button
                                color={hidden ? "primary" : "secondary"}
                                onClick={() => {
                                    setHidden(true);
                                    props.onHandleChangeHid(true);
                                }}
                            >
                                Hidden article
                            </Button>
                        </ButtonGroup>
                    </NavItem>
                : ""}
            </Nav>
        </div>
    );
}

export default NavMenu;