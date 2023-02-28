package se.kth.iv1201.appserv.jobapp.exceptions;

public class IllegalUserAuthenticationException extends Exception{
    private static final long serialVersionUID = 6355945960847848819L;

    public IllegalUserAuthenticationException(String msg){
        super(msg);
    }
}
