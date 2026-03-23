package com.nqdung.paymenthub.constant;

public class CategoryConstants {
    // Status constant
    public static final int STATUS_NEW = 1;         // Mới
    public static final int STATUS_PENDING = 3;     // Chờ duyệt
    public static final int STATUS_APPROVED = 4;    // Đã duyệt
    public static final int STATUS_REJECTED = 5;    // Từ chối
    public static final int STATUS_CANCELLED = 7;   // Hủy duyệt

    // isDisplay constant
    public static final int DISPLAY_ALLOW_DELETE = 1;   // Chưa duyệt => Cho phép xóa
    public static final int DISPLAY_NO_DELETE = 0;      // Đã duyệt => Không cho phép xóa
}
