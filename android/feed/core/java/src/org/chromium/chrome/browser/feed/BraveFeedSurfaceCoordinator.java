/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.feed;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.supplier.Supplier;
import org.chromium.chrome.browser.feed.library.api.host.action.ActionApi;
import org.chromium.chrome.browser.feed.shared.FeedSurfaceDelegate;
import org.chromium.chrome.browser.native_page.NativePageNavigationDelegate;
import org.chromium.chrome.browser.ntp.SnapScrollHelper;
import org.chromium.chrome.browser.ntp.snippets.SectionHeaderView;
import org.chromium.chrome.browser.profiles.Profile;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.chrome.browser.tabmodel.TabModelSelector;
import org.chromium.chrome.browser.ui.messages.snackbar.SnackbarManager;
import org.chromium.chrome.feed.R;
import org.chromium.ui.UiUtils;

public class BraveFeedSurfaceCoordinator extends FeedSurfaceCoordinator {
    private Activity mActivity;
    private ScrollView mScrollViewForPolicy;
    private View mNtpHeader;
    private FrameLayout mRootView;

    public BraveFeedSurfaceCoordinator(Activity activity, SnackbarManager snackbarManager,
            TabModelSelector tabModelSelector, Supplier<Tab> tabProvider,
            @Nullable SnapScrollHelper snapScrollHelper, @Nullable View ntpHeader,
            @Nullable SectionHeaderView sectionHeaderView, ActionApi actionApi,
            boolean showDarkBackground, FeedSurfaceDelegate delegate,
            @Nullable NativePageNavigationDelegate pageNavigationDelegate, Profile profile) {
        super(activity, snackbarManager, tabModelSelector, tabProvider, snapScrollHelper, ntpHeader,
                sectionHeaderView, actionApi, showDarkBackground, delegate, pageNavigationDelegate,
                profile);
    }

    @Override
    void createScrollViewForPolicy() {
        super.createScrollViewForPolicy();

        // Remove previous view to recreate it a way we need for our NTP.
        UiUtils.removeViewFromParent(mScrollViewForPolicy);
        // Here we need to get rid of resizer and call setFillViewport.
        mScrollViewForPolicy = new ScrollView(mActivity);
        mScrollViewForPolicy.setBackgroundColor(
                ApiCompatibilityUtils.getColor(mActivity.getResources(), R.color.default_bg_color));
        mScrollViewForPolicy.setVerticalScrollBarEnabled(false);

        // Make scroll view focusable so that it is the next focusable view when the url bar clears
        // focus.
        mScrollViewForPolicy.setFocusable(true);
        mScrollViewForPolicy.setFocusableInTouchMode(true);
        mScrollViewForPolicy.setContentDescription(
                mScrollViewForPolicy.getResources().getString(R.string.accessibility_new_tab_page));

        if (mNtpHeader != null) {
            UiUtils.removeViewFromParent(mNtpHeader);
            mScrollViewForPolicy.addView(mNtpHeader);
        }
        mRootView.addView(mScrollViewForPolicy);
        mScrollViewForPolicy.setFillViewport(true);
        mScrollViewForPolicy.requestFocus();
    }
}
