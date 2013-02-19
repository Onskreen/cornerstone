package android.app;

/**
 * Author: Onskreen
 * Date: 28/02/2011
 *
 * Callback interface to trigger change in focused panel
 * in Cornerstone.
 *
 * {@hide}
 */
interface ICornerstoneManager {
    void onCornerstonePanelFocusChanged(String pkgName, boolean focus, int panelIndex);
}