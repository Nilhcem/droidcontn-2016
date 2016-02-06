package com.nilhcem.droidcontn.data.database.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.nilhcem.droidcontn.utils.Database;

import rx.functions.Func1;

public class Speaker {

    public static final String TABLE = "speakers";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String BIO = "bio";
    public static final String WEBSITE = "website";
    public static final String TWITTER = "twitter";
    public static final String GITHUB = "github";
    public static final String PHOTO = "photo";

    public final int id;
    public final String name;
    public final String title;
    public final String bio;
    public final String website;
    public final String twitter;
    public final String github;
    public final String photo;

    public Speaker(int id, String name, String title, String bio, String website, String twitter, String github, String photo) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.bio = bio;
        this.website = website;
        this.twitter = twitter;
        this.github = github;
        this.photo = photo;
    }

    public static final Func1<Cursor, Speaker> MAPPER = cursor -> {
        int id = Database.getInt(cursor, ID);
        String name = Database.getString(cursor, NAME);
        String title = Database.getString(cursor, TITLE);
        String bio = Database.getString(cursor, BIO);
        String website = Database.getString(cursor, WEBSITE);
        String twitter = Database.getString(cursor, TWITTER);
        String github = Database.getString(cursor, GITHUB);
        String photo = Database.getString(cursor, PHOTO);
        return new Speaker(id, name, title, bio, website, twitter, github, photo);
    };

    public static final class Builder {

        private final ContentValues values = new ContentValues();

        public Builder id(int id) {
            values.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public Builder title(String title) {
            values.put(TITLE, title);
            return this;
        }

        public Builder bio(String bio) {
            values.put(BIO, bio);
            return this;
        }

        public Builder website(String website) {
            values.put(WEBSITE, website);
            return this;
        }

        public Builder twitter(String twitter) {
            values.put(TWITTER, twitter);
            return this;
        }

        public Builder github(String github) {
            values.put(GITHUB, github);
            return this;
        }

        public Builder photo(String photo) {
            values.put(PHOTO, photo);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
