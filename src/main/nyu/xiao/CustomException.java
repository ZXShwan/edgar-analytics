package main.nyu.xiao;

/**
 * Author: zx
 * Date: 04/04/2018
 * Description: CustomException class, throws my custom exception
 */
public class CustomException extends RuntimeException {

    public CustomException() {

    }

    public CustomException(String message) {
        super(message);
    }
}
