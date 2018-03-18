package com.mphj.freelancer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {
    public static final String MOBILE_NUMBER = "(\\+98|0|98)?([0-9]{10})";

    private int matchIndex = 0;
    private String regExp;

    private PhoneValidator(String regExp, int matchIndex) {
        this.matchIndex = matchIndex;
        this.regExp = regExp;
    }

    public String getValid(String object) {
        Pattern p = Pattern.compile(this.regExp);
        Matcher m = p.matcher(object);
        while (m.find()) {
            if (m.group(this.matchIndex) != null && !m.group(this.matchIndex).isEmpty())
                return m.group(this.matchIndex);
        }
        return null;
    }

    public static PhoneValidator newMobileNumberValidator() {
        return new PhoneValidator(MOBILE_NUMBER, 2);
    }

    public boolean isValid(String object) {
        try {
            Pattern p = Pattern.compile(this.regExp);
            Matcher m = p.matcher(object);
            while (m.find()) {
                if (m.group(this.matchIndex) != null && !m.group(this.matchIndex).isEmpty())
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
