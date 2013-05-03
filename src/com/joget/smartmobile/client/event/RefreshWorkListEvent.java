package com.joget.smartmobile.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RefreshWorkListEvent extends GwtEvent<RefreshWorkListEventHandler>{
  public static Type<RefreshWorkListEventHandler> TYPE = new Type<RefreshWorkListEventHandler>();
  
  public RefreshWorkListEvent() {
  }

  @Override
  public Type<RefreshWorkListEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RefreshWorkListEventHandler handler) {
    handler.onRefreshWorkList(this);
  }
}
