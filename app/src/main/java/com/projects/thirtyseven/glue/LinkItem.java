package com.projects.thirtyseven.glue;

/**
 * Created by ThirtySeven on 10.06.2017.
 */

class LinkItem {

    String id;
    String link;
    long published;
    String title;

    public LinkItem(){}

    public LinkItem(String id, String link, int published, String title) {
        this.id = id;
        this.link = link;
        this.published = published;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getPublished() {
        return published;
    }

    public void setPublished(long published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
