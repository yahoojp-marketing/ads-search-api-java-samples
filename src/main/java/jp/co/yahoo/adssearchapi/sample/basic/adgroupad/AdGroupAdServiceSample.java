/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.adgroupad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import jp.co.yahoo.adssearchapi.sample.basic.adgroup.AdGroupServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAd;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceAd;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceAdType;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceAdditionalAdvancedMobileUrls;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceAdditionalAdvancedUrls;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceAppAd;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceApprovalStatus;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceCustomParameter;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceCustomParameters;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceDevicePreference;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceExtendedTextAd;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceGetResponse;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceOperation;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceSelector;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceUserStatus;
import jp.co.yahoo.adssearchapi.v4.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v4.model.CampaignServiceAppStore;
import jp.co.yahoo.adssearchapi.v4.model.CampaignServiceType;


/**
 * example AdGroupAd operation and Utility method collection.
 */
public class AdGroupAdServiceSample {

  private static final String SERVICE_NAME = "AdGroupAdService";

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
      Long campaignIdStandard = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);
      Long campaignIdMobileApp = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppStore.IOS);
      Long campaignIdDynamicAdsForSearch = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.DYNAMIC_ADS_FOR_SEARCH);
      String appIdIOS = valuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileApp);
      Long adGroupIdStandard = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdStandard);
      Long adGroupIdMobileApp = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdMobileApp);
      Long adGroupIdDynamicAdsForSearch = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdDynamicAdsForSearch);

      // =================================================================
      // AdGroupAdService ADD
      // =================================================================
      // create request.
      AdGroupAdServiceOperation addRequest = buildExampleMutateRequest(accountId, new ArrayList<AdGroupAd>() {{
        add(createExampleExtendedTextAd(campaignIdStandard, adGroupIdStandard));
        add(createExampleAppAdIOS(campaignIdMobileApp, appIdIOS, adGroupIdMobileApp));
        add(createExampleDynamicSearchLinkedAd(campaignIdDynamicAdsForSearch, adGroupIdDynamicAdsForSearch));
      }});

      // run
      List<AdGroupAdServiceValue> addResponse = mutate(addRequest, "add");
      valuesRepositoryFacade.getValuesHolder().setAdGroupAdServiceValueList(addResponse);

      // =================================================================
      // AdGroupAdService SET
      // =================================================================
      // create request.
      AdGroupAdServiceOperation setRequest = buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds()));

      // run
      mutate(setRequest, "set");

      // =================================================================
      // AdGroupAdService GET
      // =================================================================
      // create request.
      AdGroupAdServiceSelector adGroupAdSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());

      // run
      get(adGroupAdSelector);

      // =================================================================
      // AdGroupAdService REMOVE
      // =================================================================
      // create request.
      AdGroupAdServiceOperation removeRequest = buildExampleMutateRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());

      //run
      mutate(removeRequest, "remove");

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
    operation.getOperand().addAll(operand);

    return operation;
  }

  /**
   * example ExtendedText Ad request.
   *
   * @param campaignId long
   * @param adGroupId  long
   * @return AdGroupAd
   */
  public static AdGroupAd createExampleExtendedTextAd(long campaignId, long adGroupId) {

    // advanced url
    AdGroupAdServiceAdditionalAdvancedUrls additionalAdvancedUrls1 = new AdGroupAdServiceAdditionalAdvancedUrls();
    additionalAdvancedUrls1.setAdvancedUrl("http://www1.yahoo.co.jp");
    AdGroupAdServiceAdditionalAdvancedUrls additionalAdvancedUrls2 = new AdGroupAdServiceAdditionalAdvancedUrls();
    additionalAdvancedUrls2.setAdvancedUrl("http://www2.yahoo.co.jp");
    AdGroupAdServiceAdditionalAdvancedUrls additionalAdvancedUrls3 = new AdGroupAdServiceAdditionalAdvancedUrls();
    additionalAdvancedUrls3.setAdvancedUrl("http://www3.yahoo.co.jp");

    // advanced mobile url
    AdGroupAdServiceAdditionalAdvancedMobileUrls adGroupAdAdditionalAdvancedMobileUrls1 = new AdGroupAdServiceAdditionalAdvancedMobileUrls();
    adGroupAdAdditionalAdvancedMobileUrls1.setAdvancedMobileUrl("http://www1.yahoo.co.jp/mobile");
    AdGroupAdServiceAdditionalAdvancedMobileUrls adGroupAdAdditionalAdvancedMobileUrls2 = new AdGroupAdServiceAdditionalAdvancedMobileUrls();
    adGroupAdAdditionalAdvancedMobileUrls2.setAdvancedMobileUrl("http://www2.yahoo.co.jp/mobile");
    AdGroupAdServiceAdditionalAdvancedMobileUrls adGroupAdAdditionalAdvancedMobileUrls3 = new AdGroupAdServiceAdditionalAdvancedMobileUrls();
    adGroupAdAdditionalAdvancedMobileUrls3.setAdvancedMobileUrl("http://www3.yahoo.co.jp/mobile");

    // customParameters
    AdGroupAdServiceCustomParameter customParameter = new AdGroupAdServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    AdGroupAdServiceCustomParameters customParameters = new AdGroupAdServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    // ad
    AdGroupAdServiceExtendedTextAd extendedTextAd = new AdGroupAdServiceExtendedTextAd();
    extendedTextAd.setHeadline2("sample headline2");
    extendedTextAd.setPath1("path1");
    extendedTextAd.setPath2("path2");


    AdGroupAdServiceAd ad = new AdGroupAdServiceAd();
    ad.setAdType(AdGroupAdServiceAdType.EXTENDED_TEXT_AD);
    ad.setExtendedTextAd(extendedTextAd);
    ad.setHeadline1("sample headline");
    ad.setDescription1("sample ad desc");
    ad.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    ad.setAdvancedUrl("http://www.yahoo.co.jp");
    ad.setAdditionalAdvancedUrls(Arrays.asList(additionalAdvancedUrls1, additionalAdvancedUrls2, additionalAdvancedUrls3));
    ad.setAdvancedMobileUrl("http://www.yahoo.co.jp/mobile");
    ad.setAdditionalAdvancedMobileUrls(Arrays.asList(adGroupAdAdditionalAdvancedMobileUrls1, adGroupAdAdditionalAdvancedMobileUrls2, adGroupAdAdditionalAdvancedMobileUrls3));
    ad.setCustomParameters(customParameters);


    AdGroupAd adGroupAd = new AdGroupAd();
    adGroupAd.setCampaignId(campaignId);
    adGroupAd.setAdGroupId(adGroupId);
    adGroupAd.setAdName("SampleExtendedTextAd_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroupAd.setUserStatus(AdGroupAdServiceUserStatus.ACTIVE);
    adGroupAd.setAd(ad);

    return adGroupAd;
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
    appAd.setDescription2("sample ad desc2");

    AdGroupAdServiceAd ad = new AdGroupAdServiceAd();
    ad.setAdType(AdGroupAdServiceAdType.APP_AD);
    ad.setAppAd(appAd);
    ad.setHeadline1("sample headline");
    ad.setDescription1("sample ad desc");
    ad.setDevicePreference(AdGroupAdServiceDevicePreference.SMART_PHONE);
    ad.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    ad.setAdvancedUrl("http://www.apple.com/jp/itunes/app/appname/" + appId);
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
    appAd.setDescription2("sample ad desc2");

    // ad
    AdGroupAdServiceAd ad = new AdGroupAdServiceAd();
    ad.setAdType(AdGroupAdServiceAdType.APP_AD);
    ad.setAppAd(appAd);
    ad.setHeadline1("sample headline");
    ad.setDescription1("sample ad desc");
    ad.setDevicePreference(AdGroupAdServiceDevicePreference.SMART_PHONE);
    ad.setAdvancedUrl("https://play.google.com/store/apps/details?id=" + appId);


    AdGroupAd adGroupAd = new AdGroupAd();
    adGroupAd.setCampaignId(campaignId);
    adGroupAd.setAdGroupId(adGroupId);
    adGroupAd.setAdName("SampleAppAdANDROID_CreateOn_" + ApiUtils.getCurrentTimestamp());
    adGroupAd.setUserStatus(AdGroupAdServiceUserStatus.ACTIVE);
    adGroupAd.setAd(ad);

    return adGroupAd;
  }

  /**
   * example AdCustomizer request.
   *
   * @param campaignId         long
   * @param adGroupId          long
   * @param feedFolderName     String
   * @param feedAttributeNames Map
   * @return AdGroupAd
   */
  public static List<AdGroupAd> createExampleAdCustomizerAds(long campaignId, long adGroupId, String feedFolderName, Map<String, String> feedAttributeNames) {

    List<AdGroupAd> adGroupAds = new ArrayList<>();

    // example KeywordInsertion
    AdGroupAd keywordInsertion = createExampleExtendedTextAd(campaignId, adGroupId);
    keywordInsertion.setAdName("KeywordInsertion_" + ApiUtils.getCurrentTimestamp());
    keywordInsertion.getAd().setDescription1("sample {KEYWORD:keyword}");
    adGroupAds.add(keywordInsertion);

    // example CountdownOption
    AdGroupAd countdownOption = createExampleExtendedTextAd(campaignId, adGroupId);
    countdownOption.setAdName("SampleCountdownOption_" + ApiUtils.getCurrentTimestamp());
    countdownOption.getAd().setDescription1("{=COUNTDOWN(\"2020/12/15 18:00:00\",\"ja\")}");
    adGroupAds.add(countdownOption);

    // example CountdownOption & AdCustomizerDate
    AdGroupAd countdownOptionAdOfAdCustomizer = createExampleExtendedTextAd(campaignId, adGroupId);
    countdownOptionAdOfAdCustomizer.setAdName("CountdownOfAdCustomizer_" + ApiUtils.getCurrentTimestamp());
    countdownOptionAdOfAdCustomizer.getAd().setDescription1("{=COUNTDOWN(" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_DATE") + ",\"ja\")}");
    adGroupAds.add(countdownOptionAdOfAdCustomizer);

    // example DefaultText & AdCustomizerString
    AdGroupAd defaultTextOfAdCustomizer = createExampleExtendedTextAd(campaignId, adGroupId);
    defaultTextOfAdCustomizer.setAdName("DefaultTextOfAdCustomizer_" + ApiUtils.getCurrentTimestamp());
    defaultTextOfAdCustomizer.getAd().setHeadline1("{=" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_STRING") + ":default}headline");
    defaultTextOfAdCustomizer.getAd().getExtendedTextAd().setHeadline2("{=" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_STRING") + ":default}headline2");
    defaultTextOfAdCustomizer.getAd().setDescription1("{=" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_STRING") + ":default}Description");
    adGroupAds.add(defaultTextOfAdCustomizer);

    // example Mobile specification with IF function
    AdGroupAd ifFunction = createExampleExtendedTextAd(campaignId, adGroupId);
    ifFunction.setAdName("IF_Function_" + ApiUtils.getCurrentTimestamp());
    ifFunction.getAd().setHeadline1("{=IF(device=mobile,MOBILE):PC}Headline");
    ifFunction.getAd().getExtendedTextAd().setHeadline2("{=IF(device=mobile,MOBILE):PC}Headline2");
    ifFunction.getAd().setDescription1("{=IF(device=mobile,MOBILE):PC}Description");
    adGroupAds.add(ifFunction);

    // example Mobile specification with IF function & DefaultText AdCustomizerString
    AdGroupAd ifFunctionDefaultTextOfAdCustomizer = createExampleExtendedTextAd(campaignId, adGroupId);
    ifFunctionDefaultTextOfAdCustomizer.setAdName("IF_Function_DefaultTextOfAdCustomizer_" + ApiUtils.getCurrentTimestamp());
    ifFunctionDefaultTextOfAdCustomizer.getAd().setHeadline1("{=IF(device=mobile,MOBILE):PC}test {=" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_STRING") + ":default}headline");
    ifFunctionDefaultTextOfAdCustomizer.getAd().getExtendedTextAd().setHeadline2("{=IF(device=mobile,MOBILE):PC}test {=" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_STRING") + ":default}headline2");
    ifFunctionDefaultTextOfAdCustomizer.getAd().setDescription1("{=IF(device=mobile,MOBILE):PC}test {=" + feedFolderName + "." + feedAttributeNames.get("AD_CUSTOMIZER_STRING") + ":default}description");
    adGroupAds.add(ifFunctionDefaultTextOfAdCustomizer);

    return adGroupAds;
  }

  /**
   * example DynamicSearchLinkedAd request.
   *
   * @param campaignId long
   * @param adGroupId  long
   * @return AdGroupAd
   */
  private static AdGroupAd createExampleDynamicSearchLinkedAd(long campaignId, long adGroupId) {

    // customParameters
    AdGroupAdServiceCustomParameter customParameter = new AdGroupAdServiceCustomParameter();
    customParameter.setKey("id1");
    customParameter.setValue("1234");
    AdGroupAdServiceCustomParameters customParameters = new AdGroupAdServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));

    // ad
    AdGroupAdServiceAd ad = new AdGroupAdServiceAd();
    ad.setAdType(AdGroupAdServiceAdType.DYNAMIC_SEARCH_LINKED_AD);
    ad.setDescription1("sample ad desc");
    ad.setDevicePreference(AdGroupAdServiceDevicePreference.SMART_PHONE);
    ad.setTrackingUrl("http://www.yahoo.co.jp/?url={lpurl}&amp;a={creative}&amp;pid={_id1}");
    ad.setCustomParameters(customParameters);


    AdGroupAd adGroupAd = new AdGroupAd();
    adGroupAd.setCampaignId(campaignId);
    adGroupAd.setAdGroupId(adGroupId);
    adGroupAd.setAdName("SampleDynamicSearchLinkedAd_CreateOn_" + ApiUtils.getCurrentTimestamp());
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
      AdGroupAdServiceAdType.APP_AD, //
      AdGroupAdServiceAdType.EXTENDED_TEXT_AD, //
      AdGroupAdServiceAdType.DYNAMIC_SEARCH_LINKED_AD //
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
   * example mutate adGroupAds.
   *
   * @param operation AdGroupAdServiceOperation
   * @return AdGroupAdValues
   */
  public static List<AdGroupAdServiceValue> mutate(AdGroupAdServiceOperation operation, String action) throws Exception {

    AdGroupAdServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, AdGroupAdServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * Sample Program for AdGroupAdService GET.
   *
   * @param adGroupAdSelector AdGroupAdSelector
   * @return AdGroupAdValues
   * @throws Exception throw exception
   */
  public static List<AdGroupAdServiceValue> get(AdGroupAdServiceSelector adGroupAdSelector) throws Exception {

    AdGroupAdServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, "get", adGroupAdSelector, AdGroupAdServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
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

    // create request.
    AdGroupAdServiceOperation addRequest = buildExampleMutateRequest( //
      accountId, Collections.singletonList(createExampleExtendedTextAd(campaignId, adGroupId)) //
    );

    // run
    List<AdGroupAdServiceValue> addResponse = mutate(addRequest, "add");

    ValuesHolder seflValuesHolder = new ValuesHolder();
    seflValuesHolder.setBiddingStrategyServiceValueList(parentValuesHolder.getBiddingStrategyServiceValueList());
    seflValuesHolder.setFeedServiceValueList(parentValuesHolder.getFeedServiceValueList());
    seflValuesHolder.setCampaignServiceValueList(parentValuesHolder.getCampaignServiceValueList());
    seflValuesHolder.setAdGroupServiceValueList(parentValuesHolder.getAdGroupServiceValueList());
    seflValuesHolder.setAdGroupAdServiceValueList(addResponse);

    return seflValuesHolder;
  }

}
