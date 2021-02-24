/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.biddingstrategy;

import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategy;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceBiddingScheme;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceGetResponse;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceOperation;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceSelector;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceTargetCpaBiddingScheme;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceTargetRoasBiddingScheme;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceTargetSpendBiddingScheme;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v4.model.BiddingStrategyServiceValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * example BiddingStrategyService operation and Utility method collection.
 */
public class BiddingStrategyServiceSample {

  private static final String SERVICE_NAME = "BiddingStrategyService";

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
      List<BiddingStrategyServiceValue> biddingStrategyValues = mutate(addBiddingStrategyOperation, "add");
      valuesRepositoryFacade.getValuesHolder().setBiddingStrategyServiceValueList(biddingStrategyValues);

      // =================================================================
      // BiddingStrategyService::SET
      // =================================================================
      // create request.
      BiddingStrategyServiceOperation setBiddingStrategyOperation =
        buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies()));

      // run
      mutate(setBiddingStrategyOperation, "set");

      // =================================================================
      // BiddingStrategyService::GET
      // =================================================================
      // create request.
      BiddingStrategyServiceSelector biddingStrategySelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategyIds());

      // run
      get(biddingStrategySelector);

      // =================================================================
      // BiddingStrategyService::REMOVE
      // =================================================================
      // create request.
      BiddingStrategyServiceOperation removeBiddingStrategyOperation =
        buildExampleMutateRequest(accountId, valuesRepositoryFacade.getBiddingStrategyValuesRepository().getBiddingStrategies());

      // run
      mutate(removeBiddingStrategyOperation, "remove");

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
    List<BiddingStrategyServiceValue> biddingStrategyValues = mutate(addBiddingStrategyOperation, "add");
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
    mutate(removeBiddingStrategyOperation, "remove");
  }

  /**
   * Sample Program for BiddingStrategyService MUTATE.
   *
   * @param operation BiddingStrategyOperation
   * @return BiddingStrategyValues
   */
  public static List<BiddingStrategyServiceValue> mutate(BiddingStrategyServiceOperation operation, String action) throws Exception {

    BiddingStrategyServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, BiddingStrategyServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example get biddingStrategies.
   *
   * @param selector BiddingStrategySelector
   * @return BiddingStrategyValues
   */
  public static List<BiddingStrategyServiceValue> get(BiddingStrategyServiceSelector selector) throws Exception {

    BiddingStrategyServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, "get", selector, BiddingStrategyServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example mutate request.
   */
  public static BiddingStrategyServiceOperation buildExampleMutateRequest(long accountId, List<BiddingStrategy> operand) {
    BiddingStrategyServiceOperation operation = new BiddingStrategyServiceOperation();
    operation.setAccountId(accountId);
    operation.getOperand().addAll(operand);

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
    targetCpaBidding.setBiddingStrategyName("SampleTargetCpa_CreateOn_" + ApiUtils.getCurrentTimestamp());
    targetCpaBidding.setBiddingScheme(addTargetCpaBiddingScheme);

    return targetCpaBidding;
  }

  /**
   * example TargetSpendBidding request.
   *
   * @return BiddingStrategy
   */
  public static BiddingStrategy createExampleTargetSpendBidding() {
    BiddingStrategyServiceBiddingScheme addTargetSpendBiddingScheme = new BiddingStrategyServiceBiddingScheme();
    BiddingStrategyServiceTargetSpendBiddingScheme targetSpendBiddingScheme = new BiddingStrategyServiceTargetSpendBiddingScheme();
    targetSpendBiddingScheme.setBidCeiling((long) 700);
    addTargetSpendBiddingScheme.setTargetSpendBiddingScheme(targetSpendBiddingScheme);
    addTargetSpendBiddingScheme.setType(BiddingStrategyServiceType.TARGET_SPEND);

    BiddingStrategy targetSpendBidding = new BiddingStrategy();
    targetSpendBidding.setBiddingStrategyName("SampleTargetSpend_CreateOn_" + ApiUtils.getCurrentTimestamp());
    targetSpendBidding.setBiddingScheme(addTargetSpendBiddingScheme);

    return targetSpendBidding;
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
    targetRoasBidding.setBiddingStrategyName("SampleTargetRoas_CreateOn_" + ApiUtils.getCurrentTimestamp());
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
      operand.setBiddingStrategyId(biddingStrategy.getBiddingStrategyId());
      operand.setBiddingStrategyName("Sample_UpdateOn_" + biddingStrategy.getBiddingStrategyId() + "_" + ApiUtils.getCurrentTimestamp());

      if (biddingStrategy.getBiddingScheme().getType().equals(BiddingStrategyServiceType.TARGET_CPA)) {
        BiddingStrategyServiceBiddingScheme biddingScheme = new BiddingStrategyServiceBiddingScheme();
        BiddingStrategyServiceTargetCpaBiddingScheme setTargetCpaBiddingScheme = new BiddingStrategyServiceTargetCpaBiddingScheme();
        setTargetCpaBiddingScheme.setTargetCpa((long) 550);
        setTargetCpaBiddingScheme.setBidCeiling((long) 750);
        setTargetCpaBiddingScheme.setBidFloor((long) 250);
        biddingScheme.setTargetCpaBiddingScheme(setTargetCpaBiddingScheme);
        biddingScheme.setType(BiddingStrategyServiceType.TARGET_CPA);

        operand.setBiddingScheme(biddingScheme);

        // TargetSpendBiddingScheme
      } else if (biddingStrategy.getType().equals(BiddingStrategyServiceType.TARGET_SPEND)) {
        BiddingStrategyServiceBiddingScheme biddingScheme = new BiddingStrategyServiceBiddingScheme();
        BiddingStrategyServiceTargetSpendBiddingScheme setTargetSpendBiddingScheme = new BiddingStrategyServiceTargetSpendBiddingScheme();
        setTargetSpendBiddingScheme.setBidCeiling((long) 750);
        biddingScheme.setTargetSpendBiddingScheme(setTargetSpendBiddingScheme);
        biddingScheme.setType(BiddingStrategyServiceType.TARGET_SPEND);

        operand.setBiddingScheme(biddingScheme);

        // TargetRoasBiddingScheme
      } else if (biddingStrategy.getType().equals(BiddingStrategyServiceType.TARGET_ROAS)) {
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
      selector.setBiddingStrategyIds(biddingStrategyIds);
    }
    selector.setBiddingStrategyTypes(Arrays.asList(
      BiddingStrategyServiceType.TARGET_CPA, //
      BiddingStrategyServiceType.TARGET_SPEND, //
      BiddingStrategyServiceType.TARGET_ROAS //
    ));

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

}
