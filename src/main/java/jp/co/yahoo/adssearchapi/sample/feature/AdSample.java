/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.basic.adgroup.AdGroupServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupad.AdGroupAdServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupbidmultiplier.AdGroupBidMultiplierServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupcriterion.AdGroupCriterionServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.adgroupfeed.AdGroupFeedServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.biddingstrategy.BiddingStrategyServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaign.CampaignServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaigncriterion.CampaignCriterionServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaignfeed.CampaignFeedServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.campaigntarget.CampaignTargetServiceSample;
import jp.co.yahoo.adssearchapi.sample.basic.feeditem.FeedItemServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v8.YahooJapanAdsApiClient;
import jp.co.yahoo.adssearchapi.v8.api.AdGroupAdServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.AdGroupBidMultiplierServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.AdGroupCriterionServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.AdGroupFeedServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.AdGroupServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.BiddingStrategyServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.CampaignCriterionServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.CampaignFeedServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.CampaignServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.CampaignTargetServiceApi;
import jp.co.yahoo.adssearchapi.v8.api.FeedItemServiceApi;
import jp.co.yahoo.adssearchapi.v8.model.AdGroup;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupAd;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupAdServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupAdServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupBidMultiplierServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupBidMultiplierServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupCriterion;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupCriterionServiceUse;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.BiddingStrategyServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.BiddingStrategyServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v8.model.BiddingStrategyServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.Campaign;
import jp.co.yahoo.adssearchapi.v8.model.CampaignCriterion;
import jp.co.yahoo.adssearchapi.v8.model.CampaignCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.CampaignCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.CampaignCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.CampaignServiceAppStore;
import jp.co.yahoo.adssearchapi.v8.model.CampaignServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v8.model.CampaignServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.CampaignTarget;
import jp.co.yahoo.adssearchapi.v8.model.CampaignTargetServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.CampaignTargetServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.CampaignTargetServiceValue;
import jp.co.yahoo.adssearchapi.v8.model.FeedItemServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.FeedItemServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v8.model.FeedItemServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.FeedItemServiceValue;

/**
 * example Ad operation and Utility method collection.
 */
public class AdSample {

  private static final YahooJapanAdsApiClient yahooJapanAdsApiClient = ApiUtils.getYahooJapanAdsApiClient();
  private static final AdGroupAdServiceApi adGroupAdService = new AdGroupAdServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupBidMultiplierServiceApi adGroupBidMultiplierService = new AdGroupBidMultiplierServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupCriterionServiceApi adGroupCriterionService = new AdGroupCriterionServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupFeedServiceApi adGroupFeedService = new AdGroupFeedServiceApi(yahooJapanAdsApiClient);
  private static final AdGroupServiceApi adGroupService = new AdGroupServiceApi(yahooJapanAdsApiClient);
  private static final BiddingStrategyServiceApi biddingStrategyService = new BiddingStrategyServiceApi(yahooJapanAdsApiClient);
  private static final CampaignCriterionServiceApi campaignCriterionService = new CampaignCriterionServiceApi(yahooJapanAdsApiClient);
  private static final CampaignFeedServiceApi campaignFeedService = new CampaignFeedServiceApi(yahooJapanAdsApiClient);
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
    long accountId = ApiUtils.ACCOUNT_ID;

    try {

      // =================================================================
      // BiddingStrategyService
      // =================================================================
      // ADD
      BiddingStrategyServiceOperation addRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleMutateRequest(
          accountId,
          Arrays.asList(BiddingStrategyServiceSample.createExampleMaximizeClicksBidding())
      );
      List<BiddingStrategyServiceValue> addResponseBiddingStrategy = biddingStrategyService.biddingStrategyServiceAddPost(addRequestBiddingStrategy).getRval().getValues();
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
      biddingStrategyService.biddingStrategyServiceSetPost(setRequestBiddingStrategy);

      // GET
      BiddingStrategyServiceSelector getRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleGetRequest(
          accountId,
          valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategyIds()
      );
      biddingStrategyService.biddingStrategyServiceGetPost(getRequestBiddingStrategy);

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
                CampaignServiceSample.createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId))
            );
            add(CampaignServiceSample.createExampleMobileAppCampaignForIOS(
                "SampleMobileAppCampaignForIOS_",
                CampaignServiceSample.createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId))
            );
            add(CampaignServiceSample.createExampleMobileAppCampaignForANDROID(
                "SampleMobileAppCampaignForANDROID_",
                CampaignServiceSample.createPortfolioBiddingCampaignBiddingStrategy(biddingStrategyId))
            );
          }}
      );
      List<CampaignServiceValue> addResponseCampaign = campaignService.campaignServiceAddPost(addRequestCampaign).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setCampaignServiceValueList(addResponseCampaign);

      Long campaignIdStandard = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);
      Long campaignIdMobileAppIOS = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppStore.IOS);
      String appIdIOS = valuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileAppIOS);
      Long campaignIdMobileAppAndroid = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceAppStore.ANDROID);
      String appIdAndroid = valuesRepositoryFacade.getCampaignValuesRepository().findAppId(campaignIdMobileAppAndroid);

      // Check Status
      CampaignServiceSample.checkStatus(valuesRepositoryFacade.getCampaignValuesRepository().getCampaignIds());

      // SET
      CampaignServiceOperation setRequestCampaign = CampaignServiceSample.buildExampleMutateRequest(
          accountId,
          CampaignServiceSample.createExampleSetRequest(valuesRepositoryFacade.getCampaignValuesRepository().getCampaigns())
      );
      campaignService.campaignServiceSetPost(setRequestCampaign);

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
      List<CampaignTargetServiceValue> addResponseCampaignTarget = campaignTargetService.campaignTargetServiceAddPost(addRequestCampaignTarget).getRval().getValues();
      List<CampaignTarget> campaignTargets = new ArrayList<>();
      for (CampaignTargetServiceValue values : addResponseCampaignTarget ) {
        campaignTargets.add(values.getCampaignTarget());
      }

      // SET
      CampaignTargetServiceOperation setRequestCampaignTarget = CampaignTargetServiceSample.buildExampleMutateRequest(
          accountId,
          CampaignTargetServiceSample.createExampleSetRequest(campaignTargets)
      );
      campaignTargetService.campaignTargetServiceSetPost(setRequestCampaignTarget);

      // GET
      CampaignTargetServiceSelector getRequestCampaignTarget = CampaignTargetServiceSample.buildExampleGetRequest(accountId, campaignTargets);
      campaignTargetService.campaignTargetServiceGetPost(getRequestCampaignTarget);

      // =================================================================
      // CampaignCriterionService
      // =================================================================
      // ADD
      CampaignCriterionServiceOperation addRequestCampaignCriterion = CampaignCriterionServiceSample.buildExampleMutateRequest(
          accountId, Arrays.asList(CampaignCriterionServiceSample.createExampleNegativeCampaignCriterion(campaignIdStandard))
      );
      List<CampaignCriterionServiceValue> addResponseCampaignCriterion = campaignCriterionService.campaignCriterionServiceAddPost(addRequestCampaignCriterion).getRval().getValues();
      List<CampaignCriterion> campaignCriterions = new ArrayList<>();
      for (CampaignCriterionServiceValue values : addResponseCampaignCriterion ) {
        campaignCriterions.add(values.getCampaignCriterion());
      }

      // GET
      CampaignCriterionServiceSelector getRequestCampaignCriterion = CampaignCriterionServiceSample.buildExampleGetRequest(accountId, campaignCriterions);
      campaignCriterionService.campaignCriterionServiceGetPost(getRequestCampaignCriterion);

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
      List<AdGroupServiceValue> addResponseAdGroup = adGroupService.adGroupServiceAddPost(addRequestAdGroup).getRval().getValues();
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
      adGroupService.adGroupServiceSetPost(setRequestAdGroup);

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
      List<AdGroupCriterionServiceValue> addResponseAdGroupCriterion = adGroupCriterionService.adGroupCriterionServiceAddPost(addRequestAdGroupCriterion).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setAdGroupCriterionServiceValueList(addResponseAdGroupCriterion);

      // GET
      AdGroupCriterionServiceSelector getRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleGetRequest(
          accountId, AdGroupCriterionServiceUse.BIDDABLE, valuesRepositoryFacade.getAdGroupCriterionValuesRepository().getAdGroupCriterions()
      );
      adGroupCriterionService.adGroupCriterionServiceGetPost(getRequestAdGroupCriterion);

      // SET
      AdGroupCriterionServiceOperation setRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupCriterionServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupCriterionValuesRepository().getAdGroupCriterions())
      );
      adGroupCriterionService.adGroupCriterionServiceSetPost(setRequestAdGroupCriterion);

      // =================================================================
      // AdGroupBidMultiplierService
      // =================================================================
      // SET
      AdGroupBidMultiplierServiceOperation setRequestAdGroupBidMultiplier = AdGroupBidMultiplierServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupBidMultiplierServiceSample.createExampleSetRequest(campaignIdStandard, adGroupIdStandard)
      );
      adGroupBidMultiplierService.adGroupBidMultiplierServiceSetPost(setRequestAdGroupBidMultiplier);

      // GET
      AdGroupBidMultiplierServiceSelector getRequestAdGroupBidMultiplier = AdGroupBidMultiplierServiceSample.buildExampleGetRequest(accountId, campaignIdStandard, adGroupIdStandard);
      adGroupBidMultiplierService.adGroupBidMultiplierServiceGetPost(getRequestAdGroupBidMultiplier);

      // =================================================================
      // AdGroupAdService
      // =================================================================
      // ADD
      AdGroupAdServiceOperation addRequestAdGroupAd = AdGroupAdServiceSample.buildExampleMutateRequest(
          accountId,
          new ArrayList<AdGroupAd>() {{
            add(AdGroupAdServiceSample.createExampleExtendedTextAd(campaignIdStandard, adGroupIdStandard));
            add(AdGroupAdServiceSample.createExampleAppAdIOS(campaignIdMobileAppIOS, appIdIOS, adGroupIdMobileAppIOS));
            add(AdGroupAdServiceSample.createExampleAppAdANDROID(campaignIdMobileAppAndroid, appIdAndroid, adGroupIdMobileAppAndroid));
          }}
      );
      List<AdGroupAdServiceValue> addResponseAdGroupAd = adGroupAdService.adGroupAdServiceAddPost(addRequestAdGroupAd).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setAdGroupAdServiceValueList(addResponseAdGroupAd);

      // SET
      AdGroupAdServiceOperation setRequestAdGroupAd = AdGroupAdServiceSample.buildExampleMutateRequest(
          accountId,
          AdGroupAdServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds())
      );
      adGroupAdService.adGroupAdServiceSetPost(setRequestAdGroupAd);

      // GET
      AdGroupAdServiceSelector getRequestAdGroupAd = AdGroupAdServiceSample.buildExampleGetRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());
      adGroupAdService.adGroupAdServiceGetPost(getRequestAdGroupAd);

      // =================================================================
      // FeedItemService
      // =================================================================
      // ADD
      FeedItemServiceOperation addRequestFeedItem = FeedItemServiceSample.buildExampleMutateRequest(
          accountId,
          FeedItemServicePlaceholderType.QUICKLINK,
          Arrays.asList(FeedItemServiceSample.createExampleQuicklink())
      );
      List<FeedItemServiceValue> addResponseFeedItem = feedItemService.feedItemServiceAddPost(addRequestFeedItem).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setFeedItemServiceValueList(addResponseFeedItem);

      Long feedItemId = valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItemId(FeedItemServicePlaceholderType.QUICKLINK);

      // GET
      FeedItemServiceSelector getRequestFeedItem = FeedItemServiceSample.buildExampleGetRequest(accountId, valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItemIds());
      feedItemService.feedItemServiceGetPost(getRequestFeedItem);

      // SET
      FeedItemServiceOperation setRequestFeedItem = FeedItemServiceSample.buildExampleMutateRequest(
          accountId,
          FeedItemServicePlaceholderType.QUICKLINK,
          FeedItemServiceSample.createExampleSetRequest(valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItems())
      );
      feedItemService.feedItemServiceSetPost(setRequestFeedItem);

      // =================================================================
      // CampaignFeedService
      // =================================================================
      // SET
      CampaignFeedServiceOperation setRequestCampaignFeed = CampaignFeedServiceSample.buildExampleMutateRequest(
          accountId,
          Arrays.asList(CampaignFeedServiceSample.createExampleSetRequest(accountId, campaignIdStandard, feedItemId, CampaignFeedServicePlaceholderType.QUICKLINK))
      );
      campaignFeedService.campaignFeedServiceSetPost(setRequestCampaignFeed);

      // GET
      CampaignFeedServiceSelector getRequestCampaignFeed = CampaignFeedServiceSample.buildExampleGetRequest(accountId, Collections.singletonList(campaignIdStandard), Collections.singletonList(feedItemId));
      campaignFeedService.campaignFeedServiceGetPost(getRequestCampaignFeed);

      // =================================================================
      // AdGroupFeedService
      // =================================================================
      // SET
      AdGroupFeedServiceOperation setRequestAdGroupFeed = AdGroupFeedServiceSample.buildExampleMutateRequest(
          accountId,
          Arrays.asList(AdGroupFeedServiceSample.createExampleSetRequest(accountId, campaignIdStandard, adGroupIdStandard, feedItemId, AdGroupFeedServicePlaceholderType.QUICKLINK))
      );
      adGroupFeedService.adGroupFeedServiceSetPost(setRequestAdGroupFeed);

      // GET
      AdGroupFeedServiceSelector getRequestAdGroupFeed = AdGroupFeedServiceSample.buildExampleGetRequest(accountId, campaignIdStandard, adGroupIdStandard, feedItemId);
      adGroupFeedService.adGroupFeedServiceGetPost(getRequestAdGroupFeed);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;

    } finally {
      FeedItemServiceSample.cleanup(valuesHolder);
    }
  }
}
