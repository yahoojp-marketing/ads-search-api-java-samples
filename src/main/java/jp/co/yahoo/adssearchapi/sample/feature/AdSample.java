/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.feature;

import jp.co.yahoo.adssearchapi.sample.basic.adgroup.AdGroupServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupad.AdGroupAdServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupbidmultiplier.AdGroupBidMultiplierServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupcriterion.AdGroupCriterionServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.biddingstrategy.BiddingStrategyServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaign.CampaignServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaigncriterion.CampaignCriterionServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaigntarget.CampaignTargetServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v12.YahooJapanAdsApiClient;
import jp.co.yahoo.adssearchapi.v12.api.AdGroupAdServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.AdGroupBidMultiplierServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.AdGroupCriterionServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.AdGroupServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.BiddingStrategyServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.CampaignCriterionServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.CampaignServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.CampaignTargetServiceApi;
import jp.co.yahoo.adssearchapi.v12.api.FeedItemServiceApi;
import jp.co.yahoo.adssearchapi.v12.model.AdGroup;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupAd;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupAdServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupAdServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupBidMultiplierServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupBidMultiplierServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupCriterion;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupCriterionServiceUse;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.AdGroupServiceValue;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceValue;
import jp.co.yahoo.adssearchapi.v12.model.Campaign;
import jp.co.yahoo.adssearchapi.v12.model.CampaignCriterion;
import jp.co.yahoo.adssearchapi.v12.model.CampaignCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.CampaignCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.CampaignCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v12.model.CampaignServiceAppOsType;
import jp.co.yahoo.adssearchapi.v12.model.CampaignServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v12.model.CampaignServiceValue;
import jp.co.yahoo.adssearchapi.v12.model.CampaignTarget;
import jp.co.yahoo.adssearchapi.v12.model.CampaignTargetServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.CampaignTargetServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.CampaignTargetServiceValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * example Ad operation and Utility method collection.
 */
public class AdSample {

  private static final YahooJapanAdsApiClient yahooJapanAdsApiClient = ApiUtils.getYahooJapanAdsApiClient();
  private static final AdGroupAdServiceApi adGroupAdService = new AdGroupAdServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupBidMultiplierServiceApi adGroupBidMultiplierService = new AdGroupBidMultiplierServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupCriterionServiceApi adGroupCriterionService = new AdGroupCriterionServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupServiceApi adGroupService = new AdGroupServiceApi(yahooJapanAdsApiClient);
  private static final BiddingStrategyServiceApi biddingStrategyService = new BiddingStrategyServiceApi(yahooJapanAdsApiClient);
  private static final CampaignCriterionServiceApi campaignCriterionService = new CampaignCriterionServiceApi(yahooJapanAdsApiClient);
  private static final CampaignServiceApi campaignService = new CampaignServiceApi(yahooJapanAdsApiClient);
  private static final CampaignTargetServiceApi campaignTargetService = new CampaignTargetServiceApi(yahooJapanAdsApiClient);
  private static final FeedItemServiceApi feedItemService= new FeedItemServiceApi(yahooJapanAdsApiClient);

  /**
   * main method for AdSample
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {

    // =================================================================
    // Setting
    // =================================================================
    ValuesHolder valuesHolder = new ValuesHolder();
    ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);
    Long feedId = valuesRepositoryFacade.getPageFeedAssetSetValuesRepository().findPageFeedAssetSetId();

    long accountId = ApiUtils.ACCOUNT_ID;

    List<Long> feedIds = new ArrayList<>();
    feedIds.add(feedId);

    try {

      // =================================================================
      // BiddingStrategyService
      // =================================================================
      // ADD
      BiddingStrategyServiceOperation addRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleMutateRequest(
          accountId,
          Arrays.asList(BiddingStrategyServiceSample.createExampleMaximizeClicksBidding())
      );
      List<BiddingStrategyServiceValue> addResponseBiddingStrategy = biddingStrategyService.biddingStrategyServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestBiddingStrategy).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setBiddingStrategyServiceValueList(addResponseBiddingStrategy);

      Long biddingStrategyId = valuesRepositoryFacade.getBiddingStrategyValuesRepository().findBiddingStrategyId(BiddingStrategyServiceType.MAXIMIZE_CLICKS);

      // sleep 30 second.
      System.out.println("\n***** sleep 30 seconds *****\n");
      Thread.sleep(30000);

      // SET
      BiddingStrategyServiceOperation setRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleMutateRequest(
          accountId,
          BiddingStrategyServiceSample.createExampleSetRequest(valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies())
      );
      biddingStrategyService.biddingStrategyServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestBiddingStrategy);

      // GET
      BiddingStrategyServiceSelector getRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleGetRequest(
          accountId,
          valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategyIds()
      );
      biddingStrategyService.biddingStrategyServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, getRequestBiddingStrategy);

      // sleep 30 second.
      System.out.println("\n***** sleep 30 seconds *****\n");
      Thread.sleep(30000);

      // =================================================================
      // CampaignService
      // =================================================================
      // ADD
      CampaignServiceOperation addRequestCampaign = CampaignServiceSample.buildExampleMutateRequest(
          accountId,
          new ArrayList<Campaign>() {{
            add(CampaignServiceSample.createExampleStandardCampaign(
                "SampleStandardCampaign_",
                feedIds,
                CampaignServiceSample.createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId))
            );
            add(CampaignServiceSample.createExampleMobileAppCampaignForIOS(
                "SampleMobileAppCampaignForIOS_",
                feedIds,
                CampaignServiceSample.createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId))
            );
            add(CampaignServiceSample.createExampleMobileAppCampaignForANDROID(
                "SampleMobileAppCampaignForANDROID_",
                feedIds,
                CampaignServiceSample.createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId))
            );
          }}
      );
      List<CampaignServiceValue> addResponseCampaign = campaignService.campaignServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestCampaign).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setCampaignServiceValueList(addResponseCampaign);

      Long campaignIdStandard = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);
      Long campaignIdMobileAppIOS = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppOsType.IOS);
      String appIdIOS = valuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileAppIOS);
      Long campaignIdMobileAppAndroid = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppOsType.ANDROID);
      String appIdAndroid = valuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileAppAndroid);

      // Check Status
      CampaignServiceSample.checkStatus(valuesRepositoryFacade.getCampaignValuesRepository().getCampaignIds());

      // SET
      CampaignServiceOperation setRequestCampaign = CampaignServiceSample.buildExampleMutateRequest(
          accountId,
          CampaignServiceSample.createExampleSetRequest(valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns())
      );
      campaignService.campaignServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestCampaign);

      // =================================================================
      // CampaignTargetService
      // =================================================================
      // ADD
      CampaignTargetServiceOperation addRequestCampaignTarget = CampaignTargetServiceSample.buildExampleMutateRequest(
          accountId,
          new ArrayList<CampaignTarget>() {{
            add(CampaignTargetServiceSample.createExampleScheduleTarget(accountId, campaignIdStandard));
            add(CampaignTargetServiceSample.createExampleLocationTarget(accountId, campaignIdStandard));
            add(CampaignTargetServiceSample.createExampleNetworkTarget(accountId, campaignIdStandard));
          }}
      );
      List<CampaignTargetServiceValue> addResponseCampaignTarget = campaignTargetService.campaignTargetServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestCampaignTarget).getRval().getValues();
      List<CampaignTarget> campaignTargets = new ArrayList<>();
      for (CampaignTargetServiceValue values : addResponseCampaignTarget ) {
        campaignTargets.add(values.getCampaignTarget());
      }

      // SET
      CampaignTargetServiceOperation setRequestCampaignTarget = CampaignTargetServiceSample.buildExampleMutateRequest(
          accountId,
          CampaignTargetServiceSample.createExampleSetRequest(campaignTargets)
      );
      campaignTargetService.campaignTargetServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestCampaignTarget);

      // GET
      CampaignTargetServiceSelector getRequestCampaignTarget = CampaignTargetServiceSample.buildExampleGetRequest(accountId, campaignTargets);
      campaignTargetService.campaignTargetServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, getRequestCampaignTarget);

      // =================================================================
      // CampaignCriterionService
      // =================================================================
      // ADD
      CampaignCriterionServiceOperation addRequestCampaignCriterion = CampaignCriterionServiceSample.buildExampleMutateRequest(
          accountId, Arrays.asList(CampaignCriterionServiceSample.createExampleNegativeCampaignCriterion(campaignIdStandard))
      );
      List<CampaignCriterionServiceValue> addResponseCampaignCriterion = campaignCriterionService.campaignCriterionServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestCampaignCriterion).getRval().getValues();
      List<CampaignCriterion> campaignCriterions = new ArrayList<>();
      for (CampaignCriterionServiceValue values : addResponseCampaignCriterion ) {
        campaignCriterions.add(values.getCampaignCriterion());
      }

      // GET
      CampaignCriterionServiceSelector getRequestCampaignCriterion = CampaignCriterionServiceSample.buildExampleGetRequest(accountId, campaignCriterions);
      campaignCriterionService.campaignCriterionServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, getRequestCampaignCriterion);

      // =================================================================
      // AdGroupService
      // =================================================================
      // ADD
      AdGroupServiceOperation addRequestAdGroup = AdGroupServiceSample.buildExampleMutateRequest(
          accountId,
          new ArrayList<AdGroup>() {{
            add(AdGroupServiceSample.createExampleStandardAdGroup(campaignIdStandard));
            add(AdGroupServiceSample.createExampleMobileAppIOSAdGroup(campaignIdMobileAppIOS));
            add(AdGroupServiceSample.createExampleMobileAppANDROIDAdGroup(campaignIdMobileAppAndroid));
          }}
      );
      List<AdGroupServiceValue> addResponseAdGroup = adGroupService.adGroupServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestAdGroup).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setAdGroupServiceValueList(addResponseAdGroup);

      Long adGroupIdStandard = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdStandard);
      Long adGroupIdMobileAppIOS = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdMobileAppIOS);
      Long adGroupIdMobileAppAndroid = valuesRepositoryFacade.getAdGroupValuesRepository().findAdGroupId(campaignIdMobileAppAndroid);

      // Check Status
      AdGroupServiceSample.checkStatus(valuesRepositoryFacade.getAdGroupValuesRepository().getAdGroups());

      // SET
      AdGroupServiceOperation setRequestAdGroup= AdGroupServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupValuesRepository().getAdGroups())
      );
      adGroupService.adGroupServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestAdGroup);

      // =================================================================
      // AdGroupCriterionService
      // =================================================================
      // ADD
      AdGroupCriterionServiceOperation addRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleMutateRequest(
          accountId,
          new ArrayList<AdGroupCriterion>() {{
            add(AdGroupCriterionServiceSample.createExampleBiddableAdGroupCriterion(campaignIdStandard, adGroupIdStandard));
          }}
      );
      List<AdGroupCriterionServiceValue> addResponseAdGroupCriterion = adGroupCriterionService.adGroupCriterionServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestAdGroupCriterion).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setAdGroupCriterionServiceValueList(addResponseAdGroupCriterion);

      // GET
      AdGroupCriterionServiceSelector getRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleGetRequest(
          accountId, AdGroupCriterionServiceUse.BIDDABLE, valuesRepositoryFacade.getAdGroupCriterionValuesRepository().getAdGroupCriterions()
      );
      adGroupCriterionService.adGroupCriterionServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, getRequestAdGroupCriterion);

      // SET
      AdGroupCriterionServiceOperation setRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupCriterionServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupCriterionValuesRepository().getAdGroupCriterions())
      );
      adGroupCriterionService.adGroupCriterionServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestAdGroupCriterion);

      // =================================================================
      // AdGroupBidMultiplierService
      // =================================================================
      // SET
      AdGroupBidMultiplierServiceOperation setRequestAdGroupBidMultiplier = AdGroupBidMultiplierServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupBidMultiplierServiceSample.createExampleSetRequest(campaignIdStandard, adGroupIdStandard)
      );
      adGroupBidMultiplierService.adGroupBidMultiplierServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestAdGroupBidMultiplier);

      // GET
      AdGroupBidMultiplierServiceSelector getRequestAdGroupBidMultiplier = AdGroupBidMultiplierServiceSample.buildExampleGetRequest(accountId, campaignIdStandard, adGroupIdStandard);
      adGroupBidMultiplierService.adGroupBidMultiplierServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, getRequestAdGroupBidMultiplier);

      // =================================================================
      // AdGroupAdService
      // =================================================================
      // ADD
      AdGroupAdServiceOperation addRequestAdGroupAd = AdGroupAdServiceSample.buildExampleMutateRequest(
          accountId,
          new ArrayList<AdGroupAd>() {{
            add(AdGroupAdServiceSample.createExampleAppAdIOS(campaignIdMobileAppIOS, appIdIOS, adGroupIdMobileAppIOS));
            add(AdGroupAdServiceSample.createExampleAppAdANDROID(campaignIdMobileAppAndroid, appIdAndroid, adGroupIdMobileAppAndroid));
          }}
      );
      List<AdGroupAdServiceValue> addResponseAdGroupAd = adGroupAdService.adGroupAdServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addRequestAdGroupAd).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setAdGroupAdServiceValueList(addResponseAdGroupAd);

      // SET
      AdGroupAdServiceOperation setRequestAdGroupAd = AdGroupAdServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupAdServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds())
      );
      adGroupAdService.adGroupAdServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setRequestAdGroupAd);

      // GET
      AdGroupAdServiceSelector getRequestAdGroupAd = AdGroupAdServiceSample.buildExampleGetRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());
      adGroupAdService.adGroupAdServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, getRequestAdGroupAd);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;

    }
  }
}
