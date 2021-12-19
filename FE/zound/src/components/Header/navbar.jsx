import React, { useEffect, useState } from 'react';
// import PropTypes from 'prop-types';
import { Collapse, DropdownItem, DropdownMenu, DropdownToggle, Nav, Navbar, NavbarBrand, NavbarText, NavbarToggler, NavItem, NavLink, UncontrolledDropdown } from 'reactstrap';
import AddArticleBtn from './addArticleBtn';
import "./navbarStyles.css"
import { useNavigate } from 'react-router-dom';
import profileApi from '../../api/profileApi';
import AdminManBtn from './adminManBtn';

// NavBar.propTypes = {};

function SignInUpNav(props) {
    return (
        <>
            <NavLink href="/sign-in">Sign In</NavLink>
            <NavLink href="/sign-up">Sign Up</NavLink>
        </>
    );
}

function UserAvtNav(props) {

    // const [reloadToggle, setReloadToggle] = useState(false);
    const [avatar, setAvatar] = useState("");

    let navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const username = localStorage.getItem("username");

                const response = await profileApi.get(username);

                // setProfileInfo(response);

                setAvatar(response.avatar);

                // console.log("Fetch profile successfully: ", response);
                
            } catch (error) {
                console.log("Failed to fetch profile info: ", error);
            }
        }

        fetchProfile();
    }, []);

    const logOutHandler = () => {
        localStorage.removeItem("username");
        localStorage.removeItem("token");
        localStorage.removeItem("role");

        props.onHandleChange();

        navigate("/articles");
    };

    return (
        <div className="user-avatar-nav">
            {/* <NavLink href="/profile">  
                <img src={avatar} alt="Avatar" />
            </NavLink> */}

            <UncontrolledDropdown
                // inNavbar
                // nav
            >
                <DropdownToggle
                    // caret
                    nav
                >
                    <img src={avatar} alt="Avatar" />
                </DropdownToggle>
                <DropdownMenu end>
                    <DropdownItem>
                        <NavLink href="/profile">
                            Profile
                        </NavLink>
                    </DropdownItem>
                    <DropdownItem>
                        <NavLink href="/change-password">
                            Change password
                        </NavLink>
                    </DropdownItem>
                    <DropdownItem divider />
                    <DropdownItem
                        onClick={() => logOutHandler()}
                        id="btn-sign-out">
                        Sign out
                    </DropdownItem>
                </DropdownMenu>
            </UncontrolledDropdown>

            {/* <Button
                color="primary"
                onClick={() => logOutHandler()}
            >
                Sign out
            </Button> */}
        </div>
    );
}

function NavBar(props) {

    const [reloadToggle, setReloadToggle] = useState(false);

    const receiveData = () => {
        setReloadToggle(!reloadToggle);
    };

    return (
        <div className="my-navbar">
            <Navbar
                color="light"
                expand="md"
                // fixed="top"
                light
            >
                <NavbarBrand id="my-brand-logo" href="/">
                Zound
                </NavbarBrand>
                <NavbarToggler className="me-2" onClick={function noRefCheck(){} } />
                <Collapse navbar>
                <Nav
                    className="me-auto"
                    navbar
                >
                    <NavItem>
                    <NavLink href="https://facebook.com/dinhphu.nguyen.355">
                        Facebook
                    </NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink href="https://github.com/dinhphu28">
                        GitHub
                    </NavLink>
                    </NavItem>
                </Nav>

                {/* <AddArticleBtn /> */}
                {(localStorage.getItem("role") === "mod" || localStorage.getItem("role") === "admin") ? <AddArticleBtn /> : ""}
                {(localStorage.getItem("role") === "admin") ? <AdminManBtn /> : ""}
                
                {(localStorage.getItem("username") !== null) ? <UserAvtNav onHandleChange={receiveData} /> : <SignInUpNav />}
                {/* <NavLink href="/sign-in">Sign In</NavLink>
                <NavLink href="/sign-up">Sign Up</NavLink> */}
                <NavbarText>
                    {(localStorage.getItem("username") !== null) ? localStorage.getItem("username") : ""}
                </NavbarText>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default NavBar;