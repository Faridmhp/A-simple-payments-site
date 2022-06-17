import React from 'react';

function Cash() {
    // state Variables
    const [type, setType] = React.useState('cash');
    const [amount, setAmount] = React.useState();
    const [sentTo, setsentTo] = React.useState("");
    const [Note, setNote] = React.useState("");
    
    const [resSuccess, setSResult] = React.useState(false);
    //return
    const [uid, setUid] = React.useState("");
    const [error, setError] = React.useState("");

    const CashSubmit = () => {
        const body = {
            amount: amount,
            type: type,
            sentTo: sentTo,
            Note: Note
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body),
        };
        console.log("%s", amount)
        fetch('/makeCashPayment', settings)
        .then(res => res.json())
            .then(data => {
                //console.log(data.success);
                //console.log(data.paymentUId);
                if (data.success) {
                    setUid(data.paymentUId);
                    setSResult(true);
                } else {
                    setError(data.error);
                }
                console.log("send")
            })
            .catch(e => console.log(e));
    };

    if(resSuccess){
        return (<div className="container"> Success!  payment id: { uid }! </div>);
    }

    return (
        <div class="container">
                <h2>Cash Payment</h2>
                <input type="text" id="image-title" class="type" placeholder="Amount"
                    value={amount}
                    onChange={(event) => setAmount(event.target.value)}>
                </input>
                <input type="text" id="image-title" class="type" placeholder="sentTo"
                    value={sentTo}
                    onChange={(event) => setsentTo(event.target.value)}>
                </input>
                <input type="text" id="image-title" class="type" placeholder="Note"
                    value={Note}
                    onChange={(event) => setNote(event.target.value)}>
                </input>
                <br></br>
                {resSuccess}
                {error}
                <button id="upload-btn" type="submit" class="submit" onClick={ CashSubmit }>Submit</button>
        </div>
    );
        // TO DO:
        // 1)  Make sure all the input are field before submiting
        // 2) Navigate the page to Home page after the submit


}

export default Cash;