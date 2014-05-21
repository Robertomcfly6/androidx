/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package android.support.v17.leanback.widget;

import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.support.v17.leanback.R;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * An abstract {@link Presenter} for rendering a detailed description of an
 * item. Typically this Presenter will be used in a DetailsOveriewRowPresenter.
 *
 * <p>Subclasses will override {@link #onBindDescription} to implement the data
 * binding for this Presenter.
 */
public abstract class AbstractDetailsDescriptionPresenter extends Presenter {

    public static class ViewHolder extends Presenter.ViewHolder {
        private final TextView mTitle;
        private final TextView mSubtitle;
        private final TextView mBody;
        private final int mTitleMargin;
        private final int mUnderTitleBaseline;
        private final int mUnderSubtitleBaseline;
        private final int mBodyLineSpacing;
        private final int mSubtitleAscent;
        private final int mBodyAscent;

        public ViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.lb_details_description_title);
            mSubtitle = (TextView) view.findViewById(R.id.lb_details_description_subtitle);
            mBody = (TextView) view.findViewById(R.id.lb_details_description_body);

            FontMetricsInt titleFontMetricsInt = getFontMetricsInt(mTitle);
            final int titleAscent = view.getResources().getDimensionPixelSize(
                    R.dimen.lb_details_description_title_baseline);
            // Ascent is negative
            mTitleMargin = titleAscent + titleFontMetricsInt.ascent;

            mUnderTitleBaseline = view.getResources().getDimensionPixelSize(
                    R.dimen.lb_details_description_under_title_baseline);
            mUnderSubtitleBaseline = view.getResources().getDimensionPixelSize(
                    R.dimen.lb_details_description_under_subtitle_baseline);

            FontMetricsInt subtitleFontMetricsInt = getFontMetricsInt(mSubtitle);
            mSubtitleAscent = subtitleFontMetricsInt.ascent;

            FontMetricsInt bodyFontMetricsInt = getFontMetricsInt(mBody);
            mBodyAscent = bodyFontMetricsInt.ascent;

            mBodyLineSpacing = view.getResources().getDimensionPixelSize(
                    R.dimen.lb_details_description_body_line_spacing);
        }

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getSubtitle() {
            return mSubtitle;
        }

        public TextView getBody() {
            return mBody;
        }

        private FontMetricsInt getFontMetricsInt(TextView textView) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setTextSize(textView.getTextSize());
            paint.setTypeface(textView.getTypeface());
            return paint.getFontMetricsInt();
        }
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.lb_details_description, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public final void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ViewHolder vh = (ViewHolder) viewHolder;
        DetailsOverviewRow row = (DetailsOverviewRow) item;
        onBindDescription(vh, row.getItem());

        boolean hasTitle = true;
        if (TextUtils.isEmpty(vh.mTitle.getText())) {
            vh.mTitle.setVisibility(View.GONE);
            hasTitle = false;
        } else {
            vh.mTitle.setVisibility(View.VISIBLE);
        }
        setTopMargin(vh.mTitle, vh.mTitleMargin);

        boolean hasSubtitle = true;
        if (TextUtils.isEmpty(vh.mSubtitle.getText())) {
            vh.mSubtitle.setVisibility(View.GONE);
            hasSubtitle = false;
        } else {
            vh.mSubtitle.setVisibility(View.VISIBLE);
            if (hasTitle) {
                setTopMargin(vh.mSubtitle, vh.mUnderTitleBaseline + vh.mSubtitleAscent);
            } else {
                setTopMargin(vh.mSubtitle, 0);
            }
        }

        if (TextUtils.isEmpty(vh.mBody.getText())) {
            vh.mBody.setVisibility(View.GONE);
        } else {
            vh.mBody.setVisibility(View.VISIBLE);
            vh.mBody.setLineSpacing(vh.mBodyLineSpacing - vh.mBody.getLineHeight() -
                    vh.mBody.getLineSpacingExtra(), vh.mBody.getLineSpacingMultiplier());

            if (hasSubtitle) {
                setTopMargin(vh.mBody, vh.mUnderSubtitleBaseline + vh.mBodyAscent);
            } else if (hasTitle) {
                setTopMargin(vh.mBody, vh.mUnderTitleBaseline + vh.mBodyAscent);
            } else {
                setTopMargin(vh.mBody, 0);
            }
        }
    }

    /**
     * Binds the data from the item referenced in the DetailsOverviewRow to the
     * ViewHolder.
     *
     * @param vh The ViewHolder for this details description view.
     * @param item The item from the DetailsOverviewRow being presented.
     */
    protected abstract void onBindDescription(ViewHolder vh, Object item);

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {}

    private void setTopMargin(TextView textView, int topMargin) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        lp.topMargin = topMargin;
        textView.setLayoutParams(lp);
    }
}
