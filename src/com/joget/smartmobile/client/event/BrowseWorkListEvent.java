package com.joget.smartmobile.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BrowseWorkListEvent extends GwtEvent<BrowseWorkListEventHandler>{
  public static Type<BrowseWorkListEventHandler> TYPE = new Type<BrowseWorkListEventHandler>();
  
  public BrowseWorkListEvent() {
  }

  @Override
  public Type<BrowseWorkListEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BrowseWorkListEventHandler handler) {
    handler.onBrowseWorkList(this);
  }
}
