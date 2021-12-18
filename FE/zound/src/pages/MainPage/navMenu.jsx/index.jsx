import React, { useState } from 'react';
import { Nav, NavItem, NavLink } from 'reactstrap';
import "./NavMenu.css";
// import PropTypes from 'prop-types';
import { ARTICLE_CATEGORIES } from '../../../constants/global';

// NavMenu.propTypes = {};

function NavMenu(props) {

    const [category, setCategory] = useState(ARTICLE_CATEGORIES.default);

    return (
        <div className="nav-menu">
            <Nav pills>
                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.default}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.default) {
                                setCategory(ARTICLE_CATEGORIES.default);
                                props.onHandleChange(ARTICLE_CATEGORIES.default);
                            }
                        }}
                        href="#"
                    >
                        Newest
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.front_end}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.front_end) {
                                setCategory(ARTICLE_CATEGORIES.front_end);
                                props.onHandleChange(ARTICLE_CATEGORIES.front_end);
                            }
                        }}
                        href="#"
                    >
                        Front-End
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.back_end}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.back_end) {
                                setCategory(ARTICLE_CATEGORIES.back_end);
                                props.onHandleChange(ARTICLE_CATEGORIES.back_end);
                            }
                        }}
                        href="#"
                    >
                        Back-End
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.ios}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.ios) {
                                setCategory(ARTICLE_CATEGORIES.ios);
                                props.onHandleChange(ARTICLE_CATEGORIES.ios);
                            }
                        }}
                        href="#"
                    >
                        iOS
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.android}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.android) {
                                setCategory(ARTICLE_CATEGORIES.android);
                                props.onHandleChange(ARTICLE_CATEGORIES.android);
                            }
                        }}
                        href="#"
                    >
                        Android
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        active={category === ARTICLE_CATEGORIES.tips_tricks}
                        onClick={() => {
                            if(category !== ARTICLE_CATEGORIES.tips_tricks) {
                                setCategory(ARTICLE_CATEGORIES.tips_tricks);
                                props.onHandleChange(ARTICLE_CATEGORIES.tips_tricks);
                            }
                        }}
                        href="#"
                    >
                        Tips & Tricks
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        disabled
                        href="#"
                    >
                        Others
                    </NavLink>
                </NavItem>
            </Nav>
        </div>
    );
}

export default NavMenu;