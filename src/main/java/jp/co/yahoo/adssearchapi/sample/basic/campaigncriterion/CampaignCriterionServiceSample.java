/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaigncriterion;

import java.util.ArrayList;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.basic.campaign.CampaignServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v16.api.CampaignCriterionServiceApi;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterion;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceCriterion;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceCriterionType;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceKeyword;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceKeywordMatchType;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceUse;
import jp.co.yahoo.adssearchapi.v16.model.CampaignCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v16.model.CampaignServiceType;

/**
 * example CampaignService operation and Utility method collection.
 */
public class CampaignCriterionServiceSample {

  private static final CampaignCriterionServiceApi campaignCriterionService = new CampaignCriterionServiceApi(ApiUtils.getYahooJapanAdsApiClient());

  /**
   * main method for CampaignCriterionServiceSample
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {

    // =================================================================
    // Setting
    // =================================================================
    ValuesHolder valuesHolder = new ValuesHolder();
    long accountId = ApiUtils.ACCOUNT_ID;

    try {
      // =================================================================
      // check & create upper service object.
      // =================================================================
      valuesHolder = setup();
      ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);
      Long campaignId = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);

      // =================================================================
      // CampaignCriterionService ADD
      // =================================================================
      // create request.
      CampaignCriterionServiceOperation addCampaignCriterionOperation = buildExampleMutateRequest(accountId, new ArrayList<CampaignCriterion>() {{
        add(createExampleNegativeCampaignCriterion(campaignId));
      }});

      // run
      List<CampaignCriterionServiceValue> addCampaignCriterionValues = campaignCriterionService.campaignCriterionServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addCampaignCriterionOperation).getRval().getValues();

      List<CampaignCriterion> campaignCriterions = new ArrayList<>();
      for (CampaignCriterionServiceValue campaignCriterionValues: addCampaignCriterionValues) {
        campaignCriterions.add(campaignCriterionValues.getCampaignCriterion());
      }

      // =================================================================
      // CampaignCriterionService GET
      // =================================================================
      // create request.
      CampaignCriterionServiceSelector campaignCriterionSelector = buildExampleGetRequest(accountId, campaignCriterions);

      // run
      campaignCriterionService.campaignCriterionServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, campaignCriterionSelector);

      // =================================================================
      // CampaignCriterionService REMOVE
      // =================================================================
      // create request.
      CampaignCriterionServiceOperation removeCampaignCriterionOperation = buildExampleMutateRequest(accountId, campaignCriterions);

      // run
      campaignCriterionService.campaignCriterionServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeCampaignCriterionOperation);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cleanup(valuesHolder);
    }
  }

  /**
   * example mutate request.
   */
  public static CampaignCriterionServiceOperation buildExampleMutateRequest(long accountId, List<CampaignCriterion> operand) {
    CampaignCriterionServiceOperation operation = new CampaignCriterionServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example get request.
   *
   * @param accountId long
   * @param campaignCriterions CampaignCriterion
   * @return CampaignCriterionSelector
   */
  public static CampaignCriterionServiceSelector buildExampleGetRequest(long accountId, List<CampaignCriterion> campaignCriterions) {
    // Set Selector
    CampaignCriterionServiceSelector selector = new CampaignCriterionServiceSelector();
    selector.setAccountId(accountId);

    List<Long> campaignIds = new ArrayList<>();
    List<Long> criterionIds = new ArrayList<>();
    for (CampaignCriterion campaignCriterion : campaignCriterions) {
      campaignIds.add(campaignCriterion.getCampaignId());
      criterionIds.add(campaignCriterion.getCriterion().getCriterionId());
    }
    selector.setCampaignIds(campaignIds);
    selector.setCriterionIds(criterionIds);

    selector.setUse(CampaignCriterionServiceUse.NEGATIVE);
    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

  /**
   * check & create upper service object.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  private static ValuesHolder setup() throws Exception {

    return CampaignServiceSample.create();
  }

  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {
    CampaignServiceSample.cleanup(valuesHolder);
  }

  /**
   * example campaign criterion request.
   *
   * @param campaignId long
   * @return CampaignCriterion
   */
  public static CampaignCriterion createExampleNegativeCampaignCriterion(long campaignId) {

    // Set Keyword
    CampaignCriterionServiceKeyword keyword = new CampaignCriterionServiceKeyword();
    keyword.setText("sample text");
    keyword.setKeywordMatchType(CampaignCriterionServiceKeywordMatchType.PHRASE);

    CampaignCriterionServiceCriterion criterion = new CampaignCriterionServiceCriterion();
    criterion.setCriterionType(CampaignCriterionServiceCriterionType.KEYWORD);
    criterion.setKeyword(keyword);

    // Set Operand
    CampaignCriterion negativeCampaignCriterion = new CampaignCriterion();
    negativeCampaignCriterion.setCampaignId(campaignId);
    negativeCampaignCriterion.setUse(CampaignCriterionServiceUse.NEGATIVE);
    negativeCampaignCriterion.setCriterion(criterion);

    return negativeCampaignCriterion;
  }

}
