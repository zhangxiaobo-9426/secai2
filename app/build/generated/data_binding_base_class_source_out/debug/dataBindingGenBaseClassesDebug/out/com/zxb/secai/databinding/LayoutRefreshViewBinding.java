// Generated by data binding compiler. Do not edit!
package com.zxb.secai.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxb.libcommon.view.EmptyView;
import com.zxb.secai.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutRefreshViewBinding extends ViewDataBinding {
  @NonNull
  public final EmptyView emptyView;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SmartRefreshLayout refreshLayout;

  protected LayoutRefreshViewBinding(Object _bindingComponent, View _root, int _localFieldCount,
      EmptyView emptyView, RecyclerView recyclerView, SmartRefreshLayout refreshLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.emptyView = emptyView;
    this.recyclerView = recyclerView;
    this.refreshLayout = refreshLayout;
  }

  @NonNull
  public static LayoutRefreshViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_refresh_view, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutRefreshViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutRefreshViewBinding>inflateInternal(inflater, R.layout.layout_refresh_view, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutRefreshViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_refresh_view, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutRefreshViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutRefreshViewBinding>inflateInternal(inflater, R.layout.layout_refresh_view, null, false, component);
  }

  public static LayoutRefreshViewBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static LayoutRefreshViewBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutRefreshViewBinding)bind(component, view, R.layout.layout_refresh_view);
  }
}