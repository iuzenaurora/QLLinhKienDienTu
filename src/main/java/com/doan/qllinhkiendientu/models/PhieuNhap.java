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
    private String maPn;

    @ManyToOne
    @JoinColumn(name = "maNv")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "maNcc")
    private NhaCungCap nhaCungCap;

    private LocalDateTime ngayNhap;
    private double tongTien;

    @OneToMany(mappedBy = "phieuNhap", cascade = CascadeType.ALL)
    private List<ChiTietPhieuNhap> dsChiTiet;

    @PrePersist
    protected void onCreate() {
        if (this.ngayNhap == null) {
            this.ngayNhap = LocalDateTime.now();
        }
    }
}
