/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaign;

import jp.co.yahoo.adssearchapi.sample.basic.biddingstrategy.BiddingStrategyServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.pagefeedassetset.PageFeedAssetSetServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v14.api.CampaignServiceApi;
import jp.co.yahoo.adssearchapi.v14.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v14.model.Campaign;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceAppOsType;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceBiddingScheme;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceBiddingStrategy;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceBiddingStrategyType;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceBudget;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceCpcBiddingScheme;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceCustomParameter;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceCustomParameters;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceDynamicAdsForSearchSetting;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceEnhancedCpcEnabled;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceGeoTargetType;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceGeoTargetTypeSetting;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceMaximizeClicksBiddingScheme;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceOperation;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceSelector;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceSettingType;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceSettings;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceTargetAll;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceTargetingSetting;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceUserStatus;
import jp.co.yahoo.adssearchapi.v14.model.CampaignServiceValue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * example CampaignService operation and Utility method collection.
 */
public class CampaignServiceSample {

  private static final CampaignServiceApi campaignService = new CampaignServiceApi(ApiUtils.getYahooJapanAdsApiClient());

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
      Long feedId = valuesRepositoryFacade.getPageFeedAssetSetValuesRepository().findPageFeedAssetSetId();

      List<Long> feedIds = new ArrayList<>();
      feedIds.add(feedId);

      // =================================================================
      // CampaignService::ADD
      // =================================================================
      // create request.
      CampaignServiceOperation addCampaignOperation = buildExampleMutateRequest(accountId, new ArrayList<Campaign>() {{
        // Manual Bidding
        add(createExampleStandardCampaign("SampleManualCpcStandardCampaign_", feedIds, createManualBiddingCampaignBiddingStrategy()));
        // Portfolio Bidding
        add(createExampleStandardCampaign("SamplePortFolioBiddingStandardCampaign_", feedIds, createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId)));
        // Standard Bidding
        add(createExampleStandardCampaign("SampleStandardBiddingStandardCampaign_", feedIds, createStandardBiddingCampaignBiddingStrategy()));
      }});

      // run
      List<CampaignServiceValue> addCampaignValues = campaignService.campaignServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addCampaignOperation).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setCampaignServiceValueList(addCampaignValues);

      // =================================================================
      // CampaignService::GET
      // =================================================================
      // create request.
      CampaignServiceSelector campaignSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getCampaignValuesRepository().getCampaignIds());

      // run
      campaignService.campaignServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, campaignSelector);

      // check review status
      checkStatus(valuesRepositoryFacade.getCampaignValuesRepository().getCampaignIds());

      // =================================================================
      // CampaignService::SET
      // =================================================================
      // create request.
      CampaignServiceOperation setCampaignOperation = buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns()));

      // run
      campaignService.campaignServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setCampaignOperation);

      // =================================================================
      // CampaignService::REMOVE
      // =================================================================
      // create request.
      CampaignServiceOperation removeCampaignOperation =
          buildExampleMutateRequest(accountId, valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns());

      // run
      campaignService.campaignServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeCampaignOperation);
      valuesHolder.setCampaignServiceValueList(new ArrayList<>());

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

    CampaignServiceCpcBiddingScheme manualCpcBiddingScheme = new CampaignServiceCpcBiddingScheme();
    manualCpcBiddingScheme.setEnhancedCpcEnabled(CampaignServiceEnhancedCpcEnabled.FALSE);

    CampaignServiceBiddingScheme biddingScheme = new CampaignServiceBiddingScheme();
    biddingScheme.setBiddingStrategyType(CampaignServiceBiddingStrategyType.CPC);
    biddingScheme.setCpcBiddingScheme(manualCpcBiddingScheme);

    CampaignServiceBiddingStrategy campaignBiddingStrategy = new CampaignServiceBiddingStrategy();
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
    campaignBiddingStrategy.setPortfolioBiddingId(biddingStrategyId);

    return campaignBiddingStrategy;
  }

  /**
   * example Standard Bidding CampaignBiddingStrategy request.
   *
   * @return CampaignBiddingStrategy
   */
  private static CampaignServiceBiddingStrategy createStandardBiddingCampaignBiddingStrategy() {

    CampaignServiceMaximizeClicksBiddingScheme maximizeClicksBiddingScheme = new CampaignServiceMaximizeClicksBiddingScheme();
    maximizeClicksBiddingScheme.setBidCeiling((long) 700);

    CampaignServiceBiddingScheme biddingScheme = new CampaignServiceBiddingScheme();
    biddingScheme.setBiddingStrategyType(CampaignServiceBiddingStrategyType.MAXIMIZE_CLICKS);
    biddingScheme.setMaximizeClicksBiddingScheme(maximizeClicksBiddingScheme);

    CampaignServiceBiddingStrategy campaignBiddingStrategy = new CampaignServiceBiddingStrategy();
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
  public static Campaign createExampleStandardCampaign(String campaignNamePrefix, List<Long> feedIds, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);

    CampaignServiceDynamicAdsForSearchSetting dynamicAdsForSearchSetting = new CampaignServiceDynamicAdsForSearchSetting();
    dynamicAdsForSearchSetting.setPageFeedAssetSetIds(feedIds);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

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
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
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
  public static Campaign createExampleMobileAppCampaignForIOS(String campaignNamePrefix, List<Long> feedIds, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);

    CampaignServiceDynamicAdsForSearchSetting dynamicAdsForSearchSetting = new CampaignServiceDynamicAdsForSearchSetting();
    dynamicAdsForSearchSetting.setPageFeedAssetSetIds(feedIds);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

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
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    campaign.setEndDate("20301231");
    campaign.setType(CampaignServiceType.MOBILE_APP);
    campaign.setAppOsType(CampaignServiceAppOsType.IOS);
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
  public static Campaign createExampleMobileAppCampaignForANDROID(String campaignNamePrefix, List<Long> feedIds, CampaignServiceBiddingStrategy campaignBiddingStrategy) {

    // budget
    CampaignServiceBudget budget = new CampaignServiceBudget();
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);

    CampaignServiceDynamicAdsForSearchSetting dynamicAdsForSearchSetting = new CampaignServiceDynamicAdsForSearchSetting();
    dynamicAdsForSearchSetting.setPageFeedAssetSetIds(feedIds);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

    CampaignServiceSettings geoTargetTypeServiceSetting = new CampaignServiceSettings();
    geoTargetTypeServiceSetting.setSettingType(CampaignServiceSettingType.GEO_TARGET_TYPE_SETTING);
    geoTargetTypeServiceSetting.setGeoTargetTypeSetting(geoTargetTypeSetting);

    CampaignServiceSettings targetingServiceSetting = new CampaignServiceSettings();
    targetingServiceSetting.setSettingType(CampaignServiceSettingType.TARGET_LIST_SETTING);
    targetingServiceSetting.setTargetingSetting(targetingSetting);

    CampaignServiceSettings dynamicAdsForSearchServiceSetting =new CampaignServiceSettings();
    dynamicAdsForSearchServiceSetting.setSettingType(CampaignServiceSettingType.DYNAMIC_ADS_FOR_SEARCH_SETTING);
    dynamicAdsForSearchServiceSetting.setDynamicAdsForSearchSetting(dynamicAdsForSearchSetting);

    Campaign campaign = new Campaign();
    campaign.setCampaignName(campaignNamePrefix + ApiUtils.getCurrentTimestamp());
    campaign.setUserStatus(CampaignServiceUserStatus.ACTIVE);
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    campaign.setEndDate("20301231");
    campaign.setType(CampaignServiceType.MOBILE_APP);
    campaign.setAppOsType(CampaignServiceAppOsType.ANDROID);
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
    budget.setAmount((long) 1000);

    // settings
    CampaignServiceGeoTargetTypeSetting geoTargetTypeSetting = new CampaignServiceGeoTargetTypeSetting();
    geoTargetTypeSetting.setNegativeGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);
    geoTargetTypeSetting.setPositiveGeoTargetType(CampaignServiceGeoTargetType.LOCATION_OF_PRESENCE);

    CampaignServiceTargetingSetting targetingSetting = new CampaignServiceTargetingSetting();
    targetingSetting.setTargetAll(CampaignServiceTargetAll.ACTIVE);

    CampaignServiceDynamicAdsForSearchSetting dynamicAdsForSearchSetting = new CampaignServiceDynamicAdsForSearchSetting();
    dynamicAdsForSearchSetting.setPageFeedAssetSetIds(feedIds);

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
    campaign.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
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
        List<CampaignServiceValue> getCampaignValues = campaignService.campaignServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, campaignSelector).getRval().getValues();

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
    ValuesHolder valuesHolderFeed = PageFeedAssetSetServiceSample.create();

    ValuesHolder valuesHolder = new ValuesHolder();
    valuesHolder.setBiddingStrategyServiceValueList(valuesHolderBiddingStrategy.getBiddingStrategyServiceValueList());
    valuesHolder.setPageFeedAssetSetServiceValueList(valuesHolderFeed.getPageFeedAssetSetServiceValueList());

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

    Long feedId = parentValuesRepositoryFacade.getPageFeedAssetSetValuesRepository().findPageFeedAssetSetId();
    long accountId = ApiUtils.ACCOUNT_ID;

    List<Long> feedIds = new ArrayList<>();
    feedIds.add(feedId);

    CampaignServiceOperation addCampaignOperation = buildExampleMutateRequest(accountId, new ArrayList<Campaign>() {{
      // Standard Campaign
      add(createExampleStandardCampaign("SampleCpcStandardCampaign_", feedIds, createManualBiddingCampaignBiddingStrategy()));
      // MobileApp Campaign
      add(createExampleMobileAppCampaignForIOS("SampleCpcIOSCampaign_", feedIds, createManualBiddingCampaignBiddingStrategy()));
      // DynamicAdsForSearch Campaign
      add(createExampleDynamicAdsForSearchCampaign("SampleCpcDynamicAdsForSearchCampaign_", feedIds, createManualBiddingCampaignBiddingStrategy()));
    }});

    List<CampaignServiceValue> addCampaignValues = campaignService.campaignServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addCampaignOperation).getRval().getValues();

    ValuesHolder selfValuesHolder = new ValuesHolder();
    selfValuesHolder.setBiddingStrategyServiceValueList(parentValuesHolder.getBiddingStrategyServiceValueList());
    selfValuesHolder.setPageFeedAssetSetServiceValueList(parentValuesHolder.getPageFeedAssetSetServiceValueList());
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

      campaignService.campaignServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeCampaignOperation);
    }
    BiddingStrategyServiceSample.cleanup(valuesHolder);
    PageFeedAssetSetServiceSample.cleanup(valuesHolder);
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
      if (CampaignServiceType.STANDARD.equals(campaign.getType()) || CampaignServiceType.DYNAMIC_ADS_FOR_SEARCH.equals(campaign.getType()) || (CampaignServiceType.MOBILE_APP.equals(campaign.getType()) && CampaignServiceAppOsType.IOS.equals(campaign.getAppOsType()))) {
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
