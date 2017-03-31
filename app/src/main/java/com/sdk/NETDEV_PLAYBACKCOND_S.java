package com.sdk;

public class NETDEV_PLAYBACKCOND_S {
    public int   dwChannelID;                /* 回放的通道  Playback channel */
    public long   tBeginTime;                 /* 回放开始时间  Playback start time */
    public long   tEndTime;                   /* 回放结束时间  Playback end time */
    public int   dwLinkMode;                 /* 传输协议,参见枚举#NETDEV_PROTOCAL_E  Transport protocol, see enumeration #NETDEV_PROTOCAL_E */
    public int  hPlayWnd;                   /* 播放窗口句柄  Play window handle */
    public int   dwFileType;                 /* 录像存储类型,参见枚举#NETDEV_PLAN_STORE_TYPE_E  Recording storage type, see enumeration #NETDEV_PLAN_STORE_TYPE_E */
    public int   dwDownloadSpeed;            /* 下载速度 参见枚举#NETDEV_E_DOWNLOAD_SPEED_E */
}
