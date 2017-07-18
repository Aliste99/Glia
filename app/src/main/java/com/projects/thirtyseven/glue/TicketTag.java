package com.projects.thirtyseven.glue;

/**
 * Created by ThirtySeven on 18-июл-2017.
 */

class TicketTag {
    private int color;
    private String title;
    private String id;

    public TicketTag() {
    }

    public TicketTag(int color, String title, String id) {
        this.color = color;
        this.title = title;
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }
}
