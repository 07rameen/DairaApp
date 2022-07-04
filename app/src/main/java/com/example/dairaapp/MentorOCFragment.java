package com.example.dairaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MentorOCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentorOCFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MentorOCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentorOCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MentorOCFragment newInstance(String param1, String param2) {
        MentorOCFragment fragment = new MentorOCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mentor_o_c, container, false);
        tabLayout = view.findViewById(R.id.tablelayout_mentor_oc);
        viewPager = view.findViewById(R.id.viewpage_mentor_oc);
        tabLayout.setupWithViewPager(viewPager);
        viewpagerAdapter VP = new viewpagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        VP.addFragment(new MentorOCShowAllFragment(), "SHOW ALL");
        VP.addFragment(new MentorOCAddNewFragment(), "ADD NEW");
        VP.addFragment(new MentorOCAssignFragment(), "ASSIGN");
        viewPager.setAdapter(VP);
        return view;
    }
}