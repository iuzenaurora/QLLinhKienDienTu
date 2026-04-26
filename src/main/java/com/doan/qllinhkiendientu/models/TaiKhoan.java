package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TaiKhoan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
    @Id
    private String tenDangNhap;
    private String matKhau;
    private String quyen;

    // Giả định mỗi nhân viên hoặc khách hàng có 1 tài khoản
    // Nếu thiết kế DB của bạn khác, có thể điều chỉnh lại ở đây
}
