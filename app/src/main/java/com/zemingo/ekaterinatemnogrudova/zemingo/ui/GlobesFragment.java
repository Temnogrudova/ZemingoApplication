package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zemingo.ekaterinatemnogrudova.zemingo.R;
import com.zemingo.ekaterinatemnogrudova.zemingo.databinding.FragmentGlobesBinding;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.RssParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.Constants.CARS_ID;
import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.Constants.CULTURE_ID;
import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.Constants.SPORTS_ID;
import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.DateUtil.getCurrentDateTime;

public class GlobesFragment extends Fragment implements GlobesContract.View,  TabLayout.OnTabSelectedListener , GlobesAdapter.IAccountItemClick{
    public FragmentGlobesBinding mBinder;
    private List<RssParser.RssItem> mCars = new ArrayList<>();
    private List<RssParser.RssItem> mSport_Culture = new ArrayList<>();

    private GlobesAdapter mAdapter;
    private GlobesSectionedRecyclerViewAdapter mSectionedAdapter;
    private List<GlobesSectionedRecyclerViewAdapter.Section> sections;
    private GlobesContract.Presenter mPresenter;

    private String currentID;

    public static GlobesFragment newInstance() {
        return new GlobesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onAuthenticatorClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment across configuration changes.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_globes, container, false);
        View view = mBinder.getRoot();
        mPresenter = new GlobesPresenter (this);
        initTabs();
        mBinder.rssFeed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new GlobesAdapter(mCars, this);
        mBinder.rssFeed.setHasFixedSize(true);
        mBinder.rssFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinder.rssFeed.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        sections = new ArrayList<GlobesSectionedRecyclerViewAdapter.Section>();

        mSectionedAdapter = new
                GlobesSectionedRecyclerViewAdapter(getActivity(),R.layout.section,R.id.section_text,mAdapter);

        ViewCompat.setNestedScrollingEnabled(mBinder.rssFeed, false);

        mBinder.rssFeed.setAdapter(mSectionedAdapter);

        return view;
    }
    private void initTabs() {
        mBinder.tabs.addTab(mBinder.tabs.newTab().setText(getActivity().getResources().getString(R.string.cars)).setTag(getActivity().getResources().getString(R.string.cars)));
        mBinder.tabs.addTab(mBinder.tabs.newTab().setText(getActivity().getResources().getString(R.string.sport_culture)).setTag(getActivity().getResources().getString(R.string.sport_culture)));

        mBinder.tabs.addOnTabSelectedListener(this);

        mBinder.tabs.setScrollPosition(0,0f,true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinder.networkProgress.setVisibility(View.VISIBLE);
        sections.clear();
        mCars.clear();
        mSport_Culture.clear();
        currentID = CARS_ID;
        mPresenter.getModels(currentID);
    }
    @Override
    public void getSuccess(List<RssParser.RssItem> result) {
        {
            mBinder.networkProgress.setVisibility(View.GONE);

            switch (mBinder.tabs.getSelectedTabPosition()){
                case 0:
                    mCars.clear();
                    sections.clear();
                    mCars.addAll(result);
                    mAdapter.refreshList(mCars);
                    break;
                case 1:
                    if (currentID.equals(SPORTS_ID)) {
                        sections.clear();
                        addSection(0, getActivity().getResources().getString(R.string.sport));

                        /*TODO: not all clear, count from which to which to remove from list,
                        MAYBE SEPARATE ON 2 ARRAYS*/
                        mSport_Culture.clear();
                        mSport_Culture.addAll(result);
                        mAdapter.refreshList(mSport_Culture);
                        currentID = CULTURE_ID;
                        mPresenter.setQuery(currentID);
                        addSection(mSport_Culture.size(), getActivity().getResources().getString(R.string.culture));
                    }
                    else{
                        mSport_Culture.addAll(result);
                        mAdapter.refreshList(mSport_Culture);
                        currentID = SPORTS_ID;
                        mPresenter.setQuery(currentID);

                    }
                    break;
            }
        }
    }

    private void addSection(int firstPosition, String title) {
        GlobesSectionedRecyclerViewAdapter.Section[] dummy = new GlobesSectionedRecyclerViewAdapter.Section[sections.size()];
        sections.add(new GlobesSectionedRecyclerViewAdapter.Section(firstPosition, title));
        mSectionedAdapter.setSections(sections.toArray(dummy));
    }

    @Override
    public void setPresenter(GlobesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        {
            sections.clear();
            mBinder.networkProgress.setVisibility(View.VISIBLE);
            mCars.clear();
            mSport_Culture.clear();
            if (tab.getPosition() == 0) {
                currentID = CARS_ID;
                //mPresenter.getModels(currentID);
                mAdapter.refreshList(mCars);
            } else if (tab.getPosition() == 1) {
                currentID = SPORTS_ID;
               // mPresenter.getModels(currentID);
                mAdapter.refreshList(mSport_Culture);
            }
            mBinder.rssFeed.scrollToPosition(0);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onAccountItemClick(RssParser.RssItem item) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.link));
        startActivity(browserIntent);
        ((MainActivity)getActivity()).mBinder.tvTitle.setText(getActivity().getResources().getString(R.string.title) + " " + item.title);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).mBinder.tvDatetime.setText(getActivity().getResources().getString(R.string.date_time) + " " + getCurrentDateTime());
        mPresenter.stopTimer();
    }
}
