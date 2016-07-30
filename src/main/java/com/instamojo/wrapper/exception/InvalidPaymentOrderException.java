package com.instamojo.wrapper.exception;

/**
 * The type Invalid payment order exception.
 */
public class InvalidPaymentOrderException extends InstamojoBaseException {

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the                {@link #getCause()} method).  (A <tt>null</tt> value is                permitted, and indicates that the cause is nonexistent or                unknown.)
     * @since 1.4
     */
    public InvalidPaymentOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidPaymentOrderException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidPaymentOrderException(String message) {
        super(message);
    }

}
