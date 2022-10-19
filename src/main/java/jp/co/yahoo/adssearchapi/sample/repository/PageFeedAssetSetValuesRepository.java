package jp.co.yahoo.adssearchapi.sample.repository;

import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v9.model.PageFeedAssetSet;
import jp.co.yahoo.adssearchapi.v9.model.PageFeedAssetSetServiceValue;

import java.util.ArrayList;
import java.util.List;

public class PageFeedAssetSetValuesRepository {
  private ValuesHolder valuesHolder;

  /**
   * PageFeedAssetSetValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public PageFeedAssetSetValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return PageFeedAssetSets
   */
  public List<PageFeedAssetSet> getPageFeedAssetSet(){
    List<PageFeedAssetSet> pageFeedAssetSetFolders = new ArrayList<>();
    if (this.valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return pageFeedAssetSetFolders;
    }
    for (PageFeedAssetSetServiceValue value : this.valuesHolder.getPageFeedAssetSetServiceValueList()) {
      pageFeedAssetSetFolders.add(value.getPageFeedAssetSet());
    }
    return pageFeedAssetSetFolders;
  }

  /**
   * @return FeedIds
   */
  public List<Long> getFeedIds(){
    List<Long> feedFolderIds = new ArrayList<>();
    if (this.valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return feedFolderIds;
    }
    for (PageFeedAssetSetServiceValue value : this.valuesHolder.getPageFeedAssetSetServiceValueList()) {
      feedFolderIds.add(value.getPageFeedAssetSet().getFeedId());
    }
    return feedFolderIds;
  }

  /**
   * @return Long|null
   */
  public Long findFeedId() {

    if (this.valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return null;
    }
    for (PageFeedAssetSetServiceValue value : this.valuesHolder.getPageFeedAssetSetServiceValueList()) {
      return value.getPageFeedAssetSet().getFeedId();
    }
    return null;
  }

  /**
   * @return Long|null
   */
  public Long findPageFeedAssetSetId() {

    if (this.valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return null;
    }
    for (PageFeedAssetSetServiceValue value : this.valuesHolder.getPageFeedAssetSetServiceValueList()) {
      return value.getPageFeedAssetSet().getPageFeedAssetSetId();
    }
    return null;
  }

  /**
   * @param pageFeedAssetSetId Long
   * @return String|null
   */
  public String findPageFeedAssetSetName(Long pageFeedAssetSetId) {
    if (this.valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return null;
    }
    for (PageFeedAssetSetServiceValue value : this.valuesHolder.getPageFeedAssetSetServiceValueList()) {
      if (value.getPageFeedAssetSet().getPageFeedAssetSetId().equals(pageFeedAssetSetId)) {
        return value.getPageFeedAssetSet().getPageFeedAssetSetName();
      }
    }
    return null;
  }
}
