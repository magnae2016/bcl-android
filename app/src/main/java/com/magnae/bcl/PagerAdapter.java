package com.magnae.bcl;


import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final CharSequence title_recommendation = HtmlCompat.fromHtml("<font face=\"sans-serif-smallcaps\"><small><sup><small>&#35;</small></sup></small></font>추천", HtmlCompat.FROM_HTML_MODE_COMPACT);
    private static final CharSequence title_popularity = HtmlCompat.fromHtml("<font face=\"sans-serif-smallcaps\"><small><sup><small>&#35;</small></sup></small></font>인기", HtmlCompat.FROM_HTML_MODE_COMPACT);
    private static final CharSequence title_movie = HtmlCompat.fromHtml("<font face=\"sans-serif-smallcaps\"><small><sup><small>&#35;</small></sup></small></font>영화", HtmlCompat.FROM_HTML_MODE_COMPACT);
    private static final CharSequence title_lecture = HtmlCompat.fromHtml("<font face=\"sans-serif-smallcaps\"><small><sup><small>&#35;</small></sup></small></font>문화", HtmlCompat.FROM_HTML_MODE_COMPACT);
    private static final CharSequence title_notice = HtmlCompat.fromHtml("<font face=\"sans-serif-smallcaps\"><small><sup><small>&#35;</small></sup></small></font>공지", HtmlCompat.FROM_HTML_MODE_COMPACT);

    private static final CharSequence tabTitles[] = new CharSequence[]{
            title_recommendation,
            title_popularity,
            title_movie,
            title_lecture,
            title_notice
    };

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new RecommendationFragment();
            case 1:
                return new PopularityFragment();
            case 2:
                return new MovieFragment();
            case 3:
                return new LectureFragment();
            case 4:
                return new NoticeFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

}
