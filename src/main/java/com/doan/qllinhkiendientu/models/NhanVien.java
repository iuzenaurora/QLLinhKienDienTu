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
    private String maNv;
    private String tenNv;
    private String gioiTinh;
    private String sdt;
    private String diaChi;
    private String chucVu;

    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "nhanVien")
    private List<PhieuNhap> dsPhieuNhap;
}
