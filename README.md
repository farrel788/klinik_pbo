# PROPOSAL PROJEK

## SISTEM ANTRIAN PRIORITAS KLINIK / PUSKESMAS
### Berbasis Java Swing + MySQL dengan Penerapan Konsep OOP

---

**Mata Kuliah:** Pemrograman Berorientasi Objek (PBO)  
**Platform:** Java (JDK 17+) — Java Swing GUI + MySQL Database  
**Build Tool:** Apache Maven  
**Dipublikasikan:** 2026

---

## DAFTAR ISI

1. [Latar Belakang](#1-latar-belakang)
2. [Rumusan Masalah](#2-rumusan-masalah)
3. [Tujuan](#3-tujuan)
4. [Ruang Lingkup](#4-ruang-lingkup)
5. [Manfaat](#5-manfaat)
6. [Landasan Teori](#6-landasan-teori)
7. [Metodologi](#7-metodologi)
8. [Desain Sistem](#8-desain-sistem)
9. [Implementasi](#9-implementasi)
10. [Spesifikasi Perangkat](#10-spesifikasi-perangkat)
11. [Jadwal Pengerjaan](#11-jadwal-pengerjaan)
12. [Kesimpulan](#12-kesimpulan)

---

## 1. LATAR BELAKANG

### 1.1. Konteks Permasalahan

Pelayanan kesehatan di klinik dan puskesmas di Indonesia masih banyak yang
menggunakan sistem antrian **konvensional berbasis FIFO** (First In First Out)
— pasien yang datang lebih awal dilayani lebih dulu, tanpa mempertimbangkan
**tingkat urgensi medis**. Praktik ini menimbulkan beberapa masalah:

- Pasien **gawat darurat** (sesak nafas, serangan jantung, kecelakaan) harus
  mengantri bersama pasien umum yang hanya datang untuk kontrol rutin.
- **Lansia** (>60 tahun), **ibu hamil**, dan **penyandang disabilitas** yang
  secara sosial dan medis perlu prioritas, justru diperlakukan sama dengan
  pasien umum.
- Ketidakadilan ini berpotensi memperburuk kondisi pasien prioritas dan
  meningkatkan risiko cedera atau kematian yang seharusnya dapat dicegah.

### 1.2. Solusi yang Diusulkan

Sistem Antrian Prioritas Klinik/Puskesmas adalah aplikasi desktop berbasis
Java yang mengatur antrian pasien secara otomatis berdasarkan **tingkat
prioritas** (bukan FIFO). Pasien diklasifikasikan ke dalam empat kategori:
**Gawat Darurat, Lansia/Disabilitas, Ibu Hamil, dan Umum**. Sistem
menggunakan algoritma **Priority Queue** dengan **Comparator custom** untuk
memastikan pasien dengan kondisi paling mendesak dilayani lebih dulu.

### 1.3. Relevansi dengan Mata Kuliah PBO

Projek ini dirancang sebagai tugas akhir mata kuliah Pemrograman Berorientasi
Objek karena mengimplementasikan **seluruh konsep inti OOP** secara nyata:

| Konsep OOP | Implementasi dalam Projek |
|------------|--------------------------|
| **Abstraction** | Class abstract `Pasien`, interface `Antrian<T>` |
| **Inheritance** | 4 subclass: `PasienUmum`, `PasienLansia`, `PasienIbuHamil`, `PasienGawatDarurat` |
| **Polymorphism** | Method `getBobotPrioritas()` mengembalikan nilai berbeda per subclass (10–100) |
| **Encapsulation** | Atribut `protected`, getter/setter, validasi NIK 16 digit |
| **Interface & Generics** | `Antrian<T>` interface dengan implementasi `AntrianPrioritasKlinik<Pasien>` |

Selain itu, projek ini juga menerapkan **design pattern** (Singleton, Factory
Method, DAO), **struktur data Priority Queue**, dan arsitektur
**3-layer** (Model-View-DAO) yang merupakan best practice dalam rekayasa
perangkat lunak.

---

## 2. RUMUSAN MASALAH

Berdasarkan latar belakang di atas, rumusan masalah dalam projek ini adalah:

1. Bagaimana merancang sistem antrian yang dapat mengurutkan pasien
   berdasarkan **tingkat prioritas medis**, bukan hanya urutan kedatangan?
2. Bagaimana menerapkan **konsep OOP** (Abstraction, Inheritance,
   Polymorphism, Encapsulation, Interface, Generics) dalam studi kasus
   antrian klinik secara utuh dan tepat?
3. Bagaimana mengintegrasikan **struktur data Priority Queue** dengan
   **Comparator custom** ke dalam aplikasi GUI desktop?
4. Bagaimana merancang **database MySQL** yang mendukung CRUD data pasien,
   manajemen dokter/poli, serta riwayat pemeriksaan?
5. Bagaimana membangun **tampilan GUI yang modern, responsif, dan mudah
   digunakan** oleh petugas klinik dengan role berbeda (Admin, Loket, Poli)?

---

## 3. TUJUAN

### 3.1. Tujuan Umum

Membangun aplikasi desktop Sistem Antrian Prioritas Klinik/Puskesmas yang
menerapkan konsep OOP secara utuh dalam studi kasus nyata (real-world case).

### 3.2. Tujuan Khusus

1. **Menerapkan Abstraction** melalui class abstract `Pasien` dan interface
   `Antrian<T>` sebagai kerangka sistem.
2. **Menerapkan Inheritance** dengan membuat 4 subclass pasien yang mewarisi
   class `Pasien`.
3. **Menerapkan Polymorphism** melalui override method `getBobotPrioritas()`
   yang dipanggil oleh Priority Queue saat runtime.
4. **Menerapkan Encapsulation** dengan validasi data (NIK 16 digit) di dalam
   class, bukan dari luar.
5. **Menerapkan Interface & Generics** melalui interface `Antrian<T>` yang
   diimplementasikan oleh `AntrianPrioritasKlinik`.
6. **Mengimplementasikan algoritma Priority Queue** dengan Comparator custom
   menggunakan bobot prioritas + waktu kedatangan sebagai tie-breaker.
7. **Membangun GUI Desktop** menggunakan Java Swing dengan FlatLaf theme
   dan Ikonli icons.
8. **Menghubungkan aplikasi** dengan database MySQL untuk menyimpan data
   pasien, dokter, poli, petugas, dan riwayat pemeriksaan.
9. **Menerapkan 3 design pattern**: Singleton (koneksi database), Factory
   Method (pembuatan objek Pasien), dan DAO (akses database).

---

## 4. RUANG LINGKUP

### 4.1. Lingkup Fungsional

| No | Modul | Fitur |
|----|-------|-------|
| 1 | Login | Multi-role: Admin, Petugas Loket, Petugas Poli |
| 2 | Pendaftaran | Input data pasien, pilih kategori (4 opsi), validasi NIK, auto-generate nomor antrian |
| 3 | Antrian Prioritas | Priority Queue otomatis, tampilan terurut berdasarkan bobot prioritas |
| 4 | Pemanggilan Pasien | Tombol "Panggil Berikutnya", Skip, Selesai, notifikasi suara |
| 5 | Riwayat Pemeriksaan | Log pasien yang sudah dilayani, filter status & kategori |
| 6 | Laporan Statistik | Grafik batang (JFreeChart) jumlah pasien per kategori |
| 7 | Dashboard Ringkasan | Kartu statistik: total menunggu, breakdown per kategori |
| 8 | Tampilan TV | Jendela full-screen untuk display ruang tunggu (nomor antrian font 120px) |

### 4.2. Lingkup Non-Fungsional

- Aplikasi desktop (bukan web/mobile)
- Database lokal MySQL (bukan cloud)
- Single-user per role (belum multi-user concurrent)
- Bahasa Indonesia (UI dan dokumentasi)
- Sistem operasi Windows (target utama)

### 4.3. Batasan (Limitations)

- Tidak ada integrasi API SMS/WhatsApp untuk notifikasi
- Tidak ada cetak laporan ke PDF (masih memakai JFreeChart di layar)
- Tidak ada fitur estimasi waktu tunggu
- Tidak ada enkripsi password (plaintext untuk demo)

---

## 5. MANFAAT

### 5.1. Bagi Mahasiswa

- Memahami dan mengimplementasikan **6 konsep OOP** dalam satu projek utuh.
- Belajar **struktur data Priority Queue** dan **Comparator** di Java.
- Berlatih **desain sistem** (Class Diagram, ERD, Flowchart, Use Case).
- Mengenal **design pattern** (Singleton, Factory Method, DAO).
- Berpengalaman dengan **GUI Swing**, **JDBC**, **Maven**, dan **MySQL**.

### 5.2. Bagi Klinik / Puskesmas

- Pasien **gawat darurat, lansia, dan ibu hamil** mendapat prioritas layanan.
- Antrian lebih **adil** dan **efisien** secara medis.
- Petugas dapat melihat **antrian real-time** dengan jelas.
- Data pasien dan riwayat **tercatat rapi** di database.
- Laporan statistik membantu **analisis beban** setiap kategori pasien.

---

## 6. LANDASAN TEORI

### 6.1. Pemrograman Berorientasi Objek (OOP)

OOP adalah paradigma pemrograman yang menggunakan "objek" sebagai unit dasar.
Enam pilar utama OOP yang diterapkan dalam projek ini:

#### a. Abstraction (Abstraksi)

Abstraksi adalah proses menyembunyikan detail implementasi dan hanya
menampilkan fungsi esensial. Dalam projek ini, class `Pasien` dideklarasikan
sebagai **abstract class** — tidak bisa di-instantiate langsung, hanya sebagai
kerangka untuk subclass. Method `getBobotPrioritas()` dan `getKategori()`
dideklarasikan abstract, memaksa setiap subclass untuk mengimplementasikannya.

```java
public abstract class Pasien {
    public abstract int getBobotPrioritas();
    public abstract String getKategori();
}
```

#### b. Inheritance (Pewarisan)

Inheritance memungkinkan sebuah class mewarisi atribut dan method dari class
lain. Dalam projek ini, 4 subclass (`PasienUmum`, `PasienLansia`,
`PasienIbuHamil`, `PasienGawatDarurat`) mewarisi `Pasien` via keyword
`extends`.

```
                          Pasien (abstract)
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
    PasienUmum          PasienLansia        PasienIbuHamil
    bobot=10            bobot=70            bobot=60
                              │
                    PasienGawatDarurat
                    bobot=100
```

#### c. Polymorphism (Polimorfisme)

Polymorphism berarti "banyak bentuk" — method yang sama dapat berperilaku
berbeda tergantung tipe objek aslinya. Dalam projek ini, `getBobotPrioritas()`
mengembalikan nilai berbeda tiap subclass, namun dipanggil dengan cara yang
sama dari Priority Queue.

```java
PriorityQueue<Pasien> antrian = new PriorityQueue<>(
    Comparator.comparingInt(Pasien::getBobotPrioritas)
);
```
Saat `getBobotPrioritas()` dipanggil, JVM menjalankan method sesuai objek
asli (runtime polymorphism).

#### d. Encapsulation (Enkapsulasi)

Enkapsulasi membungkus data dan method dalam satu unit, serta menyembunyikan
detail internal dari luar. Atribut `Pasien` bermodifier `protected`, hanya
diakses lewat getter/setter. Validasi dilakukan di dalam class:

```java
public final void setNik(String nik) {
    if (nik == null || !nik.matches("\\d{16}"))
        throw new IllegalArgumentException("NIK harus 16 digit angka!");
    this.nik = nik;
}
```

Modifier `final` pada method mencegah subclass meng-override validasi ini.

#### e. Interface & Generics

Interface adalah kontrak yang mendefinisikan method apa yang harus dimiliki
suatu class tanpa menentukan implementasinya. Generics memungkinkan tipe data
ditentukan saat class digunakan.

```java
public interface Antrian<T> {
    void tambahAntrian(T item);
    T panggilBerikutnya();
    boolean isEmpty();
    int ukuran();
}
```

Diimplementasikan oleh `AntrianPrioritasKlinik implements Antrian<Pasien>`.

### 6.2. Struktur Data Priority Queue

Priority Queue (PQ) adalah struktur data antrian di mana setiap elemen
memiliki prioritas, dan elemen dengan prioritas tertinggi selalu keluar
pertama — berbeda dengan Queue biasa yang bersifat FIFO.

Java menyediakan `java.util.PriorityQueue` yang secara default adalah
min-heap (prioritas terendah keluar pertama). Untuk prioritas tertinggi,
digunakan `Comparator.reversed()`.

**Kompleksitas waktu:**
| Operasi | Kompleksitas |
|---------|-------------|
| `offer()` (tambah) | O(log n) |
| `poll()` (ambil) | O(log n) |
| `peek()` (lihat) | O(1) |

### 6.3. Design Pattern

#### Singleton Pattern

Memastikan hanya ada **satu instance** koneksi database di seluruh aplikasi.
Implementasi di `KoneksiDatabase.java`:

```java
private static KoneksiDatabase instance;
public static synchronized KoneksiDatabase getInstance() {
    if (instance == null) instance = new KoneksiDatabase();
    return instance;
}
```

#### Factory Method Pattern

Memisahkan **logika pembuatan objek** dari pemanggil. `PasienFactory`
menentukan subclass `Pasien` mana yang di-instantiate berdasarkan string
kategori.

```java
Pasien p = PasienFactory.buat("GAWAT_DARURAT", "Agus", "3201...", 40, "Sesak napas");
// p adalah instance PasienGawatDarurat
```

#### DAO (Data Access Object) Pattern

Memisahkan **logika database** (SQL) dari **logika bisnis**.
Setiap tabel punya class DAO sendiri:
- `PasienDAO` → CRUD pasien
- `DokterDAO` → CRUD dokter & poli
- `RiwayatDAO` → Login & riwayat pemeriksaan

### 6.4. GUI Swing + FlatLaf + Ikonli

**Java Swing** adalah toolkit GUI standar Java. Projek ini memanfaatkan:
- `CardLayout` untuk navigasi multi-halaman
- `JTable` dengan custom `TableCellRenderer` untuk badge warna
- `JLayeredPane` untuk toast notification

**FlatLaf** adalah library Look-and-Feel modern untuk Swing yang memberikan
tampilan flat/clean.

**Ikonli** adalah library ikon vektor untuk Swing yang mendukung ribuan ikon
Material Design.

### 6.5. JFreeChart

Library untuk membuat grafik dan chart di Java. Digunakan di `LaporanPanel`
untuk menampilkan bar chart jumlah pasien per kategori.

### 6.6. MySQL + JDBC

MySQL adalah database relasional untuk menyimpan data. JDBC (Java Database
Connectivity) adalah API standar Java untuk terhubung ke database.

---

## 7. METODOLOGI

### 7.1. Metode Pengembangan: Waterfall

Projek ini menggunakan model Waterfall (linear sekuensial) karena cocok untuk
projek skala kecil dengan spesifikasi yang jelas:

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│ Analisis │───►│  Desain  │───►│  Coding  │───►│  Testing │───►│Deployment│
│Kebutuhan │    │  Sistem  │    │          │    │          │    │          │
└──────────┘    └──────────┘    └──────────┘    └──────────┘    └──────────┘
```

### 7.2. Tahapan Pengerjaan

#### Tahap 1: Analisis Kebutuhan
- Studi literatur: OOP, Priority Queue, Java Swing, MySQL
- Analisis sistem antrian manual di klinik
- Identifikasi kebutuhan fungsional & non-fungsional
- Penentuan role pengguna (Admin, Loket, Poli)

#### Tahap 2: Desain Sistem
- Perancangan Use Case Diagram
- Perancangan Class Diagram (OOP)
- Perancangan Entity Relationship Diagram (ERD)
- Perancangan Activity Diagram (Flowchart)
- Perancangan UI/UX wireframe

#### Tahap 3: Implementasi (Coding)
- Setup project Maven + dependencies
- Pembuatan database MySQL (SQL DDL + seed)
- Implementasi Model Layer (class Pasien + 4 subclass + entity)
- Implementasi Util Layer (Priority Queue, Factory, Singleton)
- Implementasi DAO Layer (CRUD)
- Implementasi View Layer (GUI Swing)
- Implementasi MainFrame + navigasi

#### Tahap 4: Pengujian (Testing)
- Unit testing Priority Queue (JUnit)
- Integration testing database → DAO
- UI testing (manual): login, daftar, antrian, panggil, riwayat, laporan

#### Tahap 5: Deployment
- Build JAR dengan Maven
- Dokumentasi kode (Javadoc + PENJELASAN.md)
- Dokumentasi pengguna (README.md)

### 7.3. Tools yang Digunakan

| Kategori | Tools |
|----------|-------|
| Bahasa Pemrograman | Java (JDK 21) |
| IDE | VS Code / Apache NetBeans |
| Build Tool | Apache Maven 3.9 |
| Database | MySQL 8 + XAMPP / phpMyAdmin |
| GUI Framework | Java Swing + FlatLaf 3.4 |
| Ikon | Ikonli 12.3 + MaterialDesign2 |
| Chart | JFreeChart 1.5.3 |
| Version Control | Git + GitHub |

---

## 8. DESAIN SISTEM

### 8.1. Use Case Diagram

```
                    ┌─────────────────────────────────┐
                    │     Sistem Antrian Klinik        │
                    │                                  │
 ┌──────────┐      ┌──────────┐      ┌──────────┐
 │  ADMIN   │      │  LOKET   │      │   POLI   │
 └────┬─────┘      └────┬─────┘      └────┬─────┘
      │                 │                 │
      ├── Login ────────┼───────────────┬──┘
      │                 │               │
      ├── Lihat Antrian │               │
      ├── Lihat Riwayat ┼── Lihat Riwayat
      │                 ├── Daftar Pasien
      ├── Laporan       │               ├── Panggil Pasien
      │                                 ├── Skip Pasien
      └── (all menu)                    └── Selesai
```

### 8.2. Activity Diagram (Alur Pendaftaran → Pelayanan)

```
[Pasien Datang]                    [Sistem]
      │                                │
      ▼                                │
[Loket: Input Data]                    │
      │                                │
      ▼                                │
[Loket: Pilih Kategori]                │
      │                                │
      └──────────┬─────────────────────┘
                 ▼
          [Hitung Bobot Prioritas]
                 │
                 ▼
          [Generate No. Antrian]
                 │
                 ▼
          [Masukkan Priority Queue]
                 │
                 ▼
       ┌────────────────────┐
       │                     │
  [Dokter: Panggil]    [Menunggu]
       │
       ▼
  [Pasien Dipanggil]
       │
       ├── Hadir → [Selesai Periksa] → Riwayat
       │
       └── Tidak Hadir → [Skip] → Riwayat
```

### 8.3. Class Diagram (OOP)

```
 ┌─────────────────────────────┐
 │    <<abstract>>  Pasien     │
 ├─────────────────────────────┤
 │ # id, nama, nik, usia      │
 │ # keluhan, noAntrian       │
 │ # waktuDaftar, status      │
 ├─────────────────────────────┤
 │ + abstract                  │
 │   getBobotPrioritas() : int │
 │ + abstract                  │
 │   getKategori() : String    │
 └─────────────▲───────────────┘
               │ extends
    ┌──────────┼──────────┬──────────────┐
    │          │          │              │
┌───┴───┐ ┌───┴───┐  ┌───┴───┐  ┌──────┴──────┐
│ Umum  │Lansia  │Ibu Hamil│    Darurat      │
│ bobot │ bobot  │ bobot   │    bobot=100    │
│ =10   │ =70    │ =60     │                 │
└───────┘ └───────┘ └────────┘ └──────────────┘

 ┌───────────────────┐       ┌────────────────────────┐
 │<<interface>>      │       │AntrianPrioritasKlinik  │
 │   Antrian<T>      │◄──────│implements Antrian<Pas>
 ├───────────────────┤       ├────────────────────────┤
 │+ tambahAntrian(T) │       │ - PriorityQueue        │
 │+ panggilBerikutnya│       │ - riwayatPasien        │
 │+ isEmpty() : bool │       ├────────────────────────┤
 │+ ukuran() : int   │       │ + tambahAntrian()      │
 └───────────────────┘       │ + panggilBerikutnya()  │
                              │ + getDaftarAntrian()  │
 ┌─────────────┐              └────────────────────────┘
 │  Dokter      │    ┌──────────────┐
 ├─────────────┤    │    Poli      │
 │ - idDokter  │    ├──────────────┤
 │ - nama      │    │ - idPoli     │
 │ - spesialis │    │ - namaPoli   │
 │ - idPoli ───┼──►│ - jadwal     │
 └─────────────┘    └──────────────┘

 ┌──────────────────┐
 │    Petugas       │
 ├──────────────────┤
 │ - username       │
 │ - password       │
 │ - role (enum)    │
 └──────────────────┘

 ┌────────────────────────┐
 │   KoneksiDatabase      │ (Singleton)
 ├────────────────────────┤
 │ - static instance      │
 │ - connection           │
 ├────────────────────────┤
 │ + getInstance()        │
 │ + getConnection()      │
 │ + tutupKoneksi()       │
 └────────────────────────┘

 ┌────────────────────────┐
 │   PasienFactory        │ (Factory)
 ├────────────────────────┤
 │ + buat(kategori, ...)  │
 └────────────────────────┘
```

### 8.4. Entity Relationship Diagram (ERD)

```
┌───────────────┐       ┌──────────────────┐
│    poli       │       │     dokter       │
├───────────────┤       ├──────────────────┤
│ PK id_poli    │◄──────│ FK id_poli       │
│    nama_poli  │  1:N  │ PK id_dokter     │
│    jadwal     │       │    nama          │
└───────────────┘       │    spesialis     │
                        └────────┬─────────┘
                                 │ 1
                                 │
 ┌───────────────┐              │
 │   petugas     │     ┌────────┴──────────┐
 ├───────────────┤     │ riwayat_pemeriksaan│
 │ PK id_petugas │     ├───────────────────┤
 │    username   │     │ PK id_riwayat     │
 │    password   │     │ FK id_pasien      │◄──┐
 │    role (enum)│     │ FK id_dokter      │   │
 └───────────────┘     │ waktu_dipanggil   │   │
                       │ waktu_selesai     │   │
 ┌───────────────┐     │ catatan           │   │
 │    pasien     │     └───────────────────┘   │
 ├───────────────┤                             │
 │ PK id_pasien  │─────────────────────────────┘
 │    nama       │     1:1
 │    nik (unik) │
 │    usia       │
 │    kategori   │
 │    keluhan    │
 │    no_antrian │
 │    waktu_daftar│
 │    status     │
 └───────────────┘
```

**Relasi:**
- 1 **Poli** → N **Dokter** (satu poli punya banyak dokter)
- 1 **Dokter** → N **Riwayat** (satu dokter melayani banyak pasien)
- 1 **Pasien** → 1 **Riwayat** (satu pasien punya satu riwayat)
- **Petugas** berdiri sendiri (tabel login)

### 8.5. Desain Database (Struktur Tabel)

```sql
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

CREATE TABLE dokter (
    id_dokter  INT AUTO_INCREMENT PRIMARY KEY,
    nama       VARCHAR(100),
    spesialis  VARCHAR(100),
    id_poli    INT,
    FOREIGN KEY (id_poli) REFERENCES poli(id_poli)
);

CREATE TABLE poli (
    id_poli    INT AUTO_INCREMENT PRIMARY KEY,
    nama_poli  VARCHAR(100),
    jadwal     VARCHAR(100)
);

CREATE TABLE petugas (
    id_petugas INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       ENUM('ADMIN','LOKET','POLI') NOT NULL
);

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
```

**State Machine Status Pasien:**

```
     MENUNGGU ──► DIPANGGIL ──► SELESAI
          │                        ▲
          └────► SKIP ─────────────┘
```

### 8.6. Desain Arsitektur (3-Layer)

```
┌────────────────────────────────────────────────────────┐
│                   VIEW LAYER (GUI)                      │
│   MainFrame, FormLogin, BerandaPanel, AntrianPanel,    │
│   PanggilanPanel, PendaftaranPanel, RiwayatPanel,      │
│   LaporanPanel, FormTampilanTV                          │
│   Components: CategoryBadge, StatusBadge, SummaryCard, │
│               SidebarButton, ToastNotification          │
├────────────────────────────────────────────────────────┤
│                   DAO LAYER                             │
│   PasienDAO   — CRUD pasien, antrian menunggu,          │
│                 generate no antrian                     │
│   DokterDAO   — CRUD dokter & poli                      │
│   RiwayatDAO  — Login, riwayat pemeriksaan              │
├────────────────────────────────────────────────────────┤
│                   UTIL / SERVICE LAYER                   │
│   Antrian<T> (interface) + AntrianPrioritasKlinik      │
│   PasienFactory (Factory Method)                        │
│   KoneksiDatabase (Singleton)                           │
│   Theme (design token warna/font/spacing)               │
├────────────────────────────────────────────────────────┤
│                   MODEL LAYER                           │
│   Pasien (abstract) + 4 subclass                       │
│   Dokter, Poli, Petugas                                 │
├────────────────────────────────────────────────────────┤
│                   DATABASE LAYER                        │
│   MySQL — 5 tabel: pasien, dokter, poli, petugas,      │
│            riwayat_pemeriksaan                          │
└────────────────────────────────────────────────────────┘
```

Aliran data:
```
User Input (GUI) → View Layer → DAO Layer → Database MySQL
Database MySQL → DAO Layer (ResultSet → Model objects)
             → Util Layer (Priority Queue sorting)
             → View Layer (tampilkan ke JTable / JLabel)
```

---

## 9. IMPLEMENTASI

### 9.1. Struktur Folder (Maven)

```
SistemAntrianKlinik/
├── pom.xml                              ← Maven build config
├── .vscode/
│   ├── launch.json                      ← VS Code debug config
│   └── settings.json                    ← Java project settings
├── src/main/java/com/klinik/antrian/
│   ├── Main.java                        ← Entry point (main)
│   ├── model/                           ← Entity / business objects
│   │   ├── Pasien.java                  (abstract)
│   │   ├── PasienUmum.java              (extends Pasien, bobot=10)
│   │   ├── PasienLansia.java            (extends Pasien, bobot=70)
│   │   ├── PasienIbuHamil.java          (extends Pasien, bobot=60)
│   │   ├── PasienGawatDarurat.java      (extends Pasien, bobot=100)
│   │   ├── Dokter.java
│   │   ├── Poli.java
│   │   └── Petugas.java
│   ├── util/                            ← Service / utility
│   │   ├── Antrian.java                 (interface + generic)
│   │   ├── AntrianPrioritasKlinik.java  (Priority Queue impl)
│   │   ├── PasienFactory.java           (Factory Method)
│   │   ├── KoneksiDatabase.java         (Singleton)
│   │   └── Theme.java                   (design token)
│   ├── dao/                             ← Data Access Object
│   │   ├── PasienDAO.java
│   │   ├── DokterDAO.java
│   │   └── RiwayatDAO.java
│   └── view/                            ← GUI / presentation
│       ├── Refreshable.java             (interface)
│       ├── FormLogin.java
│       ├── MainFrame.java               (sidebar + CardLayout)
│       ├── FormTampilanTV.java          (full-screen display)
│       ├── components/
│       │   ├── IconHelper.java
│       │   ├── SidebarButton.java
│       │   ├── SummaryCard.java
│       │   ├── CategoryBadge.java
│       │   ├── StatusBadge.java
│       │   └── ToastNotification.java
│       └── panels/
│           ├── BerandaPanel.java
│           ├── AntrianPanel.java
│           ├── PanggilanPanel.java
│           ├── PendaftaranPanel.java
│           ├── RiwayatPanel.java
│           └── LaporanPanel.java
├── database/
│   └── klinik_antrian.sql               ← DDL + seed data
├── lib/
│   └── mysql-connector-j.jar            ← JDBC driver
├── PENJELASAN.md                        ← Dokumentasi kode lengkap
└── README.md                            ← Dokumen proposal (ini)
```

**Total: 33 file Java, 7 file konfigurasi/dokumentasi, 1 file SQL.**

### 9.2. Algoritma Priority Queue

Inti dari sistem ini adalah algoritma Priority Queue dengan Comparator custom.

```java
PriorityQueue<Pasien> antrian = new PriorityQueue<>(
    Comparator.comparingInt(Pasien::getBobotPrioritas).reversed()
        .thenComparing(Pasien::getWaktuDaftar)
);
```

**Aturan sorting (2 level):**
1. **Level 1 — Bobot Prioritas:** descending (nilai besar di depan)
2. **Level 2 — Waktu Daftar:** ascending (bila bobot sama, yang lebih dulu datang didahulukan)

**Tabel Bobot Prioritas:**
| Kategori | Bobot | Alasan |
|----------|-------|--------|
| Gawat Darurat | 100 | Kondisi mengancam nyawa |
| Lansia (>60 th) / Disabilitas | 70 | Prioritas sosial (UU Pelayanan Publik) |
| Ibu Hamil | 60 | Prioritas kesehatan ibu & anak |
| Pasien Umum | 10 | Antrian normal, FIFO di levelnya |

### 9.3. Alur Navigasi Aplikasi (User Flow)

```
                    ┌──────────────┐
                    │   MAIN.java  │
                    └──────┬───────┘
                           │
                    ┌──────▼───────┐
                    │  FormLogin   │
                    │  (3 role)    │
                    └──────┬───────┘
                           │
          ┌────────────────┼────────────────┐
          ▼                ▼                ▼
   ┌────────────┐  ┌──────────────┐  ┌──────────────┐
   │   ADMIN    │  │   LOKET     │  │    POLI      │
   │            │  │              │  │              │
   │ • Beranda  │  │ • Beranda   │  │ • Beranda    │
   │ • Daftar   │  │ • Daftar    │  │ • Panggil    │
   │ • Antrian  │  │ • Antrian   │  │ • Riwayat    │
   │ • Panggil  │  └──────────────┘  └──────────────┘
   │ • Riwayat  │
   │ • Laporan  │
   └────────────┘
```

### 9.4. Tampilan UI (Wireframe)

#### FormLogin
```
┌────────────────────────────────────────┐
│            🏥 Sistem Antrian Klinik     │
│         Masuk untuk melanjutkan        │
│                                        │
│   Username: [____________________]    │
│   Password: [____________________]    │
│                                        │
│   [        Masuk ke Sistem        ]   │
│                                        │
│   Demo: admin/admin123                 │
└────────────────────────────────────────┘
```

#### MainFrame (Sidebar + Content)
```
┌─────────────────────────────────────────────────────────┐
│ 🏥 Klinik Sehat Bersama        👤 admin (ADMIN)     ⏻  │
├────────────┬────────────────────────────────────────────┤
│ 🏠 Beranda  │                                            │
│ 📝 Daftar   │         (Content Area)                     │
│ 📋 Antrian  │         CardLayout                         │
│ 📢 Panggil  │         BerandaPanel / AntrianPanel /      │
│ 📜 Riwayat  │         PanggilanPanel / ...               │
│ 📊 Laporan  │                                            │
├────────────┴────────────────────────────────────────────┤
│                     Status Bar                          │
└─────────────────────────────────────────────────────────┘
```

#### Dashboard Antrian
```
┌───┬─────┬─────┬─────┬─────┐
│ 🏥 │  ⚠  │ 🚶  │ 🤰  │ 👤  │
│ 9  │  2  │  3  │  1  │  3  │  ← Summary Cards
│Total│Darurat│Lansia│Hamil│Umum │
└───┴─────┴─────┴─────┴─────┘ ─┐
┌─ Antrian Menunggu ────────────┤
│ No.  Nama      Kategori     │ │
│ D002 Rina     🔴 GAWAT DARURAT│ ← Badge warna
│ P001 Siti     🟠 LANSIA       │
│ A003 Budi     ⚪ UMUM         │
└───────────────────────────────┘
```

#### Panggilan Pasien
```
┌──────────────────────────┬─────────────────┐
│    SEDANG DIPANGGIL      │  Menunggu (3)   │
│                          │                 │
│       D-001              │ 🔴 D002 Rina   │
│     (font 48px)          │ 🟠 P001 Siti   │
│                          │ ⚪ A003 Budi   │
│ Agus Pratama             │                 │
│ 🔴 GAWAT DARURAT         │                 │
│                          │                 │
│ [📢 Panggil Berikutnya]  │                 │
│ [⏭ Skip] [✔ Selesai]    │                 │
└──────────────────────────┴─────────────────┘
```

### 9.5. Navigasi Menu per Role

| Menu | ADMIN | LOKET | POLI |
|------|-------|-------|------|
| Beranda | ✔ | ✔ | ✔ |
| Pendaftaran | ✔ | ✔ | ✘ |
| Antrian | ✔ | ✔ | ✘ |
| Panggilan | ✔ | ✘ | ✔ |
| Riwayat | ✔ | ✘ | ✔ |
| Laporan | ✔ | ✘ | ✘ |

### 9.6. Design Pattern yang Diterapkan

| Pattern | Lokasi | Fungsi |
|---------|--------|--------|
| **Singleton** | `util/KoneksiDatabase.java` | Satu koneksi DB seumur hidup aplikasi |
| **Factory Method** | `util/PasienFactory.java` | Membuat subclass Pasien sesuai kategori |
| **DAO** | `dao/PasienDAO.java`, `DokterDAO.java`, `RiwayatDAO.java` | Memisahkan SQL dari logika bisnis |
| **Observer** | `Refreshable.java` | Panel ter-notifikasi saat perlu reload |
| **Card Layout** | `MainFrame.java` | Navigasi multi-halaman tanpa JFrame baru |

### 9.7. User Interface Components

| Komponen | File | Fungsi |
|----------|------|--------|
| **CategoryBadge** | `components/CategoryBadge.java` | Badge warna kategor pasien (🔴🟠🟣⚪) |
| **StatusBadge** | `components/StatusBadge.java` | Badge warna status pasien |
| **SidebarButton** | `components/SidebarButton.java` | Tombol navigasi sidebar dg indikator aktif |
| **SummaryCard** | `components/SummaryCard.java` | Kartu statistik (icon + label + angka) |
| **ToastNotification** | `components/ToastNotification.java` | Notifikasi non-modal pojok kanan atas |
| **IconHelper** | `components/IconHelper.java` | Helper ikon vector Ikonli |

---

## 10. SPESIFIKASI PERANGKAT

### 10.1. Perangkat Lunak

| No | Software | Versi | Keterangan |
|----|----------|-------|------------|
| 1 | Java Development Kit | 17+ / 21 | Runtime & compiler |
| 2 | VS Code atau NetBeans | Terbaru | IDE pengembangan |
| 3 | XAMPP | 8.x | MySQL + phpMyAdmin |
| 4 | Apache Maven | 3.9+ | Build & dependency tool |
| 5 | Git | 2.x | Version control |

### 10.2. Perangkat Keras (Minimal)

| Komponen | Spesifikasi |
|----------|-------------|
| Prosesor | Intel Core i3 / setara |
| RAM | 4 GB |
| Penyimpanan | 500 MB (free space) |
| Resolusi Layar | 1366 x 768 |
| Sistem Operasi | Windows 10 / 11 |

### 10.3. Library Eksternal (Maven Dependencies)

| Dependency | Version | Fungsi |
|------------|---------|--------|
| `mysql-connector-j` | 8.0.33 | Driver JDBC MySQL |
| `flatlaf` | 3.4.1 | Theme modern FlatLightLaf |
| `flatlaf-extras` | 3.4.1 | Ekstensi FlatLaf |
| `ikonli-swing` | 12.3.1 | Ikon vector untuk Swing |
| `ikonli-materialdesign2-pack` | 12.3.1 | Set ikon Material Design |
| `jfreechart` | 1.5.3 | Grafik laporan |

---

## 11. JADWAL PENGERJAAN

| Tahap | Kegiatan | Durasi | Output |
|-------|----------|--------|--------|
| **1** | Analisis kebutuhan & studi literatur | 1 minggu | Dokumen kebutuhan |
| **2** | Perancangan desain sistem | 1 minggu | Class diagram, ERD, UI wireframe |
| **3** | Setup project & database | 2 hari | pom.xml, konfigurasi, SQL |
| **4** | Implementasi model layer | 3 hari | 8 file Java model |
| **5** | Implementasi util layer | 2 hari | Priority Queue, Factory, Singleton |
| **6** | Implementasi DAO layer | 3 hari | CRUD database |
| **7** | Implementasi GUI panels | 1 minggu | MainFrame + 6 panel + komponen |
| **8** | Integrasi & testing | 3 hari | Uji coba end-to-end |
| **9** | Dokumentasi & revisi | 2 hari | README, PENJELASAN, Javadoc |
| **10** | Finalisasi & pengumpulan | 1 hari | Build JAR |

**Total estimasi: 6 minggu (42 hari kerja).**

---

## 12. KESIMPULAN

Projek "Sistem Antrian Prioritas Klinik/Puskesmas" adalah aplikasi desktop
berbasis Java yang mengimplementasikan **enam konsep inti OOP** secara utuh
dalam studi kasus nyata: mengelola antrian pasien berdasarkan tingkat prioritas
medis, bukan urutan kedatangan biasa.

Dengan menerapkan **class abstract, inheritance 4 level, polymorphism,
encapsulation, interface, dan generics**, serta dilengkapi **3 design pattern**
(Singleton, Factory Method, DAO), projek ini memenuhi seluruh capaian
pembelajaran mata kuliah Pemrograman Berorientasi Objek.

**Kelebihan projek:**
- Menerapkan **5+ konsep OOP** dalam satu aplikasi utuh
- Algoritma **Priority Queue** dengan **Comparator** kustom
- GUI modern dengan **FlatLaf** + **Ikonli** + **JFreeChart**
- Arsitektur **3-layer** (Model-View-DAO) sesuai best practice
- **33 file Java** yang terstruktur rapi dengan Maven
- Database **MySQL** dengan 5 tabel dan relasi lengkap

**Pengembangan lebih lanjut dapat dilakukan:**
- Integrasi API WhatsApp/SMS untuk notifikasi
- Cetak laporan ke PDF (iText / JasperReports)
- Role tambahan: Kepala Puskesmas
- Estimasi waktu tunggu berdasarkan rata-rata layanan
- Grafik JFreeChart yang lebih interaktif
- Enkripsi password menggunakan BCrypt

---

**Dokumen ini disusun sebagai proposal tugas akhir mata kuliah PBO.**  
**Sistem Antrian Prioritas Klinik/Puskesmas — 2026**

---

*Untuk informasi lebih detail, silakan merujuk ke:*
- `PENJELASAN.md` — Penjelasan teknis kode per file
- `database/klinik_antrian.sql` — Struktur database lengkap
