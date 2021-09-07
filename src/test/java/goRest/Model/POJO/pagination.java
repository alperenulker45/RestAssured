package goRest.Model.POJO;

import goRest.Model.POJO.links;

import java.util.List;

public class pagination {

    private int total;
    private int pages;
    private int page;
    private int limit;
    private links links;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public goRest.Model.POJO.links getLinks() {
        return links;
    }

    public void setLinks(goRest.Model.POJO.links links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "pagination{" +
                "total=" + total +
                ", pages=" + pages +
                ", page=" + page +
                ", limit=" + limit +
                ", links=" + links +
                '}';
    }
}
