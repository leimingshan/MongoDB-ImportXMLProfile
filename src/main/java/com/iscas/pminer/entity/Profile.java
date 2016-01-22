package com.iscas.pminer.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

/**
 * Person profile.
 *
 * @author Mingshan Lei
 * @since 1.0
 */
@Entity(noClassnameStored = true) public class Profile {
    @Id private String id; // UUID is used here

    @Indexed(name = "profilename") private String name;
    private String gender;
    private String nation;

    private String birthProvince;
    private String birthCity;
    private String birthDistrict;

    private String age;

    private String birthDate;
    private String partyDate;
    private String workDate;

    @Embedded private List<StudyRecord> studyRecord;
        // study record, have current or some previous study records
    @Embedded private List<OfficeRecord> officeRecord;// work record
    @Embedded private OfficeRecord latestOfficeRecord;// latest or current work record

    private String rawText;
    private int level; // should be level 1, 2 or 3

    private String xmlPath;

    private byte[] image;

    private Date timestamp; // time stamp for insert time or update time

    public Profile() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getBirthDistrict() {
        return birthDistrict;
    }

    public void setBirthDistrict(String birthDistrict) {
        this.birthDistrict = birthDistrict;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public List<StudyRecord> getStudyRecord() {
        return studyRecord;
    }

    public void setStudyRecord(List<StudyRecord> studyRecord) {
        this.studyRecord = studyRecord;
    }

    public List<OfficeRecord> getOfficeRecord() {
        return officeRecord;
    }

    public void setOfficeRecord(List<OfficeRecord> officeRecord) {
        this.officeRecord = officeRecord;
    }

    public OfficeRecord getLatestOfficeRecord() {
        return latestOfficeRecord;
    }

    public void setLatestOfficeRecord(OfficeRecord latestOfficeRecord) {
        this.latestOfficeRecord = latestOfficeRecord;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
