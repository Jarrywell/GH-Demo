package com.android.test.demo.fragment.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * des:支持notifyDataSetChanged()修改数据的FragmentPagerAdapter
 * 参见：https://www.jianshu.com/p/354fbb20ffe3
 * Date: 18-5-10 16:14
 */
public abstract class FixedPagerAdapter<T> extends FragmentPagerAdapter {

    private final List<Holder> mHolders = new ArrayList<>();

    public FixedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        while (mHolders.size() <= position) {
            mHolders.add(null);
        }
        Fragment f = (Fragment) super.instantiateItem(container, position);
        Holder<T> holder = new Holder(f, getItemData(position));
        mHolders.set(position, holder);

        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mHolders.set(position, null);
        Fragment fragment = getFragment(object);
        super.destroyItem(container, position, fragment);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = getFragment(object);
        super.setPrimaryItem(container, position, fragment);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Fragment fragment = getFragment(object);
        return super.isViewFromObject(view, fragment);
    }

    /**
     * 重写该接口，使得notifyDataSetChanged()生效
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        Holder<T> holder = (Holder) object;
        if (mHolders.contains(holder)) {
            final T olds = holder.data;
            int position = mHolders.indexOf(holder);
            final T news = getItemData(position);
            if (Objects.equals(olds, news)) {
                return POSITION_UNCHANGED;
            } else {
                position = getPositionOfData(olds);
                return position >= 0 ? position : POSITION_NONE;
            }
        }
        return POSITION_UNCHANGED;
    }

    /**
     * 获取fragment实例
     * @param holder
     * @return
     */
    protected final Fragment getFragment(Object holder) {
        if (holder != null) {
            if (holder instanceof Holder) {
                return ((Holder) holder).f;
            } else if (holder instanceof Fragment) {
                return (Fragment) holder;
            }
        }
        return null;
    }

    /**
     * 获取position位置上fragment上的数据信息
     * @param position
     * @return
     */
    protected abstract T getItemData(int position);


    /**
     * 反查数据位置
     * @param data
     * @return
     */
    protected abstract int getPositionOfData(T data);


    private static class Holder<T> {
        private Fragment f;
        private T data;

        private Holder(Fragment f, T data) {
            this.f = f;
            this.data = data;
        }
    }
}
