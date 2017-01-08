package nanshen.service;

import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Sku.Sku;
import nanshen.data.Sku.SkuAttri.*;
import nanshen.data.Sku.SkuItem;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Sku related services
 *
 * @author WANG Minghao
 */
public interface SkuService {

    /**
     * get sku by answer id
     *
     * @param aid answer id
     * @return list of sku
     */
    List<Sku> getByAnswerId(long aid);

    /**
     * get sku by question id
     *
     * @param qid question id
     * @return list of sku
     */
    List<Sku> getByQuestionId(long qid);

    /**
     * get (answer-answer sku list) map by question id
     *
     * @param qid question id
     * @return list of sku
     */
    Map<Long, List<Sku>> getMapByQuestionId(long qid);

    /**
     * Remove sku according to skuId
     *
     * @param itemId itemId
     * @return
     */
    boolean remove(long itemId);

    /**
     * Upload the sku images
     *
     * @param itemId the sku of the image
     * @param operatorId uploader
     * @param file image file
     * @return ExecResult<SkuInfo>
     */
    ExecResult<SkuItem> uploadImage(long itemId, long operatorId, MultipartFile file) throws IOException;

    /**
     * Get sku by sku id
     *
     * @param sid
     * @return
     */
    Sku getByShowSid(UserInfo userInfo, long sid);
    /**
     * Get sku by sku id
     *
     * @param sid
     * @return
     */
    Sku getBySid(long sid);

    /**
     * get complex answer by sku id
     *
     * @param sid
     * @return
     */
    List<ComplexAnswer> getAnswersBySid(long sid);

    List<Sku> getBySkuAttributes(List<SkuCategoryOneType> categoryOneTypeList, List<SkuCategoryTwoType> categoryTwoTypeList,
                                 List<SkuColorType> colorTypeList, List<SkuMaterialType> materialTypeList,
                                 List<SkuSpecialType> specialTypeList, List<SkuStyleType> styleTypeList,
                                 List<SkuUserType> userTypeList, Long lowerPriceRange, Long higherPriceRange);

    ExecInfo likeBySid(long sid, UserInfo userInfo);

    List<Sku> getLikeList(UserInfo userInfo, PageInfo pageInfo);
}
