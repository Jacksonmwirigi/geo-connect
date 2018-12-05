package com.jackoyee.geopics.session;

public interface Session {
    public boolean isLoggedIn();


     String getToken();

    void saveEamil(String email);
     String getEmail();

    void savePasword(String password);
     String getPassword();

    void invalidate();

    void saveToken(String token);


}
