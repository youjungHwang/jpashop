package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    //메세지를 넘겨줘야 하므로 오버라이드 해줌

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
