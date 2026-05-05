package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "HoaDon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    @Id
    @Column(name = "MaHD", columnDefinition = "CHAR(5)")
    private String maHd;

    @ManyToOne
    @JoinColumn(name = "MaKH", columnDefinition = "CHAR(5)")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaNV", columnDefinition = "CHAR(5)")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaKM", columnDefinition = "CHAR(10)")
    private KhuyenMai khuyenMai;

    @Column(name = "TienGiamGia", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal tienGiamGia = BigDecimal.ZERO;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "TongTien", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal tongTien;

    @Column(name = "TrangThai", columnDefinition = "NVARCHAR(50)")
    private String trangThai = "Chờ thanh toán";

    @Column(name = "PhuongThucThanhToan", columnDefinition = "NVARCHAR(50)")
    private String phuongThucThanhToan;

    @Column(name = "DiaChiGiaoHang", columnDefinition = "NVARCHAR(255)")
    private String diaChiGiaoHang;

    @Column(name = "SoDienThoaiGiaoHang", columnDefinition = "VARCHAR(15)")
    private String soDienThoaiGiaoHang;

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
