package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.*;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Sku.Sku;
import nanshen.data.Sku.SkuAttri.*;
import nanshen.data.Sku.SkuItem;
import nanshen.data.Sku.SkuSource;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserSkuLikeMap;
import nanshen.service.QuestionService;
import nanshen.service.SkuService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.service.common.ScheduledService;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Sku related service
 *
 * @Author WANG Minghao
 */
@Service
public class SkuServiceImpl extends ScheduledService implements SkuService {

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private SkuSourceDao skuSourceDao;

    @Autowired
    private SalesInfoDao salesInfoDao;

    @Autowired
    private SkuItemDescriptionDao skuItemDescriptionDao;

    @Autowired
    private UserSkuLikeMapDao userSkuLikeMapDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    @Autowired
    private QuestionService questionService;

    /** skuId到sku信息的缓存 */
    private final LoadingCache<Long, Sku> skuCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Sku>() {
                        @Override
                        public Sku load(Long sid) throws Exception {
                            return skuDao.get(sid);
                        }
                    });

    /** skuShowId到sku信息的缓存 */
    private final LoadingCache<Long, Sku> skuShowCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Sku>() {
                        @Override
                        public Sku load(Long skuShowId) throws Exception {
                            return skuDao.getByShowId(skuShowId);
                        }
                    });

    List<Sku> cachedSkuList = new ArrayList<Sku>();

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        cachedSkuList = skuDao.getLatestSkuList(50);

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[SkuService] Update in " + totalTime + "ms");
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public List<Sku> getByAnswerId(long aid) {
        List<SkuSource> skuSourceList = skuSourceDao.getByAnswerId(aid);
        return getSkuListBySkuSourceList(skuSourceList);
    }

    @Override
    public List<Sku> getByQuestionId(long qid) {
        List<SkuSource> skuSourceList = skuSourceDao.getByQuestionId(qid);
        return getSkuListBySkuSourceList(skuSourceList);
    }

    @Override
    public Map<Long, List<Sku>> getMapByQuestionId(long qid) {
        List<SkuSource> skuSourceList = skuSourceDao.getByQuestionId(qid);
        Map<Long, List<SkuSource>> answerSkuSourceListMap = new HashMap<Long, List<SkuSource>>();
        Map<Long, List<Sku>> answerSkuListMap = new HashMap<Long, List<Sku>>();
        for (SkuSource skuSource : skuSourceList) {
            List<SkuSource> tempSkuSourceList = answerSkuSourceListMap.get(skuSource.getAid());
            if (tempSkuSourceList == null || tempSkuSourceList.size() <= 0) {
                answerSkuSourceListMap.put(skuSource.getAid(), Collections.singletonList(skuSource));
            } else {
                List<SkuSource> newTempSkuSourceList = new ArrayList<SkuSource>();
                newTempSkuSourceList.addAll(tempSkuSourceList);
                newTempSkuSourceList.add(skuSource);
                answerSkuSourceListMap.put(skuSource.getAid(), newTempSkuSourceList);
            }
        }
        for (long aid : answerSkuSourceListMap.keySet()) {
            List<SkuSource> tempSkuSourceList = answerSkuSourceListMap.get(aid);
            answerSkuListMap.put(aid, getSkuListBySkuSourceList(tempSkuSourceList));
        }
        return answerSkuListMap;
    }

    private List<Sku> getSkuListBySkuSourceList(List<SkuSource> skuSourceList) {
        List<Sku> skuList = new ArrayList<Sku>();
        for (SkuSource skuSource : skuSourceList) {
            Sku sku = skuDao.get(skuSource.getSid());
            if (sku != null) {
                skuList.add(sku);
            }
        }
        return skuList;
    }

    @Override
    public boolean remove(long itemId) {
//        updateSkuInfoList();
//        return skuItemDao.remove(itemId);
        return false;
    }

    @Override
    public ExecResult<SkuItem> uploadImage(long itemId, long operatorId, MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        SkuItem skuItem = null;
        ExecInfo execInfo = uploadImageToOss(file, is, skuItem);

        if (execInfo.isSucc()) {
            skuItem.setImgCount(skuItem.getImgCount() + 1);
            return ExecResult.succ(skuItem);
        }
        return ExecResult.fail(execInfo.getMsg());
    }

    @Override
    public Sku getByShowSid(UserInfo userInfo, long sid) {
        Sku sku = skuDao.getByShowId(sid);
        if (userInfo != null) {
            UserSkuLikeMap userSkuLikeMap = userSkuLikeMapDao.get(userInfo.getId(), sid);
            if (userSkuLikeMap != null) {
                sku.setLiked(true);
            }
        }
        long count = userSkuLikeMapDao.countSkuLike(sid);
        sku.setLikeCnt(count);
        return sku;
    }

    @Override
    public Sku getBySid(long sid) {
        return skuDao.get(sid);
    }

    @Override
    public List<ComplexAnswer> getAnswersBySid(long sid) {
        List<SkuSource> skuSourceList = skuSourceDao.getBySkuId(sid);
        List<ComplexAnswer> complexAnswerList = new ArrayList<ComplexAnswer>();
        for (SkuSource skuSource : skuSourceList) {
            ComplexAnswer complexAnswer = questionService.getComplexAnswerByAidAndQid(skuSource.getAid(), skuSource.getQid());
            complexAnswerList.add(complexAnswer);
        }
        return complexAnswerList;
    }

    @Override
    public List<Sku> getBySkuAttributes(List<SkuCategoryOneType> categoryOneTypeList, List<SkuCategoryTwoType> categoryTwoTypeList,
                                        List<SkuColorType> colorTypeList, List<SkuMaterialType> materialTypeList,
                                        List<SkuSpecialType> specialTypeList, List<SkuStyleType> styleTypeList,
                                        List<SkuUserType> userTypeList, Long lowerPriceRange, Long higherPriceRange) {
        List<Sku> filteredSkuList = new ArrayList<Sku>();
        for (Sku sku : cachedSkuList) {
            LogUtils.info("lowerPriceRange: " + lowerPriceRange);
            LogUtils.info("higherPriceRange: " + higherPriceRange);
            LogUtils.info("sku price: " + sku.getPrice());
            LogUtils.info("sku price: " + sku.getCategoryOneType());
            LogUtils.info("sku price: " + sku.getCategoryTwoType());
            LogUtils.info("sku price: " + sku.getColorType());
            LogUtils.info("sku price: " + sku.getMaterialType());
            LogUtils.info("sku price: " + sku.getSpecialType());
            LogUtils.info("sku price: " + sku.getStyleType());
            LogUtils.info("sku price: " + sku.getUserType());

            if (sku.getCategoryOneType() != null && sku.getCategoryOneType() != SkuCategoryOneType.ALL
                    && categoryOneTypeList != null && !categoryOneTypeList.contains(sku.getCategoryOneType())) {
                continue;
            }
            if (sku.getCategoryTwoType() != null && sku.getCategoryTwoType() != SkuCategoryTwoType.ALL
                    && categoryTwoTypeList != null && !categoryTwoTypeList.contains(sku.getCategoryTwoType())) {
                continue;
            }
            if (sku.getColorType() != null && sku.getColorType() != SkuColorType.ALL
                    && colorTypeList != null && !colorTypeList.contains(sku.getColorType())) {
                continue;
            }
            if (sku.getMaterialType() != null && sku.getMaterialType() != SkuMaterialType.ALL
                    && materialTypeList != null && !materialTypeList.contains(sku.getMaterialType())) {
                continue;
            }
            if (sku.getSpecialType() != null && sku.getSpecialType() != SkuSpecialType.ALL
                    && specialTypeList != null && !specialTypeList.contains(sku.getSpecialType())) {
                continue;
            }
            if (sku.getStyleType() != null && sku.getStyleType() != SkuStyleType.ALL
                    && styleTypeList != null && !styleTypeList.contains(sku.getStyleType())) {
                continue;
            }
            if (sku.getUserType() != null && sku.getUserType() != SkuUserType.ALL
                    && userTypeList != null && !userTypeList.contains(sku.getUserType())) {
                continue;
            }
            if (sku.getPrice() != 0 && lowerPriceRange != null && lowerPriceRange > sku.getPrice()) {
                continue;
            }
            if (sku.getPrice() != 0 && higherPriceRange != null && higherPriceRange < sku.getPrice()) {
                continue;
            }
            filteredSkuList.add(sku);
        }
        return filteredSkuList;
    }

    @Override
    public ExecInfo likeBySid(long sid, UserInfo userInfo) {
        if (userInfo == null) {
            return ExecInfo.fail("还未登陆或已失效，请重新登陆");
        }
        UserSkuLikeMap likeMap = userSkuLikeMapDao.insert(new UserSkuLikeMap(sid, userInfo.getId()));
        if (likeMap == null) {
            return ExecInfo.fail("喜欢失败，请稍后再喜欢一遍");
        }
        return ExecInfo.succ();
    }

    @Override
    public List<Sku> getLikeList(UserInfo userInfo, PageInfo pageInfo) {
        if (userInfo == null) {
            return new ArrayList<Sku>();
        }
        List<UserSkuLikeMap> likeMapList = userSkuLikeMapDao.getByUid(userInfo.getId(), pageInfo);
        List<Sku> skuList = new ArrayList<Sku>();
        for (UserSkuLikeMap likeMap : likeMapList) {
            try {
                skuList.add(skuCache.get(likeMap.getSid()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return skuList;
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, SkuItem skuItem) {
        String imgKey = getImgKey(file, skuItem);
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            System.out.println("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getImgKey(MultipartFile file, SkuItem skuItem) {
        String imgKey = "images/sku/" + skuItem.getId() + "/" + skuItem.getImgCount();
        System.out.println("imgKey : " + imgKey);
        return imgKey;
    }
}
