package dto;

public class LoginResponseDto{
    Boolean isLoggedIn;
    String error;

    public LoginResponseDto(Boolean isLoggedIn, String error) {
        this.isLoggedIn = isLoggedIn;
        this.error = error;
    }

    public boolean get(){
        return isLoggedIn;
    }
}

