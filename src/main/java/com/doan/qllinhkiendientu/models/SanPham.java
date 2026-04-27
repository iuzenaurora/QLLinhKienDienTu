package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SanPham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {
    @Id
    @Column(name = "MaSP", columnDefinition = "CHAR(5)")
    private String maSp;

    @Column(name = "TenSP", nullable = false, columnDefinition = "NVARCHAR(150)")
    private String tenSp;

    @ManyToOne
    @JoinColumn(name = "MaDM", columnDefinition = "CHAR(5)")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "MaNCC", columnDefinition = "CHAR(5)")
    private NhaCungCap nhaCungCap;

    @Column(name = "Gia", nullable = false, columnDefinition = "DECIMAL(18,2)")
    private BigDecimal gia;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "MoTa", columnDefinition = "NVARCHAR(255)")
    private String moTa;

    @Column(name = "HinhAnh", columnDefinition = "NVARCHAR(255)")
    private String hinhAnh;

    @Column(name = "TinhTrang", columnDefinition = "NVARCHAR(30)")
    private String tinhTrang = "Còn hàng";
}
