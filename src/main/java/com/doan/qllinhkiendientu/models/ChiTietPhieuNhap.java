package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

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
    @JoinColumn(name = "MaPN", columnDefinition = "CHAR(5)")
    private PhieuNhap phieuNhap;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaSP", columnDefinition = "CHAR(5)")
    private SanPham sanPham;

    @Column(name = "SoLuongNhap")
    private Integer soLuongNhap;

    @Column(name = "GiaNhap", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal giaNhap;
}
