package httpserver.response;

public class FourEighteenResponse extends Response {
    public FourEighteenResponse() {
        super.setPayload("I'm a teapot".getBytes());
    }

    public int getStatusCode() {
        return 418;
    }
}
