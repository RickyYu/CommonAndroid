package com.safetys.zatgov.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
@Table(name="TradeType")
public class TradeType {
    @Column(name="id",isId=true)
    private int id;
    @Column(name="typeCode")
    private String typeCode;
    @Column(name="typeName")
    private String typeName;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
