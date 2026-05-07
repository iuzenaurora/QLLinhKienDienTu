package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

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
    @JoinColumn(name = "MaHD", columnDefinition = "CHAR(5)")
    private HoaDon hoaDon;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaSP", columnDefinition = "CHAR(5)")
    private SanPham sanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal donGia;
}
