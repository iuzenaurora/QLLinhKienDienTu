package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "KhachHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    @Column(name = "MaKH", columnDefinition = "CHAR(5)")
    private String maKh;

    @Column(name = "HoTen", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String hoTen;

    @Column(name = "GioiTinh", columnDefinition = "NVARCHAR(10)")
    private String gioiTinh;

    @Column(name = "DiaChi", columnDefinition = "NVARCHAR(255)")
    private String diaChi;

    @Column(name = "DienThoai", columnDefinition = "VARCHAR(15)")
    private String dienThoai;

    @Column(name = "Email", unique = true, columnDefinition = "NVARCHAR(100)")
    private String email;

    @OneToOne
    @JoinColumn(name = "TenDangNhap", columnDefinition = "VARCHAR(100)")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "khachHang")
    private List<BinhLuan> dsBinhLuan;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    private List<ChiTietGioHang> dsGioHang;
}
