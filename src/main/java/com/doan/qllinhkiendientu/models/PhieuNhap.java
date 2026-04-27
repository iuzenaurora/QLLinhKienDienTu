package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PhieuNhap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhap {
    @Id
    @Column(name = "MaPN")
    private String maPn;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaNCC")
    private NhaCungCap nhaCungCap;

    @Column(name = "NgayNhap")
    private LocalDateTime ngayNhap;

    @Column(name = "TongTienNhap")
    private double tongTienNhap;

    @Column(name = "GhiChu")
    private String ghiChu;

    @OneToMany(mappedBy = "phieuNhap", cascade = CascadeType.ALL)
    private List<ChiTietPhieuNhap> dsChiTiet;

    @PrePersist
    protected void onCreate() {
        if (this.ngayNhap == null) {
            this.ngayNhap = LocalDateTime.now();
        }
    }
}
