package com.safetys.greenrobot.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.safetys.zatgov.bean.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserCompany = new Property(1, String.class, "userCompany", false, "USER_COMPANY");
        public final static Property FactName = new Property(2, String.class, "factName", false, "FACT_NAME");
        public final static Property IsGridPerson = new Property(3, boolean.class, "isGridPerson", false, "IS_GRID_PERSON");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_COMPANY\" TEXT," + // 1: userCompany
                "\"FACT_NAME\" TEXT," + // 2: factName
                "\"IS_GRID_PERSON\" INTEGER NOT NULL );"); // 3: isGridPerson
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userCompany = entity.getUserCompany();
        if (userCompany != null) {
            stmt.bindString(2, userCompany);
        }
 
        String factName = entity.getFactName();
        if (factName != null) {
            stmt.bindString(3, factName);
        }
        stmt.bindLong(4, entity.getIsGridPerson() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userCompany = entity.getUserCompany();
        if (userCompany != null) {
            stmt.bindString(2, userCompany);
        }
 
        String factName = entity.getFactName();
        if (factName != null) {
            stmt.bindString(3, factName);
        }
        stmt.bindLong(4, entity.getIsGridPerson() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userCompany
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // factName
            cursor.getShort(offset + 3) != 0 // isGridPerson
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserCompany(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFactName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsGridPerson(cursor.getShort(offset + 3) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
