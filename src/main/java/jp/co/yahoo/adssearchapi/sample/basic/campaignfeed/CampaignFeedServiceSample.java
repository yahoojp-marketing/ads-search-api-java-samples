/**
 * Copyright (C) 2019 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaignfeed;

import jp.co.yahoo.adssearchapi.sample.basic.feeditem.FeedItemServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeed;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServiceGetResponse;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServiceList;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServiceOperation;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServiceSelector;
import jp.co.yahoo.adssearchapi.v1.model.CampaignFeedServiceValue;
import jp.co.yahoo.adssearchapi.v1.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v1.model.FeedItemServicePlaceholderType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * example CampaignFeedService operation and Utility method collection.
 */
public class CampaignFeedServiceSample {

  private static final String SERVICE_NAME = "CampaignFeedService";

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
      mutate(setRequest, "set");

      // =================================================================
      // CampaignFeedServiceSample GET
      // =================================================================
      // create request.
      CampaignFeedServiceSelector campaignFeedSelector = buildExampleGetRequest(accountId, Collections.singletonList(campaignId), Collections.singletonList(feedItemId));

      // run
      get(campaignFeedSelector, "get");

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
   * example mutate campaignFeed.
   *
   * @param operation CampaignFeedOperation
   * @return CampaignFeedValues
   */
  public static List<CampaignFeedServiceValue> mutate(CampaignFeedServiceOperation operation, String action) throws Exception {

    CampaignFeedServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, CampaignFeedServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * Sample Program for CampaignFeedService GET.
   *
   * @param selector CampaignFeedSelector
   * @return CampaignFeedValues
   */
  public static List<CampaignFeedServiceValue> get(CampaignFeedServiceSelector selector, String action) throws Exception {

    CampaignFeedServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, action, selector, CampaignFeedServiceGetResponse.class);

    return response.getRval().getValues();
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
