package com.ahao.androidlib.adapter.listView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.util.SparseArrayCompat;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahao.androidlib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by Avalon on 2016/8/31.
 */
public class ViewHolder {
    protected SparseArrayCompat<View> mViews;
    protected int mPosition;
    private View mConvertView;
    private Context mContext;
    protected int mLayoutId;


    public ViewHolder(Context context, View itemView, ViewGroup parent, int position){
        this.mViews = new SparseArrayCompat<View>();
        this.mPosition = position;
        this.mConvertView = itemView;
        this.mContext = context;
        //setTag
        mConvertView.setTag(R.string.app_name, this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, @LayoutRes int layoutId, int position){
        ViewHolder holder = null;
        if(convertView==null){
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            holder = new ViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
        } else {
            holder = (ViewHolder) convertView.getTag(R.string.app_name);
        }
        return holder;
    }

    public <T extends View> T getView(@IdRes int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public void updatePosition(int position){
        this.mPosition = position;
    }

    public int getItemPosition(){
        return mPosition;
    }




    /** 设置文字 */
    public ViewHolder setText(@IdRes int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setImageUrl(@IdRes int viewId, String url){
        setImageUrl(viewId, url, 0, null);
        return this;
    }

    public ViewHolder setImageUrl(@IdRes int viewId, String url, RequestListener listener){
        setImageUrl(viewId, url, 0, listener);
        return this;
    }

    public ViewHolder setImageUrl(@IdRes int viewId, String url, @DrawableRes int placeholder){
        setImageUrl(viewId, url, placeholder, null);
        return this;
    }

    /** 设置有加载监听器、有占位资源的图片 */
    public ViewHolder setImageUrl(@IdRes int viewId, String url, @DrawableRes int placeholder,
                                  RequestListener listener){
        Glide.with(mContext)
                .load(url)
                .placeholder(placeholder)
                .listener(listener)
                .crossFade()
                .into((ImageView) getView(viewId));
        return this;
    }

    public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }


    public ViewHolder setAlpha(@IdRes int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /** 设置超链接 */
    public ViewHolder linkify(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /** 设置字体类型
     *  “sans”, “serif”, “monospace"
     */
    public ViewHolder setTypeface(Typeface typeface, @IdRes int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(@IdRes int viewId, boolean checked)
    {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /** 设置margin */
    public ViewHolder setMargin(@IdRes int viewId, int margin){
        setMargin(viewId, margin, margin, margin, margin);
        return this;
    }

    /** 设置margin */
    public ViewHolder setMargin(@IdRes int viewId, int leftMargin, int topMargin, int rightMargin, int bottomMargin){
        View view = getView(viewId);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        view.setLayoutParams(lp);
        return this;
    }

    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener){
        View view = getView(viewId);
        view.setClickable(true);
        view.setOnLongClickListener(listener);
        return this;
    }
}
