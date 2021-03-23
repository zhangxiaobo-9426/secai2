package com.zxb.secai.ui.my;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;

import com.zxb.secai.R;
import com.zxb.secai.databinding.LayoutFeedTypeCommentBinding;
import com.zxb.secai.databinding.LayoutProfileTabCommentInteractionBinding;
import com.zxb.secai.model.Feed;
import com.zxb.secai.ui.InteractionPresenter;
import com.zxb.secai.ui.MutableItemKeyedDataSource;
import com.zxb.secai.ui.home.FeedAdapter;
import com.zxb.secai.ui.login.UserManager;
import com.zxb.secai.utils.TimeUtils;

public class ProfileListAdapter extends FeedAdapter {
    public ProfileListAdapter(Context context, String category) {
        super(context, category);
    }

    @Override
    public int getItemViewType2(int position) {
        if (TextUtils.equals(mCategory, ProfileActivity.TAB_TYPE_COMMENT)) {
            return R.layout.layout_feed_type_comment;
        } else if (TextUtils.equals(mCategory, ProfileActivity.TAB_TYPE_ALL)) {
            Feed feed = getItem(position);
            if (feed.topComment != null && feed.topComment.userId == UserManager.get().getUserId()) {
                return R.layout.layout_feed_type_comment;
            }
        }
        return super.getItemViewType2(position);
    }

    @Override
    protected void onBindViewHolder2(ViewHolder holder, int position) {
        super.onBindViewHolder2(holder, position);
        View deleteView = holder.itemView.findViewById(R.id.feed_delete);
        TextView createTime = holder.itemView.findViewById(R.id.create_time);

        Feed feed = getItem(position);
        createTime.setVisibility(View.VISIBLE);
        createTime.setText(TimeUtils.calculate(feed.createTime));

        boolean isCommentTab = TextUtils.equals(mCategory, ProfileActivity.TAB_TYPE_COMMENT);
        deleteView.setVisibility(View.VISIBLE);
        deleteView.setOnClickListener(v -> {
            //如果是个人主页的评论tab，删除的时候，实际上是删除帖子的评论。
            if (isCommentTab) {
                InteractionPresenter.deleteFeedComment(mContext, feed.itemId, feed.topComment.commentId)
                        .observe((LifecycleOwner) mContext, success -> {
                            refreshList(feed);
                        });
            } else {
                InteractionPresenter.deleteFeed(mContext, feed.itemId)
                        .observe((LifecycleOwner) mContext, success -> {
                            refreshList(feed);
                        });
            }
        });
    }

    private void refreshList(Feed delete) {
        //实际上这个方法 可以再封装一下
        PagedList<Feed> currentList = getCurrentList();
        MutableItemKeyedDataSource<Integer, Feed> dataSource = new MutableItemKeyedDataSource<Integer, Feed>((ItemKeyedDataSource) currentList.getDataSource()) {
            @NonNull
            @Override
            public Integer getKey(@NonNull Feed item) {
                return item.id;
            }
        };
        //for循环一遍,过滤掉被删除的这个帖子
        for (Feed feed : currentList) {
            if (feed != delete) {
                dataSource.data.add(feed);
            }
        }
        PagedList<Feed> pagedList = dataSource.buildNewPagedList(currentList.getConfig());
        submitList(pagedList);
    }
}
