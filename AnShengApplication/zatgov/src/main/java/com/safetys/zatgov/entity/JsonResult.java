package com.safetys.zatgov.entity;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class JsonResult {
    private Boolean success = false;//是否成功
    private String msg;//普通文字描述信息（如要获取的数据、中文提示等）
    private String identify;//标识符描述信息（如英文字符串标识符）

    private String entity;//表单数据



    private int totalCount;//表单数据总数

    private PageInfo page;//列表数据

    private Object json;//json格式字符串

    public JsonResult(){}

    public JsonResult(Boolean success, String identify) {
        super();
        this.success = success;
        this.identify = identify;
    }

    public JsonResult(Boolean success, String msg, String identify) {
        super();
        this.success = success;
        this.msg = msg;
        this.identify = identify;
    }
    public Boolean isSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getIdentify() {
        return identify;
    }
    public void setIdentify(String identify) {
        this.identify = identify;
    }


    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }






    /**
     * @return the json
     */
    public Object getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(Object json) {
        this.json = json;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "JsonResult [success=" + success + ", msg=" + msg
                + ", identify=" + identify + ", entity=" + entity
                + ", totalCount=" + totalCount + ", page=" + page + ", json="
                + json + "]";
    }

}
