package com.bjpowernode.bean;

public class Constant {
    /*
        类型
     */
    public static final String TYPE_COMPUTER = "计算机";
    public static final String TYPE_LITERATURE = "文学";
    public static final String TYPE_MANAGEMENT = "管理";
    public static final String TYPE_ECONOMY = "经济";

    /*
        图书状态
     */
    public static final String STATUS_STORAGE = "入库";
    public static final String STATUS_LEND = "出借";

    /*
        用户状态
     */
    public static final String USER_OK = "正常";
    public static final String USER_FROZEN = "冻结";

    /*
        用户借阅状态
     */
    public static final String USER_LEND_YES = "已借阅";
    public static final String USER_LEND_NO = "未借阅";

    /*
        借阅状态
     */
    public static final String LEND_RETURN = "归还";
    public static final String LEND_LEND = "借出";

    /*
        用户数据文件路径
     */
    public static final String USER_DATA_DIR = "userDataDir";
    public static final String USER_DATA_FILE = "userDataDir/userDataFile.txt";

    /*
        图书数据文件路径
     */
    public static final String BOOK_DATA_DIR = "bookDataDir";
    public static final String BOOK_DATA_FILE = "bookDataDir/bookDataDir.txt";

    /*
        图书数据文件路径
     */
    public static final String LEND_DATA_DIR = "lendDataDir";
    public static final String LEND_DATA_FILE = "lendDataDir/lendDataDir.txt";


}
