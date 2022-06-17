import React from 'react';

function Credit() {
    // state Variables
    const [type, setType] = React.useState('credit');
    //enter
    const [amount, setAmount] = React.useState();
    const [number, setNumber] = React.useState();
    const [securityCode, setSecurityCode] = React.useState();
    const [sentTo, setsentTo] = React.useState("");
    const [Note, setNote] = React.useState("");
    //return
    const [uid, setUid] = React.useState("");
    const [resSuccess, setSResult] = React.useState(false);
    const [resFail, setFResult] = React.useState(false);

    const [error, setError] = React.useState(null);

    const creditSubmit = () => {
        const body = { 
            type: type,
            amount: amount,
            number: number,
            securityCode: securityCode,
            sentTo: sentTo,
            Note: Note
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body),
        };
        console.log("%s", uid)
        fetch('/makeCreditCardPayment', settings)
            .then(res => res.json())
            .then(data => {
                //console.log(data.success);
                //console.log(data.paymentUId);
                if (data.success) {
                    setUid(data.paymentUId);
                    setSResult(true);
                } else if(data.error){
                    setFResult(true);
                    setError(data.error);
                }
                console.log("send")
            })
            .catch(e => console.log(e));
    };

    if(resSuccess){
        return (<div className="container"> Success!  payment id: { uid }! </div>);
    }
    
    if(resFail){
        return (<div className="container"> Error! {error} </div>);
    }
    
    return(
        <div class="container">

                <h2>Credit Payment</h2>
                <input type="text" id="image-title" class="type" placeholder="Amount"
                    value={amount}
                    onChange={(event) => setAmount(event.target.value)}>
                </input>
                <br></br>
                <input type="text" id="image-title" class="type" placeholder="Number"
                    value={number}
                    onChange={(event) => setNumber(event.target.value)}>
                </input>
                <br></br>
                <input type="text" id="image-title" class="type" placeholder="Security Code"
                    value={securityCode}
                    onChange={(event) => setSecurityCode(event.target.value)}>
                </input>
                <input type="text" id="image-title" class="type" placeholder="sentTo"
                    value={sentTo}
                    onChange={(event) => setsentTo(event.target.value)}>
                </input>
                <input type="text" id="image-title" class="type" placeholder="Note"
                    value={Note}
                    onChange={(event) => setNote(event.target.value)}>
                </input>
                {error}
                <br></br>
                <button id="upload-btn" type="submit" class="submit"  onClick={ creditSubmit }>Submit</button>

        </div>
    );
        // TO DO:
        // 1)  Make sure all the input are field before submiting
        // 2) Navigate the page to Home page after the submit
}

export default Credit;