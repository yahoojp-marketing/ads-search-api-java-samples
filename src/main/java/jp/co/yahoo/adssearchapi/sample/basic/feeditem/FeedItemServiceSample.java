/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.feeditem;

import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v12.api.FeedItemServiceApi;
import jp.co.yahoo.adssearchapi.v12.model.FeedItemServiceApprovalStatus;
import jp.co.yahoo.adssearchapi.v12.model.FeedItemServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v12.model.FeedItemServiceSelector;

import java.util.Arrays;
import java.util.List;

/**
 * example FeedItemService operation and Utility method collection.
 */
public class FeedItemServiceSample {

  private static final FeedItemServiceApi feedItemService = new FeedItemServiceApi(ApiUtils.getYahooJapanAdsApiClient());

  /**
   * main method for FeedItemServiceSample
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
      // FeedItemService GET
      // =================================================================
      // create request.
      FeedItemServiceSelector feedItemSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItemIds());

      // run
      feedItemService.feedItemServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, feedItemSelector);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * example get request.
   *
   * @param accountId   long
   * @param feedItemIds Long
   * @return FeedItemSelector
   */
  public static FeedItemServiceSelector buildExampleGetRequest(long accountId, List<Long> feedItemIds) {

    // Set Selector
    FeedItemServiceSelector selector = new FeedItemServiceSelector();
    selector.setAccountId(accountId);

    if (feedItemIds.size() > 0) {
      selector.setFeedItemIds(feedItemIds);
    }

    selector.setPlaceholderTypes(Arrays.asList( //
        FeedItemServicePlaceholderType.AD_CUSTOMIZER //
    ));

    selector.setApprovalStatuses(Arrays.asList( //
        FeedItemServiceApprovalStatus.APPROVED, //
        FeedItemServiceApprovalStatus.APPROVED_WITH_REVIEW, //
        FeedItemServiceApprovalStatus.REVIEW, //
        FeedItemServiceApprovalStatus.PRE_DISAPPROVED, //
        FeedItemServiceApprovalStatus.POST_DISAPPROVED //
    ));

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

}
