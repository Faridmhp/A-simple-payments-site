import React from 'react';
import { useNavigate } from "react-router-dom";
import { useEffect } from 'react/cjs/react.production.min';
import isLogin from './globalData';

function Log() {
      // state Variables
    const [username, setUserName] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [isLogedIn, setIsLogIn] = React.useState(false);
    const [isRegister, setIsregister] = React.useState(false);
    const [error, setError] = React.useState(null);
    //const [display,setDisplay] = React.useState("display:none");

    // navigate variable TO NAVIGATE THE PAGE BACK TO THE HOME AFTER LOGIN IN 
    let navigate = useNavigate();

    const handleSubmit = () => {
        const body = {
            username: username,
            password: password,
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body),
        };
        console.log("%s", username)
        fetch('/logIn', settings)
            .then(res => res.json())
            .then(data => {
                if (data.isLoggedIn) {
                    isLogin.isLoggedin = true;
                    isLogin.username = username;
                    setIsLogIn(true);
                } else if (data.error) {
                    setError(data.error);
                }
                console.log("send")
            })
            .catch(e => console.log(e));
    };

    const regSubmit = () => {
        const body = {
            username: username,
            password: password,
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body),
        };
        console.log("%s", username)
    fetch('/register', settings)
        .then(res => res.json())
        .then(data => {
            if (data.isLoggedIn) {
                isLogin.isLoggedin = true;
                isLogin.username = username;
                setIsregister(true);
            } else if (data.error) {
                setError(data.error);
            }
            console.log("send")
        })
        .catch(e => console.log(e));
};

    if(isLogedIn){
        //document.getElementById("image-form").style.display = "none";
        //setDisplay("none")
        document.getElementById("login-btn").style.display = "None";
        document.getElementById("logout-btn").style.display = "Block";
        return (<div className="container"><h1> Welcome back, { username }! </h1> </div>);
    }
    else if(isRegister){
        document.getElementById("login-btn").style.display = "None";
        document.getElementById("logout-btn").style.display = "Block";
        return (<div className="container"><h1> Thanks for register, { username }! </h1> </div>);
    }

    if(isLogin.isLoggedin){
        //document.getElementById("image-form").style.display = none;
        return (<div className="container"><h1> Welcome back, { isLogin.username }! </h1> </div>);
    }

    return (
        <div className="container">
            <h2>Login</h2>
                <input type="text" id="user-input" className="type" placeholder="Username" required name="username"
                    value={username}
                    onChange={(event) => setUserName(event.target.value)}>
                </input>
                <br></br>
                <input id="pass-input" type="password" className="type" placeholder="Password" required name="password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}> 
                </input>
                {error}
                <br></br>
                <button id="login-bt" className="submit" onClick={ handleSubmit }>Login</button>
                <button id="login-bt" className="submit" onClick={ regSubmit }>Register</button>
        </div>
    );
        // TO DO:
        // 1)  Navigate the page to a choosing payment aption page (you have to hide the payment bars before login the user)

}

export default Log;

/*                () => {
    let userInput = document.getElementById("user-input");
    let passInput = document.getElementById("pass-input");
    if (!userInput.value) {
        alert('Please enter your name');
    }
    else if (!passInput.value) {
        alert('Please enter your name');
    } else {
        navigate("/home");
    }

                <form id="login-form" method="POST" action="" enctype="application/x-www-form-urlencoded">
                

            </form>
            type="submit"
}*/