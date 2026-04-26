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
    private String maBl;

    @ManyToOne
    @JoinColumn(name = "maSp")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "maKh")
    private KhachHang khachHang;

    private String noiDung;
    private int soSao;
    private LocalDateTime ngayDang;

    @PrePersist
    protected void onCreate() {
        if (this.ngayDang == null) {
            this.ngayDang = LocalDateTime.now();
        }
    }
}
