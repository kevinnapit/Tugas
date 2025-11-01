import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // 1. Inisialisasi Data Menu
        Menu[] daftarMenu = new Menu[8];

        // daftar makanan
        daftarMenu[0] = new Menu("Nasi Padang", 25000, "makanan");
        daftarMenu[1] = new Menu("Nasi Goreng", 22000, "makanan");
        daftarMenu[2] = new Menu("Mie Ayam", 18000, "makanan");
        daftarMenu[3] = new Menu("Soto Betawi", 30000, "makanan");

        // daftar minuman
        daftarMenu[4] = new Menu("Es Teh Manis", 5000, "minuman");
        daftarMenu[5] = new Menu("Jus Alpukat", 15000, "minuman");
        daftarMenu[6] = new Menu("Kopi Susu", 12000, "minuman");
        daftarMenu[7] = new Menu("Air Mineral", 3000, "minuman");

        // 2. Inisialisasi Scanner
        Scanner scanner = new Scanner(System.in);

        // 3. Menampilkan Menu

        tampilkanMenu(daftarMenu);

        // 4. Proses Pemesaan (maksimal 4 item)

        System.out.println("\n--- Silakan Masukkan Pesanan Anda ---");
        System.out.println("(Maksimal 4 jenis menu. Ketik 'stop' pada nama menu jika selesai memesan)");

        // --- Pesanan 1 ---
        System.out.println("\n--- Pesanan 1 ---");
        System.out.print("Masukkan Nama Menu: ");
        String nama1 = scanner.nextLine();
        int qty1 = 0;
        if (!nama1.equalsIgnoreCase("stop")) {
            System.out.print("Masukkan Jumlah: ");
            qty1 = scanner.nextInt();
            scanner.nextLine();
        }

        // --- Pesanan 2 ---
        String nama2 = "stop"; // Default 'stop' jika pesanan 1 sudah 'stop'
        int qty2 = 0;
        // Struktur Keputusan: Hanya tanya pesanan 2 jika pesanan 1 TIDAK 'stop'
        if (!nama1.equalsIgnoreCase("stop")) {
            System.out.println("\n--- Pesanan 2 ---");
            System.out.print("Masukkan Nama Menu: ");
            nama2 = scanner.nextLine();
            // Struktur Keputusan Bersarang: Hanya tanya jumlah jika nama menu 2 BUKAN 'stop'
            if (!nama2.equalsIgnoreCase("stop")) {
                System.out.print("Masukkan Jumlah: ");
                qty2 = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer
            }
        }

        // --- Pesanan 3 ---
        String nama3 = "stop";
        int qty3 = 0;
        // Struktur Keputusan: Hanya tanya pesanan 3 jika pesanan 1 DAN 2 TIDAK 'stop'
        if (!nama1.equalsIgnoreCase("stop") && !nama2.equalsIgnoreCase("stop")) {
            System.out.println("\n--- Pesanan 3 ---");
            System.out.print("Masukkan Nama Menu: ");
            nama3 = scanner.nextLine();
            if (!nama3.equalsIgnoreCase("stop")) {
                System.out.print("Masukkan Jumlah: ");
                qty3 = scanner.nextInt();
                scanner.nextLine();
            }
        }

        // --- Pesanan 4 ---
        String nama4 = "stop";
        int qty4 = 0;
        // Struktur Keputusan: Hanya tanya pesanan 4 jika pesanan 1, 2, DAN 3 TIDAK 'stop'
        if (!nama1.equalsIgnoreCase("stop") && !nama2.equalsIgnoreCase("stop") && !nama3.equalsIgnoreCase("stop")) {
            System.out.println("\n--- Pesanan 4 ---");
            System.out.print("Masukkan Nama Menu: ");
            nama4 = scanner.nextLine();
            if (!nama4.equalsIgnoreCase("stop")) {
                System.out.print("Masukkan Jumlah: ");
                qty4 = scanner.nextInt();
                scanner.nextLine();
            }
        }

        scanner.close();

        // --- 5. Mencari Objek Menu Berdasarkan Nama ---

        Menu menu1 = cariMenu(nama1, daftarMenu);
        Menu menu2 = cariMenu(nama2, daftarMenu);
        Menu menu3 = cariMenu(nama3, daftarMenu);
        Menu menu4 = cariMenu(nama4, daftarMenu);

        double subTotal = 0;
        double total1 = 0;
        double total2 = 0;
        double total3 = 0;
        double total4 = 0;

        if (menu1 != null && qty1 > 0) {
            total1 = menu1.harga * qty1;
            subTotal = subTotal + total1;
        }
        if (menu2 != null && qty2 > 0) {
            total2 = menu2.harga * qty2;
            subTotal = subTotal + total2;
        }
        if (menu3 != null && qty3 > 0) {
            total3 = menu3.harga * qty3;
            subTotal = subTotal + total3;
        }
        if (menu4 != null && qty4 > 0) {
            total4 = menu4.harga * qty4;
            subTotal = subTotal + total4;
        }

        // Inisialisasi variabel untuk biaya lain dan diskon
        double diskon = 0;
        double bogoDiscount = 0;
        String bogoItemName = ""; // Untuk menyimpan nama item yg kena BOGO
        double biayaPelayanan = 20000;
        double pajak = subTotal * 0.10; // Pajak 10% dari SubTotal

        if (subTotal > 100000) {
            diskon = subTotal * 0.10;
        }

        if (subTotal > 50000) {
            // Cek apakah pesanan 1 adalah minuman?
            if (menu1 != null && menu1.kategori.equals("minuman") && qty1 > 0) {
                bogoDiscount = menu1.harga; // Gratis harga 1 item
                bogoItemName = menu1.nama;
            }
            // Jika pesanan 1 bukan minuman, cek pesanan 2
            else if (menu2 != null && menu2.kategori.equals("minuman") && qty2 > 0) {
                bogoDiscount = menu2.harga;
                bogoItemName = menu2.nama;
            }
            // Jika pesanan 2 bukan minuman, cek pesanan 3
            else if (menu3 != null && menu3.kategori.equals("minuman") && qty3 > 0) {
                bogoDiscount = menu3.harga;
                bogoItemName = menu3.nama;
            }
            // Jika pesanan 3 bukan minuman, cek pesanan 4
            else if (menu4 != null && menu4.kategori.equals("minuman") && qty4 > 0) {
                bogoDiscount = menu4.harga;
                bogoItemName = menu4.nama;
            }
        }

        double totalAkhir = subTotal - diskon - bogoDiscount + pajak + biayaPelayanan;

        cetakStruk(
                menu1, qty1, total1,
                menu2, qty2, total2,
                menu3, qty3, total3,
                menu4, qty4, total4,
                subTotal, diskon, bogoDiscount, bogoItemName,
                pajak, biayaPelayanan, totalAkhir);

    }
    
     public static void tampilkanMenu(Menu[] daftarMenu) {
        System.out.println("========= MENU RESTORAN =========");
        System.out.println("--- Makanan ---");
        // Kita tahu Makanan ada di indeks 0 sampai 3
        System.out.println("1. " + daftarMenu[0].nama + " - Rp " + daftarMenu[0].harga);
        System.out.println("2. " + daftarMenu[1].nama + " - Rp " + daftarMenu[1].harga);
        System.out.println("3. " + daftarMenu[2].nama + " - Rp " + daftarMenu[2].harga);
        System.out.println("4. " + daftarMenu[3].nama + " - Rp " + daftarMenu[3].harga);

        System.out.println("\n--- Minuman ---");
        // Kita tahu Minuman ada di indeks 4 sampai 7
        System.out.println("5. " + daftarMenu[4].nama + " - Rp " + daftarMenu[4].harga);
        System.out.println("6. " + daftarMenu[5].nama + " - Rp " + daftarMenu[5].harga);
        System.out.println("7. " + daftarMenu[6].nama + " - Rp " + daftarMenu[6].harga);
        System.out.println("8. " + daftarMenu[7].nama + " - Rp " + daftarMenu[7].harga);
        System.out.println("=================================");
    }
    
    public static Menu cariMenu(String nama, Menu[] daftarMenu) {
        // Cek indeks 0
        if (nama.equalsIgnoreCase(daftarMenu[0].nama)) {
            return daftarMenu[0];
        }
        // Cek indeks 1
        else if (nama.equalsIgnoreCase(daftarMenu[1].nama)) {
            return daftarMenu[1];
        }
        // Cek indeks 2
        else if (nama.equalsIgnoreCase(daftarMenu[2].nama)) {
            return daftarMenu[2];
        }
        // Cek indeks 3
        else if (nama.equalsIgnoreCase(daftarMenu[3].nama)) {
            return daftarMenu[3];
        }
        // Cek indeks 4
        else if (nama.equalsIgnoreCase(daftarMenu[4].nama)) {
            return daftarMenu[4];
        }
        // Cek indeks 5
        else if (nama.equalsIgnoreCase(daftarMenu[5].nama)) {
            return daftarMenu[5];
        }
        // Cek indeks 6
        else if (nama.equalsIgnoreCase(daftarMenu[6].nama)) {
            return daftarMenu[6];
        }
        // Cek indeks 7
        else if (nama.equalsIgnoreCase(daftarMenu[7].nama)) {
            return daftarMenu[7];
        }
        // Jika tidak ada yang cocok
        else {
            return null; // Mengembalikan 'null' (kosong) jika menu tidak ditemukan
        }
    }
    

    public static void cetakStruk(
        Menu m1, int q1, double t1,
            Menu m2, int q2, double t2,
            Menu m3, int q3, double t3,
            Menu m4, int q4, double t4,
            double subTotal, double diskon, double bogoDiscount, String bogoItemName,
            double pajak, double biayaPelayanan, double totalAkhir
    ) {

        System.out.println("\n\n========= STRUK PEMBAYARAN =========");
        System.out.println("Item               | Qty | Harga      | Total");
        System.out.println("------------------------------------------");

        if (m1 != null && q1 > 0) {
            // System.printf dipakai untuk memformat teks agar rapi (rata kiri/kanan)
            // %-18s: String rata kiri, lebar 18 karakter
            // %-3d: Angka (decimal) rata kiri, lebar 3 karakter
            // %-7.0f: Angka desimal (float) rata kiri, lebar 7, 0 angka di belakang koma
            System.out.printf("%-18s | %-3d | Rp %-7.0f | Rp %.0f\n", m1.nama, q1, m1.harga, t1);
        }
        
        // Struktur Keputusan: Tampilkan pesanan 2 HANYA JIKA dipesan
        if (m2 != null && q2 > 0) {
            System.out.printf("%-18s | %-3d | Rp %-7.0f | Rp %.0f\n", m2.nama, q2, m2.harga, t2);
        }
        // Struktur Keputusan: Tampilkan pesanan 3 HANYA JIKA dipesan
        if (m3 != null && q3 > 0) {
            System.out.printf("%-18s | %-3d | Rp %-7.0f | Rp %.0f\n", m3.nama, q3, m3.harga, t3);
        }
        // Struktur Keputusan: Tampilkan pesanan 4 HANYA JIKA dipesan
        if (m4 != null && q4 > 0) {
            System.out.printf("%-18s | %-3d | Rp %-7.0f | Rp %.0f\n", m4.nama, q4, m4.harga, t4);
        }

        System.out.println("------------------------------------------");
        System.out.printf("Subtotal           : Rp %.0f\n", subTotal);

        // --- Skenario Struktur Keputusan di Struk ---
        // Tampilkan baris Diskon 10% HANYA JIKA ada diskon
        if (diskon > 0) {
            System.out.printf("Diskon (10%%)       : Rp -%.0f\n", diskon);
        }

        // Tampilkan baris BOGO HANYA JIKA ada diskon BOGO
        if (bogoDiscount > 0) {
            System.out.printf("Promo BOGO (%s) : Rp -%.0f\n", bogoItemName, bogoDiscount);
        }

        // Menampilkan biaya tetap
        System.out.printf("Pajak (10%%)        : Rp %.0f\n", pajak);
        System.out.printf("Biaya Pelayanan    : Rp %.0f\n", biayaPelayanan);
        System.out.println("==========================================");
        System.out.printf("TOTAL AKHIR        : Rp %.0f\n", totalAkhir);
        System.out.println("==========================================");
        System.out.println("      Terima Kasih Telah Berkunjung!      ");

    }
}
