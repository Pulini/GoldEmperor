package com.goldemperor.PgdActivity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Nova on 2017/9/9.
 */


public class QtyPass {

    private float fqtypass;


    private float fqtyprocesspass;




    public QtyPass() {
    }
    public float getFqtypass() {
        return fqtypass;
    }
    public void setFqtypass(int fqtypass) {
        this.fqtypass = fqtypass;
    }

    public float getFqtyprocesspass() {
        return fqtyprocesspass;
    }
    public void setFqtyprocesspass(int fqtyprocesspass) {
        this.fqtyprocesspass = fqtyprocesspass;
    }

}
