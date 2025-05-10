package business;

public class AppDbException extends RuntimeException {

    public AppDbException(String message) {
        super(message);
    }

    public AppDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class AppConnectionDbException extends AppDbException {
        public AppConnectionDbException(String message) { super(message); }

        public AppConnectionDbException(String message, Throwable cause) { super(message, cause); }
    }

    public static class AppQueryDbException extends AppDbException {
        public AppQueryDbException(String message) {
            super(message);
        }

        public AppQueryDbException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class AppUpdateDbException extends AppDbException {
        public AppUpdateDbException(String message) {
            super(message);
        }

        public AppUpdateDbException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
