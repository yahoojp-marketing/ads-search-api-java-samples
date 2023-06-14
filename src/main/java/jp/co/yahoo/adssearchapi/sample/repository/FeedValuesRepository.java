/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import java.util.ArrayList;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v11.model.Feed;
import jp.co.yahoo.adssearchapi.v11.model.FeedServiceAttribute;
import jp.co.yahoo.adssearchapi.v11.model.FeedServicePlaceholderField;
import jp.co.yahoo.adssearchapi.v11.model.FeedServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v11.model.FeedServiceValue;

/**
 * Utility method collection for Java Sample Program.
 */
public class FeedValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * FeedValueRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public FeedValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return Feeds
   */
  public List<Feed> getFeeds(){
    List<Feed> feedFolders = new ArrayList<>();
    if (this.valuesHolder.getFeedServiceValueList().size() == 0) {
      return feedFolders;
    }
    for (FeedServiceValue value : this.valuesHolder.getFeedServiceValueList()) {
      feedFolders.add(value.getFeed());
    }
    return feedFolders;
  }

  /**
   * @return FeedIds
   */
  public List<Long> getFeedIds(){
    List<Long> feedFolderIds = new ArrayList<>();
    if (this.valuesHolder.getFeedServiceValueList().size() == 0) {
      return feedFolderIds;
    }
    for (FeedServiceValue value : this.valuesHolder.getFeedServiceValueList()) {
      feedFolderIds.add(value.getFeed().getFeedId());
    }
    return feedFolderIds;
  }

  /**
   * @param feedFolderPlaceholderType FeedPlaceholderType
   * @return Long|null
   */
  public Long findFeedId(FeedServicePlaceholderType feedFolderPlaceholderType) {
    if (this.valuesHolder.getFeedServiceValueList().size() == 0) {
      return null;
    }
    for (FeedServiceValue value : this.valuesHolder.getFeedServiceValueList()) {
      if (value.getFeed().getPlaceholderType().equals(feedFolderPlaceholderType)) {
        return value.getFeed().getFeedId();
      }
    }
    return null;
  }

  /**
   * @param feedFolderId Long
   * @return String|null
   */
  public String findFeedName(Long feedFolderId) {
    if (this.valuesHolder.getFeedServiceValueList().size() == 0) {
      return null;
    }
    for (FeedServiceValue value : this.valuesHolder.getFeedServiceValueList()) {
      if (value.getFeed().getFeedId().equals(feedFolderId)) {
        return value.getFeed().getFeedName();
      }
    }
    return null;
  }

  /**
   * @param feedFolderId Long
   * @param feedFolderPlaceholderField FeedPlaceholderField
   * @return Long|null
   */
  public Long findFeedAttributeId(Long feedFolderId, FeedServicePlaceholderField feedFolderPlaceholderField) {
    if (this.valuesHolder.getFeedServiceValueList().size() == 0) {
      return null;
    }
    for (FeedServiceValue value : this.valuesHolder.getFeedServiceValueList()) {
      if (value.getFeed().getFeedId().equals(feedFolderId)) {
        for (FeedServiceAttribute feedAttribute :value.getFeed().getFeedAttribute()) {
          if (feedAttribute.getPlaceholderField().equals(feedFolderPlaceholderField)) {
            return feedAttribute.getFeedAttributeId();
          }
        }
      }
    }
    return null;
  }

  /**
   * @param feedFolderId Long
   * @param feedFolderPlaceholderField FeedPlaceholderField
   * @return String|null
   */
  public String  findFeedAttributeName(Long feedFolderId, FeedServicePlaceholderField feedFolderPlaceholderField) {
    if (this.valuesHolder.getFeedServiceValueList().size() == 0) {
      return null;
    }
    for (FeedServiceValue value : this.valuesHolder.getFeedServiceValueList()) {
      if (value.getFeed().getFeedId().equals(feedFolderId)) {
        for (FeedServiceAttribute feedAttribute :value.getFeed().getFeedAttribute()) {
          if (feedAttribute.getPlaceholderField().equals(feedFolderPlaceholderField)) {
            return feedAttribute.getFeedAttributeName();
          }
        }
      }
    }
    return null;
  }
}
