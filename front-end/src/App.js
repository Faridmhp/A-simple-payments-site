import './App.css';
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'
//import CheckPayment from './CheckPayment';
import Log from './Log';
import Cash from './Cash';
import Credit from './Credit';
import Register from './register';
import isLogin from './globalData';
import CheckPayment from './CheckPayment';
import Logout from './Logout';

function App() {

  //Using Router for navigating items in Navbar
  //Inserting the Navbar in Router so it would show in all the pages
  var [display] = React.useState();

  return (
    <Router>
        <div>
        <div id="banner">
          <div id="banner-title">
            Team Missing PayApp
          </div>
        </div>
        <nav className="navbar">
          <div className="nav-items">
            <div><Link to="/" id="home-btm" className="nav-btm">Home</Link></div>
            <div><Link to="/credit" id="post-image-btm" className="nav-btm" >Credit Payment</Link></div>
            <div><Link to="/cash" id="post-image-btm" className="nav-btm" >Cash Payment</Link></div>
            <div><Link to="/checkPayment" id="post-image-btm" className="nav-btm" >Check Payment</Link></div>
            <div id = "skip"> 111111111111111111111111111111111111 </div>
            <div id="login-btn"><Link to="/login"  className="nav-btm" >Login</Link></div>
            <div id="logout-btn"><Link to="/logout" className="nav-btm">Logout</Link></div>
          </div>
        </nav>
      </div>
      <Routes>
        <Route path="/login" element={<Log />} />
        <Route path="/cash" element={<Cash />} />
        <Route path="/credit" element={<Credit />} />
        <Route path="/" element={< root />} />
        <Route path="/checkPayment" element={<CheckPayment />} />
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </Router>
  );
}

export default App;

/*            <div><Link to="/register" id="register-btm" className="nav-btm">register</Link></div>
            <div><Link to="/login" id="login-btn" className="nav-btm">Login</Link></div>*
                    <Route path="/register" element={< Register />} />*/