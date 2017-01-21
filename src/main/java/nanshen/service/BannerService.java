package nanshen.service;

import nanshen.data.Banner.Banner;

import java.util.List;

/**
 * banner related service
 *
 * @author WANG Minghao
 */
public interface BannerService {

    List<Banner> getOnlineBannerList();
}
