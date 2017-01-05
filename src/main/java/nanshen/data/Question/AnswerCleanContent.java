package nanshen.data.Question;

/**
 * AnswerCleanContent
 *
 * @Author WANG Minghao
 */
public class AnswerCleanContent {

    private String text = "";

    private String imgUrl = "";

    public AnswerCleanContent(String imgUrl, String text) {
        this.imgUrl = imgUrl;
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
