/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.adgroupfeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.basic.feeditem.FeedItemServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v8.api.AdGroupFeedServiceApi;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeed;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServiceList;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v8.model.AdGroupFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v8.model.FeedItemServicePlaceholderType;

/**
 * example AdGroupFeedService operation and Utility method collection.
 */
public class AdGroupFeedServiceSample {

  private static final AdGroupFeedServiceApi adGroupFeedService = new AdGroupFeedServiceApi(ApiUtils.getYahooJapanAdsApiClient());

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
      Long feedItemId = valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItemId(FeedItemServicePlaceholderType.QUICKLINK);

      // =================================================================
      // AdGroupFeedServiceSample SET
      // =================================================================
      // create request.
      List<AdGroupFeedServiceList> adGroupFeedLists = new ArrayList<>();
      adGroupFeedLists.add(createExampleSetRequest(accountId, campaignId, adGroupId, feedItemId, AdGroupFeedServicePlaceholderType.QUICKLINK));
      AdGroupFeedServiceOperation setRequest = buildExampleMutateRequest(accountId, adGroupFeedLists);

      // run
      adGroupFeedService.adGroupFeedServiceSetPost(setRequest);

      // =================================================================
      // AdGroupFeedServiceSample GET
      // =================================================================
      // create request.
      AdGroupFeedServiceSelector getRequest = buildExampleGetRequest(accountId, campaignId, adGroupId, feedItemId);

      // run
      adGroupFeedService.adGroupFeedServiceGetPost(getRequest);

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
  public static AdGroupFeedServiceOperation buildExampleMutateRequest(long accountId, List<AdGroupFeedServiceList> operand) {
    AdGroupFeedServiceOperation operation = new AdGroupFeedServiceOperation();
    operation.setAccountId(accountId);
    operation.getOperand().addAll(operand);

    return operation;
  }

  /**
   * example adGroupFeed set request.
   *
   * @param accountId long
   * @param campaignId long
   * @param adGroupId long
   * @param feedItemId long
   * @param adGroupFeedPlaceholderType AdGroupFeedPlaceholderType
   * @return AdGroupFeedList
   */
  public static AdGroupFeedServiceList createExampleSetRequest(long accountId, long campaignId, long adGroupId, long feedItemId, AdGroupFeedServicePlaceholderType adGroupFeedPlaceholderType) {

    AdGroupFeed adGroupFeed = new AdGroupFeed();
    adGroupFeed.setFeedItemId(feedItemId);

    // Set adGroupFeedList.
    AdGroupFeedServiceList operand = new AdGroupFeedServiceList();
    operand.setAccountId(accountId);
    operand.setCampaignId(campaignId);
    operand.setAdGroupId(adGroupId);
    operand.setPlaceholderType(adGroupFeedPlaceholderType);
    operand.setAdGroupFeed(Collections.singletonList(adGroupFeed));

    return operand;
  }

  /**
   * example get request.
   *
   * @param accountId long
   * @param campaignId long
   * @param adGroupId long
   * @param feedItemId long
   * @return AdGroupFeedSelector
   */
  public static AdGroupFeedServiceSelector buildExampleGetRequest(long accountId, long campaignId, long adGroupId, long feedItemId) {

    AdGroupFeedServiceSelector selector = new AdGroupFeedServiceSelector();
    selector.setAccountId(accountId);
    selector.setCampaignIds(Collections.singletonList(campaignId));
    selector.setAdGroupIds(Collections.singletonList(adGroupId));
    selector.setFeedItemIds(Collections.singletonList(feedItemId));
    selector.setPlaceholderTypes(Arrays.asList( //
        AdGroupFeedServicePlaceholderType.QUICKLINK, //
        AdGroupFeedServicePlaceholderType.CALLEXTENSION, //
        AdGroupFeedServicePlaceholderType.CALLOUT, //
        AdGroupFeedServicePlaceholderType.STRUCTURED_SNIPPET //
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
  public static ValuesHolder setup() throws Exception {
    return FeedItemServiceSample.create();
  }

  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {
    FeedItemServiceSample.cleanup(valuesHolder);
  }

}
