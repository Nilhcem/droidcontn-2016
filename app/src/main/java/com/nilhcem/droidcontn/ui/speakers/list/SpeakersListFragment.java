package com.nilhcem.droidcontn.ui.speakers.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.DataProvider;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.ui.BaseFragment;
import com.nilhcem.droidcontn.ui.core.recyclerview.MarginDecoration;
import com.nilhcem.droidcontn.ui.speakers.details.SpeakerDetailsDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SpeakersListFragment extends BaseFragment<SpeakersListPresenter> implements SpeakersListView {

    @Inject Picasso picasso;
    @Inject DataProvider dataProvider;

    @Bind(R.id.speakers_list_loading) ProgressBar loading;
    @Bind(R.id.speakers_list_recyclerview) RecyclerView recyclerView;

    private SpeakersListAdapter adapter;

    public static SpeakersListFragment newInstance() {
        return new SpeakersListFragment();
    }

    @Override
    protected SpeakersListPresenter newPresenter() {
        DroidconApp.get(getContext()).component().inject(this);
        return new SpeakersListPresenter(this, dataProvider);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.speakers_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SpeakersListAdapter(picasso, this);
        recyclerView.addItemDecoration(new MarginDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displaySpeakers(List<Speaker> speakers) {
        adapter.setSpeakers(speakers);
        loading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayLoadingError(Throwable error) {
        loading.setVisibility(View.GONE);
        Snackbar.make(loading, error.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSpeakerDetails(Speaker speaker) {
        SpeakerDetailsDialogFragment.show(speaker, getFragmentManager());
    }
}
