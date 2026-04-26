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
    @JoinColumn(name = "maPn")
    private PhieuNhap phieuNhap;

    @Id
    @ManyToOne
    @JoinColumn(name = "maSp")
    private SanPham sanPham;

    private int soLuong;
    private double giaNhap;
}
