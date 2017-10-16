package com.safetys.nbsxs.utils;

import java.util.List;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

public class DbUtils {

	/**
	 * 删除数据表
	 * @param db
	 * @param mClass
	 */
	public void dropTable(DbManager db,Class<?> mClass){
		try {
			db.dropTable(mClass);
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 获取Table表中的记录数
	 * @param db
	 * @param mClass
	 * @return
	 */
	public int getTableCount(DbManager db,Class<?> mClass){
		int count = 0;
		try {
			count = (int) db.selector(mClass).count();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 存單條數據
	 * @param db
	 * @param mObject
	 */
	public boolean saveData(DbManager db,Object mObject){
		try {
			db.save(mObject);
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 存列表數據
	 * @param db
	 * @param mObjects
	 */
	public boolean saveListData(DbManager db,List<?> mObjects){
		if(mObjects == null){
			return true;
		}
		for(int i=0;i<mObjects.size();i++){
			if(!saveData(db, mObjects.get(i))){
				return false;
			}
		}
		return true;
	}
}
