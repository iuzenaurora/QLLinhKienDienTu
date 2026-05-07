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
    @Column(name = "TenDangNhap", columnDefinition = "VARCHAR(100)")
    private String tenDangNhap;

    @Column(name = "MatKhau", columnDefinition = "VARCHAR(255)")
    private String matKhau;

    @Column(name = "Quyen", nullable = false, columnDefinition = "NVARCHAR(20)")
    private String quyen;

    @Column(name = "TrangThai")
    private boolean trangThai = true;

    @Column(name = "LoaiTaiKhoan", columnDefinition = "VARCHAR(50)")
    private String loaiTaiKhoan = "LOCAL"; // 'LOCAL', 'GOOGLE', 'FACEBOOK'

    @Column(name = "ProviderId", columnDefinition = "VARCHAR(255)")
    private String providerId;
}
