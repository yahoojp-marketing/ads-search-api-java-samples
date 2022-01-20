/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.feed;

import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v7.model.Feed;
import jp.co.yahoo.adssearchapi.v7.model.FeedServiceAttribute;
import jp.co.yahoo.adssearchapi.v7.model.FeedServiceGetResponse;
import jp.co.yahoo.adssearchapi.v7.model.FeedServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v7.model.FeedServiceOperation;
import jp.co.yahoo.adssearchapi.v7.model.FeedServicePlaceholderField;
import jp.co.yahoo.adssearchapi.v7.model.FeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v7.model.FeedServiceSelector;
import jp.co.yahoo.adssearchapi.v7.model.FeedServiceValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * example FeedService operation and Utility method collection.
 */
public class FeedServiceSample {

  private static final String SERVICE_NAME = "FeedService";

  /**
   * main method for FeedServiceSample
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {
    // =================================================================
    // Setting
    // =================================================================
    ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(new ValuesHolder());
    long accountId = ApiUtils.ACCOUNT_ID;

    try {
      // =================================================================
      // FeedService ADD
      // =================================================================
      // create request.
      FeedServiceOperation addFeedServiceOperation = buildExampleMutateRequest(accountId, new ArrayList<Feed>() {{
        add(createExampleAdCustomizerFeed(accountId));
        add(createExampleDynamicAdForSearchFeed(accountId));
      }});

      // run
      List<FeedServiceValue> feedServiceValue = mutate(addFeedServiceOperation, "add");
      valuesRepositoryFacade.getValuesHolder().setFeedServiceValueList(feedServiceValue);

      // =================================================================
      // FeedService SET
      // =================================================================
      // create request.
      FeedServiceOperation setFeedServiceOperation = buildExampleMutateRequest( accountId, createExampleSetRequest(valuesRepositoryFacade.getFeedValueRepository().getFeeds()));

      // run
      mutate(setFeedServiceOperation, "set");

      // =================================================================
      // FeedService GET
      // =================================================================
      // create request.
      FeedServiceSelector feedServiceSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getFeedValueRepository().getFeedIds());

      // run
      get(feedServiceSelector, "get");

      // =================================================================
      // FeedService REMOVE
      // =================================================================
      // create request.
      FeedServiceOperation removeFeedServiceOperation = buildExampleMutateRequest(accountId, valuesRepositoryFacade.getFeedValueRepository().getFeeds());

      // run
      mutate(removeFeedServiceOperation, "remove");

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static ValuesHolder create() throws Exception {

    ValuesHolder valuesHolder = new ValuesHolder();
    long accountId = ApiUtils.ACCOUNT_ID;

    FeedServiceOperation addFeedOperation = buildExampleMutateRequest(accountId, new ArrayList<Feed>() {{
      add(createExampleAdCustomizerFeed(accountId));
      add(createExampleDynamicAdForSearchFeed(accountId));
    }});

    // Run
    List<FeedServiceValue> feedServiceValue = mutate(addFeedOperation, "add");
    valuesHolder.setFeedServiceValueList(feedServiceValue);
    return valuesHolder;
  }

  public static void cleanup(ValuesHolder valuesHolder) throws Exception {

    long accountId = ApiUtils.ACCOUNT_ID;
    if (valuesHolder.getFeedServiceValueList().size() == 0) {
      return;
    }
    ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);

    FeedServiceOperation removeFeedOperation =
      buildExampleMutateRequest(accountId, valuesRepositoryFacade.getFeedValueRepository().getFeeds());

    mutate(removeFeedOperation, "remove");
  }

  /**
   * example mutate request.
   */
  public static FeedServiceOperation buildExampleMutateRequest(long accountId, List<Feed> operand) {
    FeedServiceOperation operation = new FeedServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example AdCustomizer request.
   *
   * @param accountId long
   * @return Feed
   */
  private static Feed createExampleAdCustomizerFeed(long accountId) {

    FeedServiceAttribute feedAttributeInteger = new FeedServiceAttribute();
    feedAttributeInteger.setFeedAttributeName("SampleInteger_" + ApiUtils.getCurrentTimestamp());
    feedAttributeInteger.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_INTEGER);

    FeedServiceAttribute feedAttributePrice = new FeedServiceAttribute();
    feedAttributePrice.setFeedAttributeName("SamplePrice_" + ApiUtils.getCurrentTimestamp());
    feedAttributePrice.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_PRICE);

    FeedServiceAttribute feedAttributeDate = new FeedServiceAttribute();
    feedAttributeDate.setFeedAttributeName("SampleDate_" + ApiUtils.getCurrentTimestamp());
    feedAttributeDate.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_DATE);

    FeedServiceAttribute feedAttributeString = new FeedServiceAttribute();
    feedAttributeString.setFeedAttributeName("SampleString_" + ApiUtils.getCurrentTimestamp());
    feedAttributeString.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_STRING);

    Feed feed = new Feed();
    feed.setAccountId(accountId);
    feed.setFeedName("SampleAdCustomizerFeed_" + ApiUtils.getCurrentTimestamp());
    feed.setPlaceholderType(FeedServicePlaceholderType.AD_CUSTOMIZER);
    feed.setFeedAttribute(Arrays.asList(feedAttributeInteger, feedAttributePrice, feedAttributeDate, feedAttributeString));

    return feed;
  }

  /**
   * example DynamicAdForSearch request.
   *
   * @param accountId long
   * @return Feed
   */
  private static Feed createExampleDynamicAdForSearchFeed(long accountId) {

    Feed feed = new Feed();
    feed.setAccountId(accountId);
    feed.setFeedName("SampleDASFeed_" + ApiUtils.getCurrentTimestamp());
    feed.setPlaceholderType(FeedServicePlaceholderType.DYNAMIC_AD_FOR_SEARCH_PAGE_FEEDS);
    feed.setDomain("https://www.yahoo.co.jp");

    return feed;
  }

  /**
   * example mutate FeedService.
   *
   * @param operation FeedService
   * @return FeedServiceValue
   */
  public static List<FeedServiceValue> mutate(FeedServiceOperation operation, String action) throws Exception {

    FeedServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, FeedServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * Sample Program for FeedService GET.
   *
   * @param selector FeedSelector
   * @return FeedValue
   */
  public static List<FeedServiceValue> get(FeedServiceSelector selector, String action) throws Exception {

    FeedServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, action, selector, FeedServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example feedHolders set request.
   *
   * @return List<Feed>
   */
  public static List<Feed> createExampleSetRequest(List<Feed> feeds) {
    List<Feed> operands = new ArrayList<>();

    for (Feed feed : feeds) {

      // for AdCustomizer
      if (feed.getPlaceholderType() == FeedServicePlaceholderType.AD_CUSTOMIZER) {

        // Set FeedAttribute
        FeedServiceAttribute feedAttributeInteger = new FeedServiceAttribute();
        feedAttributeInteger.setFeedAttributeName("SampleInteger2_" + ApiUtils.getCurrentTimestamp());
        feedAttributeInteger.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_INTEGER);

        FeedServiceAttribute feedAttributePrice = new FeedServiceAttribute();
        feedAttributePrice.setFeedAttributeName("SamplePrice2_" + ApiUtils.getCurrentTimestamp());
        feedAttributePrice.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_PRICE);

        FeedServiceAttribute feedAttributeDate = new FeedServiceAttribute();
        feedAttributeDate.setFeedAttributeName("SampleDate2_" + ApiUtils.getCurrentTimestamp());
        feedAttributeDate.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_DATE);

        FeedServiceAttribute feedAttributeString = new FeedServiceAttribute();
        feedAttributeString.setFeedAttributeName("SampleString2_" + ApiUtils.getCurrentTimestamp());
        feedAttributeString.setPlaceholderField(FeedServicePlaceholderField.AD_CUSTOMIZER_STRING);

        // Set Operand
        Feed operand = new Feed();
        operand.setAccountId(feed.getAccountId());
        operand.setFeedId(feed.getFeedId());
        operand.setPlaceholderType(feed.getPlaceholderType());
        operand.setFeedAttribute(Arrays.asList(feedAttributeInteger, feedAttributePrice, feedAttributeDate, feedAttributeString));

        operands.add(operand);
      }
    }
    return operands;
  }

  /**
   * example get request.
   *
   * @param accountId     long
   * @param feedIds List<Long>
   * @return FeedSelector
   */
  public static FeedServiceSelector buildExampleGetRequest(long accountId, List<Long> feedIds) {
    // Set Selector
    FeedServiceSelector selector = new FeedServiceSelector();
    selector.setAccountId(accountId);

    if (feedIds.size() > 0) {
      selector.setFeedIds(feedIds);
    }

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

}
