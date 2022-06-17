import react from 'react';
import React from 'react';
import { useNavigate } from "react-router-dom";

function CheckPayment() {

    const [type, setType] = React.useState(''); 
    //const [index] = React.useState("0");
    const [amount, setAmount] = React.useState();
    const [uniqueId, setuniqueId] = React.useState('');

    //const [error, setError] = React.useState();

    const [isGet, setIsGet] = React.useState(false);
    const [bottom,setBottom] = React.useState("Check Payment");

    let navigate = useNavigate();

    const Tsubmit = () => {
        const settings = {
            method: 'get',
            body: JSON.stringify(),
        };
        console.log("%s", type)
        fetch('/getAllPayments', settings)
            .then(res => res.json())
            .then(data => {
                
                //setType(data.type);
                //setAmount(data.amount);
                //setuniqueId(data.uniqueId);
                
                var table = document.getElementById('myTable');
                table.innerHTML = "";

		        for (var i = 0; i < data.length; i++){
			        var row = `<tr>
							<td>${data[i].type}</td>
							<td>${data[i].uniqueId}</td>
							<td>${data[i].amount}</td>
                            <td>${data[i].sentBy}</td>
                            <td>${data[i].sentTo}</td>
					  </tr>`;
			        table.innerHTML += row;
                }
                console.log("send");
            })
            .catch(e => console.log(e));
    };

    if(isGet){
        setBottom("Get Next");
    }

    return (
        <div className="container">
            <h2>Transaction List</h2>

            <div className="row">   
            </div>
            <table className="table table-striped table-bordered">
                    <tr  className="list">
                        <th> Type</th>
                        <th> Payment Id</th>
                        <th> Amount</th>
                        <th> Sent By</th>
                        <th> Sent To</th>
                    </tr>
                    <tbody id = 'myTable'>
                         
                    </tbody>
                </table> 
            <h3></h3>
            <br></br>
            <button id="login-bt" className="submit" onClick={ Tsubmit }> { bottom }</button>
        </div>
    );
}
// TO DO:
// 1)  Fetch all the trasaction here
// 2) fixing html and css 

export default CheckPayment;

/*
*/

                /* */