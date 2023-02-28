package se.kth.iv1201.appserv.jobapp.exceptions;

/**
 * Class representing an exception if the user was authenticated with an error.
 */
public class IllegalUserAuthenticationException extends Exception{
    private static final long serialVersionUID = 6355945960847848819L;

    public IllegalUserAuthenticationException(String msg){
        super(msg);
    }
}
