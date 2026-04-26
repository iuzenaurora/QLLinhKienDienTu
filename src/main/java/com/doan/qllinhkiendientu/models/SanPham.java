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
    private String maSp;
    private String tenSp;

    @ManyToOne
    @JoinColumn(name = "maDm")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "maNcc")
    private NhaCungCap nhaCungCap;

    private double gia;
    private long soLuong;
    private String moTa;
    private String hinhAnh;
    private String tinhTrang;
}
