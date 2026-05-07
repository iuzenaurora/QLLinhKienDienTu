package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BinhLuan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinhLuan {
    @Id
    @Column(name = "MaBL", columnDefinition = "CHAR(5)")
    private String maBl;

    @ManyToOne
    @JoinColumn(name = "MaSP", columnDefinition = "CHAR(5)")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaKH", columnDefinition = "CHAR(5)")
    private KhachHang khachHang;

    @Column(name = "NoiDung", columnDefinition = "NVARCHAR(500)")
    private String noiDung;

    @Column(name = "SoSao")
    private int soSao;

    @Column(name = "NgayDang")
    private LocalDateTime ngayDang;

    @Column(name = "TrangThai")
    private boolean trangThai = true;

    @PrePersist
    protected void onCreate() {
        if (this.ngayDang == null) {
            this.ngayDang = LocalDateTime.now();
        }
    }
}
