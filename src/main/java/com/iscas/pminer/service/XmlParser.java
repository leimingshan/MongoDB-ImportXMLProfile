package com.iscas.pminer.service;

import com.iscas.pminer.entity.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Profile XML format file parser and analyzer.
 * @author Mingshan Lei
 * @since 0.1
 */
public class XmlParser {

    private File xmlFile = null;

    private Profile profile = null;

    // 使用传参的构造函数，参数为需要解析的xml文件
    public XmlParser(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public Profile getProfile() {
        if (parse()) {
            return profile;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean parse() {
        if (xmlFile == null)
            return false;

        SAXReader sr = new SAXReader();
        Document document = null;
        try {
            document = sr.read(xmlFile);
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Element root = document.getRootElement();

        Element e = root.element("cadre");
        Element info = e.element("basic_info");

        profile = new Profile();

        //人物基本信息：姓名，性别，民族
        profile.setName(info.elementText("name"));
        profile.setGender(info.elementText("gender"));
        profile.setNation(info.elementText("nation"));
        //出生地信息
        Element birthPlace = info.element("birth_place");
        profile.setBirthProvince(birthPlace.elementText("province"));
        profile.setBirthCity(birthPlace.elementText("city"));
        profile.setBirthDistrict(birthPlace.elementText("district"));
        //年龄
        profile.setAge(info.elementText("age"));

        //三类日期信息：出生日期，入党日期，工作日期
        List<Element> date = info.elements("date");
        for (Element element : date) {
            String category = element.attributeValue("category");

            if (category.equals("birth"))
                profile
                    .setBirthDate(element.elementText("year") + "-" + element.elementText("month"));
            else if (category.equals("party"))
                profile
                    .setPartyDate(element.elementText("year") + "-" + element.elementText("month"));
            else if (category.equals("work"))
                profile
                    .setWorkDate(element.elementText("year") + "-" + element.elementText("month"));
        }

        //学习记录
        List<StudyRecord> studyRecordList = new ArrayList<StudyRecord>();
        List<Element> studyRecordArray = e.element("study_record_array").elements("study_record");

        for (Element element : studyRecordArray) {
            String category = element.attributeValue("category");

            Element location = element.element("location");
            StudyRecord record = new StudyRecord();
            record.setProvince(location.elementText("province"));
            record.setCity(location.elementText("city"));

            Element university = element.element("university");
            record.setUniversityName(university.elementText("name"));
            Element level = university.element("level");
            record.setUniversityIs985(level.elementText("is985"));
            record.setUniversityIs211(level.elementText("is211"));

            record.setSchool(element.elementText("school"));
            record.setDepartment(element.elementText("department"));
            record.setMajor(element.elementText("major"));
            record.setDiploma(element.elementText("diploma"));
            record.setDegree(element.elementText("degree"));

            if (category.equals("current")) {
                //当前的学习记录，没有开始和结束时间
                //存储在list的开头
                studyRecordList.add(record);

            } else if (category.equals("previous")) {
                //之前的学习记录：包含开始和结束时间
                //可能有多个之前的学习记录，按照时间先后排序
                List<Element> dates = element.elements("date");
                for (Element date1 : dates) {
                    String category1 = date1.attributeValue("category");

                    if (category1.equals("start")) {
                        record.setStartDate(
                            date1.elementText("year") + "-" + date1.elementText("month"));
                    } else if (category1.equals("end")) {
                        record.setEndDate(
                            date1.elementText("year") + "-" + date1.elementText("month"));
                    }
                }
                studyRecordList.add(record);
            }
        }

        profile.setStudyRecord(studyRecordList);

        //工作记录
        List<OfficeRecord> officeRecordList = new ArrayList<OfficeRecord>();
        Element officeRecord = e.element("office_record_array");
        List<Element> officeRecordArray = officeRecord.elements("office_record");

        a:
        for (Element office : officeRecordArray) {
            OfficeRecord record = new OfficeRecord();
            List<Element> dates = office.elements("date");
            for (Element date1 : dates) {
                String category1 = date1.attributeValue("category");

                if (!isYearLegal(date1.elementText("year")))
                    continue a;

                if (category1.equals("start")) {
                    record
                        .setStartDate(date1.elementText("year") + "-" + date1.elementText("month"));
                } else if (category1.equals("end")) {
                    record.setEndDate(date1.elementText("year") + "-" + date1.elementText("month"));
                }
            }

            Element workPlace = office.element("location");
            record.setProvince(workPlace.elementText("province"));
            record.setCity(workPlace.elementText("city"));
            record.setDistrict(workPlace.elementText("district"));

            Element tuple_array = office.element("tuple_array");
            List<Element> tuples = tuple_array.elements("tuple");
            List<Tuple> tupleList = new ArrayList<Tuple>();
            for (Element tuple : tuples) {
                Tuple t = new Tuple();
                t.setContent(tuple.elementText("content"));
                t.setRank(tuple.elementText("rank"));

                // unit部分原来只关注level 1部分，现在加入全部unit树
                List<Element> units = tuple.element("unit_tree").elements("unit");
                List<String> unitNameList = new ArrayList<String>();
                List<String> unitRankList = new ArrayList<String>();
                for (Element unit : units) {
                    //String unitLevel = unit.attributeValue("level");
                    unitNameList.add(unit.elementText("name"));
                    unitRankList.add(unit.elementText("rank"));
                }
                t.setUnitNameList(unitNameList);
                t.setUnitRankList(unitRankList);

                Element postArray = tuple.element("post_array");
                List<Element> posts = postArray.elements("post_entity");
                List<Post> postList = new ArrayList<Post>();
                for (Element post : posts) {
                    Post p = new Post();
                    p.setName(post.elementText("post_name"));
                    p.setRank(post.elementText("rank"));
                    postList.add(p);
                }
                t.setPostArray(postList);

                tupleList.add(t);
            }
            record.setTupleList(tupleList);
            record.setRank(office.elementText("rank"));

            officeRecordList.add(record);
        }

        profile.setOfficeRecord(officeRecordList);

        Element rawText = e.element("raw_text");
        profile.setRawText(rawText.elementText("text"));
        profile.setLevel(Integer.parseInt(rawText.elementText("text_mark"))); //增加档级 xml中获取level

        //Profile解析完成
        return true;
    }

    public final static boolean isYearLegal(String year) {
        if (year.equals("-")) // "-" means this year or no data here and will be processed later
            return true;
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int yearInt = Integer.parseInt(year);
        if (yearInt > currentYear || yearInt < 1900) {
            return false;
        } else {
            return true;
        }
    }
}
