package com.projects.thirtyseven.glue;

/**
 * Created by ThirtySeven on 10.06.2017.
 */

class FBItem {

    private String title;
    private String text;
    private String URL;
    private String reachedTotal;
    private String reachedUnique;
    private String shares;

    public String getText() {
        return text;
    }

    public String getURL() {
        return URL;
    }

    public String getReachedTotal() {
        return reachedTotal;
    }

    public String getReachedUnique() {
        return reachedUnique;
    }

    public String getShares() {
        return shares;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setReachedTotal(String reachedTotal) {
        this.reachedTotal = reachedTotal;
    }

    public void setReachedUnique(String reachedUnique) {
        this.reachedUnique = reachedUnique;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
