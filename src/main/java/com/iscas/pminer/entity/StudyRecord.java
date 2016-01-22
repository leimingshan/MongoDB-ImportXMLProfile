package com.iscas.pminer.entity;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Embedded entity for study record in person profile.
 *
 * @author Mingshan Lei
 * @since 1.0
 */
@Embedded public class StudyRecord {
    private String province;
    private String city;
    private String universityName;
    private String universityIs985;
    private String universityIs211;
    private String school;
    private String department;
    private String major;
    private String diploma;
    private String degree;

    // startDate & endDate are only for previous study records
    private String startDate;
    private String endDate;

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

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityIs985() {
        return universityIs985;
    }

    public void setUniversityIs985(String universityIs985) {
        this.universityIs985 = universityIs985;
    }

    public String getUniversityIs211() {
        return universityIs211;
    }

    public void setUniversityIs211(String universityIs211) {
        this.universityIs211 = universityIs211;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

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

}
