package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SanPham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {
    @Id
    @Column(name = "MaSP")
    private String maSp;

    @Column(name = "TenSP", nullable = false)
    private String tenSp;

    @ManyToOne
    @JoinColumn(name = "MaDM")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "MaNCC")
    private NhaCungCap nhaCungCap;

    @Column(name = "Gia", nullable = false)
    private double gia;

    @Column(name = "SoLuong")
    private long soLuong;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @Column(name = "TinhTrang")
    private String tinhTrang = "Còn hàng";
}
