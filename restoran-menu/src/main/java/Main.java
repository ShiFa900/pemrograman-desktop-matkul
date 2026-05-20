
// import java.util.Scanner;

// public class Main {

//     // Array seluruh menu restoran
//     static Menu[] daftarMenu = {
//         new Menu(1, "Nasi Goreng", 15000, "Makanan"),
//         new Menu(2, "Nasi Padang", 20000, "Makanan"),
//         new Menu(3, "Mie Goreng", 10000, "Makanan"),
//         new Menu(4, "Ayam Bakar", 15000, "Makanan"),
//         new Menu(5, "Es Teh", 5000, "Minuman"),
//         new Menu(6, "Jus Alpukat", 10000, "Minuman"),
//         new Menu(7, "Air Mineral", 3000, "Minuman"),
//         new Menu(8, "Es Jeruk", 7000, "Minuman")
//     };

//     public static void main(String[] args) {
//         Scanner input = new Scanner(System.in);

// // kita akan tampilkan menu terus sampai tidak memilih kembali
//         tampilkanMenu();

//         System.out.println("\nMasukkan pesanan Anda (maks 4 item)");
//         System.out.println("Pilih nomor menu (1-8). Ketik 's' untuk berhenti memesan.\n");

//         // Array pesanan
//         int[] nomorMenu = {0, 0, 0, 0};
//         int[] qty = {0, 0, 0, 0};
//         int jumlahItem = 0;

//         // --- Input Item Ke-1 ---
//         System.out.print("Pesanan ke-1 (nomor menu / 's' untuk skip): ");
//         String pilihan = input.nextLine().trim();

//         if (!pilihan.equalsIgnoreCase("s")) {
//             nomorMenu[0] = Integer.parseInt(pilihan);
//             System.out.print("Jumlah: ");
//             qty[0] = Integer.parseInt(input.nextLine().trim());
//             jumlahItem = 1;

//             System.out.print("\nTambah pesanan? (y/t): ");
//             String tambah = input.nextLine().trim();

//             if (cekYa(tambah)) {

//                 // --- Input Item Ke-2 ---
//                 System.out.print("Pesanan ke-2 (nomor menu / 's' untuk skip): ");
//                 pilihan = input.nextLine().trim();

//                 if (!pilihan.equalsIgnoreCase("s")) {
//                     nomorMenu[1] = Integer.parseInt(pilihan);
//                     System.out.print("Jumlah: ");
//                     qty[1] = Integer.parseInt(input.nextLine().trim());
//                     jumlahItem = 2;

//                     System.out.print("\nTambah pesanan? (y/t): ");
//                     tambah = input.nextLine().trim();

//                     if (cekYa(tambah)) {

//                         // --- Input Item Ke-3 ---
//                         System.out.print("Pesanan ke-3 (nomor menu / 's' untuk skip): ");
//                         pilihan = input.nextLine().trim();

//                         if (!pilihan.equalsIgnoreCase("s")) {
//                             nomorMenu[2] = Integer.parseInt(pilihan);
//                             System.out.print("Jumlah: ");
//                             qty[2] = Integer.parseInt(input.nextLine().trim());
//                             jumlahItem = 3;

//                             System.out.print("\nTambah pesanan? (y/t): ");
//                             tambah = input.nextLine().trim();

//                             if (cekYa(tambah)) {

//                                 // --- Input Item Ke-4 ---
//                                 System.out.print("Pesanan ke-4 (nomor menu / 's' untuk skip): ");
//                                 pilihan = input.nextLine().trim();

//                                 if (!pilihan.equalsIgnoreCase("s")) {
//                                     nomorMenu[3] = Integer.parseInt(pilihan);
//                                     System.out.print("Jumlah: ");
//                                     qty[3] = Integer.parseInt(input.nextLine().trim());
//                                     jumlahItem = 4;
//                                 }
//                             }
//                         }
//                     }
//                 }
//             }
//         }

//         input.close();

//         if (jumlahItem == 0) {
//             System.out.println("\nTidak ada pesanan. Sampai jumpa!");
//             return;
//         }

//         // Hitung subtotal dan cetak struk
//         int subtotal = hitungSubtotal(nomorMenu, qty, jumlahItem);
//         cetakStruk(nomorMenu, qty, jumlahItem, subtotal);
//     }

//     // ─── Helper: cek apakah jawaban "ya" ───────────────────────────────────────
//     static boolean cekYa(String jawaban) {
//         return jawaban.equalsIgnoreCase("y")
//                 || jawaban.equalsIgnoreCase("ya")
//                 || jawaban.equalsIgnoreCase("yes");
//     }

//     // ─── Tampilkan daftar menu ─────────────────────────────────────────────────
//     static void tampilkanMenu() {
//         System.out.println("============================================");
//         System.out.println("       UMAH RASA - DAFTAR MENU     ");
//         System.out.println("============================================");

//         // lakukan perulangan di sini agar menu dapat ditampilkan terus
//         System.out.printf("%-4s %-18s %-10s %8s%n", "No", "Nama Menu", "Kategori", "Harga");
//         System.out.println("--------------------------------------------");

//         for (int i = 0; i < daftarMenu.length; i++) {
//             Menu m = daftarMenu[i];
//             // menu akan ditampilkan dari makanan terlebih dahulu baru minuman, jadi kita cek kategori dulu
//             System.out.printf("%-4s %-18s %-10s %8s%n", (i + 1), m.getNama(), m.getKategori(), "Rp " + rupiah(m.getHarga()));
//             // if (m.getKategori().equalsIgnoreCase("Minuman")) {
//             //     System.out.println("--------------------------------------------");
//             // }
//         }
//         System.out.println("============================================");

//     }

//     // mencari menu berdasarkan nomor
//     static Menu getMenu(int no) {
//         switch (no) {
//             case 1:
//                 return daftarMenu[0];
//             case 2:
//                 return daftarMenu[1];
//             case 3:
//                 return daftarMenu[2];
//             case 4:
//                 return daftarMenu[3];
//             case 5:
//                 return daftarMenu[4];
//             case 6:
//                 return daftarMenu[5];
//             case 7:
//                 return daftarMenu[6];
//             case 8:
//                 return daftarMenu[7];
//             default:
//                 return null;
//         }
//     }

//     //hitung total makanan 
//     static int hitungSubtotal(int[] nomorMenu, int[] qty, int jumlahItem) {
//         int subtotal = 0;

//         if (jumlahItem >= 1) {
//             Menu m = getMenu(nomorMenu[0]);
//             if (m != null) {
//                 subtotal += m.getHarga() * qty[0];
//             }
//         }
//         if (jumlahItem >= 2) {
//             Menu m = getMenu(nomorMenu[1]);
//             if (m != null) {
//                 subtotal += m.getHarga() * qty[1];
//             }
//         }
//         if (jumlahItem >= 3) {
//             Menu m = getMenu(nomorMenu[2]);
//             if (m != null) {
//                 subtotal += m.getHarga() * qty[2];
//             }
//         }
//         if (jumlahItem >= 4) {
//             Menu m = getMenu(nomorMenu[3]);
//             if (m != null) {
//                 subtotal += m.getHarga() * qty[3];
//             }
//         }

//         return subtotal;
//     }

//     // menghitung total harga minuman
//     static int hitungTotalMinuman(int[] nomorMenu, int[] qty, int jumlahItem) {
//         int totalMinuman = 0;

//         if (jumlahItem >= 1) {
//             Menu m = getMenu(nomorMenu[0]);
//             if (m != null && m.getKategori().equalsIgnoreCase("Minuman")) {
//                 totalMinuman += m.getHarga() * qty[0];
//             }
//         }
//         if (jumlahItem >= 2) {
//             Menu m = getMenu(nomorMenu[1]);
//             if (m != null && m.getKategori().equalsIgnoreCase("Minuman")) {
//                 totalMinuman += m.getHarga() * qty[1];
//             }
//         }
//         if (jumlahItem >= 3) {
//             Menu m = getMenu(nomorMenu[2]);
//             if (m != null && m.getKategori().equalsIgnoreCase("Minuman")) {
//                 totalMinuman += m.getHarga() * qty[2];
//             }
//         }
//         if (jumlahItem >= 4) {
//             Menu m = getMenu(nomorMenu[3]);
//             if (m != null && m.getKategori().equalsIgnoreCase("Minuman")) {
//                 totalMinuman += m.getHarga() * qty[3];
//             }
//         }

//         return totalMinuman;
//     }

//     // formating baris pesanan di struk
//     static void cetakBarisPesanan(int no, int qty) {
//         Menu m = getMenu(no);
//         if (m != null) {
//             int totalItem = m.getHarga() * qty;
//             System.out.printf("%-18s %5d  %10s  %10s%n",
//                     m.getNama(), qty,
//                     "Rp " + rupiah(m.getHarga()),
//                     "Rp " + rupiah(totalItem));
//         }
//     }

//     // mencetak struk
//     static void cetakStruk(int[] nomorMenu, int[] qty, int jumlahItem, int subtotal) {
//         System.out.println("\n============================================");
//         System.out.println("              STRUK PESANAN                 ");
//         System.out.println("============================================");
//         System.out.printf("%-18s %5s  %10s  %10s%n", "Item", "Qty", "Harga/pcs", "Total");
//         System.out.println("--------------------------------------------");

//         if (jumlahItem >= 1) {
//             cetakBarisPesanan(nomorMenu[0], qty[0]);
//         }
//         if (jumlahItem >= 2) {
//             cetakBarisPesanan(nomorMenu[1], qty[1]);
//         }
//         if (jumlahItem >= 3) {
//             cetakBarisPesanan(nomorMenu[2], qty[2]);
//         }
//         if (jumlahItem >= 4) {
//             cetakBarisPesanan(nomorMenu[3], qty[3]);
//         }

//         System.out.println("--------------------------------------------");
//         System.out.printf("%-30s %12s%n", "Subtotal:", "Rp " + rupiah(subtotal));

//         // skenario: diskon 10% jika subtotal > Rp100.000
//         int diskon = 0;
//         if (subtotal > 100000) {
//             diskon = (int) (0.10 * subtotal);
//             System.out.printf("%-30s %12s%n", "Diskon 10% (> Rp100.000):", "-Rp " + rupiah(diskon));
//         }

//         // skenario: Promo Beli 1 Gratis 1 minuman jika subtotal > Rp50.000
//         int bonusMinuman = 0;
//         if (subtotal > 50000) {
//             bonusMinuman = hitungTotalMinuman(nomorMenu, qty, jumlahItem);
//             if (bonusMinuman > 0) {
//                 System.out.printf("%-30s %12s%n", "Promo Minuman (> Rp50.000):", "-Rp " + rupiah(bonusMinuman));
//             }
//         }

//         //pajak 10%
//         int pajak = (int) (0.10 * subtotal);
//         System.out.printf("%-30s %12s%n", "Pajak (10% dari subtotal):", "+Rp " + rupiah(pajak));

//         // skenario: biaya pelayanan
//         int service = 20000;
//         System.out.printf("%-30s %12s%n", "Biaya Pelayanan:", "+Rp " + rupiah(service));

//         int totalBayar = subtotal - diskon - bonusMinuman + pajak + service;
//         System.out.println("============================================");
//         System.out.printf("%-30s %12s%n", "TOTAL BAYAR:", "Rp " + rupiah(totalBayar));
//         System.out.println("============================================");

//         // Informasi promo yang aktif
//         if (subtotal > 100000) {
//             System.out.println("[INFO] Selamat! Anda mendapat diskon 10%.");
//         }
//         if (subtotal > 50000 && bonusMinuman > 0) {
//             System.out.println("[INFO] Selamat! Promo Beli 1 Gratis 1 untuk minuman aktif.");
//         }

//         System.out.println("\n     Terima kasih atas kunjungan Anda!    ");
//         System.out.println("============================================");
//     }

//     // format angka ke rupiah
//     static String rupiah(int amount) {
//         return String.format(java.util.Locale.US, "%,d", amount).replace(",", ".");
//     }
// }
