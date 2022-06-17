package dto;

public class paymentResponseDto{
    Boolean success;
    String paymentUId;
    String error;

    public paymentResponseDto(String paymentUId, String error) {
        this.paymentUId = paymentUId;
        this.error = error;
        if(this.error == null){
            this.success = true;
        }
        else{
            this.success = false;
        }
    }

    public boolean check(){
        return error != null;
    }
}
