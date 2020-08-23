package com.jormoba.gymroutine.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jormoba.gymroutine.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5, R.string.tab_text_6};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new FragmentLista("lunes"); break;
            case 1: fragment = new FragmentLista("martes"); break;
            case 2: fragment = new FragmentLista("miercoles"); break;
            case 3: fragment = new FragmentLista("jueves"); break;
            case 4: fragment = new FragmentLista("viernes"); break;
            case 5: fragment = new FragmentLista("sabado"); break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Mostrar 6 páginas
        return 6;
    }
}