package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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

    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "SoSao")
    private int soSao;

    @Column(name = "NgayDang", columnDefinition = "DATE")
    private LocalDate ngayDang;

    @PrePersist
    protected void onCreate() {
        if (this.ngayDang == null) {
            this.ngayDang = LocalDate.now();
        }
    }
}
