/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

// clang-format off
// #import {addSingletonGetter, sendWithPromise} from 'chrome://resources/js/cr.m.js';
// clang-format on

cr.define('settings', function() {
  /** @interface */
  /* #export */ class BraveAppearanceBrowserProxy {
    /**
     * @return {!Promise<Array>}
     */
    getBraveThemeList() {}
    /**
     * @return {!Promise<Number>}
     */
    getBraveThemeType() {}
    /**
     * @param {Number} type
     */
    setBraveThemeType(value) {}
  }

  /**
   * @implements {settings.BraveAppearanceBrowserProxy}
   */
  /* #export */ class BraveAppearanceBrowserProxyImpl {
    /** @override */
    getBraveThemeList() {
      return new Promise(resolve => chrome.braveTheme.getBraveThemeList(resolve))
    }
    getBraveThemeType() {
      return cr.sendWithPromise('getBraveThemeType');
    }
    setBraveThemeType(value) {
      chrome.send('setBraveThemeType', [value]);
    }
  }

  cr.addSingletonGetter(BraveAppearanceBrowserProxyImpl);

  // #cr_define_end
  return {
    BraveAppearanceBrowserProxy: BraveAppearanceBrowserProxy,
    BraveAppearanceBrowserProxyImpl: BraveAppearanceBrowserProxyImpl,
  };
});
