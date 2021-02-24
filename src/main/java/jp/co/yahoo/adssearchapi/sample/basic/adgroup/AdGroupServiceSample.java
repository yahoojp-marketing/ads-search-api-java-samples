/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.adgroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.basic.campaign.CampaignServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v4.model.AdGroup;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceAdGroupAdRotationMode;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceAdRotationMode;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceBid;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceCriterionType;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceCustomParameter;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceCustomParameters;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceGetResponse;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceOperation;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceSelector;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceSettings;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceTargetAll;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceTargetingSetting;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceUrlApprovalStatus;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceUserStatus;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupServiceValue;
import jp.co.yahoo.adssearchapi.v4.model.CampaignServiceType;

/**
 * example AdGroupService operation and Utility method collection.
 */
public class AdGroupServiceSample {

  private static final String SERVICE_NAME = "AdGroupService";

  /**
   * main method for AdGroupServiceSample
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
      // AdGroupService ADD
      // =================================================================
      // create request.
      AdGroupServiceOperation addRequest = buildExampleMutateRequest(accountId, Collections.singletonList(createExampleStandardAdGroup(campaignId)));

      // run
      List<AdGroupServiceValue> addResponse = mutate(addRequest, "add");
      valuesRepositoryFacade.getValuesHolder().setAdGroupServiceValueList(addResponse);
      System.out.println(addResponse);

      // =================================================================
      // AdGroupService GET
      // =================================================================
      // create request.
      AdGroupServiceSelector getRequest = buildExampleGetRequest(accountId, valuesRepositoryFacade.getAdGroupValuesRepository().getAdGroups());
      // run
      get(getRequest);
      // check review status
      checkStatus(valuesRepositoryFacade.getAdGroupValuesRepository().getAdGroups());

      // =================================================================
      // AdGroupService SET
      // =================================================================
      // create request.
      AdGroupServiceOperation setRequest = buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getAdGroupValuesRepository().getAdGroups()));
      // run
      mutate(setRequest, "set");

      // =================================================================
      // AdGroupService REMOVE
      // =================================================================
      // create request.
      AdGroupServiceOperation removeRequest = buildExampleMutateRequest(accountId, valuesRepositoryFacade.getAdGroupValuesRepository().getAdGroups());
      // run
      mutate(removeRequest, "remove");
      valuesHolder.setAdGroupServiceValueList(new ArrayList<>());

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cleanup(valuesHolder);
    }
  }

  /**
   * example mutate adGroups.
   *
   * @param operation CampaignOperation
   * @return CampaignValues
   */
  public static List<AdGroupServiceValue> mutate(AdGroupServiceOperation operation, String action) throws Exception {

    AdGroupServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, AdGroupServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * Sample Program for AdGroupService GET.
   *
   * @param selector AdGroupSelector
   * @return AdGroupServiceValue
   * @throws Exception throw exception
   */
  public static List<AdGroupServiceValue> get(AdGroupServiceSelector selector) throws Exception {

    AdGroupServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, "get", selector, AdGroupServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example mutate request.
   */
  public static AdGroupServiceOperation buildExampleMutateRequest(long accountId, List<AdGroup> operand) {
    AdGroupServiceOperation operation = new AdGroupServiceOperation();
    operation.setAccountId(accountId);
    operation.getOperand().addAll(operand);

    return operation;
  }

  /**
   * example Standard AdGroup request.
   *
   * @param campaignId long
   * @return AdGroupServiceOperation
   */
  public static AdGroup createExampleStandardAdGroup(long campaignId) {

    // bid
    AdGroupServiceBid bid = new AdGroupServiceBid();
    bid.setMaxCpc((long) 100);

    // settings
    AdGroupServiceTargetingSetting targetingSetting = new AdGroupServiceTargetingSetting();
    targetingSetting.setTargetAll(AdGroupServiceTargetAll.ACTIVE);

    AdGroupServiceSettings setting = new AdGroupServiceSettings();
    setting.setCriterionType(AdGroupServiceCriterionType.TARGET_LIST);
    setting.setTargetingSetting(targetingSetting);


    // customParameters
    AdGroupServiceCustomParameter customParameter = new AdGroupServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    AdGroupServiceCustomParameters customParameters = new AdGroupServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    // ad rotation mode
    AdGroupServiceAdGroupAdRotationMode adGroupAdRotationMode = new AdGroupServiceAdGroupAdRotationMode();
    adGroupAdRotationMode.setAdRotationMode(AdGroupServiceAdRotationMode.ROTATE_FOREVER);

    AdGroup adGroup = new AdGroup();
    adGroup.setCampaignId(campaignId);
    adGroup.setAdGroupName("SampleStandardAdGroup_" + campaignId + "_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroup.setUserStatus(AdGroupServiceUserStatus.ACTIVE);
    adGroup.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    adGroup.setBid(bid);
    adGroup.setSettings(setting);
    adGroup.setCustomParameters(customParameters);
    adGroup.setAdGroupAdRotationMode(adGroupAdRotationMode);

    return adGroup;
  }

  /**
   * example MobileApp IOS AdGroup request.
   *
   * @param campaignId long
   * @return AdGroupServiceOperation
   */
  public static AdGroup createExampleMobileAppIOSAdGroup(long campaignId) {

    // bid
    AdGroupServiceBid bid = new AdGroupServiceBid();
    bid.setMaxCpc((long) 100);

    // settings
    AdGroupServiceTargetingSetting targetingSetting = new AdGroupServiceTargetingSetting();
    targetingSetting.setTargetAll(AdGroupServiceTargetAll.ACTIVE);

    AdGroupServiceSettings setting = new AdGroupServiceSettings();
    setting.setCriterionType(AdGroupServiceCriterionType.TARGET_LIST);
    setting.setTargetingSetting(targetingSetting);

    // customParameters
    AdGroupServiceCustomParameter customParameter = new AdGroupServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    AdGroupServiceCustomParameters customParameters = new AdGroupServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    // ad rotation mode
    AdGroupServiceAdGroupAdRotationMode adGroupAdRotationMode = new AdGroupServiceAdGroupAdRotationMode();
    adGroupAdRotationMode.setAdRotationMode(AdGroupServiceAdRotationMode.ROTATE_FOREVER);

    AdGroup adGroup = new AdGroup();
    adGroup.setCampaignId(campaignId);
    adGroup.setAdGroupName("SampleMobileAppIOSAdGroup_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroup.setUserStatus(AdGroupServiceUserStatus.ACTIVE);
    adGroup.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    adGroup.setBid(bid);
    adGroup.setSettings(setting);
    adGroup.setCustomParameters(customParameters);
    adGroup.setAdGroupAdRotationMode(adGroupAdRotationMode);

    return adGroup;
  }

  /**
   * example MobileApp ANDROID AdGroup request.
   *
   * @param campaignId long
   * @return AdGroupServiceOperation
   */
  public static AdGroup createExampleMobileAppANDROIDAdGroup(long campaignId) {

    // bid
    AdGroupServiceBid bid = new AdGroupServiceBid();
    bid.setMaxCpc((long) 100);

    // settings
    AdGroupServiceTargetingSetting targetingSetting = new AdGroupServiceTargetingSetting();
    targetingSetting.setTargetAll(AdGroupServiceTargetAll.ACTIVE);

    AdGroupServiceSettings setting = new AdGroupServiceSettings();
    setting.setCriterionType(AdGroupServiceCriterionType.TARGET_LIST);
    setting.setTargetingSetting(targetingSetting);

    // ad rotation mode
    AdGroupServiceAdGroupAdRotationMode adGroupAdRotationMode = new AdGroupServiceAdGroupAdRotationMode();
    adGroupAdRotationMode.setAdRotationMode(AdGroupServiceAdRotationMode.ROTATE_FOREVER);

    AdGroup adGroup = new AdGroup();
    adGroup.setCampaignId(campaignId);
    adGroup.setAdGroupName("SampleMobileAppANDROIDAdGroup_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroup.setUserStatus(AdGroupServiceUserStatus.ACTIVE);
    adGroup.setBid(bid);
    adGroup.setSettings(setting);
    adGroup.setAdGroupAdRotationMode(adGroupAdRotationMode);

    return adGroup;
  }

  /**
   * example DynamicAdsForSearch AdGroup request.
   *
   * @param campaignId long
   * @return AdGroupServiceOperation
   */
  private static AdGroup createExampleDynamicAdsForSearchAdGroup(long campaignId) {

    // bid
    AdGroupServiceBid bid = new AdGroupServiceBid();
    bid.setMaxCpc((long) 100);

    // settings
    AdGroupServiceTargetingSetting targetingSetting = new AdGroupServiceTargetingSetting();
    targetingSetting.setTargetAll(AdGroupServiceTargetAll.ACTIVE);

    AdGroupServiceSettings setting = new AdGroupServiceSettings();
    setting.setCriterionType(AdGroupServiceCriterionType.TARGET_LIST);
    setting.setTargetingSetting(targetingSetting);

    // customParameters
    AdGroupServiceCustomParameter customParameter = new AdGroupServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    AdGroupServiceCustomParameters customParameters = new AdGroupServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    // ad rotation mode
    AdGroupServiceAdGroupAdRotationMode adGroupAdRotationMode = new AdGroupServiceAdGroupAdRotationMode();
    adGroupAdRotationMode.setAdRotationMode(AdGroupServiceAdRotationMode.ROTATE_FOREVER);

    AdGroup adGroup = new AdGroup();
    adGroup.setCampaignId(campaignId);
    adGroup.setAdGroupName("SampleDynamicAdsForSearchAdGroup_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroup.setUserStatus(AdGroupServiceUserStatus.ACTIVE);
    adGroup.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    adGroup.setBid(bid);
    adGroup.setSettings(setting);
    adGroup.setCustomParameters(customParameters);
    adGroup.setAdGroupAdRotationMode(adGroupAdRotationMode);

    return adGroup;
  }

  /**
   * create sample request.
   *
   * @param accountId long
   * @param adGroups AdGroup
   * @return AdGroupSelector
   */
  public static AdGroupServiceSelector buildExampleGetRequest(long accountId, List<AdGroup> adGroups) {

    // Set Selector
    AdGroupServiceSelector selector = new AdGroupServiceSelector();
    selector.setAccountId(accountId);

    List<Long> campaignIds = new ArrayList<>();
    List<Long> adGroupIds = new ArrayList<>();
    for (AdGroup adGroup : adGroups) {
      campaignIds.add(adGroup.getCampaignId());
      adGroupIds.add(adGroup.getAdGroupId());
    }

    selector.setCampaignIds(campaignIds);
    selector.setAdGroupIds(adGroupIds);
    selector.setUserStatuses(Arrays.asList(AdGroupServiceUserStatus.ACTIVE, AdGroupServiceUserStatus.PAUSED));
    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

  /**
   * example adGroup set request.
   *
   * @param adGroups List<AdGroup>
   * @return List<AdGroup>
   */
  public static List<AdGroup> createExampleSetRequest(List<AdGroup> adGroups) {
    // create operands
    List<AdGroup> operands = new ArrayList<>();

    for (AdGroup adGroup : adGroups) {
      AdGroup operand = new AdGroup();
      operand.setCampaignId(adGroup.getCampaignId());
      operand.setAdGroupId(adGroup.getAdGroupId());
      operand.setAdGroupName("Sample_UpdateOn_" + adGroup.getAdGroupId() + "_" + ApiUtils.getCurrentTimestamp());
      operand.setUserStatus(AdGroupServiceUserStatus.PAUSED);

      // bid
      AdGroupServiceBid bid = new AdGroupServiceBid();
      bid.setMaxCpc((long) 150);
      operand.setBid(bid);

      // settings
      AdGroupServiceTargetingSetting targetingSetting = new AdGroupServiceTargetingSetting();
      targetingSetting.setTargetAll(AdGroupServiceTargetAll.DEACTIVE);

      AdGroupServiceSettings setting = new AdGroupServiceSettings();
      setting.setCriterionType(AdGroupServiceCriterionType.TARGET_LIST);
      setting.setTargetingSetting(targetingSetting);
      operand.setSettings(setting);

      // ad rotation mode
      AdGroupServiceAdGroupAdRotationMode adGroupAdRotationMode = new AdGroupServiceAdGroupAdRotationMode();
      adGroupAdRotationMode.setAdRotationMode(AdGroupServiceAdRotationMode.ROTATE_FOREVER);
      operand.setAdGroupAdRotationMode(adGroupAdRotationMode);

      if (!adGroup.getUrlReviewData().getUrlApprovalStatus().equals(AdGroupServiceUrlApprovalStatus.NONE)) {
        operand.setTrackingUrl("http://yahoo.co.jp?url={lpurl}&amp;a={creative}&amp;pid={_id2}");

        AdGroupServiceCustomParameter customParameter = new AdGroupServiceCustomParameter();
        customParameter.setKey("id2");
        customParameter.setValue("5678");
        AdGroupServiceCustomParameters customParameters = new AdGroupServiceCustomParameters();
        customParameters.setParameters(Collections.singletonList(customParameter));
        operand.setCustomParameters(customParameters);
      }
      operands.add(operand);
    }

    return operands;
  }

  /**
   * example check adGroup review status.
   *
   * @param adGroups List<AdGroup>
   * @throws Exception throw exception
   */
  public static void checkStatus(List<AdGroup> adGroups) throws Exception {

    try {
      // call 30sec sleep * 30 = 15minute
      for (int i = 0; i < 30; i++) {
        // sleep 30 second.
        System.out.println("\n***** sleep 30 seconds for Get AdGroup  *****\n");
        Thread.sleep(30000);

        AdGroupServiceSelector getRequest = buildExampleGetRequest(ApiUtils.ACCOUNT_ID, adGroups);

        List<AdGroupServiceValue> getResponse = get(getRequest);

        int approvalCount = 0;
        for (AdGroupServiceValue adGroupValues : getResponse) {
          if (adGroupValues.getAdGroup().getUrlReviewData().getUrlApprovalStatus() != null) {
            switch (adGroupValues.getAdGroup().getUrlReviewData().getUrlApprovalStatus()) {
              default:
              case REVIEW:
              case APPROVED_WITH_REVIEW:
                continue;
              case DISAPPROVED:
                throw new Exception("AdGroup Review Status failed.");
              case NONE:
              case APPROVED:
                approvalCount++;
            }
          } else {
            throw new Exception("Fail to get AdGroupService.");
          }
        }

        if (getResponse.size() == approvalCount) {
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
   * create AdGroup.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  public static ValuesHolder create() throws Exception {

    ValuesHolder parentValuesHolder = setup();
    ValuesRepositoryFacade parentValuesRepositoryFacade = new ValuesRepositoryFacade(parentValuesHolder);

    long accountId = ApiUtils.ACCOUNT_ID;
    Long campaignIdStandard = parentValuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);
    Long campaignIdMobileApp = parentValuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.MOBILE_APP);
    Long campaignIdDynamicAdsForSearch = parentValuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.DYNAMIC_ADS_FOR_SEARCH);

    // create request.
    AdGroupServiceOperation addRequest = buildExampleMutateRequest(accountId, new ArrayList<AdGroup>() {{
      add(createExampleStandardAdGroup(campaignIdStandard));
      add(createExampleMobileAppIOSAdGroup(campaignIdMobileApp));
      add(createExampleDynamicAdsForSearchAdGroup(campaignIdDynamicAdsForSearch));
    }});

    // run
    List<AdGroupServiceValue> addResponse = mutate(addRequest, "add");

    ValuesHolder selfValuesHolder = new ValuesHolder();
    selfValuesHolder.setBiddingStrategyServiceValueList(parentValuesHolder.getBiddingStrategyServiceValueList());
    selfValuesHolder.setFeedServiceValueList(parentValuesHolder.getFeedServiceValueList());
    selfValuesHolder.setCampaignServiceValueList(parentValuesHolder.getCampaignServiceValueList());
    selfValuesHolder.setAdGroupServiceValueList(addResponse);

    return selfValuesHolder;
  }

}
