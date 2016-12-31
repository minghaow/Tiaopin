package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.SalesInfoDao;
import nanshen.dao.SkuDao;
import nanshen.dao.SkuItemDescriptionDao;
import nanshen.dao.SkuSourceDao;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Sku.Sku;
import nanshen.data.Sku.SkuItem;
import nanshen.data.Sku.SkuSource;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.service.AnswerService;
import nanshen.service.QuestionService;
import nanshen.service.SkuService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.service.common.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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
    private OssFormalApi ossFormalApi;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    /** skuId到skuItem信息的缓存 */
    private final LoadingCache<Long, SkuItem> skuItemCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, SkuItem>() {
                        @Override
                        public SkuItem load(Long skuItemId) throws Exception {
                            return null;
                        }
                    });


    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        Date startDate = new Date(startTime - 7 * TimeConstants.DAY_IN_MILLISECONDS);

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
    public Sku getByShowSid(long sid) {
        return skuDao.getByShowId(sid);
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
            ComplexAnswer complexAnswer = answerService.getComplexAnswerByAidAndQid(skuSource.getAid(), skuSource.getQid());
            complexAnswerList.add(complexAnswer);
        }
        return complexAnswerList;
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
