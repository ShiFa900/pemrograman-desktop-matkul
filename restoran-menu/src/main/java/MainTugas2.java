
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTugas2 {

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
        Scanner input = new Scanner(System.in);

        // kita akan tampilkan menu terus sampai tidak memilih kembali
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

        System.out.println("--------------------------------------------");

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
    static int hitungTotal(int[] nomorMenu, int[] qty) {
        int subtotal = 0;

        for (int i = 0; i < nomorMenu.length; i++) {
            Menu m = getMenu(nomorMenu[i]);
            if (m != null) {
                subtotal += m.getHarga() * qty[i];
            }
        }

        return subtotal;
    }

    // menghitung total harga minuman
    static int hitungTotalMinuman(int[] nomorMenu, int[] qty, int jumlahItem) {
        int totalMinuman = 0;

        for (int i = 0; i < jumlahItem; i++) {
            Menu m = getMenu(nomorMenu[i]);
            if (m != null && m.getKategori() == TipeMenu.MINUMAN) {
                totalMinuman += m.getHarga() * qty[i];
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
    static void cetakStruk(int[] nomorMenu, int[] qty, long subtotal) {
        System.out.println("\n============================================");
        System.out.println("              STRUK PESANAN                 ");
        System.out.println("============================================");
        System.out.printf("%-18s %5s  %10s  %10s%n", "Item", "Qty", "Harga/pcs", "Total");
        System.out.println("--------------------------------------------");

        for (int i = 0; i < nomorMenu.length; i++) {
            cetakBarisPesanan(nomorMenu[i], qty[i]);
        }

        // if (jumlahItem >= 1) {
        //     cetakBarisPesanan(nomorMenu[0], qty[0]);
        // }
        // if (jumlahItem >= 2) {
        //     cetakBarisPesanan(nomorMenu[1], qty[1]);
        // }
        // if (jumlahItem >= 3) {
        //     cetakBarisPesanan(nomorMenu[2], qty[2]);
        // }
        // if (jumlahItem >= 4) {
        //     cetakBarisPesanan(nomorMenu[3], qty[3]);
        // }
        System.out.println("--------------------------------------------");
        System.out.printf("%-30s %12s%n", "Subtotal:", "Rp " + rupiah(subtotal));

        // skenario: diskon 10% jika subtotal > Rp100.000
        int diskon = 0;
        if (subtotal > 100000) {
            diskon = (int) (0.10 * subtotal);
            System.out.printf("%-30s %12s%n", "Diskon 10% (> Rp100.000):", "-Rp " + rupiah(diskon));
        }

        // skenario: Promo Beli 1 Gratis 1 minuman jika subtotal > Rp50.000
        int bonusMinuman = 0;
        if (subtotal > 50000) {
            bonusMinuman = hitungTotalMinuman(nomorMenu, qty, nomorMenu.length);
            if (bonusMinuman > 0) {
                System.out.printf("%-30s %12s%n", "Promo Minuman (> Rp50.000):", "-Rp " + rupiah(bonusMinuman));
            }
        }

        //pajak 10%
        int pajak = (int) (0.10 * subtotal);
        System.out.printf("%-30s %12s%n", "Pajak (10% dari subtotal):", "+Rp " + rupiah(pajak));

        // skenario: biaya pelayanan
        int service = 20000;
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
        System.out.println("============================================");
    }

    // format angka ke rupiah
    static String rupiah(long amount) {
        return String.format(java.util.Locale.US, "%,d", amount).replace(",", ".");
    }

    static int menuPelanggan() {
        Scanner input = new Scanner(System.in);

        boolean valid = false;
        while (!valid) {
            int menu = tampilkanMenu("pelanggan");
            System.out.print("Pilih nomor menu. Ketik 's' untuk berhenti memesan: ");
            int pilihan = input.nextInt();

            if (pilihan < 1 || pilihan > daftarMenu.length) {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            } else {
                valid = true;
                kelolaPesanan();

            }
        }

        return 0;
    }

    static void kelolaPesanan() {
        Scanner input = new Scanner(System.in);
        List<Integer> nomorMenu = new ArrayList<>();
        List<Integer> qty = new ArrayList<>();

        while (true) {
            tampilkanMenu("pelanggan");

            System.out.print("Pilih nomor menu. Ketik 's' untuk berhenti: ");
            String pilihan = input.nextLine().trim();

            // Berhenti jika user ketik 's'
            if (pilihan.equalsIgnoreCase("s")) {
                break;
            }

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
            System.out.println("Pesanan ditambahkan!");

            // Konfirmasi tambah pesanan
            if (!cekKonfirmasi("Tambah pesanan lagi?")) {
                break;
            }
        }

        input.close();

        // if (jumlahItem == 0) {
        //     System.out.println("\nTidak ada pesanan. Sampai jumpa!");
        //     return;
        // }
        int subtotal = hitungTotal(nomorMenu, qty, nomorMenu.length);
        cetakStruk(nomorMenu, qty, subtotal);
    }

    static void tambahMenu() {

        Scanner input = new Scanner(System.in);

        System.out.print("Nama menu: ");
        String namaMenu = input.nextLine().trim();

        System.out.print("Harga: ");
        int harga = input.nextInt();

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

        System.out.println("Menu berhasil ditambahkan!");
    }

    static void editMenu() {
// tampilkan menu yang ada
        tampilkanMenu("admin");
// minta input nomor menu yang ingin diedit
        Scanner input = new Scanner(System.in);
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
                System.out.print("Harga[" + menu.getHarga() + "]: ");
                String hargaBaru = input.next().trim(); // ✅ tidak ada sisa '\n'

                String pesanKonfirmasi = "Apakah Anda yakin ingin mengubah harga menu ini";

                if (cekKonfirmasi(pesanKonfirmasi)) {
                    if (!hargaBaru.isEmpty()) {
                        menu.setHarga(Long.parseLong(hargaBaru));
                    }
                    System.out.println("Menu berhasil diedit!");
                } else {
                    System.out.println("Perubahan dibatalkan.");
                }
            }
        }
    }

    static boolean cekKonfirmasi(String pesan) {
        Scanner input = new Scanner(System.in);

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
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nomor menu yang ingin dihapus/'0' untuk kembali: ");
        int noMenu = input.nextInt();
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
                    System.out.println("Menu berhasil dihapus!");
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                }
            }
        }
    }

    static int tampilkanMenuAdmin() {
        Scanner input = new Scanner(System.in);

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

            if (menu == 1) {
                // tambah menu di sini
                tambahMenu();
            } else if (menu == 2) {
                // edit menu di sini
                editMenu();
            } else if (menu == 3) {
                // hapus menu di sini
                hapusMenu();
            } else if (menu == 4) {
                tampilkanMenu("admin");
            } else if (menu == 5) {
                valid = true;
                mainMenu();
            } else if (menu == 0) {
                System.out.println("Pilihan tidak valid. Silakan coba lagi: ");
            }
        }
    }

    static int tampilkanMenuUtama() {
        // ini adalah menu yang handle kedua menu utama, pelanggan dan admin
        Scanner input = new Scanner(System.in);

        System.out.println("============================================");
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
        while (valid == false) {
            int menu = tampilkanMenuUtama();
            if (menu == 0) {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            } else if (menu == 1) {
                // menu pelanggan
                menuPelanggan();
            } else if (menu == 2) {
                // menu admin
                menuAdmin();
            } else {
                valid = true;
                System.out.println("Terima kasih! Sampai jumpa!");
                System.exit(0);
            }
        }
    }

}
