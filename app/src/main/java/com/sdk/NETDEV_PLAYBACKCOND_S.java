package com.sdk;

public class NETDEV_PLAYBACKCOND_S {
    public int   dwChannelID;                /* �طŵ�ͨ��  Playback channel */
    public long   tBeginTime;                 /* �طſ�ʼʱ��  Playback start time */
    public long   tEndTime;                   /* �طŽ���ʱ��  Playback end time */
    public int   dwLinkMode;                 /* ����Э��,�μ�ö��#NETDEV_PROTOCAL_E  Transport protocol, see enumeration #NETDEV_PROTOCAL_E */
    public int  hPlayWnd;                   /* ���Ŵ��ھ��  Play window handle */
    public int   dwFileType;                 /* ¼��洢����,�μ�ö��#NETDEV_PLAN_STORE_TYPE_E  Recording storage type, see enumeration #NETDEV_PLAN_STORE_TYPE_E */
    public int   dwDownloadSpeed;            /* �����ٶ� �μ�ö��#NETDEV_E_DOWNLOAD_SPEED_E */
}
