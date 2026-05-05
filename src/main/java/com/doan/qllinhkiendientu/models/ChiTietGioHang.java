package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ChiTietGioHang")
@IdClass(ChiTietGioHangId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietGioHang {

    @Id
    @ManyToOne
    @JoinColumn(name = "MaKH", columnDefinition = "CHAR(5)")
    private KhachHang khachHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaSP", columnDefinition = "CHAR(5)")
    private SanPham sanPham;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "NgayThem")
    private LocalDateTime ngayThem;

    @PrePersist
    protected void onCreate() {
        if (this.ngayThem == null) {
            this.ngayThem = LocalDateTime.now();
        }
    }
}
