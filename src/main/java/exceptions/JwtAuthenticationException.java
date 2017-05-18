package exceptions;

/**
 * Created by user on 16/05/17.
 */
public class JwtAuthenticationException extends Exception {
    public JwtAuthenticationException(String s){
        super(s);
    }
}
