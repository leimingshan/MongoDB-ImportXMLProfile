package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;

public class ProfileLevelJudge {
    private Profile profile;
    private FamilyNamesReader nameJudge = FamilyNamesReader.getInstance();

    public ProfileLevelJudge() {

    }

    public ProfileLevelJudge(Profile pf) {
        this.profile = pf;
    }

    public int getLevel() {
        // 根据profile的基本信息情况确定分级
        int result = 1;
        if (!nameJudge.checkName(profile.getName()))
            return 3;
        if (!checkBasicInfo(profile.getGender()))
            return 3;
        if (!checkBasicInfo(profile.getNation()) || !checkBasicInfo(profile.getAge()))
            return 3;
        if (!checkBasicInfo(profile.getBirthProvince()))
            return 3;
        if (!checkBasicInfo(profile.getWorkDate()) || !checkBasicInfo(profile.getBirthDate()))
            return 2;
        if (profile.getOfficeRecord() == null || profile.getOfficeRecord().size() < 5)
            return 2;

        return result;
    }

    public int getLevel(Profile profile) {
        this.profile = profile;
        return getLevel();
    }

    public boolean checkBasicInfo(String info) {
        if (info == null || info.equals("") || info.equals("-") || info.equals("--") || info
            .equals("---")) {
            return false;
        } else {
            return true;
        }
    }
}
