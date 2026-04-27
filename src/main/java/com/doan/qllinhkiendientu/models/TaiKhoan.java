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
    @Column(name = "TenDangNhap")
    private String tenDangNhap;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "Quyen", nullable = false)
    private String quyen;

    @Column(name = "TrangThai")
    private boolean trangThai = true;

    @Column(name = "LoaiTaiKhoan")
    private String loaiTaiKhoan = "LOCAL"; // 'LOCAL', 'GOOGLE', 'FACEBOOK'

    @Column(name = "ProviderId")
    private String providerId;
}
