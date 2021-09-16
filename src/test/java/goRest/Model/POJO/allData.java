package goRest.Model.POJO;

import goRest.Model.POJO.PostData;

import java.util.List;

public class allData {

   private meta meta;
   private List<PostData> data;

    public goRest.Model.POJO.meta getMeta() {
        return meta;
    }

    public void setMeta(goRest.Model.POJO.meta meta) {
        this.meta = meta;
    }

    public List<PostData> getData() {
        return data;
    }

    public void setData(List<PostData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "allData{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
