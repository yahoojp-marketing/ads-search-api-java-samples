/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v7.model.FeedItem;
import jp.co.yahoo.adssearchapi.v7.model.FeedItemServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v7.model.FeedItemServiceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method collection for Java Sample Program.
 */
public class FeedItemValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * FeedItemValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public FeedItemValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return FeedItems
   */
  public List<FeedItem> getFeedItems(){
    List<FeedItem> feedItems = new ArrayList<>();
    if (this.valuesHolder.getFeedItemServiceValueList().size() == 0) {
      return feedItems;
    }
    for (FeedItemServiceValue value : this.valuesHolder.getFeedItemServiceValueList()) {
      feedItems.add(value.getFeedItem());
    }
    return feedItems;
  }
  /**
   * @return feedItemIds
   */
  public List<Long> getFeedItemIds(){
    List<Long> feedItemIds = new ArrayList<>();
    if (this.valuesHolder.getFeedItemServiceValueList().size() == 0) {
      return feedItemIds;
    }
    for (FeedItemServiceValue value : this.valuesHolder.getFeedItemServiceValueList()) {
      feedItemIds.add(value.getFeedItem().getFeedItemId());
    }
    return feedItemIds;
  }

  /**
   * @param feedItemPlaceholderType FeedItemPlaceholderType
   * @return FeedItem
   */
  public FeedItem findFeedItem(FeedItemServicePlaceholderType feedItemPlaceholderType) {
    if (this.valuesHolder.getFeedItemServiceValueList().size() == 0) {
      return null;
    }
    for (FeedItemServiceValue values : this.valuesHolder.getFeedItemServiceValueList()) {
      if (values.getFeedItem().getPlaceholderType().equals(feedItemPlaceholderType)) {
        return values.getFeedItem();
      }
    }
    return null;
  }

  /**
   * @param feedItemPlaceholderType feedItemPlaceholderType
   * @return Long|null
   */
  public Long findFeedItemId(FeedItemServicePlaceholderType feedItemPlaceholderType) {
    if (this.valuesHolder.getFeedItemServiceValueList().size() == 0) {
      return null;
    }
    for (FeedItemServiceValue value : this.valuesHolder.getFeedItemServiceValueList()) {
      if (value.getFeedItem().getPlaceholderType().equals(feedItemPlaceholderType)) {
        return value.getFeedItem().getFeedItemId();
      }
    }
    return null;
  }
}
