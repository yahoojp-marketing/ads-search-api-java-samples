package jp.co.yahoo.adssearchapi.sample.repository;

import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v11.model.PageFeedAssetSet;
import jp.co.yahoo.adssearchapi.v11.model.PageFeedAssetSetServiceValue;

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
   * @return PageFeedAssetSetIds
   */
  public List<Long> getPageFeedAssetSetIds(){
    List<Long> pageFeedAssetSetIdFolderIds = new ArrayList<>();
    if (this.valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return pageFeedAssetSetIdFolderIds;
    }
    for (PageFeedAssetSetServiceValue value : this.valuesHolder.getPageFeedAssetSetServiceValueList()) {
      pageFeedAssetSetIdFolderIds.add(value.getPageFeedAssetSet().getPageFeedAssetSetId());
    }
    return pageFeedAssetSetIdFolderIds;
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
