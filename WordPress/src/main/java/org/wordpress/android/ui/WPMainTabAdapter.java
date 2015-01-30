package org.wordpress.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import org.wordpress.android.models.ReaderTag;
import org.wordpress.android.ui.reader.ReaderPostListFragment;
import org.wordpress.android.ui.reader.ReaderTypes;
import org.wordpress.android.util.AppLog;

/**
 * pager adapter containing tab fragments used by WPMainActivity
 */
class WPMainTabAdapter extends FragmentStatePagerAdapter {
    static final int NUM_TABS = 4;
    static final int TAB_READER = 0;
    static final int TAB_SITES = 1;
    static final int TAB_ME = 2;
    static final int TAB_NOTIFS = 3;

    final SparseArray<Fragment> mFragments = new SparseArray<>(NUM_TABS);

    public WPMainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        // work around "Fragement no longer exists for key" Android bug
        // by catching the IllegalStateException
        // https://code.google.com/p/android/issues/detail?id=42601
        try {
            AppLog.v(AppLog.T.READER, "WPTabAdapter pager > adapter restoreState");
            super.restoreState(state, loader);
        } catch (IllegalStateException e) {
            AppLog.e(AppLog.T.READER, e);
        }
    }

    @Override
    public Parcelable saveState() {
        AppLog.v(AppLog.T.READER, "WPTabAdapter pager > adapter saveState");
        return super.saveState();
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    public boolean isValidPosition(int position) {
        return (position >= 0 && position < getCount());
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_SITES:
                break;
            case TAB_READER:
                break;
            case TAB_ME:
                break;
            case TAB_NOTIFS:
                break;
        }
        return ReaderPostListFragment.newInstance(ReaderTag.getDefaultTag(), ReaderTypes.ReaderPostListType.TAG_FOLLOWED);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO: use icons rather than text
        switch (position) {
            case TAB_SITES:
                return "Sites";
            case TAB_READER:
                return "Reader";
            case TAB_ME:
                return "Me";
            case TAB_NOTIFS:
                return "Notes";
            default:
                return super.getPageTitle(position);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object item = super.instantiateItem(container, position);
        if (item instanceof Fragment) {
            mFragments.put(position, (Fragment) item);
        }
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragments.remove(position);
    }

    public Fragment getFragment(int position) {
        if (isValidPosition(position)) {
            return mFragments.get(position);
        } else {
            return null;
        }
    }
}
