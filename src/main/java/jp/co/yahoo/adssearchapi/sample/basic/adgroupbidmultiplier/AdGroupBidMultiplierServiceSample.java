/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.adgroupbidmultiplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.basic.adgroup.AdGroupServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v16.api.AdGroupBidMultiplierServiceApi;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupBidMultiplier;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupBidMultiplierServiceOperation;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupBidMultiplierServicePlatformType;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupBidMultiplierServiceSelector;
import jp.co.yahoo.adssearchapi.v16.model.AdGroupBidMultiplierServiceValue;
import jp.co.yahoo.adssearchapi.v16.model.CampaignServiceType;

/**
 * example AdGroupBidMultiplierService operation and Utility method collection.
 */
public class AdGroupBidMultiplierServiceSample {

  private static final AdGroupBidMultiplierServiceApi adGroupBidMultiplierService = new AdGroupBidMultiplierServiceApi(ApiUtils.getYahooJapanAdsApiClient());

  /**
   * main method for AdGroupBidMultiplierServiceSample
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
      Long adGroupId = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignId);

      // =================================================================
      // AdGroupBidMultiplierService SET
      // =================================================================
      // create request.
      AdGroupBidMultiplierServiceOperation setRequest = buildExampleMutateRequest(accountId, createExampleSetRequest(campaignId, adGroupId));

      // run
      List<AdGroupBidMultiplierServiceValue> setResponse = adGroupBidMultiplierService.adGroupBidMultiplierServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequest).getRval().getValues();

      List<AdGroupBidMultiplier> adGroupBidMultipliers = new ArrayList<>();
      for (AdGroupBidMultiplierServiceValue adGroupBidMultiplierValue: setResponse) {
        adGroupBidMultipliers.add(adGroupBidMultiplierValue.getAdGroupBidMultiplier());
      }

      // =================================================================
      // AdGroupBidMultiplierService GET
      // =================================================================
      // create request.
      AdGroupBidMultiplierServiceSelector adGroupBidMultiplierSelector = buildExampleGetRequest(accountId, campaignId, adGroupId);

      // run
      adGroupBidMultiplierService.adGroupBidMultiplierServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, adGroupBidMultiplierSelector);

      // =================================================================
      // AdGroupBidMultiplierService REMOVE
      // =================================================================
      // create request.
      AdGroupBidMultiplierServiceOperation removeRequest = buildExampleMutateRequest(accountId, adGroupBidMultipliers);

      // run
      adGroupBidMultiplierService.adGroupBidMultiplierServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeRequest);

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
  public static AdGroupBidMultiplierServiceOperation buildExampleMutateRequest(long accountId, List<AdGroupBidMultiplier> operand) {
    AdGroupBidMultiplierServiceOperation operation = new AdGroupBidMultiplierServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example adGroupCriterion set request.
   *
   * @param campaignId Long
   * @param adGroupId Long
   * @return List<AdGroupBidMultiplier>
   */
  public static List<AdGroupBidMultiplier> createExampleSetRequest(Long campaignId, Long adGroupId) {

    AdGroupBidMultiplier smartPhone = new AdGroupBidMultiplier();
    smartPhone.setCampaignId(campaignId);
    smartPhone.setAdGroupId(adGroupId);
    smartPhone.setPlatformType(AdGroupBidMultiplierServicePlatformType.SMART_PHONE);
    smartPhone.setBidMultiplier(3.2);

    AdGroupBidMultiplier tablet = new AdGroupBidMultiplier();
    tablet.setCampaignId(campaignId);
    tablet.setAdGroupId(adGroupId);
    tablet.setPlatformType(AdGroupBidMultiplierServicePlatformType.TABLET);
    tablet.setBidMultiplier(5.2);

    AdGroupBidMultiplier desktop = new AdGroupBidMultiplier();
    desktop.setCampaignId(campaignId);
    desktop.setAdGroupId(adGroupId);
    desktop.setPlatformType(AdGroupBidMultiplierServicePlatformType.DESKTOP);
    desktop.setBidMultiplier(9.2);

    return Arrays.asList(smartPhone, tablet, desktop);
  }

  /**
   * create get request.
   *
   * @param accountId long
   * @param campaignId long
   * @param adGroupId long
   * @return AdGroupBidMultiplierSelector
   */
  public static AdGroupBidMultiplierServiceSelector buildExampleGetRequest(long accountId, long campaignId, long adGroupId) {
    // Set Selector
    AdGroupBidMultiplierServiceSelector selector = new AdGroupBidMultiplierServiceSelector();
    selector.setAccountId(accountId);
    selector.setCampaignIds(Collections.singletonList(campaignId));
    selector.setAdGroupIds(Collections.singletonList(adGroupId));
    selector.setPlatformTypes(Arrays.asList(AdGroupBidMultiplierServicePlatformType.SMART_PHONE, AdGroupBidMultiplierServicePlatformType.TABLET, AdGroupBidMultiplierServicePlatformType.DESKTOP));

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

}
