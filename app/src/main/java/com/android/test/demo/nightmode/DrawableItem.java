package com.android.test.demo.nightmode;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import com.android.test.demo.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tech on 18-4-12.
 */

final class DrawableItem {
    public static final String TAG = "DrawableItem";

    public final String name;

    public final int id;

    private Drawable drawable;

    private boolean loaded;

    public DrawableItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Drawable getDrawable(Context context) {
        if (loaded) {
            return drawable;
        }

        loaded = true;

        if (id != 0) {
            drawable = context.getDrawable(id);
        }

        return drawable;
    }

    public static List<DrawableItem> loadDrawables(Resources r, int resId) {
        final List<DrawableItem> result = new ArrayList<>();

        final XmlResourceParser parser = r.getXml(resId);
        int type;

        try {
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
                if (type != XmlPullParser.START_TAG) {
                    continue;
                }
                if (!parser.getName().equals("item")) {
                    continue;
                }
                TypedArray a = r.obtainAttributes(parser, R.styleable.DrawableItem);
                final String name = a.getString(R.styleable.DrawableItem_name);
                final int id = a.getResourceId(R.styleable.DrawableItem_drawable, 0);
                result.add(new DrawableItem(name, id));
                a.recycle();
            }
        } catch (Exception e) {}

        return result;
    }
}
