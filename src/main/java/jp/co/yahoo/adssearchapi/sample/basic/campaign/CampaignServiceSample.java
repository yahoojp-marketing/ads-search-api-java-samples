/**
 * Copyright (C) 2019 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaign;

import jp.co.yahoo.adssearchapi.v0.model.*;
import jp.co.yahoo.adssearchapi.sample.basic.biddingstrategy.BiddingStrategyServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.feed.FeedServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * example CampaignService operation and Utility method collection.
 */
public class CampaignServiceSample {

  private static final String SERVICE_NAME = "CampaignService";

  /**
   * main method for CampaignServiceSample
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
      Long biddingStrategyId = valuesRepositoryFacade.getBiddingStrategyValuesRepository().findBiddingStrategyId(BiddingStrategyServiceType.TARGET_CPA);

      // =================================================================
      // CampaignService::ADD
      // =================================================================
      // create request.
      CampaignServiceOperation addCampaignOperation = buildExampleMutateRequest(accountId, new ArrayList<Campaign>() {{
        // Manual Bidding
        add(createExampleStandardCampaign("SampleManualCpcStandardCampaign_", createManualBiddingCampaignBiddingStrategy()));
        // Portfolio Bidding
        add(createExampleStandardCampaign("SamplePortFolioBiddingStandardCampaign_", createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId)));
        // Standard Bidding
        add(createExampleStandardCampaign("SampleStandardBiddingStandardCampaign_", createStandardBiddingCampaignBiddingStrategy()));
      }});

      // run
      List<CampaignServiceValue> addCampaignValues = mutate(addCampaignOperation, "add");
      valuesRepositoryFacade.getValuesHolder().setCampaignServiceValueList(addCampaignValues);

      // =================================================================
      // CampaignService::GET
      // =================================================================
      // create request.
      CampaignServiceSelector campaignSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getCampaignValuesRepository().getCampaignIds());

      // run
      get(campaignSelector, "get");

      // check review status
      checkStatus(valuesRepositoryFacade.getCampaignValuesRepository().getCampaignIds());

      // =================================================================
      // CampaignService::SET
      // =================================================================
      // create request.
      CampaignServiceOperation setCampaignOperation = buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns()));

      // run
      mutate(setCampaignOperation, "set");

      // =================================================================
      // CampaignService::REMOVE
      // =================================================================
      // create request.
      CampaignServiceOperation removeCampaignOperation =
          buildExampleMutateRequest(accountId, valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns());

      // run
      mutate(removeCampaignOperation, "remove");
      valuesHolder.setCampaignServiceValueList(new ArrayList<>());

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cleanup(valuesHolder);
    }
  }

  /**
   * example mutate campaigns.
   *
   * @param operation CampaignOperation
   * @return CampaignValues
   */
  public static List<CampaignServiceValue> mutate(CampaignServiceOperation operation, String action) throws Exception {

    CampaignServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, CampaignServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example mutate request.
   */
  public static CampaignServiceOperation buildExampleMutateRequest(long accountId, List<Campaign> operand) {
    CampaignServiceOperation operation = new CampaignServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example Manual Bidding CampaignBiddingStrategy request.
   *
   * @return CampaignBiddingStrategy
   */
  private static CampaignServiceBiddingStrategy createManualBiddingCampaignBiddingStrategy() {

    CampaignServiceManualCpcBiddingScheme manualCpcBiddingScheme = new CampaignServiceManualCpcBiddingScheme();
    manualCpcBiddingScheme.setEnhancedCpcEnabled(CampaignServiceEnhancedCpcEnabled.FALSE);

    CampaignServiceBiddingScheme biddingScheme = new CampaignServiceBiddingScheme();
    biddingScheme.setBiddingStrategyType(CampaignServiceBiddingStrategyType.MANUAL_CPC);
    biddingScheme.setManualCpcBiddingScheme(manualCpcBiddingScheme);

    CampaignServiceBiddingStrategy campaignBiddingStrategy = new CampaignServiceBiddingStrategy();
    campaignBiddingStrategy.setBiddingStrategyType(CampaignServiceBiddingStrategyType.MANUAL_CPC);
    campaignBiddingStrategy.setBiddingScheme(biddingScheme);

    return campaignBiddingStrategy;
  }

  /**
   * example Portfolio Bidding CampaignBiddingStrategy request.
   *
   * @param biddingStrategyId Long
   * @return CampaignBiddingStrategy
   */
  public static CampaignServiceBiddingStrategy createPortfolioBiddingCampaignBiddingStrategy(Long biddingStrategyId) {

    CampaignServiceBiddingStrategy campaignBiddingStrategy = new CampaignServiceBiddingStrategy();
    campaignBiddingStrategy.setBiddingStrategyId(biddingStrategyId);

    return campaignBiddingStrategy;
  }

  /**
   * example Standard Bidding CampaignBiddingStrategy request.
   *
   * @return CampaignBiddingStrategy
   */
  private static CampaignServiceBiddingStrategy createStandardBiddingCampaignBiddingStrategy() {

    CampaignServiceTargetSpendBiddingScheme targetSpendBiddingScheme = new CampaignServiceTargetSpendBiddingScheme();
    targetSpendBiddingScheme.setBidCeiling((long) 700);

    CampaignServiceBiddingScheme biddingScheme = new CampaignServiceBiddingScheme();
    biddingScheme.setBiddingStrategyType(CampaignServiceBiddingStrategyType.TARGET_SPEND);
    biddingScheme.setTargetSpendBiddingScheme(targetSpendBiddingScheme);

    CampaignServiceBiddingStrategy campaignBiddingStrategy = new CampaignServiceBiddingStrategy();
    campaignBiddingStrategy.setBiddingStrategyType(CampaignServiceBiddingStrategyType.TARGET_SPEND);
    campaignBiddingStrategy.setBiddingScheme(biddingScheme);

    return campaignBiddingStrategy;
  }

  /**
   * example Standard Campaign request.
   *
   * @param campaignNamePrefix String
   * @param campaignBiddingStrategy CampaignBiddingStrategy
   * @return Campaign
   */
  public static Campaign createExampleStandardCampaign(String campaignNamePrefix, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setBudgetPeriod(CampaignServiceBudgetPeriod.DAILY);
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.DONT_CARE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.AREA_OF_INTENT);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

    CampaignServiceSettings geoTargetTypeServiceSetting = new CampaignServiceSettings();
    geoTargetTypeServiceSetting.setSettingType(CampaignServiceSettingType.GEO_TARGET_TYPE_SETTING);
    geoTargetTypeServiceSetting.setGeoTargetTypeSetting(geoTargetTypeSetting);

    CampaignServiceSettings targetingServiceSetting = new CampaignServiceSettings();
    targetingServiceSetting.setSettingType(CampaignServiceSettingType.TARGET_LIST_SETTING);
    targetingServiceSetting.setTargetingSetting(targetingSetting);


    // customParameters
    CampaignServiceCustomParameter customParameter = new CampaignServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    CampaignServiceCustomParameters customParameters = new CampaignServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    Campaign campaign = new Campaign();
    campaign.setCampaignName(campaignNamePrefix + ApiUtils.getCurrentTimestamp());
    campaign.setUserStatus(CampaignServiceUserStatus.ACTIVE);
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
    campaign.setEndDate("20301231");
    campaign.setType(CampaignServiceType.STANDARD);
    campaign.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    campaign.setBudget(budget);
    campaign.setBiddingStrategyConfiguration(campaignBiddingStrategy);
    campaign.setSettings(Arrays.asList(geoTargetTypeServiceSetting, targetingServiceSetting));
    campaign.setCustomParameters(customParameters);

    return campaign;
  }

  /**
   * example MobileApp Campaign for IOS request.
   *
   * @param campaignNamePrefix String
   * @param campaignBiddingStrategy CampaignBiddingStrategy
   * @return Campaign
   */
  public static Campaign createExampleMobileAppCampaignForIOS(String campaignNamePrefix, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setBudgetPeriod(CampaignServiceBudgetPeriod.DAILY);
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.DONT_CARE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.AREA_OF_INTENT);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

    CampaignServiceSettings geoTargetTypeServiceSetting = new CampaignServiceSettings();
    geoTargetTypeServiceSetting.setSettingType(CampaignServiceSettingType.GEO_TARGET_TYPE_SETTING);
    geoTargetTypeServiceSetting.setGeoTargetTypeSetting(geoTargetTypeSetting);

    CampaignServiceSettings targetingServiceSetting = new CampaignServiceSettings();
    targetingServiceSetting.setSettingType(CampaignServiceSettingType.TARGET_LIST_SETTING);
    targetingServiceSetting.setTargetingSetting(targetingSetting);

    // customParameters
    CampaignServiceCustomParameter customParameter = new CampaignServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    CampaignServiceCustomParameters customParameters = new CampaignServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    Campaign campaign = new Campaign();
    campaign.setCampaignName(campaignNamePrefix + ApiUtils.getCurrentTimestamp());
    campaign.setUserStatus(CampaignServiceUserStatus.ACTIVE);
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
    campaign.setEndDate("20301231");
    campaign.setType(CampaignServiceType.MOBILE_APP);
    campaign.setAppStore(CampaignServiceAppStore.IOS);
    campaign.setAppId(ApiUtils.getCurrentTimestamp());
    campaign.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    campaign.setBudget(budget);
    campaign.setBiddingStrategyConfiguration(campaignBiddingStrategy);
    campaign.setSettings(Arrays.asList(geoTargetTypeServiceSetting, targetingServiceSetting));
    campaign.setCustomParameters(customParameters);

    return campaign;
  }

  /**
   * example MobileApp Campaign for ANDROID request.
   *
   * @param campaignNamePrefix String
   * @param campaignBiddingStrategy CampaignBiddingStrategy
   * @return Campaign
   */
  public static Campaign createExampleMobileAppCampaignForANDROID(String campaignNamePrefix, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setBudgetPeriod(CampaignServiceBudgetPeriod.DAILY);
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.DONT_CARE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.AREA_OF_INTENT);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

    CampaignServiceSettings geoTargetTypeServiceSetting = new CampaignServiceSettings();
    geoTargetTypeServiceSetting.setSettingType(CampaignServiceSettingType.GEO_TARGET_TYPE_SETTING);
    geoTargetTypeServiceSetting.setGeoTargetTypeSetting(geoTargetTypeSetting);

    CampaignServiceSettings targetingServiceSetting = new CampaignServiceSettings();
    targetingServiceSetting.setSettingType(CampaignServiceSettingType.TARGET_LIST_SETTING);
    targetingServiceSetting.setTargetingSetting(targetingSetting);

    Campaign campaign = new Campaign();
    campaign.setCampaignName(campaignNamePrefix + ApiUtils.getCurrentTimestamp());
    campaign.setUserStatus(CampaignServiceUserStatus.ACTIVE);
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
    campaign.setEndDate("20301231");
    campaign.setType(CampaignServiceType.MOBILE_APP);
    campaign.setAppStore(CampaignServiceAppStore.ANDROID);
    campaign.setAppId("jp.co.yahoo." + ApiUtils.getCurrentTimestamp());
    campaign.setBudget(budget);
    campaign.setBiddingStrategyConfiguration(campaignBiddingStrategy);
    campaign.setSettings(Arrays.asList(geoTargetTypeServiceSetting, targetingServiceSetting));

    return campaign;
  }

  /**
   * example Dynamic Ads for Search Campaign request.
   *
   * @param campaignNamePrefix String
   * @param feedIds Long
   * @param campaignBiddingStrategy CampaignBiddingStrategy
   * @return Campaign
   */
  private static Campaign createExampleDynamicAdsForSearchCampaign(String campaignNamePrefix, List<Long> feedIds, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setBudgetPeriod(CampaignServiceBudgetPeriod.DAILY);
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.DONT_CARE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.AREA_OF_INTENT);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

    CampaignServiceDynamicAdsForSearchSetting dynamicAdsForSearchSetting = new CampaignServiceDynamicAdsForSearchSetting();
    dynamicAdsForSearchSetting.setFeedIds(feedIds);

    CampaignServiceSettings geoTargetTypeServiceSetting = new CampaignServiceSettings();
    geoTargetTypeServiceSetting.setSettingType(CampaignServiceSettingType.GEO_TARGET_TYPE_SETTING);
    geoTargetTypeServiceSetting.setGeoTargetTypeSetting(geoTargetTypeSetting);

    CampaignServiceSettings targetingServiceSetting = new CampaignServiceSettings();
    targetingServiceSetting.setSettingType(CampaignServiceSettingType.TARGET_LIST_SETTING);
    targetingServiceSetting.setTargetingSetting(targetingSetting);

    CampaignServiceSettings dynamicAdsForSearchServiceSetting =new CampaignServiceSettings();
    dynamicAdsForSearchServiceSetting.setSettingType(CampaignServiceSettingType.DYNAMIC_ADS_FOR_SEARCH_SETTING);
    dynamicAdsForSearchServiceSetting.setDynamicAdsForSearchSetting(dynamicAdsForSearchSetting);

    // customParameters
    CampaignServiceCustomParameter customParameter = new CampaignServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    CampaignServiceCustomParameters customParameters = new CampaignServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    Campaign campaign = new Campaign();
    campaign.setCampaignName(campaignNamePrefix + ApiUtils.getCurrentTimestamp());
    campaign.setUserStatus(CampaignServiceUserStatus.ACTIVE);
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
    campaign.setEndDate("20301231");
    campaign.setType(CampaignServiceType.DYNAMIC_ADS_FOR_SEARCH);
    campaign.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    campaign.setBudget(budget);
    campaign.setBiddingStrategyConfiguration(campaignBiddingStrategy);
    campaign.setSettings(Arrays.asList(geoTargetTypeServiceSetting, targetingServiceSetting, dynamicAdsForSearchServiceSetting));
    campaign.setCustomParameters(customParameters);

    return campaign;
  }

  /**
   * example check campaign review status.
   *
   * @param campaignIds List<Long>
   * @throws Exception throw exception
   */
  public static void checkStatus(List<Long> campaignIds) throws Exception {

    try {
      // call 30sec sleep * 30 = 15minute
      for (int i = 0; i < 30; i++) {
        // sleep 30 second.
        System.out.println("\n***** sleep 30 seconds for Get Campaign  *****\n");
        Thread.sleep(30000);

        CampaignServiceSelector campaignSelector = buildExampleGetRequest(ApiUtils.ACCOUNT_ID, campaignIds);
        List<CampaignServiceValue> getCampaignValues = get(campaignSelector, "get");

        int approvalCount = 0;
        for (CampaignServiceValue campaignValues : getCampaignValues) {
          if (campaignValues.getCampaign().getUrlReviewData().getUrlApprovalStatus() != null) {
            switch (campaignValues.getCampaign().getUrlReviewData().getUrlApprovalStatus()) {
              default:
              case REVIEW:
              case APPROVED_WITH_REVIEW:
                continue;
              case DISAPPROVED:
                throw new Exception("Campaign Review Status failed.");
              case NONE:
              case APPROVED:
                approvalCount++;
            }
          } else {
            throw new Exception("Fail to get CampaignService.");
          }
        }

        if (getCampaignValues.size() == approvalCount) {
          return;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * check & create upper service object.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  private static ValuesHolder setup() throws Exception {

    ValuesHolder valuesHolderBiddingStrategy = BiddingStrategyServiceSample.create();
    ValuesHolder valuesHolderFeed = FeedServiceSample.create();

    ValuesHolder valuesHolder = new ValuesHolder();
    valuesHolder.setBiddingStrategyServiceValueList(valuesHolderBiddingStrategy.getBiddingStrategyServiceValueList());
    valuesHolder.setFeedServiceValueList(valuesHolderFeed.getFeedServiceValueList());

    // sleep 30 second.
    System.out.println("\n***** sleep 30 seconds *****\n");
    Thread.sleep(30000);

    return valuesHolder;
  }

  /**
   * create basic Campaign.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  public static ValuesHolder create() throws Exception {

    ValuesHolder parentValuesHolder = setup();
    ValuesRepositoryFacade parentValuesRepositoryFacade = new ValuesRepositoryFacade(parentValuesHolder);
    Long feedId = parentValuesRepositoryFacade.getFeedValueRepository().findFeedId(
        FeedServicePlaceholderType.DYNAMIC_AD_FOR_SEARCH_PAGE_FEEDS
    );
    long accountId = ApiUtils.ACCOUNT_ID;

    List<Long> feedIds = new ArrayList<>();
    feedIds.add(feedId);

    CampaignServiceOperation addCampaignOperation = buildExampleMutateRequest(accountId, new ArrayList<Campaign>() {{
      // Standard Campaign
      add(createExampleStandardCampaign("SampleManualCpcStandardCampaign_", createManualBiddingCampaignBiddingStrategy()));
      // MobileApp Campaign
      add(createExampleMobileAppCampaignForIOS("SampleManualCpcIOSCampaign_", createManualBiddingCampaignBiddingStrategy()));
      // DynamicAdsForSearch Campaign
      add(createExampleDynamicAdsForSearchCampaign("SampleManualCpcDynamicAdsForSearchCampaign_", feedIds, createManualBiddingCampaignBiddingStrategy()));
    }});

    List<CampaignServiceValue> addCampaignValues = mutate(addCampaignOperation, "add");

    ValuesHolder selfValuesHolder = new ValuesHolder();
    selfValuesHolder.setBiddingStrategyServiceValueList(parentValuesHolder.getBiddingStrategyServiceValueList());
    selfValuesHolder.setFeedServiceValueList(parentValuesHolder.getFeedServiceValueList());
    selfValuesHolder.setCampaignServiceValueList(addCampaignValues);

    return selfValuesHolder;
  }

  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {
    long accountId = ApiUtils.ACCOUNT_ID;

    if (valuesHolder.getCampaignServiceValueList().size() > 0) {
      ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);
      CampaignServiceOperation removeCampaignOperation =
          buildExampleMutateRequest(accountId, valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns());

      mutate(removeCampaignOperation, "remove");
    }
    BiddingStrategyServiceSample.cleanup(valuesHolder);
    FeedServiceSample.cleanup(valuesHolder);
  }

  /**
   * Sample Program for CampaignService GET.
   *
   * @param selector CampaignSelector
   * @return CampaignValues
   */
  public static List<CampaignServiceValue> get(CampaignServiceSelector selector, String action) throws Exception {

    CampaignServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, action, selector, CampaignServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example campaigns set request.
   *
   * @param campaigns List<Campaign>
   * @return List<Campaign>
   */
  public static List<Campaign> createExampleSetRequest(List<Campaign> campaigns) {
    // create operands
    List<Campaign> operands = new ArrayList<>();

    for (Campaign campaign : campaigns) {

      Campaign operand = new Campaign();
      operand.setCampaignId(campaign.getCampaignId());
      operand.setCampaignName("Sample_UpdateOn_" + campaign.getCampaignId() + "_" + ApiUtils.getCurrentTimestamp());
      operand.setUserStatus(CampaignServiceUserStatus.PAUSED);
      operand.setEndDate("20371231");

      // set budget
      CampaignServiceBudget budget = new CampaignServiceBudget();
      budget.setAmount((long) 2000);
      operand.setBudget(budget);

      // set trackingUrl & customParameters
      if (CampaignServiceType.STANDARD.equals(campaign.getType()) || CampaignServiceType.DYNAMIC_ADS_FOR_SEARCH.equals(campaign.getType()) || (CampaignServiceType.MOBILE_APP.equals(campaign.getType()) && CampaignServiceAppStore.IOS.equals(campaign.getAppStore()))) {
        operand.setTrackingUrl("http://yahoo.co.jp?url={lpurl}&amp;a={creative}&amp;pid={_id2}");

        // set customParameters
        CampaignServiceCustomParameter customParameter = new CampaignServiceCustomParameter();
        customParameter.setKey("id2");
        customParameter.setValue("5678");
        CampaignServiceCustomParameters customParameters = new CampaignServiceCustomParameters();
        customParameters.setParameters(Collections.singletonList(customParameter));
        operand.setCustomParameters(customParameters);
      }
      operands.add(operand);
    }

    return operands;
  }

  /**
   * create sample request.
   *
   * @param accountId      long
   * @param campaignValues CampaignValues
   * @return CampaignOperation
   */
  public static CampaignServiceOperation createSampleRemoveRequest(long accountId, List<CampaignServiceValue> campaignValues) {
    // Set Operation
    CampaignServiceOperation operation = new CampaignServiceOperation();
    operation.setAccountId(accountId);

    // Set Operand
    for (CampaignServiceValue campaignValue : campaignValues) {

      Campaign campaign = new Campaign();
      campaign.setAccountId(campaignValue.getCampaign().getAccountId());
      campaign.setCampaignId(campaignValue.getCampaign().getCampaignId());

      operation.setOperand(Collections.singletonList(campaign));
    }

    return operation;
  }

  /**
   * example get request.
   *
   * @param accountId   long
   * @param campaignIds List<Long>
   * @return CampaignSelector
   */
  public static CampaignServiceSelector buildExampleGetRequest(long accountId, List<Long> campaignIds) {

    CampaignServiceSelector selector = new CampaignServiceSelector();
    selector.setAccountId(accountId);

    if (campaignIds.size() > 0) {
      selector.setCampaignIds(campaignIds);
    }
    selector.setUserStatuses(Arrays.asList(CampaignServiceUserStatus.ACTIVE, CampaignServiceUserStatus.PAUSED));

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }
}
