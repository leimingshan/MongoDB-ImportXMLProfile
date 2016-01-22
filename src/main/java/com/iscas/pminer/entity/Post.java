package com.iscas.pminer.entity;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Post class for name and job rank.
 *
 * @author Mingshan Lei
 * @since 1.0
 */
@Embedded public class Post {
    private String name;
    private String rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
