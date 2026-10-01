# 📖 Penjelasan Kode — Sistem Antrian Prioritas Klinik

Dokumen ini menjelaskan setiap bagian kode project beserta konsep OOP yang diterapkan.
Project: `SistemAntrianKlinik`  |  Bahasa: Java 17 + Swing  |  Database: MySQL

---

## 1. Struktur Folder

```
SistemAntrianKlinik/
├── src/com/klinik/antrian/
│   ├── model/         ← Class-class data (EREDITY / Pewarisan)
│   │   ├── Pasien.java              (abstract)  ← ABSTRACTION
│   │   ├── PasienUmum.java          (extends Pasien)
│   │   ├── PasienLansia.java        (extends Pasien)
│   │   ├── PasienIbuHamil.java      (extends Pasien)
│   │   ├── PasienGawatDarurat.java  (extends Pasien)
│   │   ├── Dokter.java
│   │   ├── Poli.java
│   │   └── Petugas.java             (memakai enum Role)
│   ├── util/          ← Class utility
│   │   ├── Antrian.java              (interface)  ← INTERFACE + GENERICS
│   │   ├── AntrianPrioritasKlinik.java            ← PRIORITY QUEUE + POLYMORPHISM
│   │   ├── PasienFactory.java        ← FACTORY METHOD pattern
│   │   └── KoneksiDatabase.java      ← SINGLETON pattern
│   ├── dao/           ← Data Access Object (CRUD ke MySQL)
│   │   ├── PasienDAO.java
│   │   ├── DokterDAO.java
│   │   └── RiwayatDAO.java
│   ├── view/          ← Java Swing GUI
│   │   ├── FormLogin.java
│   │   ├── FormPendaftaran.java
│   │   ├── FormDashboardLoket.java
│   │   ├── FormDashboardPoli.java
│   │   ├── FormRiwayat.java
│   │   └── FormLaporan.java
│   └── Main.java                     ← Entry point aplikasi
├── database/
│   └── klinik_antrian.sql           ← DDL + seed data
├── lib/                              ← Tempatkan mysql-connector-j.jar di sini
├── nbproject/                        ← File konfigurasi NetBeans
├── build.xml                         ← Build script Ant
├── manifest.mf
└── project.properties
```

---

## 2. Konsep OOP yang Diterapkan

### a. ABSTRACTION (Class Abstract)

**File:** `model/Pasien.java`

`Pasien` adalah class abstract — tidak bisa di-instantiate langsung, hanya bisa jadi
kerangka untuk subclass. Dengan `abstract`, kita memaksa setiap subclass mengimplementasikan
method `getBobotPrioritas()` dan `getKategori()`.

```java
public abstract class Pasien {
    public abstract int    getBobotPrioritas();   // wajib di subclass
    public abstract String getKategori();
}
```

### b. INHERITANCE (Pewarisan)

**File:** `PasienUmum.java`, `PasienLansia.java`, `PasienIbuHamil.java`, `PasienGawatDarurat.java`

Keempat subclass mewarisi seluruh atribut & method `Pasien` lewat kata kunci `extends`.
Mereka hanya perlu mengimplementasikan dua method abstract.

```java
public class PasienLansia extends Pasien { ... }
```

### c. POLYMORPHISM (Banyak Bentuk)

Saat program memanggil `pasien.getBobotPrioritas()`, JVM otomatis memilih method yang
benar berdasarkan **tipe objek asli** saat runtime — bukan tipe variabel referensinya.

Contoh di `AntrianPrioritasKlinik`:
```java
antrian = new PriorityQueue<>(
    Comparator.comparingInt(Pasien::getBobotPrioritas).reversed()
);
```
`getBobotPrioritas()` akan mengembalikan nilai berbeda:
| Subclass              | Bobot |
|-----------------------|-------|
| `PasienGawatDarurat`  | 100   |
| `PasienLansia`        | 70    |
| `PasienIbuHamil`      | 60    |
| `PasienUmum`          | 10    |

### d. ENCAPSULATION (Pembungkusan)

Semua atribut `Pasien` bermodifier `protected`, diakses lewat getter/setter.
Validasi dilakukan **di dalam class** (bukan dari luar). Contoh: validasi NIK 16 digit.

```java
public final void setNik(String nik) {
    if (nik == null || !nik.matches("\\d{16}")) {
        throw new IllegalArgumentException("NIK harus 16 digit angka!");
    }
    this.nik = nik;
}
```

Modifier `final` di method mencegah subclass meng-override validasi ini.

### e. INTERFACE + GENERICS

**File:** `util/Antrian.java`

Interface generic `Antrian<T>` menentukan kontrak operasi antrian tanpa peduli
implementasi internalnya. `T` adalah tipe generic (boleh diganti apa saja).

```java
public interface Antrian<T> {
    void tambahAntrian(T item);
    T    panggilBerikutnya();
    boolean isEmpty();
    int  ukuran();
}
```

Di-implementasikan oleh `AntrianPrioritasKlinik implements Antrian<Pasien>`.

---

## 3. Design Pattern yang Diterapkan

### a. SINGLETON — `KoneksiDatabase.java`

Memastikan hanya ada **satu instance** koneksi database sepanjang hidup aplikasi.
Cara pakai: `KoneksiDatabase.getInstance().getConnection()`.

```java
public static synchronized KoneksiDatabase getInstance() {
    if (instance == null) instance = new KoneksiDatabase();
    return instance;
}
```

### b. FACTORY METHOD — `PasienFactory.java`

Class static yang membuat objek subclass `Pasien` sesuai string kategori.
Pemanggil **tidak perlu tahu** class konkret mana yang di-instantiate.

```java
Pasien p = PasienFactory.buat("GAWAT_DARURAT", "Agus", "3201...", 40, "Sesak napas");
// p sebetulnya adalah instance PasienGawatDarurat
```

### c. DAO (Data Access Object) — `dao/*DAO.java`

Pola yang memisahkan logika akses database dari logika bisnis.
DAO menyediakan method CRUD; form GUI cukup memanggil method tanpa tahu SQL.

---

## 4. Algoritma Priority Queue

**File:** `util/AntrianPrioritasKlinik.java`

Memanfaatkan `java.util.PriorityQueue` dari Java Collection, dengan `Comparator` custom:

1. Bandingkan **bobot prioritas** (descending — paling besar di depan).
2. Bila bobot sama, bandingkan **waktu daftar** (ascending — yang datang lebih awal
   didahulukan).

```java
new PriorityQueue<>(
    Comparator.comparingInt(Pasien::getBobotPrioritas).reversed()
              .thenComparing(Pasien::getWaktuDaftar)
);
```

Method penting:
| Method                  | Keterangan                                   |
|-------------------------|----------------------------------------------|
| `tambahAntrian(p)`      | Masukkan pasien ke posisi sesuai prioritas   |
| `panggilBerikutnya()`   | Ambil & hapus pasien prioritas tertinggi    |
| `lihatBerikutnya()`     | Lihat pasien berikutnya tanpa menghapus      |
| `getDaftarAntrian()`    | Snapshot urutan untuk ditampilkan di JTable  |
| `simpanKeRiwayat(p)`    | Pindahkan pasien ke list riwayat             |

---

## 5. Alur Aplikasi

```
[Main.java]
    └──> [FormLogin]  ──> admin / loket / poli
              │
   ┌──────────┼──────────────┐
   ▼          ▼              ▼
[ADMIN]   [LOKET]        [POLI]
   │          │              │
   ▼          ▼              ▼
Dashboard  Daftar         DashboardPoli
Loket      Pasien         - Panggil berikutnya
           - Input data   - Skip
           - Auto nomor   - Selesai
```

1. **Login:** `RiwayatDAO.login(user, pass)` cek ke tabel `petugas`.
2. **Pendaftaran:** Form input → `PasienFactory.buat(...)` → `PasienDAO.insert(p)`.
3. **Dashboard Loket:** Tampilkan seluruh pasien MENUNGGU (urut prioritas).
4. **Dashboard Poli:** `antrian.panggilBerikutnya()` ambil prioritas tertinggi →
   update status ke DIPANGGIL → selesai → catat ke `riwayat_pemeriksaan`.
5. **Riwayat:** Tampilkan pasien berstatus SELESAI / SKIP.
6. **Laporan:** Hitung jumlah pasien per kategori (JTable + bar chart tekstual).

---

## 6. Cara Menjalankan

> **Project sudah dikonversi ke Maven** — bisa dibuka di VS Code maupun NetBeans.

### Persiapan Database (wajib untuk semua IDE)

1. Jalankan **XAMPP** → aktifkan **MySQL**.
2. Buka `http://localhost/phpmyadmin` → tab **Import** → pilih
   `database/klinik_antrian.sql` → klik **Go**.
3. Sesuaikan koneksi di `util/KoneksiDatabase.java` jika user/password MySQL
   Anda berbeda dengan default (`root` / kosong).

### A. Jalankan di VS Code

1. Install **Extension Pack for Java** dari Microsoft di VS Code.
2. **File → Open Folder** → pilih `SistemAntrianKlinik`.
3. VS Code akan auto-detect file `pom.xml` dan menawarkan **"Yes, I trust the authors"**.
4. Maven akan otomatis mendownload dependency `mysql-connector-j` dari internet.
5. Buka `Main.java` → klik tombol **Run** (▶️) di pojok kanan atas, atau tekan **F5**.
   - Atau buka Terminal → jalankan: `mvn clean compile exec:java`
6. Login default:
   | Role  | Username | Password  |
   |-------|----------|-----------|
   | ADMIN | admin    | admin123  |
   | LOKET | loket    | loket123  |
   | POLI  | poli     | poli123   |

### B. Jalankan di NetBeans (setelah konversi Maven)

1. **File → Open Project** → pilih `SistemAntrianKlinik`.
   NetBeans akan otomatis mengenali file `pom.xml` sebagai Maven project.
2. Klik kanan project → **Run** (atau **F6**).
3. Login menggunakan akun yang sama seperti di atas.

> ⚠️ **Catatan:** Karena project sudah pindah ke Maven, folder source sekarang
> `src/main/java/` (bukan `src/`). Jika ada broken reference Libraries, hapus dulu
> semua entry lama lalu NetBeans akan membaca dependency dari `pom.xml` secara otomatis.

---

## 7. Kontribusi Tiap File terhadap Nilai PBO

| Konsep OOP          | File                                       | Bobot Nilai |
|---------------------|--------------------------------------------|-------------|
| Abstraction         | `model/Pasien.java`, `util/Antrian.java`  | Tinggi      |
| Inheritance         | 4 subclass `Pasien*`                       | Tinggi      |
| Polymorphism        | `getBobotPrioritas()` di subclass + Queue  | Tinggi      |
| Encapsulation       | Getter/setter + validasi NIK di `Pasien`   | Sedang      |
| Interface & Generic | `Antrian<T>` & implementasinya             | Tinggi      |
| Design Pattern      | Singleton, Factory, DAO                    | Plus        |
| Struktur Data       | `PriorityQueue` + `Comparator`             | Plus         |

---

## 8. Pengembangan Lanjutan (Opsional)

- Ganti bar chart tekstual dengan **JFreeChart** untuk grafik batang/pie yang lebih menarik.
- Tambah tema modern **FlatLaf** (`FlatIntelliJLaf.install()` di `Main.java`).
- Tambah unit test **JUnit** untuk `AntrianPrioritasKlinik`.
- Tambah role **Kepala Puskesmas** untuk melihat dashboard performa.

---

**Selamat mengerjakan tugas PBO! 🚀**