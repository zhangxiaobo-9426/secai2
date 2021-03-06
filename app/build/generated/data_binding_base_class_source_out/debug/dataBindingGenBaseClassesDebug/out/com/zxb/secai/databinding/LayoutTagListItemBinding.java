// Generated by data binding compiler. Do not edit!
package com.zxb.secai.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.button.MaterialButton;
import com.zxb.secai.R;
import com.zxb.secai.model.TagList;
import com.zxb.secai.view.PPImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutTagListItemBinding extends ViewDataBinding {
  @NonNull
  public final MaterialButton actionFollow;

  @NonNull
  public final PPImageView tagAvtar;

  @NonNull
  public final TextView tagDesc;

  @NonNull
  public final TextView tagTitle;

  @Bindable
  protected TagList mTagList;

  protected LayoutTagListItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      MaterialButton actionFollow, PPImageView tagAvtar, TextView tagDesc, TextView tagTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.actionFollow = actionFollow;
    this.tagAvtar = tagAvtar;
    this.tagDesc = tagDesc;
    this.tagTitle = tagTitle;
  }

  public abstract void setTagList(@Nullable TagList tagList);

  @Nullable
  public TagList getTagList() {
    return mTagList;
  }

  @NonNull
  public static LayoutTagListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_tag_list_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutTagListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutTagListItemBinding>inflateInternal(inflater, R.layout.layout_tag_list_item, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutTagListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_tag_list_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutTagListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutTagListItemBinding>inflateInternal(inflater, R.layout.layout_tag_list_item, null, false, component);
  }

  public static LayoutTagListItemBinding bind(@NonNull View view) {
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
  public static LayoutTagListItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutTagListItemBinding)bind(component, view, R.layout.layout_tag_list_item);
  }
}
