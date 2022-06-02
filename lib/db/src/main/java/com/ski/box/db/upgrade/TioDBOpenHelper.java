package com.ski.box.db.upgrade;

import android.content.Context;
import com.ski.box.db.MigrationHelper;
import com.ski.box.db.table.CurrUserTableDao;
import com.ski.box.db.table.DaoMaster;
import com.ski.box.db.table.IpInfoTableDao;

import org.greenrobot.greendao.database.Database;

/**
 * <pre>
 *     time   : 2020/09/03
 *     desc   : 实现了数据库升级
 * </pre>
 */
public class TioDBOpenHelper extends DaoMaster.OpenHelper {

    public TioDBOpenHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * 数据库升级
     * <p>
     * 需要将所有表的Dao添加于此
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db, ifNotExists);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, ifExists);
                    }
                },

                CurrUserTableDao.class,
                IpInfoTableDao.class
        );
    }
}