-- =====================================================
-- DATABASE: klinik_antrian
-- Sistem Antrian Prioritas Klinik / Puskesmas
-- Tugas PBO - Java Swing + MySQL
-- =====================================================

DROP DATABASE IF EXISTS klinik_antrian;
CREATE DATABASE klinik_antrian;
USE klinik_antrian;

-- ---------------------------------------------------
-- TABEL: poli
-- ---------------------------------------------------
CREATE TABLE poli (
    id_poli    INT AUTO_INCREMENT PRIMARY KEY,
    nama_poli  VARCHAR(100) NOT NULL,
    jadwal     VARCHAR(100)
);

-- ---------------------------------------------------
-- TABEL: dokter
-- ---------------------------------------------------
CREATE TABLE dokter (
    id_dokter   INT AUTO_INCREMENT PRIMARY KEY,
    nama        VARCHAR(100) NOT NULL,
    spesialis   VARCHAR(100),
    id_poli     INT,
    FOREIGN KEY (id_poli) REFERENCES poli(id_poli)
);

-- ---------------------------------------------------
-- TABEL: petugas  (user login multi-role)
-- ---------------------------------------------------
CREATE TABLE petugas (
    id_petugas INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       ENUM('ADMIN','LOKET','POLI') NOT NULL
);

-- ---------------------------------------------------
-- TABEL: pasien
-- ---------------------------------------------------
CREATE TABLE pasien (
    id_pasien    INT AUTO_INCREMENT PRIMARY KEY,
    nama         VARCHAR(100) NOT NULL,
    nik          VARCHAR(16) UNIQUE,
    usia         INT,
    kategori     ENUM('UMUM','LANSIA','IBU_HAMIL','GAWAT_DARURAT') NOT NULL,
    keluhan      VARCHAR(255),
    no_antrian   VARCHAR(10),
    waktu_daftar DATETIME,
    status       ENUM('MENUNGGU','DIPANGGIL','SELESAI','SKIP') DEFAULT 'MENUNGGU'
);

-- ---------------------------------------------------
-- TABEL: riwayat_pemeriksaan
-- ---------------------------------------------------
CREATE TABLE riwayat_pemeriksaan (
    id_riwayat     INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien      INT,
    id_dokter      INT,
    waktu_dipanggil DATETIME,
    waktu_selesai   DATETIME,
    catatan        TEXT,
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien),
    FOREIGN KEY (id_dokter) REFERENCES dokter(id_dokter)
);

-- =====================================================
-- DATA AWAL (SEED)
-- =====================================================

-- Petugas default (password disimpan plaintext sederhana untuk demo)
INSERT INTO petugas (username, password, role) VALUES
('admin',    'admin123', 'ADMIN'),
('loket',    'loket123', 'LOKET'),
('poli',     'poli123',  'POLI');

-- Poli
INSERT INTO poli (nama_poli, jadwal) VALUES
('Poli Umum',            'Senin-Jumat 08:00-14:00'),
('Poli Anak',            'Senin-Rabu 09:00-13:00'),
('Poli Ibu Hamil',       'Selasa-Kamis 08:00-12:00'),
('Poli Gawat Darurat',   'Setiap Hari 24 Jam');

-- Dokter
INSERT INTO dokter (nama, spesialis, id_poli) VALUES
('dr. Andi Wijaya',   'Dokter Umum',    1),
('dr. Siti Rahma',    'Spesialis Anak',  2),
('dr. Dewi Lestari',  'Obgyn',          3),
('dr. Budi Hartono',  'UGD',            4);

-- Pasien contoh
INSERT INTO pasien (nama, nik, usia, kategori, keluhan, no_antrian, waktu_daftar, status) VALUES
('Budi Santoso',     '3201010101900001', 35, 'UMUM',           'Demam',          'A-001', NOW(),                'MENUNGGU'),
('Siti Aminah',      '3201010102000002', 65, 'LANSIA',         'Pusing & lemas', 'P-002', NOW() + INTERVAL 1 MINUTE, 'MENUNGGU'),
('Rina Melati',      '3201010103000003', 28, 'IBU_HAMIL',      'Kontrol kehamilan','H-003', NOW() + INTERVAL 2 MINUTE, 'MENUNGGU'),
('Agus Pratama',     '3201010104000004', 40, 'GAWAT_DARURAT',  'Sesak napas hebat','D-004', NOW() + INTERVAL 3 MINUTE, 'MENUNGGU');

-- =====================================================
-- SELESAI
-- =====================================================