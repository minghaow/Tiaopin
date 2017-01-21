package nanshen.service.impl;

import nanshen.constant.TimeConstants;
import nanshen.dao.Banner.BannerDao;
import nanshen.data.Banner.Banner;
import nanshen.service.BannerService;
import nanshen.service.common.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Banner Service Implementation
 * NOTE: Mainly provide id and account information map according to all kinds of list
 *
 * @Author WANG Minghao
 */
@Service
public class BannerServiceImpl extends ScheduledService  implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    /** 买手ID到买手信息的缓存 */
    private List<Banner> bannerList = new ArrayList<Banner>();

    public void update() {
        List<Banner> tempBannerList = bannerDao.getOnline();
        if (tempBannerList != null && tempBannerList.size() > 0) {
            bannerList = tempBannerList;
        }
    }

    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }


    @Override
    public List<Banner> getOnlineBannerList() {
        return bannerList;
    }
}
