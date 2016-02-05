package com.iscas.pminer.service;

import com.iscas.pminer.entity.Profile;

/**
 * Specifies the level of one person profile.
 * Profile level could be 1, 2 or 3.
 * @author Mingshan Lei
 * @since 0.1
 */
public class ProfileLevelHelper {
    private Profile profile;
    private FamilyNamesReader nameJudge = FamilyNamesReader.getInstance();

    private static final int LEVEL_THREE = 3;
    private static final int LEVEL_TWO = 2;
    private static final int LEVEL_ONE = 1;

    public ProfileLevelHelper() {

    }

    public ProfileLevelHelper(Profile profile) {
        this.profile = profile;
    }

    /**
     * Get level for profile.
     * @return the level integer value
     */
    public final int getLevel() {
        // get level value based on profile basic info.
        int defaultResult = LEVEL_ONE;
        if (!nameJudge.checkName(profile.getName())) {
            return LEVEL_THREE;
        }
        if (!checkBasicInfo(profile.getGender())) {
            return LEVEL_THREE;
        }
        if (!checkBasicInfo(profile.getNation()) || !checkBasicInfo(profile.getAge())) {
            return LEVEL_THREE;
        }
        if (!checkBasicInfo(profile.getBirthProvince())) {
            return LEVEL_THREE;
        }
        if (!checkBasicInfo(profile.getWorkDate()) || !checkBasicInfo(profile.getBirthDate())) {
            return LEVEL_TWO;
        }
        if (profile.getOfficeRecord() == null || profile.getOfficeRecord().size() < 5) {
            return LEVEL_TWO;
        }

        return defaultResult;
    }

    /**
     * Get level for profile in the parameter.
     * @param profile
     * @return the level integer value
     */
    public final int getLevel(Profile profile) {
        this.profile = profile;
        return getLevel();
    }

    /**
     * Specifies the info of string satisfies the rules.
     * @param info
     * @return whether basic info satisfies the rules.
     */
    private final boolean checkBasicInfo(String info) {
        if (info == null || info.equals("") || info.equals("-")
            || info.equals("--") || info.equals("---")) {
            return false;
        }
        return true;
    }
}
