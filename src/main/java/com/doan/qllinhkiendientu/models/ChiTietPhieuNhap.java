package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietPhieuNhap")
@IdClass(ChiTietPhieuNhapId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuNhap {

    @Id
    @ManyToOne
    @JoinColumn(name = "MaPN")
    private PhieuNhap phieuNhap;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaSP")
    private SanPham sanPham;

    @Column(name = "SoLuongNhap")
    private int soLuongNhap;

    @Column(name = "GiaNhap")
    private double giaNhap;
}
