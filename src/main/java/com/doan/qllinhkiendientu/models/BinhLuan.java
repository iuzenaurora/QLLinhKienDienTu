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
    @Column(name = "MaBL")
    private String maBl;

    @ManyToOne
    @JoinColumn(name = "MaSP")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "SoSao")
    private int soSao;

    @Column(name = "NgayDang")
    private LocalDateTime ngayDang;

    @PrePersist
    protected void onCreate() {
        if (this.ngayDang == null) {
            this.ngayDang = LocalDateTime.now();
        }
    }
}
