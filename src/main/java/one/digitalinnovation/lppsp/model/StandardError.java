package one.digitalinnovation.lppsp.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.TimeZone;

public class StandardError implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String timestamp;
    private Integer status;
    private String error;
    private String path;

    public StandardError() {
    }

    public StandardError(Integer status, String error, String path) {
        this.timestamp = Instant.now().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime().toString();
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "{\n\"timestamp\": " + "\"" + timestamp + "\"," +
        "\n\t\"status\": " + status + "," +
        "\n\t\"error\": " + "\"" + error + "\"," +
        "\n\t\"path:\": " + "\"" + path + "\"" +
        "\n}";
    }
}
