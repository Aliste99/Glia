package com.projects.thirtyseven.glue;

import android.graphics.drawable.Drawable;

/**
 * Created by alexwalker on 10.06.17.
 */

class Author {
    public Author(){}

    String name;
    Drawable photo;

    public Author(String name, Drawable photo) {

        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getPhoto() {
        return photo;
    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }
}
