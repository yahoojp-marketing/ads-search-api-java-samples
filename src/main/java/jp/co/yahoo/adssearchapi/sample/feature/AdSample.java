/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.feature;

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
import jp.co.yahoo.adssearchapi.v2.model.AdGroup;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupAd;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupAdServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupAdServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupBidMultiplierServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupBidMultiplierServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupCriterion;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupCriterionServiceUse;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.AdGroupServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.BiddingStrategyServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.BiddingStrategyServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v2.model.BiddingStrategyServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.Campaign;
import jp.co.yahoo.adssearchapi.v2.model.CampaignCriterion;
import jp.co.yahoo.adssearchapi.v2.model.CampaignCriterionServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.CampaignCriterionServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.CampaignCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.CampaignFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.CampaignFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v2.model.CampaignFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.CampaignServiceAppStore;
import jp.co.yahoo.adssearchapi.v2.model.CampaignServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v2.model.CampaignServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.CampaignTarget;
import jp.co.yahoo.adssearchapi.v2.model.CampaignTargetServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.CampaignTargetServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.CampaignTargetServiceValue;
import jp.co.yahoo.adssearchapi.v2.model.FeedItemServiceOperation;
import jp.co.yahoo.adssearchapi.v2.model.FeedItemServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v2.model.FeedItemServiceSelector;
import jp.co.yahoo.adssearchapi.v2.model.FeedItemServiceValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * example Ad operation and Utility method collection.
 */
public class AdSample {

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
        Arrays.asList(BiddingStrategyServiceSample.createExampleTargetSpendBidding())
      );
      List<BiddingStrategyServiceValue> addResponseBiddingStrategy = BiddingStrategyServiceSample.mutate(addRequestBiddingStrategy, "add");
      valuesRepositoryFacade.getValuesHolder().setBiddingStrategyServiceValueList(addResponseBiddingStrategy);

      Long biddingStrategyId = valuesRepositoryFacade.getBiddingStrategyValuesRepository().findBiddingStrategyId(BiddingStrategyServiceType.TARGET_SPEND);

      // sleep 30 second.
      System.out.println("\n***** sleep 30 seconds *****\n");
      Thread.sleep(30000);

      // SET
      BiddingStrategyServiceOperation setRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleMutateRequest(
        accountId,
        BiddingStrategyServiceSample.createExampleSetRequest(valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies())
      );
      BiddingStrategyServiceSample.mutate(setRequestBiddingStrategy, "set");

      // GET
      BiddingStrategyServiceSelector getRequestBiddingStrategy = BiddingStrategyServiceSample.buildExampleGetRequest(
        accountId,
        valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategyIds()
      );
      BiddingStrategyServiceSample.get(getRequestBiddingStrategy);

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
      List<CampaignServiceValue> addResponseCampaign = CampaignServiceSample.mutate(addRequestCampaign, "add");
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
      CampaignServiceSample.mutate(setRequestCampaign, "set");

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
      List<CampaignTargetServiceValue> addResponseCampaignTarget = CampaignTargetServiceSample.mutate(addRequestCampaignTarget, "add");
      List<CampaignTarget> campaignTargets = new ArrayList<>();
      for (CampaignTargetServiceValue values : addResponseCampaignTarget ) {
        campaignTargets.add(values.getCampaignTarget());
      }

      // SET
      CampaignTargetServiceOperation setRequestCampaignTarget = CampaignTargetServiceSample.buildExampleMutateRequest(
        accountId,
        CampaignTargetServiceSample.createExampleSetRequest(campaignTargets)
      );
      CampaignTargetServiceSample.mutate(setRequestCampaignTarget, "set");

      // GET
      CampaignTargetServiceSelector getRequestCampaignTarget = CampaignTargetServiceSample.buildExampleGetRequest(accountId, campaignTargets);
      CampaignTargetServiceSample.get(getRequestCampaignTarget, "get");

      // =================================================================
      // CampaignCriterionService
      // =================================================================
      // ADD
      CampaignCriterionServiceOperation addRequestCampaignCriterion = CampaignCriterionServiceSample.buildExampleMutateRequest(
        accountId, Arrays.asList(CampaignCriterionServiceSample.createExampleNegativeCampaignCriterion(campaignIdStandard))
      );
      List<CampaignCriterionServiceValue> addResponseCampaignCriterion = CampaignCriterionServiceSample.mutate(addRequestCampaignCriterion, "add");
      List<CampaignCriterion> campaignCriterions = new ArrayList<>();
      for (CampaignCriterionServiceValue values : addResponseCampaignCriterion ) {
        campaignCriterions.add(values.getCampaignCriterion());
      }

      // GET
      CampaignCriterionServiceSelector getRequestCampaignCriterion = CampaignCriterionServiceSample.buildExampleGetRequest(accountId, campaignCriterions);
      CampaignCriterionServiceSample.get(getRequestCampaignCriterion, "get");

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
      List<AdGroupServiceValue> addResponseAdGroup = AdGroupServiceSample.mutate(addRequestAdGroup, "add");
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
      AdGroupServiceSample.mutate(setRequestAdGroup, "set");

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
      List<AdGroupCriterionServiceValue> addResponseAdGroupCriterion = AdGroupCriterionServiceSample.mutate(addRequestAdGroupCriterion, "add");
      valuesRepositoryFacade.getValuesHolder().setAdGroupCriterionServiceValueList(addResponseAdGroupCriterion);

      // GET
      AdGroupCriterionServiceSelector getRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleGetRequest(
        accountId, AdGroupCriterionServiceUse.BIDDABLE, valuesRepositoryFacade.getAdGroupCriterionValuesRepository().getAdGroupCriterions()
      );
      AdGroupCriterionServiceSample.get(getRequestAdGroupCriterion);

      // SET
      AdGroupCriterionServiceOperation setRequestAdGroupCriterion = AdGroupCriterionServiceSample.buildExampleMutateRequest(
        accountId,
        AdGroupCriterionServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupCriterionValuesRepository().getAdGroupCriterions())
      );
      AdGroupCriterionServiceSample.mutate(setRequestAdGroupCriterion, "set");

      // =================================================================
      // AdGroupBidMultiplierService
      // =================================================================
      // SET
      AdGroupBidMultiplierServiceOperation setRequestAdGroupBidMultiplier = AdGroupBidMultiplierServiceSample.buildExampleMutateRequest(
        accountId,
        AdGroupBidMultiplierServiceSample.createExampleSetRequest(campaignIdStandard, adGroupIdStandard)
      );
      AdGroupBidMultiplierServiceSample.mutate(setRequestAdGroupBidMultiplier, "set");

      // GET
      AdGroupBidMultiplierServiceSelector getRequestAdGroupBidMultiplier = AdGroupBidMultiplierServiceSample.buildExampleGetRequest(accountId, campaignIdStandard, adGroupIdStandard);
      AdGroupBidMultiplierServiceSample.get(getRequestAdGroupBidMultiplier);

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
      List<AdGroupAdServiceValue> addResponseAdGroupAd = AdGroupAdServiceSample.mutate(addRequestAdGroupAd, "add");
      valuesRepositoryFacade.getValuesHolder().setAdGroupAdServiceValueList(addResponseAdGroupAd);

      // SET
      AdGroupAdServiceOperation setRequestAdGroupAd = AdGroupAdServiceSample.buildExampleMutateRequest(
        accountId,
        AdGroupAdServiceSample.createExampleSetRequest(valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds())
      );
      AdGroupAdServiceSample.mutate(setRequestAdGroupAd, "set");

      // GET
      AdGroupAdServiceSelector getRequestAdGroupAd = AdGroupAdServiceSample.buildExampleGetRequest(accountId, valuesRepositoryFacade.getAdGroupAdValuesRepository().getAdGroupAds());
      AdGroupAdServiceSample.get(getRequestAdGroupAd);

      // =================================================================
      // FeedItemService
      // =================================================================
      // ADD
      FeedItemServiceOperation addRequestFeedItem = FeedItemServiceSample.buildExampleMutateRequest(
        accountId,
        FeedItemServicePlaceholderType.QUICKLINK,
        Arrays.asList(FeedItemServiceSample.createExampleQuicklink())
      );
      List<FeedItemServiceValue> addResponseFeedItem = FeedItemServiceSample.mutate(addRequestFeedItem, "add");
      valuesRepositoryFacade.getValuesHolder().setFeedItemServiceValueList(addResponseFeedItem);

      Long feedItemId = valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItemId(FeedItemServicePlaceholderType.QUICKLINK);

      // GET
      FeedItemServiceSelector getRequestFeedItem = FeedItemServiceSample.buildExampleGetRequest(accountId, valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItemIds());
      FeedItemServiceSample.get(getRequestFeedItem, "get");

      // SET
      FeedItemServiceOperation setRequestFeedItem = FeedItemServiceSample.buildExampleMutateRequest(
        accountId,
        FeedItemServicePlaceholderType.QUICKLINK,
        FeedItemServiceSample.createExampleSetRequest(valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItems())
      );
      FeedItemServiceSample.mutate(setRequestFeedItem, "set");

      // =================================================================
      // CampaignFeedService
      // =================================================================
      // SET
      CampaignFeedServiceOperation setRequestCampaignFeed = CampaignFeedServiceSample.buildExampleMutateRequest(
        accountId,
        Arrays.asList(CampaignFeedServiceSample.createExampleSetRequest(accountId, campaignIdStandard, feedItemId, CampaignFeedServicePlaceholderType.QUICKLINK))
      );
      CampaignFeedServiceSample.mutate(setRequestCampaignFeed, "set");

      // GET
      CampaignFeedServiceSelector getRequestCampaignFeed = CampaignFeedServiceSample.buildExampleGetRequest(accountId, Collections.singletonList(campaignIdStandard), Collections.singletonList(feedItemId));
      CampaignFeedServiceSample.get(getRequestCampaignFeed, "get");

      // =================================================================
      // AdGroupFeedService
      // =================================================================
      // SET
      AdGroupFeedServiceOperation setRequestAdGroupFeed = AdGroupFeedServiceSample.buildExampleMutateRequest(
        accountId,
        Arrays.asList(AdGroupFeedServiceSample.createExampleSetRequest(accountId, campaignIdStandard, adGroupIdStandard, feedItemId, AdGroupFeedServicePlaceholderType.QUICKLINK))
      );
      AdGroupFeedServiceSample.mutate(setRequestAdGroupFeed, "set");

      // GET
      AdGroupFeedServiceSelector getRequestAdGroupFeed = AdGroupFeedServiceSample.buildExampleGetRequest(accountId, campaignIdStandard, adGroupIdStandard, feedItemId);
      AdGroupFeedServiceSample.get(getRequestAdGroupFeed);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;

    } finally {
      FeedItemServiceSample.cleanup(valuesHolder);
    }
  }
}
