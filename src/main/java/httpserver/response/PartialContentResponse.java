package httpserver.response;

import java.util.Arrays;

public class PartialContentResponse extends Response {
    public PartialContentResponse(int start, int end, byte[] payload) {
        byte[] partialPayload = Arrays.copyOfRange(payload, start, end);
        super.setPayload(partialPayload);
    }

    @Override
    public int getStatusCode() {
        return 206;
    }
}
