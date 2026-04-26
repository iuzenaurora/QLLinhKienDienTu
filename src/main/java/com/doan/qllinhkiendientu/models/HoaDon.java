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
    private String maHd;

    @ManyToOne
    @JoinColumn(name = "maKh")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "maNv")
    private NhanVien nhanVien;

    private LocalDateTime ngayLap;
    private double tongTien;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<ChiTietHoaDon> dsChiTiet;

    @PrePersist
    protected void onCreate() {
        if (this.ngayLap == null) {
            this.ngayLap = LocalDateTime.now();
        }
    }
}
