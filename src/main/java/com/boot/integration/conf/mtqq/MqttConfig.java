package com.boot.integration.conf.mtqq;

public class MqttConfig {

    //连接mqtt客户端id
    private String clientId;

    // 连接mqtt的url
    private String uri;

    // 连接mqtt的用户名
    private String username;

    // 连接mqtt的密码
    private String password;

    //遗嘱topic，当连接断开以后发送的topic
    private String willTopic;

    //遗嘱消息，当连接断开以后发送的内容
    private String willMsg;

    // 连接超时时间(单位秒)
    private int connectionTimeout = 10;

    // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制(单位秒)
    private int keepAliveInterval = 60;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        this.willTopic = willTopic;
    }

    public String getWillMsg() {
        return willMsg;
    }

    public void setWillMsg(String willMsg) {
        this.willMsg = willMsg;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    @Override
    public String toString() {
        return "MqttConfig{" + "clientId='" + clientId + '\'' + ", uri='" + uri + '\'' + ", username='" + username
                + '\'' + ", password='" + password + '\'' + ", willTopic='" + willTopic + '\'' + ", willMsg='" + willMsg
                + '\'' + ", connectionTimeout=" + connectionTimeout + ", keepAliveInterval=" + keepAliveInterval + '}';
    }
}
