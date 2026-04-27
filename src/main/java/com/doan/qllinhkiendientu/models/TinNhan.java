package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TinNhan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TinNhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTN")
    private Integer maTn;

    @Column(name = "NguoiGui")
    private String nguoiGui;

    @Column(name = "NguoiNhan")
    private String nguoiNhan;

    @Column(name = "NoiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @Column(name = "ThoiGian")
    private LocalDateTime thoiGian;

    @Column(name = "DaDoc")
    private boolean daDoc = false;

    @PrePersist
    protected void onCreate() {
        if (this.thoiGian == null) {
            this.thoiGian = LocalDateTime.now();
        }
    }
}
