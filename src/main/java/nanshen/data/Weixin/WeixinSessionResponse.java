package nanshen.data.Weixin;

/**
 * WeixinSessionResponse
 *
 * @Author WANG Minghao
 */
public class WeixinSessionResponse {

    /** qid */
    private String openid = "";

    /** level */
    private String session_key = "";

    public WeixinSessionResponse() {
    }

    public WeixinSessionResponse(String openid, String session_key) {
        this.openid = openid;
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}
