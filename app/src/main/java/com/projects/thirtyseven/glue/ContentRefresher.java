package com.projects.thirtyseven.glue;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11.06.2017.
 */

class ContentRefresher {
    public void refreshWeb() {
        FeedParser parser = new FeedParser();
        List<FeedParser.Entry> entries = null;
        InputStream input = null;
        try {
            input = new URL("https://kloop.kg/feed/atom/").openStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            entries = parser.parse(input);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (FeedParser.Entry entry : entries){
            Log.i("Glia Atom", entry.title + " " + entry.link);
        }
    }
}
