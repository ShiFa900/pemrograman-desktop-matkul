
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTugas2 {

    static Scanner input = new Scanner(System.in);

    // Array seluruh menu restoran
    static Menu[] daftarMenu = {
        new Menu(1, "Nasi Goreng", 15000, TipeMenu.MAKANAN),
        new Menu(2, "Nasi Padang", 20000, TipeMenu.MAKANAN),
        new Menu(3, "Mie Goreng", 10000, TipeMenu.MAKANAN),
        new Menu(4, "Ayam Bakar", 15000, TipeMenu.MAKANAN),
        new Menu(5, "Es Teh", 5000, TipeMenu.MINUMAN),
        new Menu(6, "Jus Alpukat", 10000, TipeMenu.MINUMAN),
        new Menu(7, "Air Mineral", 3000, TipeMenu.MINUMAN),
        new Menu(8, "Es Jeruk", 7000, TipeMenu.MINUMAN)
    };

    public static void main(String[] args) {
        mainMenu();
    }

    // ─── Tampilkan daftar menu ─────────────────────────────────────────────────
    static int tampilkanMenu(String cred) {
        // menu pelanggan
        System.out.println("============================================");
        System.out.println("       UMAH RASA - DAFTAR MENU     ");
        System.out.println("============================================");

        // lakukan perulangan di sini agar menu dapat ditampilkan terus
        System.out.printf("%-4s %-18s %-10s %8s%n", "No", "Nama Menu", "Kategori", "Harga");
        System.out.println("--------------------------------------------");

        for (int i = 0; i < daftarMenu.length; i++) {
            Menu m = daftarMenu[i];
            // menu akan ditampilkan dari makanan terlebih dahulu baru minuman, jadi kita cek kategori dulu
            System.out.printf("%-4s %-18s %-10s %8s%n", (i + 1), m.getNama(), m.getKategori(), "Rp " + rupiah(m.getHarga()));
        }

        System.out.println("--------------------------------------------\n");

        if (cred.equalsIgnoreCase("admin")) {
            return -1; // return -1 untuk admin agar bisa masuk ke menu admin
        }

        return 0; // return 0 untuk pelanggan agar bisa masuk ke menu pelanggan

    }

    // mencari menu berdasarkan nomor
    static Menu getMenu(int no) {
        for (Menu m : daftarMenu) {
            if (m.getId() == no) {
                return m;
            }
        }
        return null;
    }

    //hitung total makanan 
    static long hitungTotal(List<Integer> nomorMenu, List<Integer> qty) {
        long subtotal = 0;

        for (int i = 0; i < nomorMenu.size(); i++) {
            Menu m = getMenu(nomorMenu.get(i));
            if (m != null) {
                subtotal += m.getHarga() * qty.get(i);
            }
        }

        return subtotal;
    }

    // menghitung total harga minuman
    static long hitungTotalMinuman(List<Integer> nomorMenu, List<Integer> qty, int jumlahItem) {
        long totalMinuman = 0;

        for (int i = 0; i < jumlahItem; i++) {
            Menu m = getMenu(nomorMenu.get(i));
            if (m != null && m.getKategori() == TipeMenu.MINUMAN) {
                totalMinuman += m.getHarga() * qty.get(i);
            }
        }

        return totalMinuman;
    }

    // formating baris pesanan di struk
    static void cetakBarisPesanan(int no, int qty) {
        Menu m = getMenu(no);
        if (m != null) {
            long totalItem = m.getHarga() * qty;
            System.out.printf("%-18s %5d  %10s  %10s%n",
                    m.getNama(), qty,
                    "Rp " + rupiah(m.getHarga()),
                    "Rp " + rupiah(totalItem));
        }
    }

    // mencetak struk
    static void cetakStruk(List<Integer> nomorMenu, List<Integer> qty, long subtotal) {
        System.out.println("\n============================================");
        System.out.println("              STRUK PESANAN                 ");
        System.out.println("============================================");
        System.out.printf("%-18s %5s  %10s  %10s%n", "Item", "Qty", "Harga/pcs", "Total");
        System.out.println("--------------------------------------------");

        for (int i = 0; i < nomorMenu.size(); i++) {
            cetakBarisPesanan(nomorMenu.get(i), qty.get(i));
        }

        System.out.println("--------------------------------------------");
        System.out.printf("%-30s %12s%n", "Subtotal:", "Rp " + rupiah(subtotal));

        // skenario: diskon 10% jika subtotal > Rp100.000
        long diskon = 0;
        if (subtotal > 100000) {
            diskon = (long) (0.10 * subtotal);
            System.out.printf("%-30s %12s%n", "Diskon 10% (> Rp100.000):", "-Rp " + rupiah(diskon));
        }

        // skenario: Promo Beli 1 Gratis 1 minuman jika subtotal > Rp50.000
        long bonusMinuman = 0;
        if (subtotal > 50000) {
            bonusMinuman = hitungTotalMinuman(nomorMenu, qty, nomorMenu.size());
            if (bonusMinuman > 0) {
                System.out.printf("%-30s %12s%n", "Promo Minuman (> Rp50.000):", "-Rp " + rupiah(bonusMinuman));
            }
        }

        //pajak 10%
        long pajak = (long) (0.10 * subtotal);
        System.out.printf("%-30s %12s%n", "Pajak (10% dari subtotal):", "+Rp " + rupiah(pajak));

        // skenario: biaya pelayanan
        long service = 20000;
        System.out.printf("%-30s %12s%n", "Biaya Pelayanan:", "+Rp " + rupiah(service));

        long totalBayar = subtotal - diskon - bonusMinuman + pajak + service;
        System.out.println("============================================");
        System.out.printf("%-30s %12s%n", "TOTAL BAYAR:", "Rp " + rupiah(totalBayar));
        System.out.println("============================================");

        // Informasi promo yang aktif
        if (subtotal > 100000) {
            System.out.println("[INFO] Selamat! Anda mendapat diskon 10%.");
        }
        if (subtotal > 50000 && bonusMinuman > 0) {
            System.out.println("[INFO] Selamat! Promo Beli 1 Gratis 1 untuk minuman aktif.");
        }

        System.out.println("\n     Terima kasih atas kunjungan Anda!    ");
        System.out.println("============================================\n");
    }

    // format angka ke rupiah
    static String rupiah(long amount) {
        return String.format(java.util.Locale.US, "%,d", amount).replace(",", ".");
    }

    static void kelolaPesanan() {
        List<Integer> nomorMenu = new ArrayList<>();
        List<Integer> qty = new ArrayList<>();

        while (true) {
            tampilkanMenu("pelanggan");

            System.out.print("Pilih nomor menu. Ketik 's' untuk berhenti: ");
            String pilihan = input.nextLine().trim();

            // Berhenti jika user ketik 's'
            if (pilihan.equalsIgnoreCase("s") || pilihan.equalsIgnoreCase("stop") || pilihan.equalsIgnoreCase("selesai")) {
                break;
            } else {

                // Validasi apakah input adalah angka
                if (!pilihan.matches("\\d+")) {
                    System.out.println("Input tidak valid! Masukkan nomor menu.");
                    continue;
                }

                int nomorPilihan = Integer.parseInt(pilihan);

                // Validasi range nomor menu
                if (nomorPilihan < 1 || nomorPilihan > daftarMenu.length) {
                    System.out.println("Nomor menu tidak tersedia. Silakan coba lagi.");
                    continue;
                }

                // Input jumlah
                System.out.print("Jumlah: ");
                String inputJumlah = input.nextLine().trim();

                if (!inputJumlah.matches("\\d+") || Integer.parseInt(inputJumlah) < 1) {
                    System.out.println("Jumlah tidak valid!");
                    continue;
                }

                int jumlah = Integer.parseInt(inputJumlah);

                // Simpan pesanan
                nomorMenu.add(nomorPilihan);
                qty.add(jumlah);
                System.out.println("\nPesanan ditambahkan!\n");
            }

            // Konfirmasi tambah pesanan
            if (!cekKonfirmasi("Tambah pesanan lagi ")) {
                break; // keluar dari metode kelolaPesanan
            }
        }

        long subtotal = hitungTotal(nomorMenu, qty);
        cetakStruk(nomorMenu, qty, subtotal);
    }

    static void tambahMenu() {
        input.nextLine(); // bersihkan buffer sebelumnya

        System.out.print("Nama menu: ");
        String namaMenu = input.nextLine().trim();

        System.out.print("Harga: ");
        int harga = input.nextInt();
        input.nextLine(); // membersihkan newline yang tersisa
        System.out.print("Kategori (Makanan/Minuman): ");
        String kategori = input.next().trim();

        // tambahkan menu pada array daftarMenu
        Menu menuBaru = new Menu(
                daftarMenu.length + 1,
                namaMenu,
                Long.parseLong(String.valueOf(harga)),
                TipeMenu.valueOf(kategori.toUpperCase())
        );

        // buat array baru ukuran +1
        Menu[] daftarBaru = new Menu[daftarMenu.length + 1];

        // copy data lama
        for (int i = 0; i < daftarMenu.length; i++) {
            daftarBaru[i] = daftarMenu[i];
        }

// tambahkan menu baru di indeks terakhir
        daftarBaru[daftarMenu.length] = menuBaru;

// timpa array lama
        daftarMenu = daftarBaru;

        System.out.println("Menu berhasil ditambahkan!\n");
    }

    static void editMenu() {
// tampilkan menu yang ada
        tampilkanMenu("admin");
// minta input nomor menu yang ingin diedit
        System.out.print("Masukkan nomor menu yang ingin diedit/'0' untuk kembali: ");
        int noMenu = input.nextInt();
        if (noMenu == 0) {
            return; // kembali ke menu admin
        } else if (noMenu < 1 || noMenu > daftarMenu.length) {
            System.out.println("Nomor menu tidak valid.");
            return;
        }

// mencari menu berdasarkan nomor
        Menu menu = getMenu(noMenu);
        if (menu == null) {
// menu yang akan diedit tidak ditemukan
            System.out.println("Menu tidak ditemukan.");
        } else {
            if (menu.getId() == noMenu) {
// kita akan melakukan perubahan pada data ini
                System.out.println("Nama menu untuk di edit: " + menu.getNama());
                System.out.print("Harga[" + menu.getHarga() + "]: ");
                String hargaBaru = input.next().trim();

                input.nextLine(); //bersihkan newline
                String pesanKonfirmasi = "Apakah Anda yakin ingin mengubah harga menu ini";

                if (cekKonfirmasi(pesanKonfirmasi)) {
                    if (!hargaBaru.isEmpty()) {
                        menu.setHarga(Long.parseLong(hargaBaru));
                    }
                    System.out.println("Menu berhasil diedit!\n");
                } else {
                    System.out.println("Perubahan dibatalkan.\n");
                }
            }
        }
    }

    static boolean cekKonfirmasi(String pesan) {
        List<String> ya = List.of("y", "ya", "yes");
        List<String> tidak = List.of("t", "tidak", "no");

        while (true) {
            System.out.print(pesan + " (y/t)?: ");
            String jawaban = input.nextLine().trim().toLowerCase();

            if (ya.contains(jawaban)) {
                return true;
            }
            if (tidak.contains(jawaban)) {
                return false;
            }

            System.out.println("Input tidak valid! Masukkan y/t.");
        }
    }

    static void hapusMenu() {
        // tampilkan menu yang ada
        tampilkanMenu("admin");
// minta input nomor menu yang ingin dihapus
        System.out.print("Masukkan nomor menu yang ingin dihapus/'0' untuk kembali: ");
        int noMenu = input.nextInt();
        input.nextLine();
        if (noMenu == 0) {
            return; // kembali ke menu admin
        } else if (noMenu < 1 || noMenu > daftarMenu.length) {
            System.out.println("Nomor menu tidak valid.");
            return;
        }

        for (int i = 0; i < daftarMenu.length; i++) {
            Menu m = daftarMenu[i];
            if (daftarMenu[i].getId() == noMenu) {
                // tampilkan konfirmasi penghapusan
                System.out.println("Nama: " + m.getNama());
                System.out.println("Harga: " + m.getHarga());
                System.out.println("Kategori: " + m.getKategori());
                String pesanKonfirmasi = "Apakah Anda yakin ingin menghapus menu ini";
                if (cekKonfirmasi(pesanKonfirmasi)) {
                    // buat array baru ukuran -1
                    Menu[] daftarBaru = new Menu[daftarMenu.length - 1];

                    int indexBaru = 0;

                    for (Menu menu : daftarMenu) {
                        if (menu.getId() != noMenu) {
                            daftarBaru[indexBaru] = menu;
                            indexBaru++;
                        }
                    }

                    daftarMenu = daftarBaru;
                    System.out.println("Menu berhasil dihapus!\n");
                } else {
                    System.out.println("Penghapusan dibatalkan.\n");
                }
            }
        }
    }

    static int tampilkanMenuAdmin() {
        System.out.println("============================================");
        System.out.println("         SELAMAT DATANG ADMIN       ");
        System.out.println("============================================");
        System.out.println("1. Tambah Menu");
        System.out.println("2. Edit Menu");
        System.out.println("3. Hapus Menu");
        System.out.println("4. Lihat Menu");
        System.out.println("5. Kembali");

        System.out.print("Pilih menu: ");

        // cek jika input bertipe int
        if (!input.hasNextInt()) {
            return 0;
        }

        int pilihan = input.nextInt();

        if (pilihan < 1 || pilihan > 5) {
            return 0;
        }
        return pilihan;
    }

    static void menuAdmin() {
        boolean valid = false;
        while (!valid) {
            // menu admin
            int menu = tampilkanMenuAdmin();
            switch (menu) {
                case 1:
                    // tambah menu di sini
                    tambahMenu();
                    break;
                case 2:
                    // edit menu di sini
                    editMenu();
                    break;
                case 3:
                    // hapus menu di sini
                    hapusMenu();
                    break;
                case 4:
                    tampilkanMenu("admin");
                    break;
                case 5:
                    valid = true;
                    break;
                case 0:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi: ");
                    break;
                default:
                    break;
            }
        }
    }

    static int tampilkanMenuUtama() {
        // ini adalah menu yang handle kedua menu utama, pelanggan dan admin
        System.out.println("\n============================================");
        System.out.println("         SELAMAT DATANG DI UMAH RASA       ");
        System.out.println("============================================");
        System.out.println("1. Menu Pelanggan");
        System.out.println("2. Menu Admin");
        System.out.println("3. Keluar");
        System.out.print("Pilih menu yang anda inginkan (1-3): ");
        if (!input.hasNextInt()) {
            return 0;
        }

        int pilihan = input.nextInt();

        if (pilihan < 1 || pilihan > 3) {
            return 0;
        }
        return pilihan;

    }

    static void mainMenu() {
        // ini adalah method yang handle menu utama, pelanggan dan admin
        boolean valid = false;
        while (!valid) {
            int menu = tampilkanMenuUtama();
            switch (menu) {
                case 0:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
                case 1:
                    // menu pelanggan
                    kelolaPesanan();
                    break;
                case 2:
                    // menu admin
                    menuAdmin();
                    break;
                default:
                    valid = true;
                    System.out.println("Terima kasih! Sampai jumpa!");
                    System.exit(0);
            }
        }
    }

}
