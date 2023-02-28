package se.kth.iv1201.appserv.jobapp.exceptions;

/**
 * Class representing an exception if a new user was registered with an error.
 */
public class IllegalUserRegisterException extends Exception{
    private static final long serialVersionUID = 6355945960847848819L;

    public IllegalUserRegisterException(String msg){
        super(msg);
    }
}