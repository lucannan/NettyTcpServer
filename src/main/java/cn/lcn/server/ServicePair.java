package cn.lcn.server;

/**
 * Created by LCN on 2016/5/19.
 */
public class ServicePair {
    public String servicename;
    public String servicequeryData;

    public ServicePair(String servicename, String servicequeryData) {
        this.servicename = servicename;
        this.servicequeryData = servicequeryData;
    }

    public ServicePair() {
    }
}
