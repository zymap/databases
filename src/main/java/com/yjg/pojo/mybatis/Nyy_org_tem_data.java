package com.yjg.pojo.mybatis;

/**
 * Created by evan on 17-9-3.
 */
public class Nyy_org_tem_data {
    private String duid;    //由轮训服务为本条记录生成一个 uuid 作为索引
    private String cdate;   //读取记录的日期 格式为 2017-07-17 , string 类型
    private String ctime;   //读取记录的时间 格式为 01:56:41
    private int status;     //状态, integer 类型, 10 为正常, 20 为读取失败
    private String borg;    //所属机构, 这里暂时固定为 建工集团的:4dc259ff-8750-4532-9240-de0e63f06776
    private String bdevice; //所属的设备, 轮训的哪个设备的,就把 nyy_org_device 的duid 对应在这里
    private float tem;      //读取到的温度
    private int modeset;    //读取到的设备模式 10 制冷 20 制热 30 送⻛风
    private int devicestatus;//读取到的设备状态 10 开机 20 关机
    private int wind;       //读取到的设备风量 10 auto 11 一档风 12 二档风 13 三挡风
    private float temset;   //设备设定的温度

    public String getDuid() {
        return duid;
    }

    public void setDuid(String duid) {
        this.duid = duid;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBorg() {
        return borg;
    }

    public void setBorg(String borg) {
        this.borg = borg;
    }

    public String getBdevice() {
        return bdevice;
    }

    public void setBdevice(String bdevice) {
        this.bdevice = bdevice;
    }

    public float getTem() {
        return tem;
    }

    public void setTem(float tem) {
        this.tem = tem;
    }

    public int getModeset() {
        return modeset;
    }

    public void setModeset(int modeset) {
        this.modeset = modeset;
    }

    public int getDevicestatus() {
        return devicestatus;
    }

    public void setDevicestatus(int devicestatus) {
        this.devicestatus = devicestatus;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public float getTemset() {
        return temset;
    }

    public void setTemset(float temset) {
        this.temset = temset;
    }
}
