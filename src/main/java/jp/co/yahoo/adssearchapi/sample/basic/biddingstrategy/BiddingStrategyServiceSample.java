/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.biddingstrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v12.api.BiddingStrategyServiceApi;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategy;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceBiddingScheme;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceMaximizeClicksBiddingScheme;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceOperation;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceSelector;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceTargetCpaBiddingScheme;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceTargetRoasBiddingScheme;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v12.model.BiddingStrategyServiceValue;

/**
 * example BiddingStrategyService operation and Utility method collection.
 */
public class BiddingStrategyServiceSample {

  private static final BiddingStrategyServiceApi biddingStrategyService = new BiddingStrategyServiceApi(ApiUtils.getYahooJapanAdsApiClient());

  /**
   * main method for BiddingStrategyServiceSample
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
      // BiddingStrategyService::ADD
      // =================================================================
      // create request.
      BiddingStrategyServiceOperation addBiddingStrategyOperation = buildExampleMutateRequest(accountId, Collections.singletonList(createExampleTargetCpaBidding()));

      // run
      List<BiddingStrategyServiceValue> biddingStrategyValues = biddingStrategyService.biddingStrategyServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addBiddingStrategyOperation).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setBiddingStrategyServiceValueList(biddingStrategyValues);

      // =================================================================
      // BiddingStrategyService::SET
      // =================================================================
      // create request.
      BiddingStrategyServiceOperation setBiddingStrategyOperation =
          buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies()));

      // run
      biddingStrategyService.biddingStrategyServiceSetPost(ApiUtils.BASE_ACCOUNT_ID, setBiddingStrategyOperation);

      // =================================================================
      // BiddingStrategyService::GET
      // =================================================================
      // create request.
      BiddingStrategyServiceSelector biddingStrategySelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategyIds());

      // run
      biddingStrategyService.biddingStrategyServiceGetPost(ApiUtils.BASE_ACCOUNT_ID, biddingStrategySelector);

      // =================================================================
      // BiddingStrategyService::REMOVE
      // =================================================================
      // create request.
      BiddingStrategyServiceOperation removeBiddingStrategyOperation =
          buildExampleMutateRequest(accountId, valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies());

      // run
      biddingStrategyService.biddingStrategyServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeBiddingStrategyOperation);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * create basic BiddingStrategy.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  public static ValuesHolder create() throws Exception {

    ValuesHolder valuesHolder = new ValuesHolder();
    long accountId = ApiUtils.ACCOUNT_ID;
    BiddingStrategyServiceOperation addBiddingStrategyOperation = buildExampleMutateRequest(accountId, Collections.singletonList(createExampleTargetCpaBidding()));

    // Run
    List<BiddingStrategyServiceValue> biddingStrategyValues = biddingStrategyService.biddingStrategyServiceAddPost(ApiUtils.BASE_ACCOUNT_ID, addBiddingStrategyOperation).getRval().getValues();
    valuesHolder.setBiddingStrategyServiceValueList(biddingStrategyValues);
    return valuesHolder;
  }

  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {

    long accountId = ApiUtils.ACCOUNT_ID;
    if (valuesHolder.getBiddingStrategyServiceValueList().size() == 0) {
      return;
    }
    ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);

    BiddingStrategyServiceOperation removeBiddingStrategyOperation =
        buildExampleMutateRequest(accountId, valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies());

    // Run
    biddingStrategyService.biddingStrategyServiceRemovePost(ApiUtils.BASE_ACCOUNT_ID, removeBiddingStrategyOperation);
  }

  /**
   * example mutate request.
   */
  public static BiddingStrategyServiceOperation buildExampleMutateRequest(long accountId, List<BiddingStrategy> operand) {
    BiddingStrategyServiceOperation operation = new BiddingStrategyServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example TargetCpaBidding request.
   *
   * @return BiddingStrategy
   */
  private static BiddingStrategy createExampleTargetCpaBidding() {
    BiddingStrategyServiceBiddingScheme addTargetCpaBiddingScheme = new BiddingStrategyServiceBiddingScheme();
    BiddingStrategyServiceTargetCpaBiddingScheme targetCpaBiddingScheme = new BiddingStrategyServiceTargetCpaBiddingScheme();
    targetCpaBiddingScheme.setTargetCpa((long) 500);
    targetCpaBiddingScheme.setBidCeiling((long) 700);
    targetCpaBiddingScheme.setBidFloor((long) 200);
    addTargetCpaBiddingScheme.setTargetCpaBiddingScheme(targetCpaBiddingScheme);
    addTargetCpaBiddingScheme.setType(BiddingStrategyServiceType.TARGET_CPA);

    BiddingStrategy targetCpaBidding = new BiddingStrategy();
    targetCpaBidding.setPortfolioBiddingName("SampleTargetCpa_CreateOn_" + ApiUtils.getCurrentTimestamp());
    targetCpaBidding.setBiddingScheme(addTargetCpaBiddingScheme);

    return targetCpaBidding;
  }

  /**
   * example MaximizeClicksBidding request.
   *
   * @return BiddingStrategy
   */
  public static BiddingStrategy createExampleMaximizeClicksBidding() {
    BiddingStrategyServiceBiddingScheme addMaximizeClicksBiddingScheme = new BiddingStrategyServiceBiddingScheme();
    BiddingStrategyServiceMaximizeClicksBiddingScheme maximizeClicksBiddingScheme = new BiddingStrategyServiceMaximizeClicksBiddingScheme();
    maximizeClicksBiddingScheme.setBidCeiling((long) 700);
    addMaximizeClicksBiddingScheme.setMaximizeClicksBiddingScheme(maximizeClicksBiddingScheme);
    addMaximizeClicksBiddingScheme.setType(BiddingStrategyServiceType.MAXIMIZE_CLICKS);

    BiddingStrategy maximizeClicksBidding = new BiddingStrategy();
    maximizeClicksBidding.setPortfolioBiddingName("SampleMaximizeClicks_CreateOn_" + ApiUtils.getCurrentTimestamp());
    maximizeClicksBidding.setBiddingScheme(addMaximizeClicksBiddingScheme);

    return maximizeClicksBidding;
  }

  /**
   * example TargetRoasBidding request.
   *
   * @return BiddingStrategy
   */
  public static BiddingStrategy createExampleTargetRoasBidding() {
    BiddingStrategyServiceBiddingScheme addTargetRoasBiddingScheme = new BiddingStrategyServiceBiddingScheme();
    BiddingStrategyServiceTargetRoasBiddingScheme targetRoasBiddingScheme = new BiddingStrategyServiceTargetRoasBiddingScheme();
    targetRoasBiddingScheme.setTargetRoas(10.00);
    targetRoasBiddingScheme.setBidCeiling((long) 700);
    targetRoasBiddingScheme.setBidFloor((long) 600);
    addTargetRoasBiddingScheme.setTargetRoasBiddingScheme(targetRoasBiddingScheme);
    addTargetRoasBiddingScheme.setType(BiddingStrategyServiceType.TARGET_ROAS);

    BiddingStrategy targetRoasBidding = new BiddingStrategy();
    targetRoasBidding.setPortfolioBiddingName("SampleTargetRoas_CreateOn_" + ApiUtils.getCurrentTimestamp());
    targetRoasBidding.setBiddingScheme(addTargetRoasBiddingScheme);

    return targetRoasBidding;
  }

  /**
   * example biddingStrategies set request.
   *
   * @return List<BiddingStrategy>
   */
  public static List<BiddingStrategy> createExampleSetRequest(List<BiddingStrategy> biddingStrategies) {
    List<BiddingStrategy> operands = new ArrayList<>();

    for (BiddingStrategy biddingStrategy : biddingStrategies) {
      // Set Operand
      BiddingStrategy operand = new BiddingStrategy();
      operand.setPortfolioBiddingId(biddingStrategy.getPortfolioBiddingId());
      operand.setPortfolioBiddingName("Sample_UpdateOn_" + biddingStrategy.getPortfolioBiddingId() + "_" + ApiUtils.getCurrentTimestamp());

      if (biddingStrategy.getBiddingScheme().getType().equals(BiddingStrategyServiceType.TARGET_CPA)) {
        BiddingStrategyServiceBiddingScheme biddingScheme = new BiddingStrategyServiceBiddingScheme();
        BiddingStrategyServiceTargetCpaBiddingScheme setTargetCpaBiddingScheme = new BiddingStrategyServiceTargetCpaBiddingScheme();
        setTargetCpaBiddingScheme.setTargetCpa((long) 550);
        setTargetCpaBiddingScheme.setBidCeiling((long) 750);
        setTargetCpaBiddingScheme.setBidFloor((long) 250);
        biddingScheme.setTargetCpaBiddingScheme(setTargetCpaBiddingScheme);
        biddingScheme.setType(BiddingStrategyServiceType.TARGET_CPA);

        operand.setBiddingScheme(biddingScheme);

        // MaximizeClicksBiddingScheme
      } else if (biddingStrategy.getBiddingScheme().getType().equals(BiddingStrategyServiceType.MAXIMIZE_CLICKS)) {
        BiddingStrategyServiceBiddingScheme biddingScheme = new BiddingStrategyServiceBiddingScheme();
        BiddingStrategyServiceMaximizeClicksBiddingScheme setMaximizeClicksBiddingScheme = new BiddingStrategyServiceMaximizeClicksBiddingScheme();
        setMaximizeClicksBiddingScheme.setBidCeiling((long) 750);
        biddingScheme.setMaximizeClicksBiddingScheme(setMaximizeClicksBiddingScheme);
        biddingScheme.setType(BiddingStrategyServiceType.MAXIMIZE_CLICKS);

        operand.setBiddingScheme(biddingScheme);

        // TargetRoasBiddingScheme
      } else if (biddingStrategy.getBiddingScheme().getType().equals(BiddingStrategyServiceType.TARGET_ROAS)) {
        BiddingStrategyServiceBiddingScheme biddingScheme = new BiddingStrategyServiceBiddingScheme();
        BiddingStrategyServiceTargetRoasBiddingScheme setTargetRoasBiddingScheme = new BiddingStrategyServiceTargetRoasBiddingScheme();
        setTargetRoasBiddingScheme.setTargetRoas(15.00);
        setTargetRoasBiddingScheme.setBidCeiling((long) 750);
        setTargetRoasBiddingScheme.setBidFloor((long) 650);
        biddingScheme.setTargetRoasBiddingScheme(setTargetRoasBiddingScheme);
        biddingScheme.setType(BiddingStrategyServiceType.TARGET_ROAS);

        operand.setBiddingScheme(biddingScheme);
      }
      operands.add(operand);
    }
    return operands;
  }

  /**
   * example get request.
   *
   * @param accountId          long
   * @param biddingStrategyIds List<Long>
   * @return BiddingStrategySelector
   */
  public static BiddingStrategyServiceSelector buildExampleGetRequest(long accountId, List<Long> biddingStrategyIds) {
    // Set Selector
    BiddingStrategyServiceSelector selector = new BiddingStrategyServiceSelector();
    selector.setAccountId(accountId);

    if (biddingStrategyIds.size() > 0) {
      selector.setPortfolioBiddingIds(biddingStrategyIds);
    }
    selector.setBiddingStrategyTypes(Arrays.asList(
        BiddingStrategyServiceType.TARGET_CPA, //
        BiddingStrategyServiceType.MAXIMIZE_CLICKS, //
        BiddingStrategyServiceType.TARGET_ROAS //
    ));

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

}
