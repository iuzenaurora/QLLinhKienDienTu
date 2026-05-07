package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TokenQuenMatKhau")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenQuenMatKhau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaToken")
    private Integer maToken;

    @ManyToOne
    @JoinColumn(name = "TenDangNhap", columnDefinition = "VARCHAR(100)")
    private TaiKhoan taiKhoan;

    @Column(name = "ChuoiToken", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String chuoiToken;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NgayHetHan", nullable = false)
    private LocalDateTime ngayHetHan;

    @PrePersist
    protected void onCreate() {
        if (this.ngayTao == null) {
            this.ngayTao = LocalDateTime.now();
        }
    }
}
