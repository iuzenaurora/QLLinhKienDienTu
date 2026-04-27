package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "HoaDon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    @Id
    @Column(name = "MaHD")
    private String maHd;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "TongTien")
    private double tongTien;

    @Column(name = "TrangThai")
    private String trangThai; // "Chưa giao", "Đã giao", ...

    @Column(name = "PhuongThucThanhToan")
    private String phuongThucThanhToan;

    @Column(name = "DiaChiGiaoHang")
    private String diaChiGiaoHang;

    @Column(name = "SoDienThoaiGiaoHang")
    private String soDienThoaiGiaoHang;

    @Column(name = "MaGiaoDichVNPAY")
    private String maGiaoDichVNPAY;

    @Column(name = "NgayThanhToan")
    private LocalDateTime ngayThanhToan;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<ChiTietHoaDon> dsChiTiet;

    @PrePersist
    protected void onCreate() {
        if (this.ngayLap == null) {
            this.ngayLap = LocalDateTime.now();
        }
    }
}
