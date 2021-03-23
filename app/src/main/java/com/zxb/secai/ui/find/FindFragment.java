package com.zxb.secai.ui.find;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.zxb.libnavannotation.FragmentDestination;
import com.zxb.secai.model.SofaTab;
import com.zxb.secai.ui.sofa.SofaFragment;
import com.zxb.secai.utils.AppConfig;


@FragmentDestination(pageUrl = "main/tabs/find")
public class FindFragment extends SofaFragment {

    @Override
    public Fragment getTabFragment(int position) {
        SofaTab.Tabs tab = getTabConfig().tabs.get(position);
        TagListFragment fragment = TagListFragment.newInstance(tab.tag);
        return fragment;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        String tagType = childFragment.getArguments().getString(TagListFragment.KEY_TAG_TYPE);
        if (TextUtils.equals(tagType, "onlyFollow")) {
            ViewModelProviders.of(childFragment).get(TagListViewModel.class)
                    .getSwitchTabLiveData().observe(this,
                    object -> viewPager2.setCurrentItem(1));
        }
    }

    @Override
    public SofaTab getTabConfig() {
        return AppConfig.getFindTabConfig();
    }
}