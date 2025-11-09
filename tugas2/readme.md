// File: Menu.java

public class Menu {
    private String nama;
    private double harga;
    private String kategori;

    // Constructor
    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getters
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Setter (Hanya untuk harga, sesuai kebutuhan 'ubah harga')
    public void setHarga(double harga) {
        this.harga = harga;
    }
}

// File: Main.java

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    // ArrayList untuk menyimpan daftar menu.
    // Kita menggunakan ArrayList agar mudah menambah dan menghapus data.
    private ArrayList<Menu> daftarMenu = new ArrayList<>();
    
    // Scanner untuk input pengguna di seluruh aplikasi
    private Scanner scanner = new Scanner(System.in);
    
    // Format mata uang Rupiah
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));

    public static void main(String[] args) {
        Main aplikasi = new Main();
        aplikasi.inisialisasiMenuAwal(); // Tambahkan menu awal
        aplikasi.menuUtama(); // Mulai aplikasi
    }

    /**
     * Mengisi daftarMenu dengan data awal (minimum 4 makanan, 4 minuman)
     */
    public void inisialisasiMenuAwal() {
        // Makanan
        daftarMenu.add(new Menu("Nasi Goreng Spesial", 35000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Bakar Madu", 45000, "Makanan"));
        daftarMenu.add(new Menu("Sop Buntut", 60000, "Makanan"));
        daftarMenu.add(new Menu("Gado-Gado", 25000, "Makanan"));

        // Minuman
        daftarMenu.add(new Menu("Es Teh Manis", 10000, "Minuman"));
        daftarMenu.add(new Menu("Jus Alpukat", 20000, "Minuman"));
        daftarMenu.add(new Menu("Kopi Susu", 18000, "Minuman"));
        daftarMenu.add(new Menu("Air Mineral", 5000, "Minuman"));
    }

    /**
     * Menu utama aplikasi (Pilihan Pelanggan atau Pemilik Restoran)
     */
    public void menuUtama() {
        while (true) {
            System.out.println("\n--- Aplikasi Restoran Sederhana ---");
            System.out.println("1. Menu Pelanggan (Pesan)");
            System.out.println("2. Menu Pengelolaan (Pemilik)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    menuPelanggan();
                    break;
                case "2":
                    menuPengelolaan();
                    break;
                case "3":
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    return; // Keluar dari method, menghentikan program
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    // =================================================================
    // == BAGIAN MENU PELANGGAN
    // =================================================================

    /**
     * Alur lengkap untuk pelanggan: tampilkan menu, pesan, cetak struk
     */
    public void menuPelanggan() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Maaf, belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n--- Selamat Datang di Restoran Kami ---");
        tampilkanMenu();
        ArrayList<Menu> pesanan = terimaPesanan();
        
        if (pesanan.isEmpty()) {
            System.out.println("Anda tidak memesan apa pun.");
        } else {
            cetakStruk(pesanan);
        }
    }

    /**
     * Menampilkan semua menu yang ada, dikelompokkan per kategori
     */
    public void tampilkanMenu() {
        System.out.println("\n--- Daftar Menu ---");
        int nomor = 1;

        System.out.println("Kategori: Makanan");
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equals("Makanan")) {
                System.out.println(nomor + ". " + menu.getNama() + " - " + formatter.format(menu.getHarga()));
                nomor++;
            }
        }

        System.out.println("\nKategori: Minuman");
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equals("Minuman")) {
                System.out.println(nomor + ". " + menu.getNama() + " - " + formatter.format(menu.getHarga()));
                nomor++;
            }
        }
        System.out.println("--------------------");
    }

    /**
     * Proses input pesanan pelanggan
     * Menggunakan loop 'while' sampai pelanggan input 'selesai'
     * Menerapkan validasi input
     */
    public ArrayList<Menu> terimaPesanan() {
        ArrayList<Menu> pesananPelanggan = new ArrayList<>();
        while (true) {
            System.out.print("Masukkan nomor menu (atau 'selesai' untuk selesai): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                break; // Selesai memesan
            }

            try {
                int nomorMenu = Integer.parseInt(input);
                // Validasi: Nomor harus ada dalam rentang daftar menu
                if (nomorMenu >= 1 && nomorMenu <= daftarMenu.size()) {
                    Menu menuDipesan = daftarMenu.get(nomorMenu - 1);
                    pesananPelanggan.add(menuDipesan);
                    System.out.println(menuDipesan.getNama() + " berhasil ditambahkan.");
                } else {
                    // Input angka, tapi di luar rentang
                    System.out.println("Nomor menu tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                // Input bukan angka dan bukan 'selesai'
                System.out.println("Input tidak valid. Masukkan nomor menu atau 'selesai'.");
            }
        }
        return pesananPelanggan;
    }

    /**
     * Menghitung total biaya dan mencetak struk
     * Menerapkan struktur keputusan (if/else) untuk diskon dan promo
     */
    public void cetakStruk(ArrayList<Menu> pesanan) {
        System.out.println("\n--- Struk Pembayaran ---");
        
        // Menggunakan HashMap untuk menghitung jumlah per item
        Map<String, Integer> jumlahItem = new HashMap<>();
        Map<String, Double> hargaItem = new HashMap<>();
        double subTotal = 0;
        
        for (Menu menu : pesanan) {
            String nama = menu.getNama();
            jumlahItem.put(nama, jumlahItem.getOrDefault(nama, 0) + 1);
            hargaItem.put(nama, menu.getHarga());
            subTotal += menu.getHarga();
        }

        // Mencetak item yang dipesan
        for (Map.Entry<String, Integer> entry : jumlahItem.entrySet()) {
            String nama = entry.getKey();
            int jumlah = entry.getValue();
            double harga = hargaItem.get(nama);
            System.out.println(nama + "\t x" + jumlah + "\t " + formatter.format(harga * jumlah));
        }

        System.out.println("----------------------------------------");
        System.out.println("Subtotal \t\t " + formatter.format(subTotal));

        // Inisialisasi biaya
        double diskon = 0;
        double promoBOGO = 0;
        
        // Struktur Keputusan: Promo Beli 1 Gratis 1 Minuman (jika subTotal > 50.000)
        if (subTotal > 50000) {
            double hargaMinumanTermurah = Double.MAX_VALUE;
            boolean adaMinuman = false;
            for (Menu menu : pesanan) {
                if (menu.getKategori().equals("Minuman")) {
                    adaMinuman = true;
                    if (menu.getHarga() < hargaMinumanTermurah) {
                        hargaMinumanTermurah = menu.getHarga();
                    }
                }
            }
            if (adaMinuman) {
                promoBOGO = hargaMinumanTermurah;
                System.out.println("Promo BOGO Minuman \t-" + formatter.format(promoBOGO));
            }
        }
        
        // Struktur Keputusan: Diskon 10% (jika subTotal > 100.000)
        if (subTotal > 100000) {
            diskon = subTotal * 0.10;
            System.out.println("Diskon 10% \t\t-" + formatter.format(diskon));
        }

        // Hitung subtotal setelah diskon dan promo
        double subtotalSetelahDiskon = subTotal - diskon - promoBOGO;
        
        double biayaLayanan = 20000;
        double pajak = subtotalSetelahDiskon * 0.10;

        System.out.println("Biaya Layanan \t\t " + formatter.format(biayaLayanan));
        System.out.println("Pajak 10% \t\t " + formatter.format(pajak));
        System.out.println("----------------------------------------");

        double totalBayar = subtotalSetelahDiskon + biayaLayanan + pajak;
        System.out.println("Total Pembayaran \t " + formatter.format(totalBayar));
        System.out.println("----------------------------------------");
    }

    // =================================================================
    // == BAGIAN MENU PENGELOLAAN
    // =================================================================

    /**
     * Menu navigasi untuk pemilik restoran
     */
    public void menuPengelolaan() {
        while (true) {
            System.out.println("\n--- Menu Pengelolaan ---");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    tambahMenu();
                    break;
                case "2":
                    ubahHargaMenu();
                    break;
                case "3":
                    hapusMenu();
                    break;
                case "4":
                    return; // Kembali ke menuUtama()
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    /**
     * Logika untuk menambah menu baru ke 'daftarMenu'
     * Menerapkan struktur pengulangan (while) untuk validasi input
     */
    private void tambahMenu() {
        System.out.println("\n--- Tambah Menu Baru ---");
        System.out.print("Masukkan Nama Menu: ");
        String nama = scanner.nextLine();

        double harga = 0;
        while (true) {
            System.out.print("Masukkan Harga Menu: ");
            try {
                harga = Double.parseDouble(scanner.nextLine());
                if (harga >= 0) {
                    break;
                } else {
                    System.out.println("Harga tidak boleh negatif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harga tidak valid. Masukkan angka.");
            }
        }

        String kategori = "";
        while (true) {
            System.out.print("Masukkan Kategori (Makanan / Minuman): ");
            kategori = scanner.nextLine();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                // Kapitalisasi huruf pertama
                kategori = kategori.substring(0, 1).toUpperCase() + kategori.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Kategori tidak valid. Harap masukkan 'Makanan' atau 'Minuman'.");
            }
        }

        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu '" + nama + "' berhasil ditambahkan.");
    }

    /**
     * Logika untuk mengubah harga menu yang ada
     * Menerapkan validasi input dan konfirmasi 'Ya'
     */
    private void ubahHargaMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu untuk diubah.");
            return;
        }

        System.out.println("\n--- Ubah Harga Menu ---");
        tampilkanMenuRingkas(); // Tampilkan menu dengan nomor urut asli

        int nomorMenu = -1;
        while (true) {
            System.out.print("Masukkan nomor menu yang akan diubah harganya: ");
            try {
                nomorMenu = Integer.parseInt(scanner.nextLine());
                if (nomorMenu >= 1 && nomorMenu <= daftarMenu.size()) {
                    break; // Nomor valid
                } else {
                    System.out.println("Nomor menu tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan nomor.");
            }
        }

        Menu menu = daftarMenu.get(nomorMenu - 1);
        System.out.println("Menu dipilih: " + menu.getNama() + " (Harga saat ini: " + formatter.format(menu.getHarga()) + ")");

        double hargaBaru = 0;
        while (true) {
            System.out.print("Masukkan Harga Baru: ");
            try {
                hargaBaru = Double.parseDouble(scanner.nextLine());
                if (hargaBaru >= 0) {
                    break;
                } else {
                    System.out.println("Harga tidak boleh negatif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harga tidak valid. Masukkan angka.");
            }
        }

        System.out.print("Anda yakin ingin mengubah harga? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            menu.setHarga(hargaBaru);
            System.out.println("Harga menu '" + menu.getNama() + "' berhasil diubah.");
        } else {
            System.out.println("Perubahan harga dibatalkan.");
        }
    }

    /**
     * Logika untuk menghapus menu dari 'daftarMenu'
     * Menerapkan validasi input dan konfirmasi 'Ya'
     */
    private void hapusMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu untuk dihapus.");
            return;
        }

        System.out.println("\n--- Hapus Menu ---");
        tampilkanMenuRingkas(); // Tampilkan menu dengan nomor urut asli

        int nomorMenu = -1;
        while (true) {
            System.out.print("Masukkan nomor menu yang akan dihapus: ");
            try {
                nomorMenu = Integer.parseInt(scanner.nextLine());
                if (nomorMenu >= 1 && nomorMenu <= daftarMenu.size()) {
                    break; // Nomor valid
                } else {
                    System.out.println("Nomor menu tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan nomor.");
            }
        }

        Menu menu = daftarMenu.get(nomorMenu - 1);
        System.out.println("Menu dipilih: " + menu.getNama());
        System.out.print("Anda yakin ingin menghapus menu ini? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            daftarMenu.remove(nomorMenu - 1);
            System.out.println("Menu '" + menu.getNama() + "' berhasil dihapus.");
        } else {
            System.out.println("Penghapusan menu dibatalkan.");
        }
    }

    /**
     * Helper method untuk menampilkan menu (tanpa pengelompokan)
     * Digunakan untuk proses Ubah dan Hapus
     */
    private void tampilkanMenuRingkas() {
        System.out.println("Daftar Menu Saat Ini:");
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            System.out.println((i + 1) + ". " + menu.getNama() + " (" + menu.getKategori() + ") - " + formatter.format(menu.getHarga()));
        }
        System.out.println("--------------------");
    }
}