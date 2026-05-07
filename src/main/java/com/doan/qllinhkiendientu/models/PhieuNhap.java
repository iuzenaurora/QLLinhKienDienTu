package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PhieuNhap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhap {
    @Id
    @Column(name = "MaPN", columnDefinition = "CHAR(5)")
    private String maPn;

    @ManyToOne
    @JoinColumn(name = "MaNV", columnDefinition = "CHAR(5)")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaNCC", columnDefinition = "CHAR(5)")
    private NhaCungCap nhaCungCap;

    @Column(name = "NgayNhap")
    private LocalDateTime ngayNhap;

    @Column(name = "TongTienNhap", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal tongTienNhap = BigDecimal.ZERO;

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
