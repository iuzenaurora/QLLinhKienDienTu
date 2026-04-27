package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "NhaCungCap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCap {
    @Id
    @Column(name = "MaNCC")
    private String maNcc;

    @Column(name = "TenNCC", nullable = false)
    private String tenNcc;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email")
    private String email;

    @OneToMany(mappedBy = "nhaCungCap")
    private List<SanPham> dsSanPham;
}
