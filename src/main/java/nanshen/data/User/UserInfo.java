package nanshen.data.User;

import nanshen.utils.CollectionExtractor;
import org.nutz.dao.entity.annotation.*;

import java.util.*;

/**
 * User info data
 *
 * @author WANG Minghao
 */
@Table("UserInfo")
@TableIndexes(@Index(unique = true, name = "username", fields = {"username"}))
public class UserInfo {

    /** ID */
    @Id
    private long id;

    /** phone, need to be verified */
    @Column
    private String phone;

    /** user nick name */
    @Column
    private String username;

    /** email */
    @Column
    private String email;

    /** user userDesc */
    @Column
    private String userDesc = "";

    /** user openid */
    @Column
    private String openid = "";

    /** user imgUrl */
    @Column
    private String imgUrl = "";

    /** user country */
    @Column
    private String country = "";

    /** user province */
    @Column
    private String province = "";

    /** user city */
    @Column
    private String city = "";

    /** user gender */
    @Column
    private String gender = "";

    /** hashed password */
    @Column
    @ColDefine(width = 150)
    private String password;

    /** if unavailable, user cannot login */
    @Column
    private boolean available;

    /** set for future use */
    @Column
    private int priority = 0;

    /** if not activated, user cannot make payment */
    @Column
    private boolean activated = false;

    /** user type */
    @Column
    private UserType userType = UserType.AMATEUR;

    /** last login ip */
    @Column
    private String loginIp = "";

    /** last login time */
    @Column
    private Date loginTime = new Date();

    /** user authority list {@link #authorities} */
    @Many(target = UserAuthority.class, field = "userId")
    private List<UserAuthority> authoritiesInDb;

    /** 买手的权限列表 */
    private Set<AccessAuthority> authorities;

    /** 权限提取器 */
    private static final CollectionExtractor<AccessAuthority, UserAuthority> authorityExtractor =
            new CollectionExtractor<AccessAuthority, UserAuthority>() {
                @Override
                protected AccessAuthority convert(UserAuthority source) {
                    return source.getAuthority();
                }
            };

    public UserInfo(boolean activated, boolean available, String email, long id, String loginIp, Date loginTime,
                    String password, String phone, int priority, String username, UserType userType) {
        this.activated = activated;
        this.available = available;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.priority = priority;
        this.username = username;
        this.userType = userType;
        this.authorities = new HashSet<AccessAuthority>();
        this.authoritiesInDb = new ArrayList<UserAuthority>();
    }

    public UserInfo(String openid, String phone, String password, String imgUrl, String country, String province, String city, String gender, String username) {
        this.phone = phone;
        this.password = password;
        this.imgUrl = imgUrl;
        this.country = country;
        this.province = province;
        this.city = city;
        this.username = username;
        this.userType = UserType.AMATEUR;
        this.available = true;
        this.activated = true;
        this.email = phone;
        this.gender = gender;
        this.openid = openid;
        this.authorities = new HashSet<AccessAuthority>();
        this.authoritiesInDb = new ArrayList<UserAuthority>();
    }

    public UserInfo(String phone, String password) {
        this.activated = true;
        this.available = true;
        this.email = phone;
        this.password = password;
        this.phone = phone;
        this.username = phone;
        this.userType = UserType.AMATEUR;
        this.authorities = new HashSet<AccessAuthority>();
        this.authoritiesInDb = new ArrayList<UserAuthority>();
    }

    /**
     * nutz使用的默认构造方法
     * <p />
     * <b>注意</b>：nutz使用，其他地方不应该使用
     */
    public UserInfo() {}

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * get all user authority details
     *
     * @return
     */
    public Set<AccessAuthority> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    /**
     * get all user authority details
     *
     * @return 买手权限数据库结构列表
     */
    public List<UserAuthority> getAuthoritiesInDb() {
        return Collections.unmodifiableList(authoritiesInDb);
    }

    /**
     * nutz用于设置权限列表的方法
     * <p />
     * <b>注意</b>：nutz使用，其他地方不应该使用
     *
     * @param authoritiesInDb 买手权限数据库结构
     */
    public void setAuthoritiesInDb(List<UserAuthority> authoritiesInDb) {
        this.authoritiesInDb = authoritiesInDb;
        authorities = new HashSet<AccessAuthority>(authorityExtractor.extractList(authoritiesInDb));
    }

    public boolean hasAdminAuthority() {
        return authorities.contains(AccessAuthority.ADMIN);
    }

}
