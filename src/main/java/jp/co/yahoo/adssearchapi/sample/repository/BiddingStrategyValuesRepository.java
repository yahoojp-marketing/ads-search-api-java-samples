/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import java.util.ArrayList;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v7.model.BiddingStrategy;
import jp.co.yahoo.adssearchapi.v7.model.BiddingStrategyServiceType;
import jp.co.yahoo.adssearchapi.v7.model.BiddingStrategyServiceValue;

/**
 * Utility method collection for Java Sample Program.
 */
public class BiddingStrategyValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * BiddingStrategyValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public BiddingStrategyValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return BiddingStrategies
   */
  public List<BiddingStrategy> getBiddingStrategies() {
    List<BiddingStrategy> biddingStrategies = new ArrayList<>();
    if (this.valuesHolder.getBiddingStrategyServiceValueList().size() == 0) {
      return biddingStrategies;
    }
    for (BiddingStrategyServiceValue value : this.valuesHolder.getBiddingStrategyServiceValueList()) {
      biddingStrategies.add(value.getBiddingStrategy());
    }
    return biddingStrategies;
  }

  /**
   * @return BiddingStrategyIds
   */
  public List<Long> getBiddingStrategyIds() {
    List<Long> biddingStrategyIds = new ArrayList<>();
    if (this.valuesHolder.getBiddingStrategyServiceValueList().size() == 0) {
      return biddingStrategyIds;
    }
    for (BiddingStrategyServiceValue value : this.valuesHolder.getBiddingStrategyServiceValueList()) {
      biddingStrategyIds.add(value.getBiddingStrategy().getBiddingStrategyId());
    }
    return biddingStrategyIds;
  }

  /**
   * @param biddingStrategyType BiddingStrategyType
   * @return BiddingStrategy/null
   */
  public BiddingStrategy findBiddingStrategy(BiddingStrategyServiceType biddingStrategyType) {
    if (this.valuesHolder.getBiddingStrategyServiceValueList().size() == 0) {
      return null;
    }
    for (BiddingStrategyServiceValue value : this.valuesHolder.getBiddingStrategyServiceValueList()) {
      if (value.getBiddingStrategy().getBiddingScheme().getType().equals(biddingStrategyType)) {
        return value.getBiddingStrategy();
      }
    }
    return null;
  }

  /**
   * @param biddingStrategyType BiddingStrategyType
   * @return Long/null
   */
  public Long findBiddingStrategyId(BiddingStrategyServiceType biddingStrategyType) {
    if (this.valuesHolder.getBiddingStrategyServiceValueList().size() == 0) {
      return null;
    }
    for (BiddingStrategyServiceValue value : this.valuesHolder.getBiddingStrategyServiceValueList()) {
      if (value.getBiddingStrategy().getBiddingScheme().getType().equals(biddingStrategyType)) {
        return value.getBiddingStrategy().getBiddingStrategyId();
      }
    }
    return null;
  }
}
