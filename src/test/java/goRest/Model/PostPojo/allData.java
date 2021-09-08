package goRest.Model.PostPojo;

import goRest.Model.POJO.Comments;

import java.util.List;

public class allData {

    private meta meta;
    private List<PostData> postData;

    public goRest.Model.PostPojo.meta getMeta() {
        return meta;
    }

    public void setMeta(goRest.Model.PostPojo.meta meta) {
        this.meta = meta;
    }

    public List<PostData> getPostData() {
        return postData;
    }

    public void setPostData(List<PostData> postData) {
        this.postData = postData;
    }

    @Override
    public String toString() {
        return "allData{" +
                "meta=" + meta +
                ", postData=" + postData +
                '}';
    }
}
