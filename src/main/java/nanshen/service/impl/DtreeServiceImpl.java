package nanshen.service.impl;

import nanshen.dao.Dtree.DtreeOptionDao;
import nanshen.dao.Dtree.DtreeQuestionDao;
import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.User.UserInfoDao;
import nanshen.data.Dtree.DtreeOption;
import nanshen.data.Dtree.DtreeQuestion;
import nanshen.data.Dtree.DtreeQuestionType;
import nanshen.data.Dtree.DtreeTrack;
import nanshen.data.Sku.Sku;
import nanshen.data.Sku.SkuAttri.*;
import nanshen.service.DtreeService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Service
public class DtreeServiceImpl implements DtreeService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private DtreeQuestionDao dtreeQuestionDao;

    @Autowired
    private DtreeOptionDao dtreeOptionDao;

    @Autowired
    private SkuService skuService;

    @Override
    public DtreeQuestion getNextDtreeQuestion(long topicId, long qid) {
        DtreeQuestion dtreeQuestion = dtreeQuestionDao.get(topicId, qid);
        if (dtreeQuestion.getType() == DtreeQuestionType.QF) {

        } else {
            List<DtreeOption> dtreeOptionList = dtreeOptionDao.get(topicId, qid);
            dtreeQuestion.setOptionList(dtreeOptionList);
        }
        return dtreeQuestion;
    }

    @Override
    public List<Sku> getResult(List<DtreeTrack> dtreeTrackList) {
        List<SkuCategoryOneType> categoryOneTypeList = new ArrayList<SkuCategoryOneType>();
        List<SkuCategoryTwoType> categoryTwoTypeList = new ArrayList<SkuCategoryTwoType>();
        List<SkuColorType> colorTypeList = new ArrayList<SkuColorType>();
        List<SkuMaterialType> materialTypeList = new ArrayList<SkuMaterialType>();
        List<SkuSpecialType> specialTypeList = new ArrayList<SkuSpecialType>();
        List<SkuStyleType> styleTypeList = new ArrayList<SkuStyleType>();
        List<SkuUserType> userTypeList = new ArrayList<SkuUserType>();
        Long lowerPriceRange = Long.MAX_VALUE;
        Long higherPriceRange = 0L;
        for (DtreeTrack track : dtreeTrackList) {
            Long optionId = track.getOption();
            DtreeOption dtreeOption = dtreeOptionDao.get(optionId);
            if (dtreeOption != null) {
                if (dtreeOption.getCategoryOneType() != null) {
                    categoryOneTypeList.add(dtreeOption.getCategoryOneType());
                }
                if (dtreeOption.getCategoryTwoType() != null) {
                    categoryTwoTypeList.add(dtreeOption.getCategoryTwoType());
                }
                if (dtreeOption.getColorType() != null) {
                    colorTypeList.add(dtreeOption.getColorType());
                }
                if (dtreeOption.getMaterialType() != null) {
                    materialTypeList.add(dtreeOption.getMaterialType());
                }
                if (dtreeOption.getSpecialType() != null) {
                    specialTypeList.add(dtreeOption.getSpecialType());
                }
                if (dtreeOption.getStyleType() != null) {
                    styleTypeList.add(dtreeOption.getStyleType());
                }
                if (dtreeOption.getUserType() != null) {
                    userTypeList.add(dtreeOption.getUserType());
                }
                if (dtreeOption.getLowerPriceRange() != null && dtreeOption.getLowerPriceRange() < lowerPriceRange) {
                    lowerPriceRange = dtreeOption.getLowerPriceRange();
                }
                if (dtreeOption.getHigherPriceRange() != null && dtreeOption.getHigherPriceRange() > higherPriceRange) {
                    higherPriceRange = dtreeOption.getHigherPriceRange();
                }
            }
        }
        if (lowerPriceRange == Long.MAX_VALUE) {
            lowerPriceRange = null;
        }
        if (higherPriceRange == 0L) {
            higherPriceRange = null;
        }
        return skuService.getBySkuAttributes(categoryOneTypeList, categoryTwoTypeList, colorTypeList,
                materialTypeList, specialTypeList, styleTypeList, userTypeList, lowerPriceRange, higherPriceRange);
    }


}
