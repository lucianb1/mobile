package ro.hoptrop.test.mock;

/**
 * Created by Luci on 05-Mar-17.
 */
public class EdgeServerResponse<T> {

    private int status;
    private T content;

    public EdgeServerResponse(int status, T content) {
        this.status = status;
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public T getContent() {
        return content;
    }
}
