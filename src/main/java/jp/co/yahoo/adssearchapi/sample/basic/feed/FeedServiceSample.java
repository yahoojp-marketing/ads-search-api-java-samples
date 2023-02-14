/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.feed;

import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v10.api.FeedServiceApi;
import jp.co.yahoo.adssearchapi.v10.model.Feed;
import jp.co.yahoo.adssearchapi.v10.model.FeedServiceOperation;
import jp.co.yahoo.adssearchapi.v10.model.FeedServiceSelector;
import jp.co.yahoo.adssearchapi.v10.model.FeedServiceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * example FeedService operation and Utility method collection.
 */
public class FeedServiceSample {

  private static final FeedServiceApi feedService = new FeedServiceApi(ApiUtils.getYahooJapanAdsApiClient());

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
      // FeedService GET
      // =================================================================
      // create request.
      FeedServiceSelector feedServiceSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getFeedValueRepository().getFeedIds());

      // run
      feedService.feedServiceGetPost(feedServiceSelector);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static ValuesHolder create() throws Exception {

    ValuesHolder valuesHolder = new ValuesHolder();
    return valuesHolder;
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
