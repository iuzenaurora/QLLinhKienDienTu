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
    @Column(name = "MaNV")
    private String maNv;

    @Column(name = "HoTen", nullable = false)
    private String hoTen;

    @Column(name = "ChucVu")
    private String chucVu;

    @Column(name = "DienThoai")
    private String dienThoai;

    @OneToOne
    @JoinColumn(name = "TenDangNhap")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "nhanVien")
    private List<PhieuNhap> dsPhieuNhap;
}
