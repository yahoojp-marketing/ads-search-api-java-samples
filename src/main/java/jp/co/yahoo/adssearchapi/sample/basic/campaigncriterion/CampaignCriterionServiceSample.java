/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaigncriterion;

import jp.co.yahoo.adssearchapi.sample.basic.campaign.CampaignServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterion;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceCriterion;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceCriterionType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceGetResponse;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceKeyword;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceKeywordMatchType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceUse;
import jp.co.yahoo.adssearchapi.v7.model.CampaignCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v7.model.CampaignServiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * example CampaignService operation and Utility method collection.
 */
public class CampaignCriterionServiceSample {

  private static final String SERVICE_NAME = "CampaignCriterionService";

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
      List<CampaignCriterionServiceValue> addCampaignCriterionValues = mutate(addCampaignCriterionOperation, "add");

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
      get(campaignCriterionSelector, "get");

      // =================================================================
      // CampaignCriterionService REMOVE
      // =================================================================
      // create request.
      CampaignCriterionServiceOperation removeCampaignCriterionOperation = buildExampleMutateRequest(accountId, campaignCriterions);

      // run
      mutate(removeCampaignCriterionOperation, "remove");

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
   * example mutate campaignCriterions.
   *
   * @param operation CampaignCriterionOperation
   * @return CampaignCriterionValues
   */
  public static List<CampaignCriterionServiceValue> mutate(CampaignCriterionServiceOperation operation, String action) throws Exception {

    CampaignCriterionServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, CampaignCriterionServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
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
   * Sample Program for CampaignCriterionService GET.
   *
   * @param selector CampaignCriterionSelector
   * @return CampaignCriterionValues
   * @throws Exception throw exception
   */
  public static List<CampaignCriterionServiceValue> get(CampaignCriterionServiceSelector selector, String action) throws Exception {

    CampaignCriterionServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, action, selector, CampaignCriterionServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
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
