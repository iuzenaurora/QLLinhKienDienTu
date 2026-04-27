package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "NhanVien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    @Id
    @Column(name = "MaNV", columnDefinition = "CHAR(5)")
    private String maNv;

    @Column(name = "HoTen", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String hoTen;

    @Column(name = "ChucVu", columnDefinition = "NVARCHAR(50)")
    private String chucVu;

    @Column(name = "DienThoai", columnDefinition = "VARCHAR(15)")
    private String dienThoai;

    @OneToOne
    @JoinColumn(name = "TenDangNhap", columnDefinition = "VARCHAR(100)")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "nhanVien")
    private List<PhieuNhap> dsPhieuNhap;
}
