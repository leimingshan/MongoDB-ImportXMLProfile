package com.iscas.pminer.service;

import com.iscas.pminer.entity.OfficeRecord;
import com.iscas.pminer.entity.Profile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PersonFolderParser {
    private File personFolder;
    private Profile profile;

    // 必须使用传参构造函数
    public PersonFolderParser(File folder) {
        // folder 必须是个人信息的文件夹，里面还有xml和txt文件，正常情况下还要有jpg文件
        this.personFolder = folder;
    }

    public Profile getProfile() {
        if (parse())
            return profile;
        else
            return null;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private boolean parse() {
        if (!personFolder.isDirectory())
            return false;

        File[] personFiles = personFolder.listFiles();

        File xmlFile = null;
        File picFile = null;
        @SuppressWarnings("unused") File txtFile = null; // not used by now

        for (int k = 0; k < personFiles.length; k++) {
            File file = personFiles[k];
            // 处理所有后缀为.xml的数据文件
            if (file.getName().endsWith(".xml")) {
                xmlFile = file;
            } else if (file.getName().endsWith(".jpg") || file.getName().endsWith(".bmp") || file
                .getName().endsWith(".png") || file.getName().endsWith(".gif")) {
                picFile = file;
            } else if (file.getName().endsWith(".txt")) {
                txtFile = file;
            }
        }
        // 若该文件夹内没有最主要的xml数据文件，则舍弃该文件夹
        if (xmlFile == null) {
            System.err.println("Error: " + personFolder.getName() + " is not a person folder!");
            return false;
        }

        // 先解析xml文件，获取该人物的基本履历数据
        profile = new XmlParser(xmlFile).getProfile();
        if (profile == null)
            return false;

        // 重要：设置唯一Id
        profile.setId(personFolder.getName());
        // 增加文件路径，仅用于日志和显示
        profile.setXmlPath(xmlFile.getAbsolutePath());

        // 根据个人文件夹的扫描情况确定图片路径
        if (picFile != null) {
            try {
                FileInputStream fs = new FileInputStream(picFile);
                profile.setImage(IOUtils.toByteArray(fs)); // 图片文件转换为字节流存储在数据库中
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 该人没有图片数据，则使用empty.png图片文件
            try {
                profile.setImage(IOUtils.toByteArray(
                    this.getClass().getClassLoader().getResourceAsStream("empty.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ProfileLevelJudge judgeLevel = new ProfileLevelJudge();
        // 判断该人履历信息的档案级别
        int level = judgeLevel.getLevel(profile);
        profile.setLevel(level);

        // 处理该人的工作记录，找出最新的工作记录
        List<OfficeRecord> officeRecordList = profile.getOfficeRecord();
        if (officeRecordList != null && officeRecordList.size() != 0) {
            OfficeRecord item = officeRecordList.get(officeRecordList.size() - 1);
            String endDate = item.getEndDate();
            if (endDate.length() < 6)
                System.out.println("------无详细结束日期");
            else {
                String endYear = endDate.substring(0, 4);

                // 取当前年份
                Calendar now = Calendar.getInstance();
                int currentYear = now.get(Calendar.YEAR);
                if (Integer.parseInt(endYear) == currentYear) {
                    profile.setLatestOfficeRecord(item);
                }
            }
        }
        // 设置入库时间戳
        profile.setTimestamp(new Date());

        return true;
    }
}
