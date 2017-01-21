package nanshen.data.User;

import nanshen.data.Question.ComplexAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * User info data
 *
 * @author WANG Minghao
 */
public class ComplexUserInfo {

    private UserInfo userInfo;

    private List<ComplexAnswer> complexAnswerList = new ArrayList<ComplexAnswer>();

    public ComplexUserInfo(List<ComplexAnswer> complexAnswerList, UserInfo userInfo) {
        this.complexAnswerList = complexAnswerList;
        this.userInfo = userInfo;
    }

    public List<ComplexAnswer> getComplexAnswerList() {
        return complexAnswerList;
    }

    public void setComplexAnswerList(List<ComplexAnswer> complexAnswerList) {
        this.complexAnswerList = complexAnswerList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
