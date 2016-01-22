package com.iscas.pminer.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Embedded office record entity of person profile.
 *
 * @author Mingshan Lei
 * @since 1.0
 */
@Embedded public class OfficeRecord {
    private String startDate;
    private String endDate;

    /*
     * work location info contains province, city and district
     */
    private String province;
    private String city;
    private String district;

    // job position rank
    private String rank;

    @Embedded private List<Tuple> tupleList;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<Tuple> getTupleList() {
        return tupleList;
    }

    public void setTupleList(List<Tuple> tupleList) {
        this.tupleList = tupleList;
    }

}
