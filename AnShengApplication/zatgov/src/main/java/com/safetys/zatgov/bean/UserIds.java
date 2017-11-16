package com.safetys.zatgov.bean;

import java.io.Serializable;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class UserIds implements Serializable {

    private String gridName;
    private long gridId;
    private boolean checked;


    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public long getGridId() {
        return gridId;
    }
    public void setGridId(long gridId) {
        this.gridId = gridId;
    }
    public String getGridName() {
        return gridName;
    }
    public void setGridName(String gridName) {
        this.gridName = gridName;
    }
}
