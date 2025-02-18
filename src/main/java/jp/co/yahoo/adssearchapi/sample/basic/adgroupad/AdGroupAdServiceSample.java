/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.adgroupad;

import jp.co.yahoo.adssearchapi.sample.basic.adgroup.AdGroupServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v16.api.AdGroupAdServiceApi;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAd;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceAd;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceAdType;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceAppAd;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceApprovalStatus;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceCustomParameter;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceCustomParameters;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceDevicePreference;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceOperation;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceSelector;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceUserStatus;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v16.model.CampaignServiceAppOsType;
import jp.co.yahoo.adssearchapi.v16.model.CampaignServiceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * example AdGroupAd operation and Utility method collection.
 */
public class AdGroupAdServiceSample {

  private static final AdGroupAdServiceApi adGroupAdService = new AdGroupAdServiceApi(ApiUtils.getYahooJapanAdsApiClient());

  /**
   * main method for AdGroupAdServiceSample
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
      Long campaignIdMobileApp = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppOsType.IOS);
      String appIdIOS = valuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileApp);
      Long adGroupIdMobileApp = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdMobileApp);

      // =================================================================
      // AdGroupAdService ADD
      // =================================================================
      // create request.
      AdGroupAdServiceOperation addRequest = buildExampleMutateRequest(accountId, new ArrayList<AdGroupAd>() {{
        add(createExampleAppAdIOS(campaignIdMobileApp, appIdIOS, adGroupIdMobileApp));
      }});

      // run
      List<AdGroupAdServiceValue> addResponse = adGroupAdService.adGroupAdServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequest).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setAdGroupAdServiceValueList(addResponse);

      // =================================================================
      // AdGroupAdService SET
      // =================================================================
      // create request.
      AdGroupAdServiceOperation setRequest = buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds()));

      // run
      adGroupAdService.adGroupAdServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequest);

      // =================================================================
      // AdGroupAdService GET
      // =================================================================
      // create request.
      AdGroupAdServiceSelector adGroupAdSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());

      // run
      adGroupAdService.adGroupAdServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, adGroupAdSelector);

      // =================================================================
      // AdGroupAdService REMOVE
      // =================================================================
      // create request.
      AdGroupAdServiceOperation removeRequest = buildExampleMutateRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());

      //run
      adGroupAdService.adGroupAdServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeRequest);

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
  public static AdGroupAdServiceOperation buildExampleMutateRequest(long accountId, List<AdGroupAd> operand) {
    AdGroupAdServiceOperation operation = new AdGroupAdServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example App Ad IOS request.
   *
   * @param campaignId long
   * @param adGroupId  long
   * @return AdGroupAd
   */
  public static AdGroupAd createExampleAppAdIOS(long campaignId, String appId, long adGroupId) {

    // customParameters
    AdGroupAdServiceCustomParameter customParameter = new AdGroupAdServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    AdGroupAdServiceCustomParameters customParameters = new AdGroupAdServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    // ad
    AdGroupAdServiceAppAd appAd = new AdGroupAdServiceAppAd();
    appAd.setHeadline("sample headline");
    appAd.setDescription("sample ad desc");
    appAd.setDescription2("sample ad desc2");

    AdGroupAdServiceAd ad = new AdGroupAdServiceAd();
    ad.setAdType(AdGroupAdServiceAdType.APP_AD);
    ad.setAppAd(appAd);
    ad.setDevicePreference(AdGroupAdServiceDevicePreference.SMART_PHONE);
    ad.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    ad.setFinalUrl("http://www.apple.com/jp/itunes/app/appname/" + appId);
    ad.setCustomParameters(customParameters);


    AdGroupAd adGroupAd = new AdGroupAd();
    adGroupAd.setCampaignId(campaignId);
    adGroupAd.setAdGroupId(adGroupId);
    adGroupAd.setAdName("SampleAppAdIOS_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroupAd.setUserStatus(AdGroupAdServiceUserStatus.ACTIVE);
    adGroupAd.setAd(ad);

    return adGroupAd;
  }

  /**
   * example App Ad ANDROID request.
   *
   * @param campaignId long
   * @param adGroupId  long
   * @return AdGroupAd
   */
  public static AdGroupAd createExampleAppAdANDROID(long campaignId, String appId, long adGroupId) {

    AdGroupAdServiceAppAd appAd = new AdGroupAdServiceAppAd();
    appAd.setHeadline("sample headline");
    appAd.setDescription("sample ad desc");
    appAd.setDescription2("sample ad desc2");

    // ad
    AdGroupAdServiceAd ad = new AdGroupAdServiceAd();
    ad.setAdType(AdGroupAdServiceAdType.APP_AD);
    ad.setAppAd(appAd);
    ad.setDevicePreference(AdGroupAdServiceDevicePreference.SMART_PHONE);
    ad.setFinalUrl("https://play.google.com/store/apps/details?id=" + appId);


    AdGroupAd adGroupAd = new AdGroupAd();
    adGroupAd.setCampaignId(campaignId);
    adGroupAd.setAdGroupId(adGroupId);
    adGroupAd.setAdName("SampleAppAdANDROID_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroupAd.setUserStatus(AdGroupAdServiceUserStatus.ACTIVE);
    adGroupAd.setAd(ad);

    return adGroupAd;
  }

  /**
   * example adGroupAd set request.
   *
   * @param adGroupAds List<AdGroupAd>
   * @return List<AdGroupAd>
   */
  public static List<AdGroupAd> createExampleSetRequest(List<AdGroupAd> adGroupAds) {
    // create operands
    List<AdGroupAd> operands = new ArrayList<>();

    for (AdGroupAd adGroupAd : adGroupAds) {
      AdGroupAd operand = new AdGroupAd();
      operand.setCampaignId(adGroupAd.getCampaignId());
      operand.setAdGroupId(adGroupAd.getAdGroupId());
      operand.setAdId(adGroupAd.getAdId());
      operand.setAdName("UpdateOn_" + adGroupAd.getAdId() + "_" + ApiUtils.getCurrentTimestamp());
      operand.setUserStatus(AdGroupAdServiceUserStatus.PAUSED);

      operands.add(operand);
    }

    return operands;
  }

  /**
   * example get request.
   *
   * @param accountId  long
   * @param adGroupAds AdGroupAd
   * @return AdGroupAdSelector
   */
  public static AdGroupAdServiceSelector buildExampleGetRequest(long accountId, List<AdGroupAd> adGroupAds) {
    // Set Selector
    AdGroupAdServiceSelector selector = new AdGroupAdServiceSelector();
    selector.setAccountId(accountId);

    List<Long> campaignIds = new ArrayList<>();
    List<Long> adGroupIds = new ArrayList<>();
    List<Long> adIds = new ArrayList<>();
    for (AdGroupAd adGroupAd : adGroupAds) {
      campaignIds.add(adGroupAd.getCampaignId());
      adGroupIds.add(adGroupAd.getAdGroupId());
      adIds.add(adGroupAd.getAdId());
    }
    selector.setCampaignIds(campaignIds);
    selector.setAdGroupIds(adGroupIds);
    selector.setAdIds(adIds);

    selector.setAdTypes(Arrays.asList( //
        AdGroupAdServiceAdType.APP_AD //
    ));
    selector.setUserStatuses(Arrays.asList(AdGroupAdServiceUserStatus.ACTIVE, AdGroupAdServiceUserStatus.PAUSED));
    selector.setApprovalStatuses(Arrays.asList( //
        AdGroupAdServiceApprovalStatus.APPROVED, //
        AdGroupAdServiceApprovalStatus.APPROVED_WITH_REVIEW, //
        AdGroupAdServiceApprovalStatus.REVIEW, //
        AdGroupAdServiceApprovalStatus.PRE_DISAPPROVED, //
        AdGroupAdServiceApprovalStatus.POST_DISAPPROVED //
    ));

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

    return AdGroupServiceSample.create();
  }

  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {

    AdGroupServiceSample.cleanup(valuesHolder);
  }

  /**
   * create basic AdGroupAd.
   *
   * @return valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static ValuesHolder create() throws Exception {

    ValuesHolder parentValuesHolder = setup();
    ValuesRepositoryFacade parentValuesRepositoryFacade = new ValuesRepositoryFacade(parentValuesHolder);

    long accountId = ApiUtils.ACCOUNT_ID;
    Long campaignId = parentValuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);
    Long adGroupId = parentValuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignId);
    Long campaignIdMobileApp = parentValuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppOsType.IOS);
    String appIdIOS = parentValuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileApp);

    // create request.
    AdGroupAdServiceOperation addRequest = buildExampleMutateRequest( //
        accountId, Collections.singletonList(createExampleAppAdIOS(campaignId, appIdIOS, adGroupId)) //
    );

    // run
    List<AdGroupAdServiceValue> addResponse = adGroupAdService.adGroupAdServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequest).getRval().getValues();

    ValuesHolder seflValuesHolder = new ValuesHolder();
    seflValuesHolder.setBiddingStrategyServiceValueList(parentValuesHolder.getBiddingStrategyServiceValueList());
    seflValuesHolder.setPageFeedAssetSetServiceValueList(parentValuesHolder.getPageFeedAssetSetServiceValueList());
    seflValuesHolder.setCampaignServiceValueList(parentValuesHolder.getCampaignServiceValueList());
    seflValuesHolder.setAdGroupServiceValueList(parentValuesHolder.getAdGroupServiceValueList());
    seflValuesHolder.setAdGroupAdServiceValueList(addResponse);

    return seflValuesHolder;
  }

}
