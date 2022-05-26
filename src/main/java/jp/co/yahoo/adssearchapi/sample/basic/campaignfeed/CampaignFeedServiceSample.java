/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaignfeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.basic.feeditem.FeedItemServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v8.api.CampaignFeedServiceApi;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeed;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServiceList;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v8.model.CampaignFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v8.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v8.model.FeedItemServicePlaceholderType;

/**
 * example CampaignFeedService operation and Utility method collection.
 */
public class CampaignFeedServiceSample {

  private static final CampaignFeedServiceApi campaignFeedService = new CampaignFeedServiceApi(ApiUtils.getYahooJapanAdsApiClient());

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
      Long feedItemId = valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItemId(FeedItemServicePlaceholderType.QUICKLINK);

      // =================================================================
      // CampaignFeedServiceSample SET
      // =================================================================
      // create request.
      List<CampaignFeedServiceList> campaignFeedLists = new ArrayList<>();
      campaignFeedLists.add(createExampleSetRequest(accountId, campaignId, feedItemId, CampaignFeedServicePlaceholderType.QUICKLINK));
      CampaignFeedServiceOperation setRequest = buildExampleMutateRequest(accountId, campaignFeedLists);

      // run
      campaignFeedService.campaignFeedServiceSetPost(setRequest);

      // =================================================================
      // CampaignFeedServiceSample GET
      // =================================================================
      // create request.
      CampaignFeedServiceSelector campaignFeedSelector = buildExampleGetRequest(accountId, Collections.singletonList(campaignId), Collections.singletonList(feedItemId));

      // run
      campaignFeedService.campaignFeedServiceGetPost(campaignFeedSelector);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cleanup(valuesHolder);
    }
  }

  /**
   * check & create upper service object.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  private static ValuesHolder setup() throws Exception {
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

  /**
   * example mutate request.
   */
  public static CampaignFeedServiceOperation buildExampleMutateRequest(long accountId, List<CampaignFeedServiceList> operand) {
    CampaignFeedServiceOperation operation = new CampaignFeedServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example campaignFeed set request.
   *
   * @param accountId long
   * @param campaignId long
   * @param feedItemId long
   * @param campaignFeedPlaceholderType CampaignFeedPlaceholderType
   * @return CampaignFeedList
   */
  public static CampaignFeedServiceList createExampleSetRequest(long accountId, long campaignId, long feedItemId, CampaignFeedServicePlaceholderType campaignFeedPlaceholderType) {

    CampaignFeed campaignFeed = new CampaignFeed();
    campaignFeed.setFeedItemId(feedItemId);

    // Set campaignFeedList.
    CampaignFeedServiceList operand = new CampaignFeedServiceList();
    operand.setAccountId(accountId);
    operand.setCampaignId(campaignId);
    operand.setPlaceholderType(campaignFeedPlaceholderType);
    operand.setCampaignFeed(Collections.singletonList(campaignFeed));

    return operand;
  }

  /**
   * example get request.
   *
   * @param accountId long
   * @param campaignIds List<Long>
   * @param feedItemIds List<Long>
   * @return CampaignFeedSelector
   */
  public static CampaignFeedServiceSelector buildExampleGetRequest(long accountId, List<Long> campaignIds, List<Long> feedItemIds) {

    CampaignFeedServiceSelector selector = new CampaignFeedServiceSelector();
    selector.setAccountId(accountId);
    selector.setCampaignIds(campaignIds);
    selector.setFeedItemIds(feedItemIds);
    selector.setPlaceholderTypes(Arrays.asList( //
        CampaignFeedServicePlaceholderType.QUICKLINK, //
        CampaignFeedServicePlaceholderType.CALLEXTENSION, //
        CampaignFeedServicePlaceholderType.CALLOUT, //
        CampaignFeedServicePlaceholderType.STRUCTURED_SNIPPET //
    ));
    selector.setStartIndex(1);
    selector.setNumberResults(20);
    return selector;
  }
}
