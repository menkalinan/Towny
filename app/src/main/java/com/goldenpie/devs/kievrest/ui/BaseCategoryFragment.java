package com.goldenpie.devs.kievrest.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerAnimator;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.ui.activity.MainActivity;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public abstract class BaseCategoryFragment extends Fragment {

    @State
    protected Integer currentPage;
    @State
    protected Parcelable viewPagerSate;
    @State
    protected Parcelable pagerSate;
    @State
    protected String currentCat;

    @Bind(R.id.materialViewPager)
    protected MaterialViewPager mViewPager;
    @Bind(R.id.header_logo_background)
    protected FrameLayout headerLogoBackground;
    @Bind(R.id.header_logo_image)
    protected ImageView headerImage;

    @Inject
    protected ApplicationPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TownyApplication.appComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);
        mViewPager.setToolbar(((MainActivity) getActivity()).getToolbar());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerAnimator.ENABLE_LOG = false;

        if (viewPagerSate != null) {
            mViewPager.getViewPager().onRestoreInstanceState(pagerSate);
            mViewPager.onRestoreInstanceState(viewPagerSate);
        }

        setTitle();
        setViewPagerContent();
    }

    protected abstract int getContentView();

    protected abstract String getTitle();

    protected abstract void setViewPagerContent();

    private void setTitle() {
        getActivity().setTitle(getTitle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewPagerSate = mViewPager.onSaveInstanceState();
        pagerSate = mViewPager.getViewPager().onSaveInstanceState();
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDetach() {
        Glide.get(getActivity()).clearMemory();
        ButterKnife.unbind(this);
        System.gc();
        super.onDetach();
    }
}
