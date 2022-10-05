package ru.vsu.putin_p_a.web_server.http_protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    public static final String LINE_BREAKER = "\r\n";
    private final ByteArrayOutputStream content = new ByteArrayOutputStream();
    private ResponseStatus status;
    private byte[] response;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public OutputStream getOutputStream() {
        return content;
    }

    public void send(OutputStream clientOutput) throws HttpError, IOException {
        if (status == null) {
            throw new HttpError("Status wasn't set");
        }
        String head = "HTTP/1.1 " + status.getCode() + " " + status.getStatus() + LINE_BREAKER +
                "Content-length: " + content.toByteArray().length + LINE_BREAKER +
                "Connection: close" + LINE_BREAKER + LINE_BREAKER;
        clientOutput.write(head.getBytes());
        clientOutput.write(content.toByteArray());
        clientOutput.flush();
    }
}
