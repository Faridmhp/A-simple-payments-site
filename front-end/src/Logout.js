import React from 'react';
import { useNavigate } from "react-router-dom";
import { useEffect } from 'react/cjs/react.production.min';
import isLogin from './globalData';

function Logout() {
// state Variables
  const [username, setUserName] = React.useState('');
  //const [password, setPassword] = React.useState('');
  const [isLogedOut, setIsLogOut] = React.useState(false);
  //const [isRegister, setIsregister] = React.useState(false);
  const [error, setError] = React.useState(null);
  //const [display,setDisplay] = React.useState("display:none");

  // navigate variable TO NAVIGATE THE PAGE BACK TO THE HOME AFTER LOGIN IN 
  let navigate = useNavigate();

  const handleSubmit = () => {

      const settings = {
          method: 'post',
          body: JSON.stringify(),
      };
      console.log("%s", username)
      fetch('/logOut', settings)
          .then(res => res.json())
          .then(data => {
              if (data.isLoggedIn) {
                  isLogin.isLoggedin = false;
                  isLogin.username = "";
                  setIsLogOut(true);
              } else if (data.error) {
                  setError(data.error);
              }
              console.log("send")
          })
          .catch(e => console.log(e));
  };


  if(isLogedOut){
      //document.getElementById("image-form").style.display = "none";
      //setDisplay("none")
      document.getElementById("login-btn").style.display = "Block";
      document.getElementById("logout-btn").style.display = "None";
      return (<div className="container"><h1> Goodbey </h1> </div>);
  }

  return (
      <div className="container">
          <h1>You are trying to logout: </h1>
              {error}
              <br></br>
              <button id="login-bt" className="submit" onClick={ handleSubmit }>Logout</button>
              <button id="login-bt" className="submit" > Cancel </button>
      </div>
  );
      // TO DO:
      // 1)  Navigate the page to a choosing payment aption page (you have to hide the payment bars before login the user)

}

export default Logout;