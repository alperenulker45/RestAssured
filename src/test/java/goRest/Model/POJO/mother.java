package goRest.Model.POJO;

import java.util.List;

public class mother {

    private meta meta;
    private List<Comments> data;

    public goRest.Model.POJO.meta getMeta() {
        return meta;
    }

    public void setMeta(goRest.Model.POJO.meta meta) {
        this.meta = meta;
    }

    public List<Comments> getData() {
        return data;
    }

    public void setData(List<Comments> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "mother{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
