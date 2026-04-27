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
    @Column(name = "MaKH")
    private String maKh;

    @Column(name = "HoTen", nullable = false)
    private String hoTen;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "DienThoai")
    private String dienThoai;

    @Column(name = "Email", unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "TenDangNhap")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "khachHang")
    private List<BinhLuan> dsBinhLuan;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    private List<ChiTietGioHang> dsGioHang;
}
