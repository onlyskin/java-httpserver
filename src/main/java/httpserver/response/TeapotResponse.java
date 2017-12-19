package httpserver.response;

public class TeapotResponse extends Response {
    public TeapotResponse() {
        super.setPayload("I'm a teapot".getBytes());
    }

    public int getStatusCode() {
        return 418;
    }
}
