package com.sdk;
     /*云端设备登录信息*/
public class NETDEV_CLOUD_DEV_LOGIN_S {
		public String szDeviceName;              /* 设备名称 Device name */
		public String szDevicePassword;          /* 设备登录密码 (可选) Device login password (optional) */
		public int dwT2UTimeout;                 /* P2P超时时间, 默认为15s P2P timeout (default: 15s) */
}
