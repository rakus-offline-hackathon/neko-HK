package hackathon.neko.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    @JsonProperty
    private String title;

    @JsonProperty
    private String code;

    @JsonProperty
    private String result;

    public Data(String title, String code, String result) {
        this.title = title;
        this.code = code;
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }
}
