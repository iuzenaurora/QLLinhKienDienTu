package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "DanhMuc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DanhMuc {
    @Id
    @Column(name = "MaDM", columnDefinition = "CHAR(5)")
    private String maDm;

    @Column(name = "TenDM", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String tenDm;

    @OneToMany(mappedBy = "danhMuc")
    private List<SanPham> dsSanPham;
}
