package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietHoaDon")
@IdClass(ChiTietHoaDonId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHoaDon {

    @Id
    @ManyToOne
    @JoinColumn(name = "maHd")
    private HoaDon hoaDon;

    @Id
    @ManyToOne
    @JoinColumn(name = "maSp")
    private SanPham sanPham;

    private int soLuong;
    private double donGia;
}
