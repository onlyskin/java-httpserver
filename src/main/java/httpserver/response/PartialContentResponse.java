package httpserver.response;

import httpserver.Range;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class PartialContentResponse extends Response {
    public PartialContentResponse(Range range, byte[] payload) {
        byte[] partialPayload = getPartialPayload(range, payload);
        super.setPayloadStream(new ByteArrayInputStream(partialPayload));
    }

    private byte[] getPartialPayload(Range range, byte[] payload) {
        return Arrays.copyOfRange(payload, range.getStart(), range.getEnd() + 1);
    }

    @Override
    public int getStatusCode() {
        return 206;
    }
}
