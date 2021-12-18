// import logo from './logo.svg';
import { Suspense } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import NotFound from './components/NotFound/index';
import Header from './components/Header/index';
import MainPage from './pages/MainPage/index.jsx';
import Footer from './components/Footer';
import Contact from './components/Contact';
import SignInPage from './pages/SignInPage';
import SignUpPage from './pages/SignUpPage';
import AddArticle from './pages/AddArticle/AddArticle';
import { useState } from 'react';
import Profile from './pages/Profile/index';
import ChangePassword from './pages/ChangePassword/index';
import AdminMan from './pages/AdminManagement/AdminMan';

function App() {

  const [reloadToggle, setReloadToggle] = useState(false);

  const receiveReloadToggle = () => {
    setReloadToggle(!reloadToggle);
  };

  return (
    <div className="my-app">
      {/* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header> */}
      <Suspense fallback={<div>Loading...</div>}>
        <BrowserRouter>
          <Header />
          
          <Routes>
            <Route path="/articles/*" element={<MainPage />} />
            <Route path="/" element={<Navigate replace to="/articles" />} />

            <Route path="/sign-in" element={<SignInPage onHandleChange={receiveReloadToggle} />} />
            <Route path="/sign-up" element={<SignUpPage />} />
            <Route path="/change-password" element={<ChangePassword />} />

            <Route path="/create-article" element={<AddArticle />} />
            
            <Route path="/profile" element={<Profile />} />

            <Route path="/admin" element={<AdminMan />} />

            <Route path="*" element={<NotFound />} />
          </Routes>

          <Contact />
          <Footer />
        </BrowserRouter>
      </Suspense>
    </div>
  );
}

export default App;