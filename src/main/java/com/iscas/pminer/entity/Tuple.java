package com.iscas.pminer.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

/**
 * Embedded tuple entity for office record.
 *
 * @author Mingshan Lei
 * @since 1.0
 */
@Embedded public class Tuple {
    private String content;
    private String rank;

    private List<String> unitNameList;
    private List<String> unitRankList;
    @Embedded private List<Post> postArray;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<String> getUnitNameList() {
        return unitNameList;
    }

    public void setUnitNameList(List<String> unitNameList) {
        this.unitNameList = unitNameList;
    }

    public List<String> getUnitRankList() {
        return unitRankList;
    }

    public void setUnitRankList(List<String> unitRankList) {
        this.unitRankList = unitRankList;
    }

    public List<Post> getPostArray() {
        return postArray;
    }

    public void setPostArray(List<Post> postArray) {
        this.postArray = postArray;
    }
}
