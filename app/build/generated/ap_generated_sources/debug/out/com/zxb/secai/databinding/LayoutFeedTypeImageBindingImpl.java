package com.zxb.secai.databinding;
import com.zxb.secai.R;
import com.zxb.secai.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutFeedTypeImageBindingImpl extends LayoutFeedTypeImageBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(7);
        sIncludes.setIncludes(0, 
            new String[] {"layout_feed_author", "layout_feed_text", "layout_feed_tag", "layout_feed_top_comment", "layout_feed_interaction"},
            new int[] {1, 2, 3, 4, 5},
            new int[] {com.zxb.secai.R.layout.layout_feed_author,
                com.zxb.secai.R.layout.layout_feed_text,
                com.zxb.secai.R.layout.layout_feed_tag,
                com.zxb.secai.R.layout.layout_feed_top_comment,
                com.zxb.secai.R.layout.layout_feed_interaction});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.feed_image, 6);
    }
    // views
    @Nullable
    private final com.zxb.secai.databinding.LayoutFeedAuthorBinding mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView01;
    @Nullable
    private final com.zxb.secai.databinding.LayoutFeedTextBinding mboundView02;
    @Nullable
    private final com.zxb.secai.databinding.LayoutFeedTagBinding mboundView03;
    @Nullable
    private final com.zxb.secai.databinding.LayoutFeedTopCommentBinding mboundView04;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutFeedTypeImageBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private LayoutFeedTypeImageBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (com.zxb.secai.view.PPImageView) bindings[6]
            , (com.zxb.secai.databinding.LayoutFeedInteractionBinding) bindings[5]
            );
        this.mboundView0 = (com.zxb.secai.databinding.LayoutFeedAuthorBinding) bindings[1];
        setContainedBinding(this.mboundView0);
        this.mboundView01 = (android.widget.LinearLayout) bindings[0];
        this.mboundView01.setTag(null);
        this.mboundView02 = (com.zxb.secai.databinding.LayoutFeedTextBinding) bindings[2];
        setContainedBinding(this.mboundView02);
        this.mboundView03 = (com.zxb.secai.databinding.LayoutFeedTagBinding) bindings[3];
        setContainedBinding(this.mboundView03);
        this.mboundView04 = (com.zxb.secai.databinding.LayoutFeedTopCommentBinding) bindings[4];
        setContainedBinding(this.mboundView04);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
        }
        mboundView0.invalidateAll();
        mboundView02.invalidateAll();
        mboundView03.invalidateAll();
        mboundView04.invalidateAll();
        interactionBinding.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (mboundView0.hasPendingBindings()) {
            return true;
        }
        if (mboundView02.hasPendingBindings()) {
            return true;
        }
        if (mboundView03.hasPendingBindings()) {
            return true;
        }
        if (mboundView04.hasPendingBindings()) {
            return true;
        }
        if (interactionBinding.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.feed == variableId) {
            setFeed((com.zxb.secai.model.Feed) variable);
        }
        else if (BR.lifeCycleOwner == variableId) {
            setLifeCycleOwner((androidx.lifecycle.LifecycleOwner) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setFeed(@Nullable com.zxb.secai.model.Feed Feed) {
        updateRegistration(1, Feed);
        this.mFeed = Feed;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.feed);
        super.requestRebind();
    }
    public void setLifeCycleOwner(@Nullable androidx.lifecycle.LifecycleOwner LifeCycleOwner) {
        this.mLifeCycleOwner = LifeCycleOwner;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.lifeCycleOwner);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        mboundView0.setLifecycleOwner(lifecycleOwner);
        mboundView02.setLifecycleOwner(lifecycleOwner);
        mboundView03.setLifecycleOwner(lifecycleOwner);
        mboundView04.setLifecycleOwner(lifecycleOwner);
        interactionBinding.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeInteractionBinding((com.zxb.secai.databinding.LayoutFeedInteractionBinding) object, fieldId);
            case 1 :
                return onChangeFeed((com.zxb.secai.model.Feed) object, fieldId);
            case 2 :
                return onChangeFeedAuthor((com.zxb.secai.model.User) object, fieldId);
            case 3 :
                return onChangeFeedTopComment((com.zxb.secai.model.Comment) object, fieldId);
        }
        return false;
    }
    private boolean onChangeInteractionBinding(com.zxb.secai.databinding.LayoutFeedInteractionBinding InteractionBinding, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeFeed(com.zxb.secai.model.Feed Feed, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.author) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeFeedAuthor(com.zxb.secai.model.User FeedAuthor, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeFeedTopComment(com.zxb.secai.model.Comment FeedTopComment, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.zxb.secai.model.Feed feed = mFeed;
        java.lang.String feedFeedsText = null;
        com.zxb.secai.model.User feedAuthor = null;
        com.zxb.secai.model.Comment feedTopComment = null;
        androidx.lifecycle.LifecycleOwner lifeCycleOwner = mLifeCycleOwner;
        java.lang.String feedActivityText = null;

        if ((dirtyFlags & 0x2eL) != 0) {


            if ((dirtyFlags & 0x22L) != 0) {

                    if (feed != null) {
                        // read feed.feeds_text
                        feedFeedsText = feed.feeds_text;
                        // read feed.activityText
                        feedActivityText = feed.activityText;
                    }
            }
            if ((dirtyFlags & 0x26L) != 0) {

                    if (feed != null) {
                        // read feed.author
                        feedAuthor = feed.getAuthor();
                    }
                    updateRegistration(2, feedAuthor);
            }
            if ((dirtyFlags & 0x2aL) != 0) {

                    if (feed != null) {
                        // read feed.topComment
                        feedTopComment = feed.topComment;
                    }
                    updateRegistration(3, feedTopComment);
            }
        }
        if ((dirtyFlags & 0x30L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x30L) != 0) {
            // api target 1

            this.interactionBinding.setLifeCycleOwner(lifeCycleOwner);
        }
        if ((dirtyFlags & 0x22L) != 0) {
            // api target 1

            this.interactionBinding.setFeed(feed);
            this.mboundView02.setFeedText(feedFeedsText);
            this.mboundView03.setTagText(feedActivityText);
        }
        if ((dirtyFlags & 0x26L) != 0) {
            // api target 1

            this.mboundView0.setUser(feedAuthor);
        }
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            this.mboundView02.setLines(3);
        }
        if ((dirtyFlags & 0x2aL) != 0) {
            // api target 1

            this.mboundView04.setComment(feedTopComment);
        }
        executeBindingsOn(mboundView0);
        executeBindingsOn(mboundView02);
        executeBindingsOn(mboundView03);
        executeBindingsOn(mboundView04);
        executeBindingsOn(interactionBinding);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): interactionBinding
        flag 1 (0x2L): feed
        flag 2 (0x3L): feed.author
        flag 3 (0x4L): feed.topComment
        flag 4 (0x5L): lifeCycleOwner
        flag 5 (0x6L): null
    flag mapping end*/
    //end
}