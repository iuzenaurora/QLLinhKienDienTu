package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietGioHang")
@IdClass(ChiTietGioHangId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietGioHang {

    @Id
    @ManyToOne
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaSP")
    private SanPham sanPham;

    @Column(name = "SoLuong")
    private int soLuong;
}
