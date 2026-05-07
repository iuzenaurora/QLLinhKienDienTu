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
    @Column(name = "MaNCC", columnDefinition = "CHAR(5)")
    private String maNcc;

    @Column(name = "TenNCC", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String tenNcc;

    @Column(name = "DiaChi", columnDefinition = "NVARCHAR(255)")
    private String diaChi;

    @Column(name = "SoDienThoai", columnDefinition = "VARCHAR(20)")
    private String soDienThoai;

    @Column(name = "Email", columnDefinition = "NVARCHAR(100)")
    private String email;

    @OneToMany(mappedBy = "nhaCungCap")
    private List<SanPham> dsSanPham;
}
