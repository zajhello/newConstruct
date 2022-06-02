package com.ski.box.common.page;

import android.content.Context;
import android.view.MenuItem;

import java.io.Serializable;

/**
 * desc : ActionBar右侧按钮
 */
public interface OptionsMenu extends Serializable {
    /**
     * 菜单id
     *
     * @return 菜单id
     */
    int getMenuId();

    /**
     * 响应事件
     *
     * @param context 上下文
     * @param item    菜单子项
     */
    void onOptionsItemSelected(Context context, MenuItem item);
}