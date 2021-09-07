package goRest.Model.POJO;

import java.util.List;

public class meta {

    private pagination pagination;

    public goRest.Model.POJO.pagination getPagination() {
        return pagination;
    }

    public void setPagination(goRest.Model.POJO.pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "meta{" +
                "pagination=" + pagination +
                '}';
    }
}
