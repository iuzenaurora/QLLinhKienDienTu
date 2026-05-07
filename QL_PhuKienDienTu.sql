CREATE DATABASE QL_PHUKIEN_DIENTU
GO
USE QL_PHUKIEN_DIENTU;
GO
-- ==========================================
-- PHẦN 1: TÀI KHOẢN & PHÂN QUYỀN
-- ==========================================

-- 1. BẢNG TÀI KHOẢN (Hỗ trợ đăng nhập Local & Google OAuth2)
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(100) PRIMARY KEY, 
    MatKhau VARCHAR(255),                 
    Quyen NVARCHAR(20) NOT NULL,          -- ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER
    TrangThai BIT DEFAULT 1,              -- 1: Hoạt động, 0: Bị khóa
    LoaiTaiKhoan VARCHAR(50) DEFAULT 'LOCAL', -- 'LOCAL' hoặc 'GOOGLE'
    ProviderId VARCHAR(255) NULL          
)

-- 2. BẢNG TOKEN QUÊN MẬT KHẨU [MỚI BỔ SUNG]
CREATE TABLE TokenQuenMatKhau (
    MaToken INT PRIMARY KEY IDENTITY(1,1),
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    ChuoiToken VARCHAR(255) NOT NULL UNIQUE, -- Chuỗi UUID sinh ra ngẫu nhiên
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayHetHan DATETIME NOT NULL             -- Thường set +15 phút từ lúc tạo
)

-- 3. BẢNG KHÁCH HÀNG 
CREATE TABLE KhachHang (
    MaKH CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    GioiTinh NVARCHAR(10),
    DiaChi NVARCHAR(255),
    DienThoai VARCHAR(15),
    Email NVARCHAR(100) UNIQUE,
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap)
)

-- 4. BẢNG NHÂN VIÊN 
CREATE TABLE NhanVien (
    MaNV CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    ChucVu NVARCHAR(50),
    DienThoai VARCHAR(15),
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap)
)

-- ==========================================
-- PHẦN 2: DANH MỤC & SẢN PHẨM
-- ==========================================

-- 5. BẢNG DANH MỤC
CREATE TABLE DanhMuc (
    MaDM CHAR(5) PRIMARY KEY,
    TenDM NVARCHAR(100) NOT NULL
)

-- 6. BẢNG NHÀ CUNG CẤP
CREATE TABLE NhaCungCap (
    MaNCC CHAR(5) PRIMARY KEY,
    TenNCC NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SoDienThoai VARCHAR(20),
    Email NVARCHAR(100)
)

-- 7. BẢNG SẢN PHẨM
CREATE TABLE SanPham (
    MaSP CHAR(5) PRIMARY KEY,
    TenSP NVARCHAR(150) NOT NULL,
    MaDM CHAR(5) FOREIGN KEY REFERENCES DanhMuc(MaDM),
    MaNCC CHAR(5) FOREIGN KEY REFERENCES NhaCungCap(MaNCC), 
    Gia DECIMAL(18,2) NOT NULL,
    SoLuong INT DEFAULT 0,
    MoTa NVARCHAR(255),
    HinhAnh NVARCHAR(255),
	TinhTrang NVARCHAR(30) DEFAULT N'Còn hàng'
)

-- 8. BẢNG BÌNH LUẬN (Thêm cột Kiểm duyệt)
CREATE TABLE BinhLuan (
    MaBL CHAR(5) PRIMARY KEY,
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
    NoiDung NVARCHAR(500),
    SoSao INT CHECK(SoSao BETWEEN 1 AND 5),
    NgayDang DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1 -- [MỚI] 1: Đang hiển thị, 0: Bị Admin ẩn/xóa
)

-- ==========================================
-- PHẦN 3: GIAO DỊCH (HÓA ĐƠN & KHUYẾN MÃI)
-- ==========================================

-- 9. BẢNG KHUYẾN MÃI / VOUCHER [MỚI BỔ SUNG]
CREATE TABLE KhuyenMai (
    MaKM CHAR(10) PRIMARY KEY, -- VD: 'TET2026', 'GIAM10K'
    TenKM NVARCHAR(100),
    PhanTramGiam INT DEFAULT 0,        -- Giảm theo % (VD: 10%)
    TienGiamToiDa DECIMAL(18,2) DEFAULT 0, -- Số tiền giảm tối đa nếu dùng %
    DonToiThieu DECIMAL(18,2) DEFAULT 0,   -- Đơn hàng tối thiểu để áp dụng
    SoLuong INT DEFAULT 100,           -- Số lượng mã có thể nhập
    NgayBatDau DATETIME,
    NgayKetThuc DATETIME,
    TrangThai BIT DEFAULT 1            -- 1: Đang kích hoạt, 0: Đã tắt/Hết hạn
)

-- 10. BẢNG HÓA ĐƠN (Gộp NgayThanhToan và Cột Khuyến mãi)
CREATE TABLE HoaDon (
    MaHD CHAR(5) PRIMARY KEY,
    MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaNV CHAR(5) FOREIGN KEY REFERENCES NhanVien(MaNV), -- Null nếu khách tự mua online
    MaKM CHAR(10) FOREIGN KEY REFERENCES KhuyenMai(MaKM), -- [MỚI] Mã Voucher đã áp dụng
    TienGiamGia DECIMAL(18,2) DEFAULT 0,                  -- [MỚI] Số tiền được giảm
    NgayLap DATETIME DEFAULT GETDATE(),
    NgayThanhToan DATETIME NULL,                          -- Dùng khi quẹt thẻ VNPay xong
    TongTien DECIMAL(18,2),                               -- Tiền cuối cùng khách phải trả
    TrangThai NVARCHAR(50) DEFAULT N'Chờ thanh toán',
	PhuongThucThanhToan NVARCHAR(50),                     -- 'COD' hoặc 'VNPAY'
	DiaChiGiaoHang NVARCHAR(255),
	SoDienThoaiGiaoHang VARCHAR(15)
)

-- 11. BẢNG CHI TIẾT HÓA ĐƠN
CREATE TABLE ChiTietHoaDon (
    MaHD CHAR(5) FOREIGN KEY REFERENCES HoaDon(MaHD),
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    SoLuong INT,
    DonGia DECIMAL(18,2), 
    PRIMARY KEY (MaHD, MaSP)
)

-- 12. BẢNG CHI TIẾT GIỎ HÀNG (Dùng khi muốn lưu giỏ hàng vào DB thay vì Session)
CREATE TABLE ChiTietGioHang (
	MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
	MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
	SoLuong INT,
    NgayThem DATETIME DEFAULT GETDATE(), -- [MỚI] Để quản lý hàng tồn trong giỏ lâu ngày
	PRIMARY KEY (MaKH, MaSP)
)

-- ==========================================
-- PHẦN 4: QUẢN LÝ KHO & HỆ THỐNG
-- ==========================================

-- 13. BẢNG PHIẾU NHẬP KHO
CREATE TABLE PhieuNhap (
    MaPN CHAR(5) PRIMARY KEY,
    MaNV CHAR(5) FOREIGN KEY REFERENCES NhanVien(MaNV), 
    MaNCC CHAR(5) FOREIGN KEY REFERENCES NhaCungCap(MaNCC),
    NgayNhap DATETIME DEFAULT GETDATE(),
    TongTienNhap DECIMAL(18,2) DEFAULT 0,
    GhiChu NVARCHAR(255)
)

-- 14. BẢNG CHI TIẾT PHIẾU NHẬP
CREATE TABLE ChiTietPhieuNhap (
    MaPN CHAR(5) FOREIGN KEY REFERENCES PhieuNhap(MaPN),
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    SoLuongNhap INT,
    GiaNhap DECIMAL(18,2),
    PRIMARY KEY (MaPN, MaSP)
)

-- 15. BẢNG TIN NHẮN (LIVE CHAT WEBSOCKETS)
CREATE TABLE TinNhan (
	MaTN INT PRIMARY KEY IDENTITY(1,1),
	NguoiGui VARCHAR(100), -- Dùng TenDangNhap (Email KH hoặc Username Admin)
	NguoiNhan VARCHAR(100),
	NoiDung NVARCHAR(MAX),
	ThoiGian DATETIME DEFAULT GETDATE(),
	DaDoc BIT DEFAULT 0
)

-- 16. BẢNG THÔNG BÁO HỆ THỐNG
CREATE TABLE ThongBao (
	MaTB INT PRIMARY KEY IDENTITY(1,1),
	TieuDe NVARCHAR(255),
	NoiDung NVARCHAR(MAX),
	NgayTao DATETIME DEFAULT GETDATE(),
	TrangThai BIT DEFAULT 0 -- 0: Chưa xem, 1: Đã xem
)
GO

ALTER TABLE SanPham ADD NguongToiThieu INT DEFAULT 10;
GO

-- Cập nhật chung cho tất cả sản phẩm là 5 (nếu chưa làm)
UPDATE SanPham SET NguongToiThieu = 5;

-- Cập nhật riêng cho các nhóm hàng bán chạy (cần ngưỡng cao hơn để nhập hàng sớm)
UPDATE SanPham SET NguongToiThieu = 15 WHERE MaDM = 'DM04'; -- Ví dụ: Cáp sạc
UPDATE SanPham SET NguongToiThieu = 10 WHERE MaDM = 'DM01'; -- Ví dụ: Ốp lưng

-- Cập nhật đích danh cho sản phẩm cụ thể
UPDATE SanPham SET NguongToiThieu = 3 WHERE MaSP = 'SP020'; -- Bàn phím AKKO giá trị cao, để ngưỡng thấp

-- Giả sử SP001 đang có 50 cái, ngưỡng là 10. Ta sửa cho nó còn 5 cái.
UPDATE SanPham 
SET SoLuong = 5, NguongToiThieu = 10 
WHERE MaSP = 'SP001';
-- Kiểm tra lại để chắc chắn dữ liệu đã thay đổi
SELECT MaSP, TenSP, SoLuong, NguongToiThieu FROM SanPham WHERE MaSP = 'SP001';
SELECT * FROM ThongBao ORDER BY NgayTao DESC;
INSERT INTO KhachHang VALUES
('KH001', N'Nguyễn Văn A', N'Nam', 'TP.HCM', '0123456789', 'a@gmail.com', null)

INSERT INTO DanhMuc (MaDM, TenDM) VALUES 
('DM01',N'Ốp lưng'),
('DM02',N'Tai nghe'),
('DM03',N'Sạc dự phòng'),
('DM04',N'Cáp sạc'),
('DM05',N'Kính cường lực'),
('DM06',N'Chuột'),
('DM07',N'Bàn Phím');

INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SoDienThoai, Email) VALUES 
('NCC01',N'Phụ kiện An Phát', N'Hà Nội', '0987654321', 'anphat@gmail.com'),
('NCC02',N'MobileKing', N'TP.HCM', '0912345678', 'mobileking@gmail.com'),
('NCC03',N'TechZone', N'Đà Nẵng', '0905123456', 'techzone@gmail.com'),
('NCC04',N'Phụ kiện Minh Tâm', N'Cần Thơ', '0977888999', 'minhtam@gmail.com'),
('NCC05',N'GadgetPro', N'Hải Phòng', '0933444555', 'gadgetpro@gmail.com');

INSERT INTO SanPham (MaSP, TenSP, MaDM, MaNCC, Gia, SoLuong, MoTa, HinhAnh, TinhTrang) VALUES 
('SP001',N'Ốp lưng iPhone 14 trong suốt', 'DM01','NCC01', 120000, 50, N'Ốp lưng nhựa dẻo chống sốc', N'OpLungiPhone14TrongSuot.jpg', N'Còn hàng'),
('SP002',N'Ốp lưng Samsung S23 chống va đập','DM01','NCC01', 250000, 30, N'Ốp lưng silicon cao cấp', N'OpLungSamSungS23.jpg', N'Còn hàng'),
('SP003',N'Ốp lưng MagSafe iPhone 15','DM01','NCC01', 120000, 40, N'Hỗ trợ sạc không dây', N'OpLungMagsafeiPhone15.jpg', N'Còn hàng'),
('SP004',N'Ốp lưng da Galaxy Z Flip 5','DM01','NCC01', 300000, 25, N'Chất liệu da thật, cao cấp', N'OpLungDaGalaxyZFlip5.jpg', N'Còn hàng'),
('SP005',N'Tai nghe Bluetooth Baseus','DM02','NCC01', 350000, 60, N'Tai nghe không dây pin 8 giờ', N'TaiNgheBluetoothBaseus.jpg', N'Còn hàng'),
('SP006',N'Tai nghe True Wireless Xiaomi Buds','DM02','NCC01', 490000, 50, N'Chống ồn chủ động, Bluetooth 5.3', N'TaiNgheTrueWirelessXiaomiBuds.jpg', N'Còn hàng'),
('SP007',N'Tai nghe Apple AirPods 3','DM02','NCC01', 4500000, 20, N'Âm thanh không gian, cảm biến lực', N'AirPods3.jpg', N'Còn hàng'),
('SP008',N'Tai nghe Samsung Buds2 Pro','DM02','NCC01', 4200000, 25, N'Hỗ trợ âm thanh 24-bit Hi-Fi', N'SamSungBuds2Pro.jpg', N'Còn hàng'),
('SP009',N'Sạc dự phòng Romoss 20000mAh Sense8','DM03','NCC01', 590000, 40, N'Hỗ trợ sạc nhanh PD 18W, 3 cổng ra', N'SacDuPhongRomoss20000mAhSense8.jpg', N'Còn hàng'),
('SP010',N'Sạc dự phòng Innostyle PowerGo 10000mAh', 'DM03','NCC01', 690000, 35, N'Thiết kế nhỏ gọn, sạc nhanh QC 3.0', N'InnostylePowerGo10000mAh.jpg', N'Còn hàng'),
('SP011',N'Cáp sạc Baseus Type-C 2m','DM04','NCC01', 259000, 80, N'Dây bọc dù siêu bền, hỗ trợ PD 60W', N'CapSacBaseusType-C2m.jpg', N'Còn hàng'),
('SP012',N'Cáp sạc Remax Lightning RC-094i','DM04','NCC01', 119000, 70, N'Chứng nhận MFi, độ bền cao', N'CapSacRemaxLightningRC-094i.jpg', N'Còn hàng'),
('SP013',N'Kính cường lực iPhone 14 Pro Max Nillkin','DM05','NCC01', 129000, 50, N'Kính Nillkin 9H, chống vân tay, độ trong cao', N'CuongLuciPhone14ProMaxNillkin.jpg', N'Còn hàng'),
('SP014',N'Kính cường lực KingKong','DM05', 'NCC01', 309000, 60, N'Chống trầy, phủ nano, cảm ứng nhạy', N'CuongLucKingKong.jpg', N'Còn hàng'),
('SP015',N'Chuột không dây Ugreen Silent MU006','DM06', 'NCC01', 180000, 30, N'Kết nối không dây 2.4 GHz với khoảng cách hoạt động lên tới khoảng 15m, Thiết kế công thái học', N'Chuột không dây Ugreen Silent MU006.png', N'Còn hàng'),
('SP016',N'Chuột không dây LOGITECH SIGNATURE M650','DM06', 'NCC03', 630000, 10, N'Kết nối không dây linh hoạt, Tích hợp công nghệ SilentTouch', N'Chuột không dây LOGITECH SIGNATURE M650.png', N'Còn hàng'),
('SP017',N'Chuột Gaming có dây LOGITECH G102 LightSync','DM06', 'NCC02', 395000, 15, N'Trang bị cảm biến gaming cấp độ cao với độ nhạy lên đến 8.000 DPI, Đèn RGB với công nghệ LIGHTSYNC', N'Chuột Gaming có dây LOGITECH G102 LightSync.png', N'Còn hàng'),
('SP018',N'Bàn phím cơ có dây Newmen GM328','DM07', 'NCC05', 565000, 15, N'Cảm giác gõ phím êm dịu, có độ bền cao, Hệ thống đèn LED ấn tượng', N'Bàn phím cơ có dây Newmen GM328.png', N'Còn hàng'),
('SP019',N'Bàn phím Bluetooth Newmen BK66 Foldable','DM07', 'NCC04', 1145000, 10, N'Tích hợp touchpad, Thiết kế gập ba khúc (foldable) giúp bàn phím trở nên cực kỳ nhỏ gọn khi mang đi', N'Bàn phím Bluetooth Newmen BK66 Foldable.png', N'Còn hàng'),
('SP020',N'Bàn phím cơ AKKO 3068B Pincess Switch','DM07', 'NCC02', 1999000, 10, N'Kết nối đa chế độ, Keycap chất lượng, Pin dung lượng khoảng 1800 mAh', N'Bàn phím cơ AKKO 3068B Pincess Switch.png', N'Còn hàng')

select * from ChiTietGioHang

